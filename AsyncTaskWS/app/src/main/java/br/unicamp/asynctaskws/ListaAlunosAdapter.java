package br.unicamp.asynctaskws;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListaAlunosAdapter extends ArrayAdapter<Aluno> {

    private Context context;
    private ArrayList<Aluno> listaDeAlunos = null;

    public ListaAlunosAdapter(@NonNull Context context, @NonNull ArrayList<Aluno> listaDeAlunos) {
        super(context, 0, listaDeAlunos);
        this.context = context;
        this.listaDeAlunos = listaDeAlunos;
    }

    public View getView(int position, View view, ViewGroup parent){

        Aluno aluno = listaDeAlunos.get(position);

        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.lista_itens,null);

        TextView textViewRA = view.findViewById(R.id.text_view_RA);
        textViewRA.setText(aluno.getRA());

        TextView textViewNome = view.findViewById(R.id.text_view_nome);
        textViewNome.setText(aluno.getNome());

        TextView textViewEmail = view.findViewById(R.id.text_view_correio);
        textViewEmail.setText(aluno.getCorreio());

        return view;

    }

}
