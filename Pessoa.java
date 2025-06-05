package modelo;

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

}
