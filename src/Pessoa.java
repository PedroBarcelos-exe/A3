import java.time.LocalDate; 


public class Pessoa {
    
    private int id; 
    private String nome;
    private String cpf; 
    private LocalDate dataNascimento; 
    private char genero; 

    public Pessoa(String nome, String cpf, LocalDate dataNascimento, char genero) {
        this.nome = nome; 
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

   
    public Pessoa(int id, String nome, String cpf, LocalDate dataNascimento, char genero) {
        this(nome, cpf, dataNascimento, genero);
        this.id = id; 
    }

    
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public char getGenero() { return genero; }

   
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setGenero(char genero) { this.genero = genero; }

   
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Data de Nascimento: " + dataNascimento + ", Gênero: " + genero;
    }

    
    public static boolean isValidCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}