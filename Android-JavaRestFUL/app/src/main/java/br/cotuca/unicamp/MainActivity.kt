package br.cotuca.unicamp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    private var text: TextView? = null
    private var bt: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById<View>(R.id.text) as TextView

        bt = findViewById<View>(R.id.bt) as Button
        bt!!.setOnClickListener { processamento() }
    }

    private fun processamento() {
        val num = Num(this.text, this.bt!!)
        // Executa o doInBackground e passa o valor 50 como parâmetro
        num.execute(50)
    }

}
