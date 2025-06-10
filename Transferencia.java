import java.time.LocalDate; 


public class Transferencia {
    private int id; 
    private int idVeiculo; 
    private int idPessoaCompra; 
    private Integer idPessoaVenda; 
    private String placaAnterior; 
    private String placaAtual; 
    private String statusTransferencia; 
    private LocalDate data;

    
    public Transferencia(int idVeiculo, int idPessoaCompra, Integer idPessoaVenda,
                         String placaAnterior, String placaAtual, LocalDate data) {
        this.idVeiculo = idVeiculo;
        this.idPessoaCompra = idPessoaCompra;
        this.idPessoaVenda = idPessoaVenda;
        this.placaAnterior = placaAnterior;
        this.placaAtual = placaAtual;
        this.data = data;
        this.statusTransferencia = "N"; 
    }

    
    public Transferencia(int id, int idVeiculo, int idPessoaCompra, Integer idPessoaVenda,
                         String placaAnterior, String placaAtual, String statusTransferencia,
                         LocalDate data) {
        this(idVeiculo, idPessoaCompra, idPessoaVenda, placaAnterior, placaAtual, data);
        this.id = id; 
        this.statusTransferencia = statusTransferencia; 
    }

    
    public int getId() { return id; }
    public int getIdVeiculo() { return idVeiculo; }
    public int getIdPessoaCompra() { return idPessoaCompra; }
    public Integer getIdPessoaVenda() { return idPessoaVenda; } 
    public String getPlacaAnterior() { return placaAnterior; }
    public String getPlacaAtual() { return placaAtual; }
    public String getStatusTransferencia() { return statusTransferencia; }
    public LocalDate getData() { return data; }

    
    public void setId(int id) { this.id = id; }
    public void setStatusTransferencia(String statusTransferencia) { this.statusTransferencia = statusTransferencia; }

    
    @Override
    public String toString() {
        return "ID: " + id + ", ID Veículo: " + idVeiculo + ", ID Comprador: " + idPessoaCompra +
                ", ID Vendedor: " + (idPessoaVenda != null ? idPessoaVenda : "N/A") +
                ", Placa Anterior: " + (placaAnterior != null ? placaAnterior : "N/A") +
                ", Placa Atual: " + placaAtual + ", Status: " + statusTransferencia + ", Data: " + data;
    }
}