import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class prepareForAnnotate {
    public static void main(String[] args) throws Exception {
        String filename = new SimpleDateFormat("yy-MM-dd-HH.mm").format(Calendar.getInstance().getTime());

        Scanner in = new Scanner(System.in);
        System.out.println("URL web stranice članka: ");
        String url = in.next();
        String[] sentences = getArticle.getArticle(url);


        int[] sentiment = new int[sentences.length];
        for(int s = 0; s < sentences.length; s++) {
            for(int i = 0; i < s; i++)
                System.out.println(sentiment[i] + "\t" + sentences[i]);
            for(int i = s; i < sentences.length; i++) {
                System.out.println("_\t" + sentences[i]);
            }
            System.out.println("Sentiment sljedeće rečenice: ");
            sentiment[s] = in.nextInt();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        }

        for(int i = 0; i < sentences.length; i++)
            System.out.println(sentiment[i] + "\t" + sentences[i]);

        FileWriter fw = new FileWriter("/Users/snc/Box/Škola/HR/Croatian-sentimentAnalysis/clanke/" + filename);
        System.out.println("\n\nPišem podatke na /Users/snc/Box/Škola/HR/Croatian-sentimentAnalysis/clanke/" + filename);
        fw.write(url + '\n');
        for(int i = 0; i < sentences.length; i++) {
            if(sentiment[i] == -2)
                continue;
            fw.write(sentiment[i] + "\t;;;\t" + sentences[i] + "\n");
        }
        fw.close();
        updateCounts(url);
    }

    public static void updateCounts(String url) throws Exception {
        BufferedReader fr;
        String[] fileText = new String[5]; //one line for each news source
        try {
            fr = new BufferedReader(new FileReader("/Users/snc/Box/Škola/HR/Croatian-sentimentAnalysis/counts.txt"));
            int i = 0;
            while(fr.ready())
                fileText[i++] = fr.readLine();
        } catch (Exception e) {
            System.out.println("Nije bio moguće pročitati fajl brojeva članaka.");
            return;
        }

        if(url.startsWith("https://dnevnik.hr"))
            fileText[0] = "Dnevnik\t\t" + (Integer.parseInt(fileText[0].split("\t\t")[1]) + 1);
        else if(url.startsWith("https://vijesti.hrt.hr"))
            fileText[1] = "HRT\t\t" + (Integer.parseInt(fileText[1].split("\t\t")[1]) + 1);
        else if(url.startsWith("https://www.24sata.hr/news"))
            fileText[2] = "24sata\t\t" + (Integer.parseInt(fileText[2].split("\t\t")[1]) + 1);

        BufferedWriter fw;
        try {
            fw = new BufferedWriter(new FileWriter("/Users/snc/Box/Škola/HR/Croatian-sentimentAnalysis/counts.txt"));
            for(String s : fileText)
                fw.write(s + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Nije bio moguće napisati fajl brojeva članaka.");
            return;
        }
        System.out.println("Osviježen je fajl brojeva članaka.");
    }
}
