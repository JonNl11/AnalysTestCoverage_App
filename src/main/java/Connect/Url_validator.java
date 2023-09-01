package Connect;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class Url_validator {
    /**
     * Classe qui vérifie la validité d'une Url.
     */
    public URL url ;
    public static String start = "https://coveralls.io/github/";
    public static String coveralls_io = "https://coveralls.io";
    public static String separator= File.separator;

    public Url_validator(){}

    /**
     * Instantiateur de l'url.
     * @param t_url: Url sous forme de chaine de caractere.
     * @throws IOException
     */
    public Url_validator(String t_url) throws IOException {
        this.url = new URL(t_url);
    }

    /**
     * Méthode qui teste qu'une URL est correcte.
     * @return true si l'URL est correcte.
     * @throws IOException
     */
    public boolean isOk() throws IOException{
        try {
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("HEAD");
            huc.setConnectTimeout(1000000);
            int responseCode = huc.getResponseCode();
            boolean result = HttpURLConnection.HTTP_OK == responseCode;
            if (result) {
                return true;
            } else {
                return false;
            }
        }catch (UnknownHostException e){
            return false;
        }
    }

    /**
     * Setter d'Url.
     * @param url : nouvel url à tester.
     * @throws MalformedURLException
     */
    public void setUrl(String url) throws MalformedURLException {
        this.url = new URL(url);
    }
}
