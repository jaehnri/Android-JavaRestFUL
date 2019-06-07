/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.asynctaskws

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import com.google.gson.reflect.TypeToken

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList
import java.lang.reflect.Type


/**
 *
 * @author simone
 */
class Cliente {

    val alunos: String
        @Throws(MalformedURLException::class, IOException::class)
        get() {
            val objURL = URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunos")
            val con = objURL.openConnection() as HttpURLConnection
            con.requestMethod = "GET"

            val responseCode = con.responseCode

            val br = BufferedReader(InputStreamReader(con.inputStream))
            val response = StringBuffer()

            var inputLine: String
            while ((inputLine = br.readLine()) != null)
                response.append(inputLine)

            br.close()
            con.disconnect()

            return response.toString()
        }

    @Throws(MalformedURLException::class, IOException::class)
    fun getAlunoNome(Nome: String): String {
        val objURL = URL("http://http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunoByNome/$Nome")
        val con = objURL.openConnection() as HttpURLConnection

        con.requestMethod = "GET"
        val responseCode = con.responseCode

        println("Enviando requisição 'GET'")
        println("Response Code: $responseCode")

        val br = BufferedReader(InputStreamReader(con.inputStream))

        var inputLine: String
        val response = StringBuffer()

        while ((inputLine = br.readLine()) != null)
            response.append(inputLine)

        br.close()
        con.disconnect()

        return response.toString()
    }

    @Throws(MalformedURLException::class, IOException::class)
    fun getAlunoRA(RA: String): String {
        val objURL = URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunoByRA/$RA")
        val con = objURL.openConnection() as HttpURLConnection

        con.requestMethod = "GET"
        val responseCode = con.responseCode

        val br = BufferedReader(InputStreamReader(con.inputStream))

        var inputLine: String
        val response = StringBuffer()
        while ((inputLine = br.readLine()) != null)
            response.append(inputLine)

        br.close()
        con.disconnect()

        return response.toString()
    }

    @Throws(MalformedURLException::class, IOException::class)
    fun deleteAluno(RA: String): String {
        val objURL = URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/DeleteAluno/$RA")
        val con = objURL.openConnection() as HttpURLConnection

        con.requestMethod = "GET"
        val responseCode = con.responseCode

        println("Enviando requisição 'GET'")
        println("Response Code: $responseCode")

        val br = BufferedReader(InputStreamReader(con.inputStream))

        var inputLine: String
        val response = StringBuffer()
        while ((inputLine = br.readLine()) != null)
            response.append(inputLine)

        br.close()
        con.disconnect()
        return response.toString()
    }

    @Throws(MalformedURLException::class, IOException::class)
    fun postAluno(aluno_json: String): String {
        val objURL = URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/PostAluno")
        val con = objURL.openConnection() as HttpURLConnection

        con.doOutput = true
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json")

        //System.out.println("Enviando requisição 'POST'");
        val os = con.outputStream

        os.write(aluno_json.toByteArray())

        val responseCode = con.responseCode
        //System.out.println("Response Code: "+ responseCode);

        //Armazena o retorno do método POST do servidor
        val br = BufferedReader(InputStreamReader(con.inputStream))

        val response = StringBuffer()
        var inputLine: String
        while ((inputLine = br.readLine()) != null)
            response.append(inputLine)

        br.close()
        con.disconnect()
        return response.toString()
    }


    @Throws(MalformedURLException::class, IOException::class)
    fun putAluno(aluno_json: String): String {
        val objURL = URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/PutAluno")
        val con = objURL.openConnection() as HttpURLConnection

        con.doOutput = true

        con.requestMethod = "PUT"
        con.setRequestProperty("Content-Type", "application/json")

        //System.out.println("Enviando requisição 'PUT'");

        val os = con.outputStream
        os.write(aluno_json.toByteArray())

        val responseCode = con.responseCode
        //System.out.println("Response Code: "+ responseCode);

        //Armazena o retorno do método POST do servidor
        val br = BufferedReader(InputStreamReader(con.inputStream))
        val response = StringBuffer()
        var inputLine: String

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine)

        }

        br.close()
        con.disconnect()
        return response.toString()
    }

    companion object {

        /**
         * @param args the command line arguments
         */
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            // TODO code application logic here
            val cliente = Cliente()
            val reader = BufferedReader(InputStreamReader(System.`in`))
            val gson = Gson()

            val exit = false
            while (!exit) {
                println("==== Cliente RESTFul ====")
                println("1 - Consultar todos os alunos.")
                println("2 - Consultar aluno por RA.")
                println("3 - Consultar aluno por nome.")
                println("4 - Inserir aluno.")
                println("5 - Alterar nome e e-mail do aluno.")
                println("6 - Excluir aluno por RA.")
                println("0 - Finalizar o programa.")
                println()
                print("Escolha uma operação:")

                var response: String? = null
                try {
                    val operation = Integer.parseInt(reader.readLine())

                    when (operation) {
                        0 -> {
                            System.exit(0)
                        }
                        1 -> {
                            response = cliente.alunos
                            val alunos = gson.fromJson<Any>(response, object : TypeToken<List<Aluno>>() {

                            }.type) as ArrayList<Aluno>

                            println(alunos)
                        }
                        2 -> {
                            print("Digite o RA do aluno:")
                            val ra = reader.readLine().trim { it <= ' ' }

                            response = cliente.getAlunoRA(ra)
                            val aluno = gson.fromJson<Aluno>(response, Aluno::class.java!!) as Aluno

                            println(aluno)
                        }
                        3 -> {
                            print("Digite o nome do aluno:")
                            val nome = reader.readLine().trim { it <= ' ' }

                            response = cliente.getAlunoNome(nome)
                            val aluno = gson.fromJson<Aluno>(response, Aluno::class.java!!) as Aluno

                            println(aluno)
                        }
                        4 -> {
                            print("Digite o RA do aluno:")
                            val ra = reader.readLine().trim { it <= ' ' }

                            print("Digite o nome do aluno:")
                            val nome = reader.readLine().trim { it <= ' ' }

                            print("Digite o e-mail do aluno:")
                            val email = reader.readLine().trim { it <= ' ' }

                            val aluno = Aluno(ra, nome, email)
                            val aluno_json = gson.toJson(aluno)
                            response = cliente.postAluno(aluno_json)

                            println("Aluno inserido com sucesso!")
                        }
                        5 -> {
                            print("Digite o RA do aluno:")
                            val ra = reader.readLine().trim { it <= ' ' }

                            print("Digite o nome do aluno:")
                            val nome = reader.readLine().trim { it <= ' ' }

                            print("Digite o e-mail do aluno:")
                            val email = reader.readLine().trim { it <= ' ' }

                            val aluno = Aluno(ra, nome, email)
                            val aluno_json = gson.toJson(aluno)
                            response = cliente.putAluno(aluno_json)

                            println("Aluno alterado com sucesso!")
                        }
                        6 -> {
                            print("Digite o RA do aluno:")
                            val ra = reader.readLine().trim { it <= ' ' }
                            response = cliente.deleteAluno(ra)

                            println("Aluno excluído com sucesso!")
                        }
                        else -> println("Opção inválida!")
                    }
                } catch (ex: Exception) {
                    println(ex.message)
                }

                println()
            }
        }
    }
}