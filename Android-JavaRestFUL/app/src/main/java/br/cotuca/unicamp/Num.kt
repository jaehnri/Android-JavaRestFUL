package br.cotuca.unicamp

import android.os.AsyncTask
import android.widget.Button
import android.widget.TextView

class Num//Construtor
(private val text: TextView?, private val bt: Button) : AsyncTask<Int, Int, Void>() {
    //Processamento:
    //Este método é executado em uma thread a parte,
    //ou seja ele não pode atualizar a interface gráfica,
    //por isso ele chama o método onProgressUpdate,
    // o qual é executado pela
    //UI thread.
    protected override fun doInBackground(vararg params: Int?): Void? {
        val n = params[0]
        var i = 0
        while (i < n!!) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            //Notifica o Android de que ele precisa
            //fazer atualizações na
            //tela e chama o método onProgressUpdate
            //para fazer a atualização da interface gráfica
            //passando o valor do
            //contador como parâmetro.
            publishProgress(i)
            i++
        }
        return null
    }
    // É invocado para fazer uma atualização na
    // interface gráfica

    protected fun onProgressUpdate(vararg values: Int) {
        if (text != null) {
            text.text = values[0].toString()
        }
    }
}
