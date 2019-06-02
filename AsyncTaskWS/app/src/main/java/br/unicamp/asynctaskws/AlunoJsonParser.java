package br.unicamp.asynctaskws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlunoJsonParser
{
    public static ArrayList<Aluno> parseDados(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            ArrayList<Aluno> alunoList = new ArrayList<>();

            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Aluno aluno = new Aluno();

                aluno.setRA(jsonObject.getString("RA"));
                aluno.setNome(jsonObject.getString("Nome"));
                aluno.setCorreio(jsonObject.getString("Correio"));

                alunoList.add(aluno);
            }

            return alunoList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
