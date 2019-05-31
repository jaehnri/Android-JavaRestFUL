package br.unicamp.cotuca;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabInclusao;
    ListView listView;
    ArrayList<Aluno> alunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.lvAgenda);
        alunos = new ArrayList<>();

        fabInclusao = (FloatingActionButton)findViewById(R.id.fabInclusao);
        fabInclusao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intentInclusao = new Intent(MainActivity.this, Inclusao.class);
            startActivityForResult(intentInclusao, 1);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (1): {
                if (resultCode == MainActivity.RESULT_OK) {
                    String nomeRecebido     = data.getStringExtra("nome");
                    String telefoneRecebido = data.getStringExtra("telefone");
                    String emailRecebido    = data.getStringExtra("email");

                    adicionarAluno(nomeRecebido, telefoneRecebido, emailRecebido);

                    ListaAlunosAdapter alunosAdapter = new ListaAlunosAdapter(this, alunos);
                   // ArrayAdapter<Aluno> alunosAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
                    listView.setAdapter(alunosAdapter);
                    break;
                }
            }
        }
    }

    private List<Aluno> adicionarAluno(String nome, String telefone, String email)
    {
        alunos.add(criarAluno(nome, telefone, email));
        return alunos;
    }
    private Aluno criarAluno (String nome, String telefone, String email)
    {
        Aluno aluno = new Aluno(nome, telefone, email);
        return aluno;
    }
}
