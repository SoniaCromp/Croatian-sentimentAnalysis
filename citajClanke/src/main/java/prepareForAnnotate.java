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

        FileWriter fw = new FileWriter("/Users/snc/Box/Škola/HR/clanke/" + filename);
        System.out.println("\n\nPišem podatke na /Users/snc/Box/Škola/HR/clanke/" + filename);
        fw.write(url + '\n');
        for(int i = 0; i < sentences.length; i++) {
            if(sentiment[i] == -2)
                continue;
            fw.write(sentiment[i] + "\t;;;\t" + sentences[i] + "\n");
        }
        fw.close();
    }
}
