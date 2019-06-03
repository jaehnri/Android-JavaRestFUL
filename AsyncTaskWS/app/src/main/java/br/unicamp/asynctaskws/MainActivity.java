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
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String QualMetodo = "";

    ProgressBar      progressBar;
    ListView         lvAlunos;
    ArrayList<Aluno> alunosList;
    Button           btnConsultarTodos;
    Button           btnConsultarRA;
    TextView         edRA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAlunos = findViewById(R.id.lvAlunos);
        alunosList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        edRA = findViewById(R.id.edRA);

        btnConsultarTodos = findViewById(R.id.btnConsultarTodos);
        btnConsultarTodos.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isOnline())
                {
                    QualMetodo = "1";
                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo);
                }
            }
        });

        btnConsultarRA = findViewById(R.id.btnConsultarRA);
        btnConsultarRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edRA.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(),"RA não pode ser nulo!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (edRA.getText().toString().length() != 5 )
                {
                    Toast.makeText(getApplicationContext(),"RA inválido!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isOnline())
                {
                    QualMetodo = "2";
                    String RA = edRA.getText().toString();
                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo, RA);
                }
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;

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
        protected String doInBackground (String... params)
        {

            Cliente cliente = new Cliente();
            String conteudo = "";

                if (params[0].equals("1"))
                {
                    try
                    {
                        conteudo = cliente.getAlunos();
                        return conteudo;
                    }
                    catch (Exception ex)
                    {
                        return ex.getMessage();
                    }
                }

                if (params[0].equals("2"))
                {
                    try
                    {
                        conteudo = cliente.getAlunoRA(params[1]);
                        return conteudo;
                    }
                    catch (Exception ex)
                    {
                        return ex.getMessage();
                    }
                }
                return conteudo;
            }

        @Override
        protected void onPostExecute(String s) {
            //atualizarView(s);
            alunosList = AlunoJsonParser.parseDados(s);
            ListaAlunosAdapter alunosAdapter = new ListaAlunosAdapter(MainActivity.this, alunosList);
            lvAlunos.setAdapter(alunosAdapter);
            //atualizarView();
            progressBar.setVisibility(View.INVISIBLE);
        }

        protected void onProgressUpdate(String... values) {
            //atualizarView(values[0]);
        }
    }

}
