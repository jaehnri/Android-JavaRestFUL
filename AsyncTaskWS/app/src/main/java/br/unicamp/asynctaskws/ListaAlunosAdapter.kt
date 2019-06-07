package br.unicamp.asynctaskws

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

import java.util.ArrayList

class ListaAlunosAdapter(private val context: Context, listaDeAlunos: ArrayList<Aluno>) : ArrayAdapter<Aluno>(context, 0, listaDeAlunos) {
    private val listaDeAlunos: ArrayList<Aluno>? = null

    init {
        this.listaDeAlunos = listaDeAlunos
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        var view = view

        val aluno = listaDeAlunos!![position]

        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lista_itens, null)

        val textViewRA = view!!.findViewById<TextView>(R.id.text_view_RA)
        textViewRA.text = aluno.ra

        val textViewNome = view.findViewById<TextView>(R.id.text_view_nome)
        textViewNome.text = aluno.nome

        val textViewEmail = view.findViewById<TextView>(R.id.text_view_correio)
        textViewEmail.text = aluno.correio

        return view

    }

}
