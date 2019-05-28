package br.cotuca.unicamp.myapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {

    public static String getDados(String uri){

        BufferedReader br = null;

        try{

            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type","application/json");

            StringBuilder stringBuilder = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String linha;
            while((linha=br.readLine())!=null){
                stringBuilder.append(linha + "\n");
            }
            return stringBuilder.toString();

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

        finally {
            if(br!=null){
                try {
                    br.close();
                }
                catch(IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }
}
