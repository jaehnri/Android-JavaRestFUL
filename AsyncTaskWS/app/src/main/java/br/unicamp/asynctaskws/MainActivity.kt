package br.unicamp.asynctaskws

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.reflect.TypeToken

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var QualMetodo = ""

    internal var progressBar: ProgressBar
    internal var lvAlunos: ListView
    internal var alunosList: ArrayList<Aluno>? = null

    internal var btnConsultarTodos: Button
    internal var btnConsultarRA: Button
    internal var btnAlterar: Button
    internal var btnExcluir: Button
    internal var btnInserir: Button

    internal var edRA: TextView
    internal var edNome: TextView
    internal var edCorreio: TextView

    private val isOnline: Boolean
        get() {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = cm.activeNetworkInfo

            return if (networkInfo != null && networkInfo.isConnected)
                true
            else
                false

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lvAlunos = findViewById(R.id.lvAlunos)
        alunosList = ArrayList()

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        edRA = findViewById(R.id.edRA)
        edNome = findViewById(R.id.edNome)
        edCorreio = findViewById(R.id.edCorreio)

        btnConsultarTodos = findViewById(R.id.btnConsultarTodos)
        btnConsultarTodos.setOnClickListener {
            if (isOnline) {
                QualMetodo = "1"
                val minhaAsyncTask = MinhaAsyncTask()
                minhaAsyncTask.execute(QualMetodo)
                EsconderTeclado(this@MainActivity)
                LimparCampos()
            }
        }

        btnConsultarRA = findViewById(R.id.btnConsultarRA)
        btnConsultarRA.setOnClickListener {
            if (RAValido() && isOnline) {
                QualMetodo = "2"
                val RA = edRA.text.toString()
                val minhaAsyncTask = MinhaAsyncTask()
                minhaAsyncTask.execute(QualMetodo, RA)
                EsconderTeclado(this@MainActivity)
                LimparCampos()
            }
        }

        btnExcluir = findViewById(R.id.btnExcluir)
        btnExcluir.setOnClickListener {
            if (RAValido() && isOnline) {
                QualMetodo = "3"
                val RA = edRA.text.toString()
                val minhaAsyncTask = MinhaAsyncTask()
                minhaAsyncTask.execute(QualMetodo, RA)
                EsconderTeclado(this@MainActivity)
                LimparCampos()
            }
        }

        btnInserir = findViewById(R.id.btnInserir)
        btnInserir.setOnClickListener {
            if (CamposValidos() && isOnline) {
                QualMetodo = "4"
                val RA = edRA.text.toString()
                val Nome = edNome.text.toString()
                val Correio = edCorreio.text.toString()

                val minhaAsyncTask = MinhaAsyncTask()
                minhaAsyncTask.execute(QualMetodo, RA, Nome, Correio)
                EsconderTeclado(this@MainActivity)
                LimparCampos()
            }
        }

        btnAlterar = findViewById(R.id.btnAlterar)
        btnAlterar.setOnClickListener {
            if (CamposValidos() && isOnline) {
                QualMetodo = "5"
                val RA = edRA.text.toString()
                val Nome = edNome.text.toString()
                val Correio = edCorreio.text.toString()

                val minhaAsyncTask = MinhaAsyncTask()
                minhaAsyncTask.execute(QualMetodo, RA, Nome, Correio)
                EsconderTeclado(this@MainActivity)
                LimparCampos()
            }
        }
    }

    fun RAValido(): Boolean {
        if (edRA.text.toString().length != 5) {
            Toast.makeText(applicationContext, "RA inválido!!", Toast.LENGTH_SHORT).show()
            edRA.requestFocus()
            return false
        }
        return true
    }

    fun CamposValidos(): Boolean {
        if (edRA.text.toString().matches("".toRegex()) || edNome.text.toString().matches("".toRegex()) || edCorreio.text.toString().matches("".toRegex())) {
            Toast.makeText(applicationContext, "Preencha todos os campos!!", Toast.LENGTH_SHORT).show()
            edRA.requestFocus()
            return false
        }

        if (edRA.text.toString().length != 5) {
            Toast.makeText(applicationContext, "RA inválido!!", Toast.LENGTH_SHORT).show()
            edRA.requestFocus()
            return false
        }

        return true
    }

    fun LimparCampos() {
        edRA.text = ""
        edCorreio.text = ""
        edNome.text = ""
    }

    private inner class MinhaAsyncTask : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String): String {
            val gson = Gson()
            val cliente = Cliente()
            val conteudo = ""
            var response = ""

            try {
                when (params[0]) {
                    "1"  // consulta todos
                    -> {
                        return cliente.alunos
                    }
                    "2" // consulta por ra
                    -> {
                        return cliente.getAlunoRA(params[1])
                    }
                    "3" // exclusão por ra
                    -> {
                        return cliente.deleteAluno(params[1])
                    }
                    "4" // insercao de aluno
                    -> {
                        val aluno = Aluno(params[1], params[2], params[3])

                        val aluno_json = gson.toJson(aluno)
                        response = cliente.postAluno(aluno_json)

                        return response
                    }
                    "5" // alteracao por ra
                    -> {
                        val aluno = Aluno(params[1], params[2], params[3])

                        val aluno_json = gson.toJson(aluno)
                        response = cliente.putAluno(aluno_json)

                        return response
                    }
                }
            } catch (ex: Exception) {
                QualMetodo = "-1"
                return ex.message.toString()
            }

            return conteudo
        }

        override fun onPostExecute(s: String) {
            lvAlunos.adapter = null

            if (QualMetodo == "-1") {
                Toast.makeText(applicationContext, "Operação mal sucedida!!", Toast.LENGTH_SHORT).show()
            } else {
                if (QualMetodo == "3") {
                    Toast.makeText(applicationContext, "Exclusão feita com sucesso!!", Toast.LENGTH_SHORT).show()
                } else if (QualMetodo == "4") {
                    Toast.makeText(applicationContext, "Inclusão feita com sucesso!!", Toast.LENGTH_SHORT).show()
                } else if (QualMetodo == "5") {
                    Toast.makeText(applicationContext, "Alteração feita com sucesso!!", Toast.LENGTH_SHORT).show()
                }

                alunosList = AlunoJsonParser.parseDados(s)
                val alunosAdapter = ListaAlunosAdapter(this@MainActivity, alunosList!!)
                lvAlunos.adapter = alunosAdapter
            }

            progressBar.visibility = View.INVISIBLE
        }

        override fun onProgressUpdate(vararg values: String) {
            //atualizarView(values[0]);
        }
    }

    companion object {

        fun EsconderTeclado(activity: Activity) {
            val view = activity.findViewById<View>(android.R.id.content)
            if (view != null) {
                val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

}
