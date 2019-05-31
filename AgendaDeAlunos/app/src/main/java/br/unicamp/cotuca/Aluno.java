package br.unicamp.cotuca;

public class Aluno {
    private String nome;
    private String telefone;
    private String email;

    public Aluno()
    {

    }

    public Aluno (String nome, String telefone, String email)
    {
        super();
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString()
    {
        return " Nome: " + nome + "\n" +
                "Telefone: " + telefone + "\n" +
                "E-mail: " + email;
    }
}
