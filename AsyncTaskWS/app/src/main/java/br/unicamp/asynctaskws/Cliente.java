/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.asynctaskws;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.util.List;


/**
 *
 * @author simone
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
       Cliente cliente = new Cliente();
       BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
       Gson gson = new Gson();
       
       boolean exit = false;
       while (!exit)
       {
           System.out.println("==== Cliente RESTFul ====");
           System.out.println("1 - Consultar todos os alunos.");
           System.out.println("2 - Consultar aluno por RA.");
           System.out.println("3 - Consultar aluno por nome.");
           System.out.println("4 - Inserir aluno.");
           System.out.println("5 - Alterar nome e e-mail do aluno.");
           System.out.println("6 - Excluir aluno por RA.");
           System.out.println("0 - Finalizar o programa.");
           System.out.println();
           System.out.print("Escolha uma operação:");
           
           String response = null;
           try {
               int operation = Integer.parseInt(reader.readLine());
               
               switch (operation) {
                   case 0: {
                       System.exit(0);
                       break;
                   }
                   case 1: {
                       response = cliente.getAlunos();
                       ArrayList<Aluno> alunos = (ArrayList<Aluno>)gson.fromJson(response, new TypeToken<List<Aluno>>(){}.getType());
                       
                       System.out.println(alunos);
                       break;
                   }
                   case 2: {
                       System.out.print("Digite o RA do aluno:");
                       String ra = reader.readLine().trim();
                       
                       response = cliente.getAlunoRA(ra);
                       Aluno aluno = (Aluno)gson.fromJson(response, Aluno.class);
                       
                       System.out.println(aluno);
                       break;
                   }
                   case 3: {
                       System.out.print("Digite o nome do aluno:");
                       String nome = reader.readLine().trim();
                       
                       response = cliente.getAlunoNome(nome);
                       Aluno aluno = (Aluno)gson.fromJson(response, Aluno.class);
                       
                       System.out.println(aluno);
                       break;
                   }
                   case 4: {
                       System.out.print("Digite o RA do aluno:");
                       String ra = reader.readLine().trim();
                       
                       System.out.print("Digite o nome do aluno:");
                       String nome = reader.readLine().trim();
                       
                       System.out.print("Digite o e-mail do aluno:");
                       String email = reader.readLine().trim();
                       
                       Aluno aluno = new Aluno(ra, nome, email);
                       String aluno_json = gson.toJson(aluno);
                       response = cliente.postAluno(aluno_json);
                       
                       System.out.println("Aluno inserido com sucesso!");
                       break;
                   }
                   case 5: {
                       System.out.print("Digite o RA do aluno:");
                       String ra = reader.readLine().trim();
                       
                       System.out.print("Digite o nome do aluno:");
                       String nome = reader.readLine().trim();
                       
                       System.out.print("Digite o e-mail do aluno:");
                       String email = reader.readLine().trim();
                       
                       Aluno aluno = new Aluno(ra, nome, email);
                       String aluno_json = gson.toJson(aluno);
                       response = cliente.putAluno(aluno_json);
                       
                       System.out.println("Aluno alterado com sucesso!");
                       break;
                   }
                   case 6: {
                       System.out.print("Digite o RA do aluno:");
                       String ra = reader.readLine().trim();
                       response = cliente.deleteAluno(ra);
                       
                       System.out.println("Aluno excluído com sucesso!");
                       break;
                   }
                   default:
                       System.out.println("Opção inválida!");
               }
           }
           catch (Exception ex) {
               System.out.println(ex.getMessage());
           }
           
           System.out.println();
       }
    }
    
    public String getAlunos() throws MalformedURLException, IOException {
        URL objURL = new URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunos");
        HttpURLConnection con = (HttpURLConnection) objURL.openConnection();
        con.setRequestMethod("GET");
        
        int responseCode = con.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer response = new StringBuffer();
        
        String inputLine;
        while((inputLine = br.readLine()) != null)
            response.append(inputLine);
        
        br.close();
        con.disconnect();
        
        return response.toString();
    }
    
    public String getAlunoNome(String Nome) throws MalformedURLException, IOException {
        URL objURL = new URL("http://http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunoByNome/"+Nome);
        HttpURLConnection con = (HttpURLConnection) objURL.openConnection();
        
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        
        System.out.println("Enviando requisição 'GET'");
        System.out.println("Response Code: "+ responseCode);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        
        while((inputLine = br.readLine()) != null)
            response.append(inputLine);
        
        br.close();
        con.disconnect();
        
        return response.toString();
    }
    
    public String getAlunoRA(String RA) throws MalformedURLException, IOException{
        URL objURL = new URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/GetAlunoByRA/"+RA);
        HttpURLConnection con = (HttpURLConnection) objURL.openConnection();
        
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = br.readLine()) != null)
            response.append(inputLine);
             
        br.close();
        con.disconnect();
        
        return response.toString();
    }
    
    public String deleteAluno(String RA) throws MalformedURLException, IOException{
        URL objURL = new URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/DeleteAluno/"+RA);
        HttpURLConnection con = (HttpURLConnection) objURL.openConnection();
        
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        System.out.println("Enviando requisição 'GET'");
        System.out.println("Response Code: "+ responseCode);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = br.readLine()) != null)
            response.append(inputLine);
             
        br.close();
        con.disconnect();
        return response.toString();
    }
    
    public String postAluno(String aluno_json) throws MalformedURLException, IOException{
        URL objURL = new URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/PostAluno");
        HttpURLConnection con = (HttpURLConnection)objURL.openConnection();
        
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        //System.out.println("Enviando requisição 'POST'");
        OutputStream os = con.getOutputStream();
        
        os.write(aluno_json.getBytes());
        
        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: "+ responseCode);
        
        //Armazena o retorno do método POST do servidor        
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); 
        
        StringBuffer response = new StringBuffer();        
        String inputLine;
        while((inputLine = br.readLine())!=null)
            response.append(inputLine);
        
        br.close();
        con.disconnect();
        return response.toString();
    }
    
    
    public String putAluno(String aluno_json) throws MalformedURLException, IOException {
        URL objURL = new URL("http://10.0.2.2:8080/restful-webproject/webresources/generic/PutAluno");
        HttpURLConnection con = (HttpURLConnection)objURL.openConnection();
        
        con.setDoOutput(true);
        
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        
        //System.out.println("Enviando requisição 'PUT'");
        
        OutputStream os = con.getOutputStream();    
        os.write(aluno_json.getBytes());
        
        int responseCode = con.getResponseCode();
        //System.out.println("Response Code: "+ responseCode);
        
        //Armazena o retorno do método POST do servidor        
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream())); 
        StringBuffer response = new StringBuffer();        
        String inputLine;
        
        while((inputLine = br.readLine())!=null){
            response.append(inputLine);
        
        }
        
        br.close();
        con.disconnect();
        return response.toString();
    }
}