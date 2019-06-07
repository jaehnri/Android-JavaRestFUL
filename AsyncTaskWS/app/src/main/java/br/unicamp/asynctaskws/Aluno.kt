package br.unicamp.asynctaskws

class Aluno {

    var ra: String? = null
    var nome: String? = null
    var correio: String? = null

    constructor() {
        this.ra = null
        this.nome = null
        this.correio = null
    }

    constructor(RA: String, Nome: String, Mail: String) {
        this.ra = RA
        this.nome = Nome
        this.correio = Mail
    }

    override fun toString(): String {
        return "\nAluno:\n - RA: " + this.ra + "\n - Nome: " + this.nome + "\n - E-mail: " + this.correio + "\n"
    }
}
