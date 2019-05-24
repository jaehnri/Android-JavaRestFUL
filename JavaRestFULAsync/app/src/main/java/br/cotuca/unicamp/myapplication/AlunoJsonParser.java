package br.cotuca.unicamp.myapplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AlunoJsonParser {

    public static ArrayList<Aluno> parseAlunoJson(String alunoJson) throws JSONException {
        JSONArray jsonArrayAluno = new JSONArray(alunoJson);

        ArrayList<Aluno> listaAluno = new ArrayList<>();

        for(int i=0; i<jsonArrayAluno.length(); i++){
            JSONObject jsonObject = jsonArrayAluno.getJSONObject(i);
            Aluno aluno = new Aluno();

            aluno.setRA(jsonObject.getString("id"));
            aluno.setNome(jsonObject.getString("nome"));
            aluno.setCorreio(jsonObject.getString("correio"));

            listaAluno.add(aluno);
        }

        return listaAluno;

    }
}
