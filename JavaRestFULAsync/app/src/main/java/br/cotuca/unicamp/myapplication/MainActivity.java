package br.cotuca.unicamp.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textViewTarefa;
    ProgressBar progressBar;
    List<Aluno> alunoList;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTarefa = (TextView) findViewById(R.id.tvTarefa);
        textViewTarefa.setMovementMethod(new ScrollingMovementMethod());
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        alunoList = new ArrayList<>();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tarefa ,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.iniciar_tarefa){
            if (isOnline()){
                //buscarDados();
                buscarDados("http://177.220.18.71:8080/restful-webproject/webresources/generic/GetAlunos");
                Toast.makeText(this, "Rede não está disponível",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Rede não está disponível",
                        Toast.LENGTH_LONG).show();
            }
        }
        if(item.getItemId() == R.id.limpar_tarefas){
            textViewTarefa.setText("");
            progressBar.setVisibility(View.INVISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }


    private void buscarDados(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }
    protected void atualizarView(String message){
        textViewTarefa.append(message + "\n");
    }

    protected void atualizarView(){
        if (alunoList != null){
            for (Aluno aluno: alunoList) {
                textViewTarefa.append(aluno.getNome() + "\n");
            }
        }
    }

    protected boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            atualizarView("Tarefa Iniciada");
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String... params) {

            String conteudo = HttpManager.getDados(params[0]);
            return conteudo;
        }

        @Override
        protected void onPostExecute(String s) {
            //atualizarView(s);
            //produtoList = ProdutoXMLParser.parseDados(s);
            //alunoList = AlunoJsonParser.parseComidaJson(s);
            alunoList = AlunoXMLParser.parseDados(s);
            atualizarView();
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //atualizarView(values[0]);
        }
    }
}
