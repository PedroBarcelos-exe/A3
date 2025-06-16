import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.SQLException;

public class Main {
    // Declaração e inicialização de objetos estáticos e finais para DAOs e Scanner,
    // que serão usados em toda a aplicação.
    private static final Scanner sc = new Scanner(System.in);
    private static final PessoaDAO pessoaDAO = new PessoaDAO();
    private static final VeiculoDAO veiculoDAO = new VeiculoDAO();
    private static final TransferenciaDAO transferenciaDAO = new TransferenciaDAO();

    public static void main(String[] args) {
        // Testa a conexão com o banco de dados no início da aplicação.
        mysql.testConnection();
        System.out.println("\n");

        int num;
        // Loop principal do menu, continua até que o usuário digite 0 para sair.
        do {
            System.out.println("=================================================");
            System.out.println("MENU PRINCIPAL");
            System.out.println("1. Cadastrar Pessoa/Veículo");
            System.out.println("2. Transferir veículo");
            System.out.println("3. Consultar informações");
            System.out.println("4. Relatórios Gerenciais");
            System.out.println("5. Baixa de Veículo");
            System.out.println("===============================================");
            System.out.print("Digite o número 0 para sair: ");

            try {
                num = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha deixada pelo nextInt()

                // Estrutura de controle para navegar entre as opções do menu principal.
                switch (num) {
                    case 1: menuCadastro(); break; // Chama o menu de cadastro.
                    case 2: transferirVeiculo(); break; // Chama a função de transferência de veículo.
                    case 3: menuConsulta(); break; // Chama o menu de consulta.
                    case 4: menuRelatorios(); break; // Chama o menu de relatórios.
                    case 5: baixaVeiculo(); break; // Chama a função de baixa de veículo.
                    case 0: System.out.println("Saindo do sistema. Até mais!"); break; // Mensagem de saída.
                    default: System.out.println("Opção inválida. Por favor, escolha uma opção entre 0 e 5."); break; // Opção inválida.
                }
            } catch (InputMismatchException e) {
                // Captura exceção para entrada inválida (não numérica).
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine(); // Limpa o buffer do scanner.
                num = -1; // Define num como -1 para que o loop continue.
            }
        } while (num != 0);

        sc.close(); // Fecha o scanner ao sair da aplicação.
    }

    // Método para exibir o menu de cadastro e gerenciar as opções.
    private static void menuCadastro() {
        int subNum;
        // Loop do menu de cadastro.
        do {
            System.out.println("\n--- MENU DE CADASTRO ---");
            System.out.println("1. Cadastrar Pessoa");
            System.out.println("2. Cadastrar Veículo");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                subNum = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha.

                // Navega entre as opções do menu de cadastro.
                switch (subNum) {
                    case 1: cadastrarPessoa(); break; // Chama a função de cadastro de pessoa.
                    case 2: cadastrarVeiculo(); break; // Chama a função de cadastro de veículo.
                    case 0: System.out.println("Voltando ao Menu Principal..."); break; // Volta ao menu anterior.
                    default: System.out.println("Opção inválida. Tente novamente."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine(); // Limpa o buffer do scanner.
                subNum = -1; // Define subNum como -1 para que o loop continue.
            }
        } while (subNum != 0);
    }

    // Método para cadastrar uma nova pessoa.
    private static void cadastrarPessoa() {
        System.out.println("\n--- CADASTRO DE PESSOA ---");
        System.out.print("Nome: ");
        String nome = sc.nextLine();

        String cpf;
        // Loop para validar o CPF e verificar duplicidade.
        while (true) {
            System.out.print("CPF (apenas números, 11 dígitos): ");
            cpf = sc.nextLine();
            if (!Pessoa.isValidCpf(cpf)) { // Valida o formato do CPF.
                System.out.println("CPF inválido. Deve conter exatamente 11 dígitos numéricos.");
            } else {
                try {
                    if (pessoaDAO.findByCpf(cpf) != null) { // Verifica se o CPF já existe no banco.
                        System.out.println("Erro: Já existe uma pessoa cadastrada com este CPF.");
                    } else {
                        break; // Sai do loop se o CPF for válido e não duplicado.
                    }
                } catch (SQLException e) {
                    System.err.println("Erro ao verificar CPF no banco de dados: " + e.getMessage());
                    return; // Sai do método em caso de erro no banco.
                }
            }
        }

        LocalDate dataNascimento = null;
        // Loop para validar o formato da data de nascimento.
        while (dataNascimento == null) {
            System.out.print("Data de Nascimento (YYYY-MM-DD): ");
            String dataStr = sc.nextLine();
            try {
                dataNascimento = LocalDate.parse(dataStr); // Converte a string para LocalDate.
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            }
        }

        char genero;
        // Loop para validar a entrada do gênero.
        while (true) {
            System.out.print("Gênero (M/F/O para Outro): ");
            String generoStr = sc.nextLine().toUpperCase();
            if (generoStr.length() == 1 && (generoStr.charAt(0) == 'M' || generoStr.charAt(0) == 'F' || generoStr.charAt(0) == 'O')) {
                genero = generoStr.charAt(0);
                break; // Sai do loop se o gênero for válido.
            } else {
                System.out.println("Gênero inválido. Digite M, F ou O.");
            }
        }

        Pessoa novaPessoa = new Pessoa(nome, cpf, dataNascimento, genero); // Cria um novo objeto Pessoa.
        try {
            int newId = pessoaDAO.insert(novaPessoa); // Insere a pessoa no banco de dados.
            System.out.println("Pessoa cadastrada com sucesso! ID: " + newId);
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar pessoa no banco de dados: " + e.getMessage());
        }
    }

    // Método para cadastrar um novo veículo.
    private static void cadastrarVeiculo() {
        System.out.println("\n--- CADASTRO DE VEÍCULO ---");
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Modelo: ");
        String modelo = sc.nextLine();

        int ano = 0;
        // Loop para validar o ano do veículo.
        while (true) {
            System.out.print("Ano: ");
            try {
                ano = sc.nextInt();
                // Validação do ano (exemplo: entre 1900 e o ano atual + 1).
                if (ano < 1900 || ano > LocalDate.now().getYear() + 1) {
                    System.out.println("Ano inválido. Por favor, digite um ano válido.");
                } else {
                    break; // Sai do loop se o ano for válido.
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número inteiro para o ano.");
                sc.nextLine(); // Limpa o buffer do scanner.
            } finally {
                sc.nextLine(); // Garante que a linha restante seja consumida.
            }
        }

        System.out.print("Cor: ");
        String cor = sc.nextLine();

        Pessoa proprietario = null;
        // Loop para garantir que um proprietário válido seja encontrado.
        while (proprietario == null) {
            System.out.print("CPF do proprietário (para vincular o veículo): ");
            String cpfProprietario = sc.nextLine();
            try {
                proprietario = pessoaDAO.findByCpf(cpfProprietario); // Busca o proprietário pelo CPF.
                if (proprietario == null) {
                    System.out.println("Proprietário não encontrado. Por favor, cadastre a pessoa primeiro ou digite um CPF válido.");
                }
            } catch (SQLException e) {
                System.err.println("Erro ao buscar proprietário por CPF no banco de dados: " + e.getMessage());
                return; // Sai do método em caso de erro no banco.
            }
        }

        Veiculo novoVeiculo = new Veiculo(marca, modelo, ano, cor); // Cria um novo objeto Veiculo.
        try {
            int idVeiculo = veiculoDAO.insert(novoVeiculo); // Insere o veículo no banco de dados.
            novoVeiculo.setId(idVeiculo); // Define o ID gerado para o objeto veículo.

            String placaMercosul = Veiculo.generateMercosulPlate(); // Gera uma nova placa Mercosul.

            // Cria a primeira transferência para o veículo (emplacamento inicial).
            Transferencia primeiraTransferencia = new Transferencia(
                novoVeiculo.getId(),
                proprietario.getId(),
                null, // Não há vendedor no primeiro emplacamento.
                null, // Não há placa anterior no primeiro emplacamento.
                placaMercosul,
                LocalDate.now() // Data atual do emplacamento.
            );

            transferenciaDAO.insert(primeiraTransferencia); // Insere a primeira transferência no banco.

            System.out.println("Veículo cadastrado e emplacado com sucesso! Placa Mercosul: " + placaMercosul + ", ID Veículo: " + idVeiculo);

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar veículo ou realizar primeiro emplacamento: " + e.getMessage());
        }
    }

    // Método para realizar a transferência de um veículo.
    private static void transferirVeiculo() {
        System.out.println("\n--- TRANSFERÊNCIA DE VEÍCULO ---");
        System.out.print("Digite a placa atual do veículo a ser transferido: ");
        String placaAtualInput = sc.nextLine().toUpperCase();

        try {
            // Busca o veículo pela placa atual.
            Veiculo veiculo = veiculoDAO.findByPlacaAtual(placaAtualInput);
            if (veiculo == null) {
                System.out.println("Veículo com a placa " + placaAtualInput + " não encontrado ou não é a placa atual de um veículo no sistema.");
                return;
            }

            // Busca a última transferência para obter o proprietário atual (vendedor).
            Transferencia ultimaTransferencia = transferenciaDAO.findLastTransferencia(veiculo.getId());
            if (ultimaTransferencia == null) {
                System.out.println("Erro: Não foi possível encontrar a última transferência para este veículo. Verifique o cadastro do veículo.");
                return;
            }
            Pessoa vendedor = pessoaDAO.findById(ultimaTransferencia.getIdPessoaCompra());
            if (vendedor == null) {
                System.out.println("Erro: Proprietário atual (vendedor) não encontrado para o veículo. A transferência não pode prosseguir.");
                return;
            }

            System.out.println("Veículo encontrado: " + veiculo.getMarca() + " " + veiculo.getModelo() + " (Placa atual: " + veiculo.getPlacaAtual() + ")");
            System.out.println("Proprietário atual (Vendedor): " + vendedor.getNome() + " (CPF: " + vendedor.getCpf() + ")");

            System.out.print("Digite o CPF do novo proprietário (Comprador): ");
            String cpfComprador = sc.nextLine();
            Pessoa comprador = pessoaDAO.findByCpf(cpfComprador); // Busca o novo proprietário.

            if (comprador == null) {
                System.out.println("Novo proprietário (Comprador) não encontrado. Por favor, cadastre a pessoa primeiro.");
                return;
            }

            // Verifica se o comprador é diferente do vendedor.
            if (comprador.getId() == vendedor.getId()) {
                System.out.println("Erro: O comprador não pode ser o mesmo que o vendedor. Transferência inválida.");
                return;
            }

            String novaPlaca;
            // Verifica se a placa atual está no formato antigo e a converte, se necessário.
            if (Veiculo.isOldPlateFormat(veiculo.getPlacaAtual())) {
                novaPlaca = Veiculo.convertOldPlateToMercosul(veiculo.getPlacaAtual());
                System.out.println("Placa antiga detectada. Convertendo para Mercosul: " + veiculo.getPlacaAtual() + " -> " + novaPlaca);
            } else {
                novaPlaca = Veiculo.generateMercosulPlate();
                System.out.println("Placa já está no padrão Mercosul. Gerando uma nova aleatoriamente: " + novaPlaca);
            }

            // Atualiza o status da última transferência para 'Antiga'.
            transferenciaDAO.updateStatusOfCurrentTransfer(veiculo.getId(), "A");

            // Cria a nova transferência.
            Transferencia novaTransferencia = new Transferencia(
                veiculo.getId(),
                comprador.getId(),
                vendedor.getId(),
                veiculo.getPlacaAtual(), // A placa atual do veículo será a placa anterior da nova transferência.
                novaPlaca,
                LocalDate.now()
            );

            transferenciaDAO.insert(novaTransferencia); // Insere a nova transferência no banco.
            System.out.println("Transferência realizada com sucesso para o veículo com a placa " + novaPlaca + "!");

        } catch (SQLException e) {
            System.err.println("Erro ao transferir veículo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na conversão da placa: " + e.getMessage());
        }
    }

    // Método para exibir o menu de consulta e gerenciar as opções.
    private static void menuConsulta() {
        int subNum;
        // Loop do menu de consulta.
        do {
            System.out.println("\n--- MENU DE CONSULTA ---");
            System.out.println("1. Consultar Veículo por Placa");
            System.out.println("2. Consultar Veículos por Proprietário (CPF)");
            System.out.println("3. Consultar Histórico de Transferências de Veículo");
            System.out.println("4. Consultar Veículos com Placa no Modelo Antigo");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                subNum = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha.

                // Navega entre as opções do menu de consulta.
                switch (subNum) {
                    case 1: consultarVeiculoPorPlaca(); break; // Consulta veículo por placa.
                    case 2: consultarVeiculosPorProprietario(); break; // Consulta veículos por CPF do proprietário.
                    case 3: consultarHistoricoTransferencias(); break; // Consulta histórico de transferências de um veículo.
                    case 4: consultarVeiculosPlacaAntiga(); break; // Consulta veículos com placa antiga.
                    case 0: System.out.println("Voltando ao Menu Principal..."); break; // Volta ao menu anterior.
                    default: System.out.println("Opção inválida. Tente novamente."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine(); // Limpa o buffer do scanner.
                subNum = -1; // Define subNum como -1 para que o loop continue.
            }
        } while (subNum != 0);
    }

    // Método para consultar detalhes de um veículo pela placa atual.
    private static void consultarVeiculoPorPlaca() {
        System.out.println("\n--- CONSULTAR VEÍCULO POR PLACA ---");
        System.out.print("Digite a placa do veículo: ");
        String placa = sc.nextLine().toUpperCase();

        try {
            Veiculo veiculo = veiculoDAO.findByPlacaAtual(placa); // Busca o veículo pela placa.
            if (veiculo != null) {
                System.out.println("Detalhes do Veículo:");
                System.out.println("ID: " + veiculo.getId());
                System.out.println("Marca: " + veiculo.getMarca());
                System.out.println("Modelo: " + veiculo.getModelo());
                System.out.println("Ano: " + veiculo.getAno());
                System.out.println("Cor: " + veiculo.getCor());
                System.out.println("Placa Atual: " + veiculo.getPlacaAtual());

                // Busca a última transferência para encontrar o proprietário atual.
                Transferencia ultimaTransferencia = transferenciaDAO.findLastTransferencia(veiculo.getId());
                if (ultimaTransferencia != null) {
                    Pessoa proprietario = pessoaDAO.findById(ultimaTransferencia.getIdPessoaCompra());
                    if (proprietario != null) {
                        System.out.println("Proprietário Atual: " + proprietario.getNome() + " (CPF: " + proprietario.getCpf() + ")");
                    } else {
                        System.out.println("Proprietário atual não encontrado no cadastro de pessoas.");
                    }
                } else {
                    System.out.println("Nenhuma transferência registrada para este veículo.");
                }

            } else {
                System.out.println("Veículo com a placa " + placa + " não encontrado ou não é a placa atual de nenhum veículo.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar veículo por placa: " + e.getMessage());
        }
    }

    // Método para consultar veículos de um determinado proprietário pelo CPF.
    private static void consultarVeiculosPorProprietario() {
        System.out.println("\n--- CONSULTAR VEÍCULOS POR PROPRIETÁRIO ---");
        System.out.print("Digite o CPF do proprietário: ");
        String cpf = sc.nextLine();

        try {
            Pessoa proprietario = pessoaDAO.findByCpf(cpf); // Busca o proprietário pelo CPF.
            if (proprietario == null) {
                System.out.println("Proprietário com CPF " + cpf + " não encontrado.");
                return;
            }

            System.out.println("Proprietário: " + proprietario.getNome() + " (ID: " + proprietario.getId() + ")");

            List<Veiculo> veiculos = veiculoDAO.findByProprietario(proprietario.getId()); // Busca veículos do proprietário.

            if (veiculos.isEmpty()) {
                System.out.println("Nenhum veículo encontrado para este proprietário.");
            } else {
                System.out.println("Veículos de " + proprietario.getNome() + ":");
                for (Veiculo v : veiculos) {
                    System.out.println("- " + v.getMarca() + " " + v.getModelo() + " (Ano: " + v.getAno() + ", Placa: " + v.getPlacaAtual() + ")");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar veículos por proprietário: " + e.getMessage());
        }
    }

    // Método para consultar o histórico de transferências de um veículo.
    private static void consultarHistoricoTransferencias() {
        System.out.println("\n--- CONSULTAR HISTÓRICO DE TRANSFERÊNCIAS DE VEÍCULO ---");
        System.out.print("Digite o ID do veículo para consultar o histórico: ");
        int idVeiculo;
        try {
            idVeiculo = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha.
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Digite um número inteiro para o ID do veículo.");
            sc.nextLine(); // Limpa o buffer do scanner.
            return;
        }

        try {
            Veiculo veiculo = veiculoDAO.findById(idVeiculo); // Busca o veículo pelo ID.
            if (veiculo == null) {
                System.out.println("Veículo com ID " + idVeiculo + " não encontrado.");
                return;
            }

            System.out.println("Histórico de Transferências para o Veículo (ID: " + veiculo.getId() + ", Modelo: " + veiculo.getModelo() + "):");
            List<Transferencia> history = transferenciaDAO.findHistoryByVehicle(idVeiculo); // Busca o histórico de transferências.

            if (history.isEmpty()) {
                System.out.println("Nenhum histórico de transferência encontrado para este veículo.");
            } else {
                // Itera sobre o histórico para exibir os detalhes de cada transferência.
                for (Transferencia t : history) {
                    Pessoa comprador = pessoaDAO.findById(t.getIdPessoaCompra());
                    Pessoa vendedor = null;
                    if (t.getIdPessoaVenda() != null) {
                        vendedor = pessoaDAO.findById(t.getIdPessoaVenda());
                    }

                    System.out.println("------------------------------------");
                    System.out.println("Data: " + t.getData());
                    System.out.println("Placa Atual (nesta etapa): " + t.getPlacaAtual());
                    System.out.println("Placa Anterior (nesta etapa): " + (t.getPlacaAnterior() != null ? t.getPlacaAnterior() : "N/A (Primeiro Emplacamento)"));
                    System.out.println("Comprador: " + (comprador != null ? comprador.getNome() : "Desconhecido") + " (ID: " + t.getIdPessoaCompra() + ")");
                    System.out.println("Vendedor: " + (vendedor != null ? vendedor.getNome() : "N/A (Primeiro Emplacamento ou Vendedor Desconhecido)"));
                    System.out.println("Status: " + (t.getStatusTransferencia().equals("N") ? "Atual" : "Anterior"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar histórico de transferências: " + e.getMessage());
        }
    }

    // Método para consultar veículos com placa no modelo antigo.
    private static void consultarVeiculosPlacaAntiga() {
        System.out.println("\n--- CONSULTAR VEÍCULOS COM PLACA NO MODELO ANTIGO ---");
        try {
            List<Veiculo> veiculosAntigos = veiculoDAO.findVehiclesWithOldPlateFormat(); // Busca veículos com placa antiga.
            if (veiculosAntigos.isEmpty()) {
                System.out.println("Nenhum veículo encontrado com placa no modelo antigo e status 'N' (atual).");
            } else {
                System.out.println("Veículos com placa no modelo antigo:");
                for (Veiculo v : veiculosAntigos) {
                    System.out.println("- " + v.getMarca() + " " + v.getModelo() + " (Ano: " + v.getAno() + ", Placa: " + v.getPlacaAtual() + ")");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar veículos com placa antiga: " + e.getMessage());
        }
    }

    // Método para exibir o menu de relatórios e gerenciar as opções.
    private static void menuRelatorios() {
        int subNum;
        // Loop do menu de relatórios.
        do {
            System.out.println("\n--- MENU DE RELATÓRIOS ---");
            System.out.println("1. Quantidade de veículos por marca");
            System.out.println("2. Veículos transferidos por período");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                subNum = sc.nextInt();
                sc.nextLine(); // Consome a quebra de linha.

                // Navega entre as opções do menu de relatórios.
                switch (subNum) {
                    case 1: relatorioVeiculosPorMarca(); break; // Gera relatório de veículos por marca.
                    case 2: relatorioVeiculosTransferidosPorPeriodo(); break; // Gera relatório de veículos transferidos por período.
                    case 0: System.out.println("Voltando ao Menu Principal..."); break; // Volta ao menu anterior.
                    default: System.out.println("Opção inválida. Tente novamente."); break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                sc.nextLine(); // Limpa o buffer do scanner.
                subNum = -1; // Define subNum como -1 para que o loop continue.
            }
        } while (subNum != 0);
    }

    // Método para gerar um relatório de quantidade de veículos por marca.
    private static void relatorioVeiculosPorMarca() {
        System.out.println("\n--- RELATÓRIO: QUANTIDADE DE VEÍCULOS POR MARCA ---");
        try {
            Map<String, Integer> counts = veiculoDAO.countByMarca(); // Obtém a contagem de veículos por marca.
            if (counts.isEmpty()) {
                System.out.println("Nenhum veículo encontrado para gerar o relatório por marca.");
            } else {
                // Exibe a contagem para cada marca.
                counts.forEach((marca, qtd) -> System.out.println("Marca: " + marca + ", Quantidade: " + qtd));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de veículos por marca: " + e.getMessage());
        }
    }

    // Método para gerar um relatório de veículos transferidos por um período.
    private static void relatorioVeiculosTransferidosPorPeriodo() {
        System.out.println("\n--- RELATÓRIO: VEÍCULOS TRANSFERIDOS POR PERÍODO ---");
        LocalDate startDate = null;
        LocalDate endDate = null;

        // Loop para obter e validar a data de início.
        while (startDate == null) {
            System.out.print("Digite a data de início (YYYY-MM-DD): ");
            String dateStr = sc.nextLine();
            try {
                startDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            }
        }

        // Loop para obter e validar a data de fim.
        while (endDate == null) {
            System.out.print("Digite a data de fim (YYYY-MM-DD): ");
            String dateStr = sc.nextLine();
            try {
                endDate = LocalDate.parse(dateStr);
                if (endDate.isBefore(startDate)) { // Valida se a data de fim é anterior à data de início.
                    System.out.println("A data de fim não pode ser anterior à data de início.");
                    endDate = null; // Reseta endDate para continuar o loop.
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use YYYY-MM-DD.");
            }
        }

        try {
            // Busca as transferências dentro do período especificado.
            List<Transferencia> transfers = transferenciaDAO.findByPeriod(startDate, endDate);
            if (transfers.isEmpty()) {
                System.out.println("Nenhuma transferência encontrada no período de " + startDate + " a " + endDate + ".");
            } else {
                System.out.println("Transferências no período de " + startDate + " a " + endDate + ":");
                // Itera sobre as transferências para exibir os detalhes.
                for (Transferencia t : transfers) {
                    Veiculo v = veiculoDAO.findById(t.getIdVeiculo());
                    Pessoa comprador = pessoaDAO.findById(t.getIdPessoaCompra());
                    Pessoa vendedor = null;
                    if (t.getIdPessoaVenda() != null) {
                        vendedor = pessoaDAO.findById(t.getIdPessoaVenda());
                    }

                    System.out.println("------------------------------------");
                    System.out.println("Data: " + t.getData());
                    System.out.println("Veículo: " + (v != null ? v.getMarca() + " " + v.getModelo() + " (ID: " + v.getId() + ")" : "ID: " + t.getIdVeiculo()));
                    System.out.println("Placa Atual: " + t.getPlacaAtual());
                    System.out.println("Comprador: " + (comprador != null ? comprador.getNome() : "Desconhecido"));
                    System.out.println("Vendedor: " + (vendedor != null ? vendedor.getNome() : "N/A"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao gerar relatório de veículos transferidos por período: " + e.getMessage());
        }
    }

    // Método para dar baixa em um veículo, removendo seu histórico de transferências.
    private static void baixaVeiculo() {
        System.out.println("\n--- BAIXA DE VEÍCULO ---");
        System.out.print("Digite o ID do veículo para dar baixa: ");
        int idVeiculo;
        try {
            idVeiculo = sc.nextInt();
            sc.nextLine(); // Consome a quebra de linha.
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Digite um número inteiro para o ID do veículo.");
            sc.nextLine(); // Limpa o buffer do scanner.
            return;
        }

        try {
            Veiculo veiculo = veiculoDAO.findById(idVeiculo); // Busca o veículo pelo ID.
            if (veiculo == null) {
                System.out.println("Veículo com ID " + idVeiculo + " não encontrado.");
                return;
            }

            // Confirmação antes de proceder com a baixa.
            System.out.print("Tem certeza que deseja dar baixa no veículo " + veiculo.getModelo() + " (ID: " + idVeiculo + ")? Esta ação removerá TODO o histórico de transferências associado. (S/N): ");
            String confirm = sc.nextLine().toUpperCase();

            if (confirm.equals("S")) {
                transferenciaDAO.deleteByVehicle(idVeiculo); // Exclui todas as transferências associadas ao veículo.
                System.out.println("Veículo com ID " + idVeiculo + " e seu histórico de transferências foram baixados/removidos com sucesso.");
            } else {
                System.out.println("Operação de baixa de veículo cancelada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao dar baixa no veículo: " + e.getMessage());
        }
    }
}