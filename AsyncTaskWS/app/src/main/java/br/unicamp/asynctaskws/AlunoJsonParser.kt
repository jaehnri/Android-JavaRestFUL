package br.unicamp.asynctaskws

import com.google.gson.JsonObject

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

object AlunoJsonParser {
    fun parseDados(content: String): ArrayList<Aluno>? {
        try {
            try {
                val jsonArray = JSONArray(content)
                val alunoList = ArrayList<Aluno>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val aluno = Aluno()

                    aluno.ra = jsonObject.getString("RA")
                    aluno.nome = jsonObject.getString("nome")
                    aluno.correio = jsonObject.getString("correio")

                    alunoList.add(aluno)
                }

                return alunoList
            } catch (e: JSONException) {
                val jsonObject = JSONObject(content)

                val alunoList = ArrayList<Aluno>()

                val aluno = Aluno()
                aluno.ra = jsonObject.getString("RA")
                aluno.nome = jsonObject.getString("nome")
                aluno.correio = jsonObject.getString("correio")

                alunoList.add(aluno)
                return alunoList
            }

        } catch (e: JSONException) {
            e.printStackTrace()
            return null
        }

    }
}
