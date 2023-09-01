package Elements;

import java.util.Date;
import java.util.Objects;

public class Build implements Comparable<Build> {
    /**
     * Cette classe représente un build c-à-d l'ensemble de classes qu'un contributeur.
     * du projet soumet à un moment donné.
     */
    private  String local_reference;
    private String global_reference;
    private  String committer;
    private Date date;
    private String url;
    private String branch;

    private int total_lines,covered_lines;
    private double percentage;
    private int nbr_files;
    private boolean charged =false;


    public Build(){}

    /**
     * Instatiateur de Builds par référence locale.
     * @param local_reference: référence du build dans le projet.
     */
    public Build(String  local_reference) {

        this.local_reference = local_reference;

    }

    /**
     * Instantiateur de build par les attributs suivants.
     * @param local_reference: référence locale.
     * @param commiter : le nom du contributeur .
     * @param date : la date du commit.
     */
    public Build(String local_reference, String commiter, Date date) {
        this.local_reference = local_reference;
        this.committer = commiter;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Getter de la référence locale.
     * @return la référence locale.
     */
    public String getLocal_reference() {
        return local_reference;
    }

    /**
     * Getter de la référence globale.
     * @return la référence globale.
     */
    public String getGlobal_reference() {
        return global_reference;
    }

    /**
     * Getter du nom du contributeur .
     * @return le nom du contributeur .
     */
    public String getCommiter() {
        return committer;
    }

    /**
     * Getter du nom de la branche.
     * @return : le nom de la branche.
     */
    public String getBranch() {
        return branch;
    }

    /**
     * Getter de la date du commit.
     * @return la date du commit.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter du nombre total de lignes d'un build .
     * @return le nombre total de lignes.
     */
    public int getTotal_lines() {
        return total_lines;
    }

    /**
     * Getter du nombre de lignes couvertes d'un build.
     * @return le nombre de lignes couvertes.
     */
    public int getCovered_lines() {
        return covered_lines;
    }

    /**
     * Getter du pourcentage de couverture.
     * @return le pourcentage de couverture.
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Getter du nombre de fichiers contenus dans un build.
     * @return le nombre de fichiers contenus dans le build.
     */
    public int getNbr_files() {
        return nbr_files;
    }

    /**
     * Setter du nombre de lignes totales d'un build.
     * @param total_lines le nombre total de lignes.
     */
    public void setTotal_lines(int total_lines) {
        this.total_lines = total_lines;
    }

    /**
     * Setter de la rréférence globale c-à-d la référence du build dans le site Coveralls indépendamment
     * du projet.
     * @param global_reference : référence globale.
     */
    public void setGlobal_reference(String global_reference) {
        this.global_reference = global_reference;
    }

    /**
     * Setter de la date
     * @param date: date du commit.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Setter de la rréférence locale c-à-d la référence du build dans le site Coveralls en fonction du nom de projet.
     * du projet.
     * @param local_reference : référence locale.
     */
    public void setLocal_reference(String local_reference) {
        this.local_reference = local_reference;
    }

    /**
     * Setter du nombre de lignes couvertes .
     * @param covered_lines nombres de lignes couvertes.
     */
    public void setCovered_lines(int covered_lines) {
        this.covered_lines = covered_lines;
    }

    /**
     * Setter du pourcentage de couverture d'un build.
     * @param percentage : pourcentage de couverture.
     */
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    /**
     * Setter du nombre de fichiers.
     * @param nbr_files : nombres de classes.
     */
    public void setNbr_files(int nbr_files) {
        this.nbr_files = nbr_files;
    }

    /**
     * Setter du committer
     * @param committer la personne qui a fait le commit.
     */
    public void setCommitter(String committer) {
        this.committer = committer;
    }

    /**
     * Setter de la branch du build.
     * @param branch : nom de la branche.
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * Setter de l'Url du build.
     * @param url: Url du build.
     */
    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "\"Builds\": { " +
                "\"local_reference\":" + local_reference + ",\n" +
                "\" global_reference\":" + global_reference + ",\n"+
                "\" committer\":" + committer + ",\n" +
                "\"branch\":"+ branch+",\n"+
                "\" date\":" + date + ",\n"+
                "\" Url\":" + url + ",\n" +
                "\" total_lines\":" + total_lines +",\n"+
                "\" covered_lines\":" + covered_lines + ",\n"+
                "\" percentage\":" + percentage + ",\n"+
                "\" nbr_files\":" + nbr_files + ",\n"+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Build builds = (Build) o;
        return total_lines == builds.total_lines
                && covered_lines == builds.covered_lines
                && Double.compare(builds.percentage, percentage) == 0
                && nbr_files == builds.nbr_files
                && Objects.equals(local_reference, builds.local_reference)
                && Objects.equals(global_reference, builds.global_reference)
                && Objects.equals(committer, builds.committer)
                && Objects.equals(date, builds.date)
                && Objects.equals(url, builds.url)
                && Objects.equals(branch, builds.branch);
    }

    @Override
    public int hashCode() {
        return Objects.hash(local_reference, global_reference, committer, date, url, branch, total_lines, covered_lines, percentage, nbr_files);
    }

    /**
     * Méthode de comparaison des builds pour les ajouters à l'ensemble treeset ordonné.
     * @param o: Un autre objet build.
     * @return
     */
    @Override
    public int compareTo(Build o) {
        Date o_d = o.getDate();
        if(this.date.before(o_d)){
            return -1;
        }else if(o_d.before(this.date)){
            return 1;
        }else{
            int href =Integer.parseInt(this.local_reference);
            int other = Integer.parseInt(o.getLocal_reference());
            if(href < other){
                return -1;
            }else {
                return 1;
            }

        }
    }
}
