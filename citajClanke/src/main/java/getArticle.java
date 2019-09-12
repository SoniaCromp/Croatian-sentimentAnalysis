import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.List;

public class getArticle {
    public static void main(String[] args) throws Exception {
        String[] art = getArticle("https://dnevnik.hr/vijesti/hrvatska/zrakoplove-hrvatskoj-nude-danska-i-norveska---574861.html");
        for(String s : art)
            System.out.println(s);
    }

    public static String[] getArticle(String url) throws Exception {
        Document webpage = Jsoup.connect(url).get();
        webpage.outputSettings(new Document.OutputSettings().prettyPrint(false));
        Document html = Jsoup.parse(webpage.body().html());
        if(url.startsWith("https://dnevnik.hr"))
            return fetchDnevnik(html);
        else if(url.startsWith("https://vijesti.hrt.hr"))
            return fetchHRT(html);
        else {
            System.out.println("došlo je pogreške čitajući izvor vijesti...");
            return null;
        }
    }

    public static String[] fetchDnevnik(Document html) {
        String article = "";
        article += html.getElementsByClass("title g10").text().replaceAll("''", "\"").stripTrailing() + " \n";
        Elements ps = html.getElementsByTag("p");
        for(Element p : ps) {
            if((p.className().equals("") || p.className().equals("lead intextAdIgnore")) && !p.html().contains("<span class=")) {
                if(p.html().startsWith("Dnevnik Nove TV gledajte svakog dana od 19:15, a više o najvažnijim vijestima čitajte na portalu")) {
                    return article.split("((?<=[.!?][ \n])|\n)"); //splits text on sentences;
                }
                String str = p.text().replaceAll("''", "\"") + " ";
                if(!str.matches("[ \t\n]*")) //get rid of those pesky plain whitespace lines
                    article += str;
            }
        }
        String timeControl = "(. [^a-z])";
        article = article.replaceAll("LINEBREAK ", "");
        return article.split("((?<=([.!?])[ \n](?=([^a-z])))|\n)");
    }

    public static String[] fetchHRT(Document html) {
        //html.select("br").append("LINEBREAK");
        String article = "";
        article += html.getElementsByClass("title").text().replaceAll("''", "\"").stripTrailing() + " \n";
        Elements ps = html.getElementsByTag("p");
        ps.select("a").remove();
        article += ps.text();
        //article = article.replaceAll("(LINEBREAK )+", "\n ");
        //article = article.replaceAll("LINEBREAK", "");
        return article.split("((?<=([.!?])[ \n](?=([^a-z])))|\n)");
    }
}
