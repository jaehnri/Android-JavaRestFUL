package br.unicamp.asynctaskws;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProdutoJSONParser
{
    public static ArrayList<Produto> parseDados(String content) {
        try {
            JSONArray jsonArray = new JSONArray(content);
            ArrayList<Produto> produtoList = new ArrayList<>();

            for (int i=0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Produto produto = new Produto();

                produto.setId(jsonObject.getInt("id"));
                produto.setNome(jsonObject.getString("nome"));
                produto.setCategoria(jsonObject.getString("categoria"));
                produto.setDescricao(jsonObject.getString("descricao"));

                produtoList.add(produto);
            }

            return produtoList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
