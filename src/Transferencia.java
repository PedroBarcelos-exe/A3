import java.time.LocalDate; 


public class Transferencia {
    // Atributos da classe Transferencia
    private int id; // ID único da transferência
    private int idVeiculo; // ID do veículo envolvido na transferência
    private int idPessoaCompra; // ID da pessoa que está comprando o veículo
    private Integer idPessoaVenda; // ID da pessoa que está vendendo o veículo (pode ser nulo para primeiro emplacamento)
    private String placaAnterior; // Placa anterior do veículo (pode ser nulo para primeiro emplacamento)
    private String placaAtual; // Placa atual do veículo após a transferência
    private String statusTransferencia; // Status da transferência ('N' para Nova/Atual, 'A' para Antiga)
    private LocalDate data; // Data em que a transferência ocorreu

    /**
     * Construtor para criar um novo objeto Transferencia sem um ID.
     * Usado para registrar uma nova transferência onde o ID é gerado pelo banco de dados.
     * O status da transferência é inicializado como 'N' (Nova).
     * @param idVeiculo O ID do veículo que está sendo transferido.
     * @param idPessoaCompra O ID da pessoa que está adquirindo o veículo.
     * @param idPessoaVenda O ID da pessoa que está vendendo o veículo. Pode ser null se for um primeiro emplacamento.
     * @param placaAnterior A placa do veículo antes da transferência. Pode ser null se for um primeiro emplacamento.
     * @param placaAtual A nova placa do veículo após a transferência.
     * @param data A data em que a transferência ocorreu.
     */
    public Transferencia(int idVeiculo, int idPessoaCompra, Integer idPessoaVenda,
                         String placaAnterior, String placaAtual, LocalDate data) {
        this.idVeiculo = idVeiculo;
        this.idPessoaCompra = idPessoaCompra;
        this.idPessoaVenda = idPessoaVenda;
        this.placaAnterior = placaAnterior;
        this.placaAtual = placaAtual;
        this.data = data;
        this.statusTransferencia = "N"; // 'N' para Nova, indicando que esta é a transferência atual.
    }

    /**
     * Construtor para criar um objeto Transferencia com um ID existente e status.
     * Usado geralmente ao recuperar dados do banco de dados.
     * @param id O ID único da transferência.
     * @param idVeiculo O ID do veículo envolvido na transferência.
     * @param idPessoaCompra O ID da pessoa que comprou o veículo.
     * @param idPessoaVenda O ID da pessoa que vendeu o veículo. Pode ser null.
     * @param placaAnterior A placa anterior do veículo. Pode ser null.
     * @param placaAtual A placa atual do veículo.
     * @param statusTransferencia O status da transferência ('N' para Nova/Atual, 'A' para Antiga).
     * @param data A data da transferência.
     */
    public Transferencia(int id, int idVeiculo, int idPessoaCompra, Integer idPessoaVenda,
                         String placaAnterior, String placaAtual, String statusTransferencia,
                         LocalDate data) {
        this(idVeiculo, idPessoaCompra, idPessoaVenda, placaAnterior, placaAtual, data);
        this.id = id; 
        this.statusTransferencia = statusTransferencia; 
    }

    // Métodos Getters: para acessar os valores dos atributos privados.
    public int getId() { return id; }
    public int getIdVeiculo() { return idVeiculo; }
    public int getIdPessoaCompra() { return idPessoaCompra; }
    public Integer getIdPessoaVenda() { return idPessoaVenda; } // Retorna Integer para permitir null
    public String getPlacaAnterior() { return placaAnterior; }
    public String getPlacaAtual() { return placaAtual; }
    public String getStatusTransferencia() { return statusTransferencia; }
    public LocalDate getData() { return data; }

    // Métodos Setters: para modificar os valores dos atributos privados.
    public void setId(int id) { this.id = id; }
    public void setStatusTransferencia(String statusTransferencia) { this.statusTransferencia = statusTransferencia; }

    /**
     * Retorna uma representação em String do objeto Transferencia.
     * Inclui informações sobre o ID, IDs das pessoas envolvidas, placas e data.
     * @return Uma String contendo os detalhes da transferência.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", ID Veículo: " + idVeiculo + ", ID Comprador: " + idPessoaCompra +
                ", ID Vendedor: " + (idPessoaVenda != null ? idPessoaVenda : "N/A") + // Exibe "N/A" se idPessoaVenda for null
                ", Placa Anterior: " + (placaAnterior != null ? placaAnterior : "N/A") + // Exibe "N/A" se placaAnterior for null
                ", Placa Atual: " + placaAtual + ", Status: " + statusTransferencia + 
                ", Data: " + data;
    }
}