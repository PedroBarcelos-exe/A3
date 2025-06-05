//package modelo;

public class Veiculo {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String cor;

    public Veiculo(String marca, String modelo, int ano, String cor) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }

    public Veiculo(int id, String marca, String modelo, int ano, String cor) {
        this(marca, modelo, ano, cor);
        this.id = id;
    }

}
