public class Carros {
    private Carros[] carros = new Carros[100];
    private int id;
    private String marca;
    private String cor;
    private int ano;


    public void Carros(int novoID, int novoAno,String novaMarca, String novaCor){
        this.id = novoID;
        this.ano = novoAno;
        this.marca = novaMarca;
        this.cor = novaCor;
    }

    
}
