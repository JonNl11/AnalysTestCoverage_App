package Efficient;

import Connect.Url_validator;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;

public class Metrics_Mnger extends Task<String> {
    /**
     * Cette classe possède la méthode principale de l'analyse globale.
     */

    private ArrayList<String> names;
    private ArrayList<String>covered;
    private File file;
    private Url_validator valid=new Url_validator();
    private BufferedReader in;
    private String sep ;

    public Metrics_Mnger(File file, String sep) throws FileNotFoundException {
        names = new ArrayList<>();
        covered = new ArrayList<>();
        this.file = file;
        in = new BufferedReader(new FileReader(file));
        this.sep=sep;
    }


    @Override
    protected String call() throws Exception {
        String line ="";
        try {
            while((line = in.readLine()) != null) {
                if(isCancelled()){
                    cancel();
                }

                String [] row =line.split(sep);

                if (! row[1].equalsIgnoreCase("name")){
                    names.add(row[1]);
                    String nom =row[1].substring(1,row[1].length()-1);

                    updateMessage(nom);

                    String surl = Url_validator.start+row[1].substring(1,row[1].length()-1);
                    System.out.println(surl);
                    valid.setUrl(surl);
                    if(valid.isOk()){
                        Document d = Jsoup.connect(surl).get();
                        Elements links = d.select("div[class='emptyGuide']");
                        if(links.size() ==0){
                            covered.add(nom);
                            updateValue(nom);
                        }
                    }
                }
            }
        } catch(FileNotFoundException e) {
            System.out.println("fichier absent");
            e.printStackTrace();
            //System.exit(-1);
        }catch(IOException e) {
            e.printStackTrace();
            System.out.println("erreur de lecture");
            //System.exit(-1);
        }
        in.close();
        updateTitle("finish");
        return line;

}

    }
