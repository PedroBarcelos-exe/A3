import java.util.Random; 


public class Veiculo {
    private int id; // ID único do veículo
    private String marca; // Marca do veículo (ex: Fiat, Ford)
    private String modelo; // Modelo do veículo (ex: Palio, Ka)
    private int ano; // Ano de fabricação do veículo
    private String cor; // Cor do veículo
    private String placaAtual; // Placa atual do veículo (Mercosul ou Antiga)

    /**
     * Construtor para criar um novo objeto Veiculo sem um ID e sem placa inicial.
     * Utilizado principalmente para o primeiro cadastro, onde o ID é gerado pelo banco e a placa será gerada/definida posteriormente.
     * @param marca A marca do veículo.
     * @param modelo O modelo do veículo.
     * @param ano O ano de fabricação do veículo.
     * @param cor A cor do veículo.
     */
    public Veiculo(String marca, String modelo, int ano, String cor) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    /**
     * Construtor para criar um objeto Veiculo com um ID existente.
     * Usado geralmente ao recuperar dados do banco de dados, onde o ID já foi gerado.
     * @param id O ID único do veículo.
     * @param marca A marca do veículo.
     * @param modelo O modelo do veículo.
     * @param ano O ano de fabricação do veículo.
     * @param cor A cor do veículo.
     */
    public Veiculo(int id, String marca, String modelo, int ano, String cor) {
        // Chama o construtor anterior para inicializar os atributos básicos.
        this(marca, modelo, ano, cor);
        this.id = id; // Define o ID.
    }

    /**
     * Construtor completo para criar um objeto Veiculo com ID e placa atual.
     * Usado ao recuperar todos os dados de um veículo do banco de dados, incluindo sua placa atual.
     * @param id O ID único do veículo.
     * @param marca A marca do veículo.
     * @param modelo O modelo do veículo.
     * @param ano O ano de fabricação do veículo.
     * @param cor A cor do veículo.
     * @param placaAtual A placa atual do veículo.
     */
    public Veiculo(int id, String marca, String modelo, int ano, String cor, String placaAtual) {
        this(id, marca, modelo, ano, cor); // Chama o construtor que inicializa todos os atributos exceto placaAtual.
        this.placaAtual = placaAtual; // Define a placa atual.
    }

    // Métodos Getters: para acessar os valores dos atributos privados.
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getAno() { return ano; }
    public String getCor() { return cor; }
    public String getPlacaAtual() { return placaAtual; }

    // Métodos Setters: para modificar os valores dos atributos privados.
    public void setId(int id) { this.id = id; }
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAno(int ano) { this.ano = ano; }
    public void setCor(String cor) { this.cor = cor; }
    public void setPlacaAtual(String placaAtual) { this.placaAtual = placaAtual; }

    /**
     * Gera uma nova placa no padrão Mercosul (LLLNLNN).
     * @return Uma String representando uma placa aleatória no formato Mercosul.
     */
    public static String generateMercosulPlate() {
        Random random = new Random();
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digitos = "0123456789";
        StringBuilder placa = new StringBuilder(); 

        // 3 letras
        for (int i = 0; i < 3; i++) {
            placa.append(letras.charAt(random.nextInt(letras.length())));
        }
        // 1 dígito
        placa.append(digitos.charAt(random.nextInt(digitos.length())));
        // 1 letra
        placa.append(letras.charAt(random.nextInt(letras.length())));
        // 2 dígitos
        for (int i = 0; i < 2; i++) {
            placa.append(digitos.charAt(random.nextInt(digitos.length())));
        }
        return placa.toString(); 
    }

    /**
     * Converte uma placa do padrão Antigo (LLL-NNNN) para o padrão Mercosul (LLLNLNN).
     * A conversão substitui o segundo número da placa antiga por uma letra correspondente (A=0, B=1, etc.).
     * @param oldPlate A placa no formato antigo (ex: "ABC-1234").
     * @return A placa convertida para o formato Mercosul (ex: "ABC1D34").
     * @throws IllegalArgumentException Se a placa antiga não estiver no formato esperado.
     */
    public static String convertOldPlateToMercosul(String oldPlate) {
        if (oldPlate == null || !oldPlate.matches("[A-Z]{3}-\\d{4}")) {
            throw new IllegalArgumentException("Placa antiga inválida: " + oldPlate + ". Formato esperado: LLL-NNNN.");
        }
        String[] partes = oldPlate.split("-"); 
        String letras = partes[0]; 
        String numeros = partes[1]; 

        // Converte o segundo dígito numérico da placa antiga para uma letra.
        char segundoDigitoChar = numeros.charAt(1); 
        int valorNumerico = Character.getNumericValue(segundoDigitoChar); 
        char novaLetra = (char) ('A' + valorNumerico); 

        // Constrói a nova placa Mercosul: LLL + primeiro dígito + nova letra + dois últimos dígitos.
        return letras + numeros.charAt(0) + novaLetra + numeros.substring(2);
    }

    /**
     * Verifica se uma dada placa está no formato antigo (LLL-NNNN).
     * @param placa A placa a ser verificada.
     * @return true se a placa estiver no formato antigo, false caso contrário.
     */
    public static boolean isOldPlateFormat(String placa) {
        return placa != null && placa.matches("[A-Z]{3}-\\d{4}");
    }

    /**
     * Verifica se uma dada placa está no formato Mercosul (LLLNLNN).
     * @param placa A placa a ser verificada.
     * @return true se a placa estiver no formato Mercosul, false caso contrário.
     */
    public static boolean isMercosulPlateFormat(String placa) {
        return placa != null && placa.matches("[A-Z]{3}\\d[A-Z]\\d{2}");
    }

    /**
     * Retorna uma representação em String do objeto Veiculo.
     * Inclui informações sobre ID, marca, modelo, ano, cor e placa atual.
     * @return Uma String contendo os detalhes do veículo.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Marca: " + marca + ", Modelo: " + modelo + ", Ano: " + ano + ", Cor: " + cor + ", Placa: " + (placaAtual != null ? placaAtual : "N/A");
    }
}