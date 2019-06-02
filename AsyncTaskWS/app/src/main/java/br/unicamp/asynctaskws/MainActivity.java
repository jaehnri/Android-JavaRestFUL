package br.unicamp.asynctaskws;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView    tvTarefa;
    ProgressBar progressBar;
    List<Aluno> alunoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTarefa    = findViewById(R.id.tvTarefa);
        tvTarefa.setMovementMethod(new ScrollingMovementMethod());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        alunoList = new ArrayList<>();
    }

    private boolean isOnline() {
        ConnectivityManager cm  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;

    }

    protected void atualizarView(){
        if (alunoList != null) {
            for (Aluno aluno: alunoList) {
                tvTarefa.append(aluno.getRA() + "\n");
                tvTarefa.append(aluno.getNome() + "\n");
                tvTarefa.append(aluno.getCorreio() + "\n");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iniciar_tarefa) {
            /*MinhaAsyncTask task = new MinhaAsyncTask();
            String[] tasks = new String[10];
            for (int i=0; i<10; i++) {
                tasks[i] = "Task " + i;
            }
            task.execute(tasks);*/

            if (isOnline()) {
                //buscarDados("http://177.220.18.68:3000/api/produtos/xml");
                //buscarDados("http://192.168.0.16:3000/api/produtos/json");
                buscarDados("http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunos");
            } else {
                Toast.makeText(this, "Rede não está disponível", Toast.LENGTH_SHORT).show();
            }
        }

        if (item.getItemId() == R.id.limpar_tarefas) {
            tvTarefa.setText("");
            progressBar.setVisibility(View.INVISIBLE);
        }

        return super.onOptionsItemSelected(item);
    }

    private void buscarDados(String uri) {
        MinhaAsyncTask task = new MinhaAsyncTask();
        progressBar.setVisibility(View.VISIBLE);
        task.execute(uri);
    }


    private class MinhaAsyncTask extends AsyncTask<String, String, String>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground (String... params) {

            String conteudo = HttpManager.getDados(params[0]);
            return conteudo;
        }

        @Override
        protected void onPostExecute(String s) {
            //atualizarView(s);
            alunoList = AlunoJsonParser.parseDados(s);
            atualizarView();
            progressBar.setVisibility(View.INVISIBLE);
        }

        protected void onProgressUpdate(String... values) {
            //atualizarView(values[0]);
        }
    }

}
