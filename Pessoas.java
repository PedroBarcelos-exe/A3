public class Pessoas{
    private Pessoas[] pessoas = new Pessoas[100];
    private int id;
    private String nome;
    private double CPF;
    private double dataNascimento;
    private char genero;

    public void Pessoas(int novoID, double novoCPF, double novaDatadeNascimento, String novoNome, char novoGenero){
        this.id = novoID;
        this.CPF = novoCPF;
        this.dataNascimento = novaDatadeNascimento;
        this.nome = novoNome;
        this.genero = novoGenero;
    }
}