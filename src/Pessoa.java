import java.time.LocalDate; 


public class Pessoa {
    // Atributos da classe Pessoa
    private int id; // ID único da pessoa
    private String nome; // Nome completo da pessoa
    private String cpf; // CPF da pessoa (Cadastro de Pessoas Físicas), deve ser único
    private LocalDate dataNascimento; // Data de nascimento da pessoa
    private char genero; // Gênero da pessoa (ex: 'M' para Masculino, 'F' para Feminino)

    /**
     * Construtor para criar um novo objeto Pessoa sem um ID.
     * Ideal para cadastros iniciais onde o ID é gerado pelo banco de dados.
     * @param nome O nome completo da pessoa.
     * @param cpf O CPF da pessoa.
     * @param dataNascimento A data de nascimento da pessoa.
     * @param genero O gênero da pessoa ('M' ou 'F').
     */
    public Pessoa(String nome, String cpf, LocalDate dataNascimento, char genero) {
        this.nome = nome; 
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    /**
     * Construtor para criar um objeto Pessoa com um ID existente.
     * Usado geralmente ao recuperar dados do banco de dados.
     * @param id O ID único da pessoa.
     * @param nome O nome completo da pessoa.
     * @param cpf O CPF da pessoa.
     * @param dataNascimento A data de nascimento da pessoa.
     * @param genero O gênero da pessoa ('M' ou 'F').
     */
    public Pessoa(int id, String nome, String cpf, LocalDate dataNascimento, char genero) {
        this(nome, cpf, dataNascimento, genero); // Chama o construtor anterior para inicializar os atributos básicos.
        this.id = id; // Define o ID.
    }

    // Métodos Getters: para acessar os valores dos atributos privados.
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public char getGenero() { return genero; }

    // Métodos Setters: para modificar os valores dos atributos privados.
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public void setGenero(char genero) { this.genero = genero; }

    /**
     * Retorna uma representação em String do objeto Pessoa.
     * @return Uma String contendo os detalhes da pessoa.
     */
    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Data de Nascimento: " + dataNascimento + ", Gênero: " + genero;
    }

    /**
     * Valida se um CPF está no formato correto (11 dígitos numéricos).
     * Esta é uma validação de formato básico e não verifica a validade matemática do CPF.
     * @param cpf O CPF a ser validado.
     * @return true se o CPF for não nulo e contiver exatamente 11 dígitos numéricos, false caso contrário.
     */
    public static boolean isValidCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }
}