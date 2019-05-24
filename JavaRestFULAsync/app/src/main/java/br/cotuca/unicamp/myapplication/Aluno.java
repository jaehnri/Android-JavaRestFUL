package br.cotuca.unicamp.myapplication;

public class Aluno {

    private String RA;
    private String nome;
    private String correio;

    public Aluno() {
        this.RA = null;
        this.nome = null;
        this.correio = null;
    }

    public Aluno(String RA, String Nome, String Mail) {
        this.RA = RA;
        this.nome = Nome;
        this.correio = Mail;
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String RA) {
        this.RA = RA;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String Nome) {
        this.nome = Nome;
    }

    public String getCorreio() {
        return this.correio;
    }

    public void setCorreio(String Correio) {
        this.correio = Correio;
    }

    @Override
    public String toString() {
        return "\nAluno:\n - RA: " + this.RA + "\n - Nome: " + this.nome + "\n - E-mail: " + this.correio + "\n";
    }
}
