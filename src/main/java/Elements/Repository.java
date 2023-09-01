package Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;
import  org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class Repository {

    /**
     * Cette classe représente tout un repertoire Github
     * avec une archives de l'évolution de la couverture des tests au fil du temps
     * ainsi que la couverture globale finale.
     * */

    private URL Url;
    private String ref,global_ref;

    public static  String [] headers ={"Branche","Date","Committer","#Files","Cov Lines",
            "Tot lines","Percentage","Url","Local ref"};

    private TreeSet<Build> All_builds;

    public Repository(){
        All_builds= new TreeSet<>();
    }

    /**
     * Instantiateur d'un projet.
     * @param Url: Url coveralls permettant d'acceder au différente métriques.
     * @param ref :  Nom du projet.
     */
    public Repository(URL Url, String ref){
        this.Url = Url;
        this.ref = ref;
        All_builds= new TreeSet<>();
    }


    /**
     * Méthode qui rajoute un build à la liste de build d'un projet.
     * @param one : le build à ajouter.
     */
    public void add_new_build(Build one) throws InterruptedException {
        All_builds.add(one);
    }
    /**
     *Méthode qui extrait les différentes braches d'un repertoire.
     * @return : une liste contenant le nom des branches.
     */
    public ArrayList<String> getBranch(){
        ArrayList<String>result = new ArrayList<>();
        for (Build b : All_builds){
            if(!result.contains(b.getBranch())){
                result.add(b.getBranch());
            }
        }
        return result;
    }

    /**
     * Permet de vérifier qu'un build apparteint au repertoire.
     * @param b : le build à vérifier.
     * @return booléen correspondant;
     */
    public boolean contains(Build b){
        boolean res = false;
        for (Build s : All_builds
        ) {
            if (s.equals(b)){
                res = true;
                break;
            }
        }
        return res;
    }

    /**
     * méthode qui extrait le nom de tous les committeurs du projet
     * @return : une liste de nom.
     */
    public ArrayList<String> getCommitters(){
        ArrayList<String>result = new ArrayList<>();
        for (Build b : All_builds){
            if(!result.contains(b.getCommiter())){
                result.add(b.getCommiter());
            }
        }

        return result;
    }

    /**
     * Getter de l'Url coveralls du projet
     * @return :  l'url du projet.
     */
    public URL getUrl() {
        return Url;
    }

    /**
     * Getter du nom du projet.
     * @return : nom du projet.
     */
    public String getRef() {
        return ref;
    }


    /**
     * Getter des builds.
     * @return les builds.
     */
    public TreeSet<Build> get_All_builds() {
        return this.All_builds;
    }


    public void setGlobal_ref(String global_ref) {
        this.global_ref = global_ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setUrl(URL url) {
        this.Url = url;
    }

    /**
     * Code pour extraire les données sous forme d'un document csv.
     * @throws FileNotFoundException
     */
    public void Extract_csv() throws FileNotFoundException {
        String name ="Resources"+ File.separator+"detailled_csv"+ File.separator +
                this.ref.replaceAll("[^\\w\\s]","")+".csv";
        File csv = new File(name);
        PrintWriter out = new PrintWriter(csv);
        Iterator<Build> iterate = All_builds.iterator();

        out.printf("%s ,%s, %s, %s ,%s ,%s ,%s ,%s ,%s",headers[0],headers[1],headers[2],headers[3],
                headers[4],headers[5],headers[6],headers[7],headers[8]
        );
        out.println();
        while(iterate.hasNext()){
            Build b = iterate.next();
            out.printf("%s ,%s, %s, %s ,%s ,%s ,%s ,%s ,%s\n",
                    b.getBranch(),b.getDate(),b.getCommiter(),String.valueOf(b.getNbr_files())
                    ,String.valueOf(b.getCovered_lines()),String.valueOf(b.getTotal_lines()),
                    String.valueOf(b.getPercentage()),b.getUrl(),b.getLocal_reference());
        }
        out.close();
    }
    /**
     * Code pour extraire les données sous forme d'un document txt.
     * @throws FileNotFoundException
     */
    public void Extract_txt() throws FileNotFoundException {
        String name ="Resources"+ File.separator+"detailled_txt"+ File.separator +
                this.ref.replaceAll("[^\\w\\s]","")+".txt";
        File csv = new File(name);
        PrintWriter out = new PrintWriter(csv);
        int i = 0;
        Object [] arr = All_builds.toArray();
        out.printf("%s ,%s, %s, %s ,%s ,%s ,%s ,%s ,%s\n",headers[0],headers[1],headers[2],headers[3],
                headers[4],headers[5],headers[6],headers[7],headers[8]
        );
        //out.println();
        for (int j = 0; j < arr.length; j++) {
            Build b = (Build) arr[j];
            out.printf("%s ,%s ,%s, %s, %s, %s, %s, %s, %s\n",
                    b.getBranch(),b.getDate(),b.getCommiter(),String.valueOf(b.getNbr_files())
                    ,String.valueOf(b.getCovered_lines()),String.valueOf(b.getTotal_lines()),
                    String.valueOf(b.getPercentage()),b.getUrl(),b.getLocal_reference());

            //  out.println();
        }
        out.close();
    }
    /**
     * Code pour extraire les données sous forme d'un document (.json).
     * @throws FileNotFoundException
     */
    public void Extract_json() throws FileNotFoundException, JSONException {

        JSONObject jsonObject =new JSONObject();
        JSONArray arr = new JSONArray();

        for (Build b:All_builds) {
            JSONObject o = new JSONObject();

            o.put("Branche" , b.getBranch());
            o.put("Date" , b.getDate());
            o.put("Committer" , b.getCommiter());
            o.put("Nbr_Files" , b.getNbr_files());
            o.put("Cov_lines" , b.getCovered_lines());
            o.put("Tot_lines" , b.getTotal_lines());
            o.put("Percentage" , b.getPercentage());
            o.put("Url " , b.getUrl());
            o.put("Local_ref" , b.getLocal_reference());

            arr.put(o);
        }
        jsonObject.put("Builds",arr);

        String name ="Resources"+ File.separator+"detailled_json"+ File.separator +
                this.ref.replaceAll("[^\\w\\s]","")+".json";

        try (PrintWriter out = new PrintWriter(new FileWriter(name))) {
            out.write(jsonObject.toString(4));
            out.close();
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Impossible to make Json file");
        }

    }

    /**
     * Code pour extraire les données sous toutes les formes prises en charges
     * c-à-d csv,yml,txt et json.
     * @throws FileNotFoundException
     * @throws JSONException
     */
    public void Extract_data() throws FileNotFoundException, JSONException {
        Extract_csv();
        Extract_txt();
        Extract_json();
    }

    @Override
    public String toString() {
        return "repository{" +
                "Url=" + Url +
                ", ref='" + ref + '\'' +
                ", All_builds=" + All_builds.size() +
                '}';
    }
}
