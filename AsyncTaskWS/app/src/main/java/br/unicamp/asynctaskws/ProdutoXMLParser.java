package br.unicamp.asynctaskws;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ProdutoXMLParser
{
    public static List<Produto> parseDados(String conteudo)
    {
        try {
            boolean dadosNaTag = false;
            String tagAtual = "";
            Produto produto = null;
            List<Produto> produtoList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(conteudo));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagAtual = parser.getName();
                        if (tagAtual.equals("produto")) {
                            dadosNaTag = true;
                            produto = new Produto();
                            produtoList.add(produto);
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("produto")) {
                            dadosNaTag = false;
                        }
                        tagAtual = "";
                        break;

                    case XmlPullParser.TEXT:
                        if (dadosNaTag && produto != null) {
                            switch (tagAtual) {
                                case "id":
                                    produto.setId(Integer.parseInt(parser.getText()));
                                    break;
                                case "nome":
                                    produto.setNome(parser.getText());
                                    break;
                                case "categoria":
                                    produto.setCategoria(parser.getText());
                                    break;
                                case "descricao":
                                    produto.setDescricao(parser.getText());
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }

                eventType = parser.next();
            }

            return produtoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
