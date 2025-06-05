//package modelo;

import java.time.LocalDate;
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
}