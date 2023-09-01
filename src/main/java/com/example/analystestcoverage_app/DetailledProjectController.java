package com.example.analystestcoverage_app;


import Connect.Url_validator;
import Efficient.File_loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailledProjectController implements Initializable {
    public TextField textfprojectname;
    public TextField projectpath;
    public Button ok1;
    public Button back;
    public Text warning;
    public ChoiceBox choicestals;
    public ComboBox combo1;
    public Button ok2;

    /**
     * Action qui ouvre la page de l'analyse  individuelle de projets.
     * passé est un nom de projet.
     * @param actionEvent : click sur le premier bouton ok.
     * @throws IOException
     */
    public void ok1(ActionEvent actionEvent) throws IOException {
        if(!textfprojectname.getText().isEmpty()) {

            FXMLLoader loader =new FXMLLoader(getClass().getResource("Detailled_One.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            try {
                String s = textfprojectname.getText();
                String Url = Url_validator.start + textfprojectname.getText();
                Url_validator url = new Url_validator(Url);
                if(url.isOk()){

                    if(is_repository(Url)){
                        warning.setText("This project is a repository not a project : please enter correct project name.");
                        warning.setVisible(true);
                        textfprojectname.clear();
                    }else {

                        DetailledOne_Controller detailledOne_controller = loader.getController();
                        detailledOne_controller.pass(Url, s);

                        textfprojectname.clear();
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(scene);
                        window.setResizable(true);
                        window.show();
                        warning.setVisible(false);
                        ok2.setDisable(true);
                        ok1.setDisable(true);
                    }
                }else{
                    warning.setText("This project is probably not using test coverage : url incorrect");
                    warning.setVisible(true);
                    textfprojectname.clear();
                }
            }catch(Exception e ){
                warning.setText("This project is probably not using test coverage ");
                warning.setVisible(true);
                textfprojectname.clear();
            }

        }else{
            warning.setText("The file name can't be Empty");
            warning.setVisible(true);
        }
    }

    /**
     * Code qui charge la page précédente de la scène.
     * @param actionEvent: Action d'appuyer sur le bouton back.
     * @throws IOException
     */
    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Cette méthode initialise le combobox de la partie Single project analysis
     * avec les projets extraits des 4 premiers fichiers de Resources\Inner.
     *
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String name = "Resources"+File.separator+"Outer" ;
        File f = new File(name);

        File [] pathsnames ;
        pathsnames = f.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".txt");
            }
        });

        File_loader charge ;
        int limit = 4;
        for (File fl: pathsnames  ) {
          if(limit !=0) {
              try {
                  charge = new File_loader(fl, combo1);
                  Thread th = new Thread(charge);
                  th.setDaemon(true);
                  th.start();
              } catch (Exception e) {
              }
              limit--;
          }
        }
    }
    /**
     * Cette méthode ouvre la page suivante de l'analyse single avec la donnée selectionnée du
     * combobox.
     * @param actionEvent: click sur le deuxième bouton ok.
     * @throws Exception
     */
    public void ok2(ActionEvent actionEvent) throws Exception {
        try{
            String s = combo1.getSelectionModel().getSelectedItem().toString();
            FXMLLoader loader =new FXMLLoader(getClass().getResource("Detailled_One.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            String Url = Url_validator.start + s;
            Url_validator url = new Url_validator(Url);
            if(url.isOk()) {
                DetailledOne_Controller detailledOne_controller = loader.getController();
                detailledOne_controller.pass(Url, s);
                textfprojectname.clear();
                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.setResizable(true);
                window.show();
                warning.setVisible(false);
                ok2.setDisable(true);
                ok1.setDisable(true);
            }
        }catch(Exception s){
            warning.setText("You have to choose a project in the list.");
            warning.setVisible(true);
        }
    }
    /**
     * Cette méthode vérifie que l'Url passé correspond à la page Coveralls d'un repertoire.
     * @param url : l'élément à vérifier.
     * @return vrai si l'url correspond à un repertoire.
     * @throws IOException
     */
    public static boolean is_repository(String url) throws IOException {
        Document df =  Jsoup.connect(url).timeout(10000).get();
        String ones = df.title().split("\\|")[0];
        String[] sop = ones.split(" ");
        if(sop.length > 1){
            return true;
        }
        return false;
    }
}
