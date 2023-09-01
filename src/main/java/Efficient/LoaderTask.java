package Efficient;

import Connect.Url_validator;
import Elements.Build;
import Elements.Repository;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LoaderTask extends Task<Build> {

    /**
     * Classe qui rasssemble toutes les méthodes pour extraire les informations d'un projet sur la page
     * Coveralls.io
     */
    private String url ;
    private File file;
    private String Url;
    private Repository repos;
    private Url_validator validator;

    private ArrayList<Build> RAPES,Not_charged,Built;

    public LoaderTask(String nom_du_projet) throws Exception {
        this.url = nom_du_projet;
        String url_to_check = Url_validator.start+nom_du_projet;
        validator = new Url_validator(url_to_check);
        if (validator.isOk()) {
            this.Url =url_to_check ;
            repos = new Repository(new URL(Url), nom_du_projet);
            RAPES = new ArrayList<>();
            Not_charged =new ArrayList<>();
            Built = new ArrayList<>();

        }else{
            throw new IOException("Projet non trouvé, vérifier l'UrL");
        }
    }

    public Repository getRepos() {
        return repos;
    }

    @Override
    protected Build call() throws Exception {
        starter();
            updateMessage("charging");
        Build b = null;
        for (int i = 0; i < RAPES.size(); i++) {
            b = RAPES.get(i);
            if (isCancelled()) {
                break;
            }
            try {
                Document d = Jsoup.connect(b.getUrl()).get();
                Elements lines = d.select("p");
               // System.out.println("url "+b.getUrl());
                /*
                final File fp = new File("C:\\Users\\jonat\\Documents\\cours\\Cours\\Master\\Rapport Projet\\files\\"
                        +"filename11.txt");
                FileWriter fw = new FileWriter(fp);
                fw.write(d.text());
                fw.close();
                */
                for (Element dd : lines) {
                    if (dd.text().contains(" relevant lines covered")) {
                        String[] line = dd.text().split(" ");
                        int cov = Integer.valueOf(line[0]);
                        int total = Integer.valueOf(line[2]);
                        String percentage = line[line.length - 1].substring(1, line[line.length - 1].length() - 2);
                        double nj = Double.valueOf(percentage);
                        b.setPercentage(nj);
                        b.setTotal_lines(total);
                        b.setCovered_lines(cov);
                    }
                }
                /*
                final File f = new File("C:\\Users\\jonat\\Documents\\cours\\Cours\\Master\\Rapport Projet\\files\\"
                        +"filename1.html");
               FileWriter fs = new FileWriter(f);
               fs.write(d.outerHtml());
               fs.close();
                System.out.println("written");
                */
                try {
                    /*
                    Elements lnks = d.select("div[class='show-detail-item']");
                    System.out.println("atteint 1 "+ lnks.text());
                    String[] j = lnks.text().split("#")[0].split(" ");

                    b.setNbr_files(Integer.parseInt(j[j.length - 1]));
                    Elements lks = d.select("div[class='td']");

                    String committed_by = lks.get(1).text();
                    System.out.println("j : "+ lks.text());
                    System.out.println("atteint 21");
                    String comm = (String) committed_by.subSequence(13, committed_by.length());
                    b.setCommitter(comm);


                    Elements le = d.select("div[data-branch]");
                    if (le.size() == 1) {
                        b.setBranch(le.attr("data-branch"));
                    } else {
                        b.setBranch(le.get(le.size() - 1).attr("data-branch"));
                    }

                     */

                    Elements files = d.select("a[name='filesAll']");
                    String [] caracter = files.text().split(" ");
                    b.setNbr_files(Integer.parseInt(caracter[caracter.length - 1]));

                    Elements commiters = d.select("div[id='runCommit']");
                    String [] caracters = commiters.text().split(" Commit ");
                    String [] parts = caracters[0].split(" ");
                    String comm =  parts[parts.length-2] + " " + parts[parts.length-1];
                    b.setCommitter(comm);

                    Elements branches = d.select("em");
                    String [] branche = branches.text().split(":");
                    b.setBranch(branche[1]);

                    Elements links = d.select("div[id='runInfo']");
                    b.setDate(make_date(links.text()));
                    repos.add_new_build(b);

                    updateValue(b);

                    updateProgress(i, RAPES.size());

                    Built.add(b);

                    updateMessage("charging");

                } catch (NumberFormatException e) {
                   System.out.println("erreur parsage");
                } catch (Exception e) {
                    Not_charged.add(b);
                   // System.out.println("waiting : connection issue package not loaded");
                    Thread.sleep(1000);
                }
            }catch(SocketTimeoutException e ){
              //  System.out.println("Time out : server not responsive");
              //  System.out.println("sleeping");
               // System.out.println(e);
                Thread.sleep(1000);
                //System.out.println("-------------------------------------------------");
        }catch (Exception e){
                //    System.out.println("connection issue : cant continue ");
               // System.out.println(e);
                //System.out.println("--------------------------------------------------");

                }
        }
        updateProgress(RAPES.size(), RAPES.size());
        updateMessage("fini");
        updateTitle("end");
        return b;
    }

    public ArrayList<Build> getBuilt() {

        return Built;
    }

    /**
     * Cette méthode extrait la date au format adéquoit d'une chaine de caractère contenant l'information.
     * @param committed : chaine contenant les informations sur la date.
     * @return : retourne la date.
     * @throws ParseException
     */
    private Date make_date  (String committed) throws ParseException {
            String temp = committed.split("-")[0].substring(10);
            Date ret = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH).parse(temp);
            return ret ;
        }

    /**
     * Méthode qui itère sur le nombre total de pages contenants des builds.
     * Cette méthode extrait également la référence globale de chaque build.
     * @throws Exception
     */
    private void starter () throws Exception {
        try {
            Document doc = Jsoup.connect(this.Url).get();
            String title = doc.title();
            Elements links = doc.select("a[class='btn']"); // a with href
            this.Url = Url_validator.coveralls_io+ links.attr("href");
            String end = links.attr("href");
            String ref = links.attr("href").substring(7,end.length()-7) ;
            repos.setGlobal_ref(ref);
        }catch(Exception e){
            throw new Exception("Something goes wrong while charging file : please check your connection");
        }
        int i = 1;
        Url_validator validate = new Url_validator();
        String url_temp =this.Url+ "?page="+i ;
        validate.setUrl(url_temp);
        ArrayList<Element> all_builds_diamonds = new ArrayList<>();
        int kook = 0;
        while(validate.isOk() /*&& kook <4*/){
            if(isCancelled()){
                break;
            }
            Document doc = Jsoup.connect(url_temp).get();
            Elements links = doc.select("a[class='btn btn-micro']");
            all_builds_diamonds.addAll(links);
            i++;
            url_temp = this.Url+"?page="+i;
            validate.setUrl(url_temp);
            links.clear();
            kook++;
        }
        for (Element el: all_builds_diamonds ) {
            Build b = new Build();
            String rr = el.wholeOwnText();
            String bb = rr.substring(1,rr.length()-1);
            b.setLocal_reference(bb);
            String href= el.attr("href");
            b.setGlobal_reference(href);
            String temp = Url_validator.coveralls_io+href;
            b.setUrl(temp);
             if(! href.substring(0,5).equalsIgnoreCase("https")) {
                RAPES.add(b);
            }
        }
    }
}
