package br.cotuca.unicamp.myapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
public class AlunoXMLParser {

    public static List<Aluno> parseDados(String conteudo) {

        try {

            boolean dadosNaTag = false;
            String tagAtual = "";
            Aluno aluno = null;
            List<Aluno> alunoList = new ArrayList<>();
            XmlPullParserFactory factory =
                    XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(conteudo));
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagAtual = parser.getName();
                        if (tagAtual.equals("produto")) {
                            dadosNaTag = true;
                            aluno = new Aluno();
                            alunoList.add(aluno);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("produto")) {
                            dadosNaTag = false;
                        }
                        tagAtual = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (dadosNaTag && aluno != null) {
                            switch (tagAtual) {
                                case "id":

                                    aluno.setRA(parser.getText());
                                    break;
                                case "nome":
                                    aluno.setNome(parser.getText());
                                    break;
                                case "correio":
                                    aluno.setCorreio(parser.getText());
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }

            return alunoList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
