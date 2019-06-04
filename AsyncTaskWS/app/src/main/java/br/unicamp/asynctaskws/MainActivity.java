package br.unicamp.asynctaskws;

import android.app.Activity;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public String QualMetodo = "";

    ProgressBar      progressBar;
    ListView         lvAlunos;
    ArrayList<Aluno> alunosList;

    Button           btnConsultarTodos;
    Button           btnConsultarRA;
    Button           btnAlterar;
    Button           btnExcluir;
    Button           btnInserir;

    TextView         edRA;
    TextView         edNome;
    TextView         edCorreio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAlunos = findViewById(R.id.lvAlunos);
        alunosList = new ArrayList<>();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        edRA = findViewById(R.id.edRA);
        edNome = findViewById(R.id.edNome);
        edCorreio = findViewById(R.id.edCorreio);

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
                    LimparCampos();
                }
            }
        });

        btnConsultarRA = findViewById(R.id.btnConsultarRA);
        btnConsultarRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((RAValido()) && (isOnline()))
                {
                    QualMetodo = "2";
                    String RA = edRA.getText().toString();
                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo, RA);
                    EsconderTeclado(MainActivity.this);
                    LimparCampos();
                }
            }
        });

        btnExcluir = findViewById(R.id.btnExcluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((RAValido()) && (isOnline()))
                {
                    QualMetodo = "3";
                    String RA = edRA.getText().toString();
                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo, RA);
                    EsconderTeclado(MainActivity.this);
                    LimparCampos();
                }
            }
        });

        btnInserir = findViewById(R.id.btnInserir);
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if ((CamposValidos()) && (isOnline()))
                {
                    QualMetodo = "4";
                    String RA = edRA.getText().toString();
                    String Nome = edNome.getText().toString();
                    String Correio = edCorreio.getText().toString();

                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo, RA, Nome, Correio);
                    EsconderTeclado(MainActivity.this);
                    LimparCampos();
                }
            }
        });

        btnAlterar = findViewById(R.id.btnAlterar);
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((CamposValidos()) && (isOnline()))
                {
                    QualMetodo = "5";
                    String RA = edRA.getText().toString();
                    String Nome = edNome.getText().toString();
                    String Correio = edCorreio.getText().toString();

                    MinhaAsyncTask minhaAsyncTask = new MinhaAsyncTask();
                    minhaAsyncTask.execute(QualMetodo, RA, Nome, Correio);
                    EsconderTeclado(MainActivity.this);
                    LimparCampos();
                }
            }
        });
    }

    public static void EsconderTeclado(Activity activity)
    {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    public boolean RAValido()
    {
        if (edRA.getText().toString().length() != 5 )
        {
            Toast.makeText(getApplicationContext(),"RA inválido!!", Toast.LENGTH_SHORT).show();
            edRA.requestFocus();
            return false;
        }
        return true;
    }
    public boolean CamposValidos()
    {
        if ((edRA.getText().toString().matches("") || (edNome.getText().toString().matches("")) || (edCorreio.getText().toString().matches(""))))
        {
            Toast.makeText(getApplicationContext(),"Preencha todos os campos!!", Toast.LENGTH_SHORT).show();
            edRA.requestFocus();
            return false;
        }

        if (edRA.getText().toString().length() != 5 )
        {
            Toast.makeText(getApplicationContext(),"RA inválido!!", Toast.LENGTH_SHORT).show();
            edRA.requestFocus();
            return false;
        }

        return true;
    }

    public void LimparCampos()
    {
        edRA.setText("");
        edCorreio.setText("");
        edNome.setText("");
    }

    private boolean isOnline() {
        ConnectivityManager cm  = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;

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
            Gson gson = new Gson();
            Cliente cliente = new Cliente();
            String conteudo = "";
            String response = "";

            try
            {
                switch (params[0])
                {
                    case "1":  // consulta todos
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
                    case "2": // consulta por ra
                    {
                        if (params[0].equals("2"))
                        {
                            try
                            {
                                response = cliente.getAlunoRA(params[1]);
                                return response;
                            }
                            catch (Exception ex)
                            {
                                return ex.getMessage();
                            }

                        }
                    }
                    case "3": // exclusão por ra
                        if (params[0].equals("3"))
                        {
                            try
                            {
                                response = cliente.deleteAluno(params[1]);
                                return response;
                            }
                            catch (Exception ex)
                            {
                                return ex.getMessage();
                            }
                        }
                    case "4": // insercao de aluno
                    {
                        if (params[0].equals("4"))
                        {
                            try
                            {
                                Aluno aluno = new Aluno(params[1], params[2], params[3]);

                                String aluno_json = gson.toJson(aluno);
                                response = cliente.postAluno(aluno_json);

                                return response;
                            }
                            catch (Exception ex)
                            {
                                return ex.getMessage();
                            }
                        }
                    }
                    case "5": // alteracao por ra
                    {
                        if (params[0].equals("5"))
                        {
                            try
                            {
                                Aluno aluno = new Aluno(params[1], params[2], params[3]);
                                String aluno_json = gson.toJson(aluno);
                                response = cliente.putAluno(aluno_json);

                                return response;
                            }
                            catch (Exception ex)
                            {
                                return ex.getMessage();
                            }
                        }
                    }

                }
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }

                return conteudo;
            }

        @Override
        protected void onPostExecute(String s) {
            //atualizarView(s);
            Cliente client = new Cliente();

            if (QualMetodo.equals("3"))
            {
                Toast.makeText(getApplicationContext(),"Exclusão feita com sucesso!!", Toast.LENGTH_SHORT).show();
            }

            if (QualMetodo.equals("4"))
            {
                Toast.makeText(getApplicationContext(),"Inclusão feita com sucesso!!", Toast.LENGTH_SHORT).show();
            }

            if (QualMetodo.equals("5"))
            {
                Toast.makeText(getApplicationContext(),"Alteração feita com sucesso!!", Toast.LENGTH_SHORT).show();
            }

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
