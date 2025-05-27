public class Pessoas{
    private int id;
    private String nome;
    private double CPF;
    private double dataNascimento;
    private char genero;

    public Pessoas(int id, double CPF, double dataNascimento, String nome, char genero){
        this.id = id;
        this.CPF = CPF;
        this.dataNascimento = dataNascimento;
        this.nome = nome;
        this.genero = genero;
    }

}