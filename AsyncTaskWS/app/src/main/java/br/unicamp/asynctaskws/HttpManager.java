package br.unicamp.agendadealunos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager
{
    public static String getDados(String uri) {
        BufferedReader reader = null;

        try
        {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            /*con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type","application/json");
            OutputStream os = con.getOutputStream();
            os.write("");*/ // enviar request post

            StringBuilder stringBuilder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + '\n');
            }

            reader.close();

            return stringBuilder.toString();
        }
        catch (Exception e)
        {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e2) {}
            e.printStackTrace();
            return null;
        }
    }
}
