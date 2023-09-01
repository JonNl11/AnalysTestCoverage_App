package com.example.analystestcoverage_app;

import Efficient.LoaderTask;
import Elements.Build;
import Elements.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailledOne_Controller implements Initializable {
    public Button Back;
    public Text textprincipal;
    public Button linecharts;
    public Button scatter;
    public ListView lv;
    public Button Extractcsv;
    public Button extractJson;
    public Button extract_txt;
    public Button load;
    public ProgressBar progressbar;
    public TableView tabview;
    public TableColumn<Build,String> Branche;
    public TableColumn<Build,String> committer;
    public TableColumn<Build,Integer> nbr_offiles;
    public TableColumn<Build,Integer> coveredlines;
    public TableColumn<Build,Integer> totlines;
    public TableColumn<Build,Double> percentage;
    public Label labdetail;
    private String Url;
    private File pather;
    private Repository repository;
    private String s;
    private  LoaderTask loaderTask;
    private ObservableList<Build>ob = FXCollections.observableArrayList();

    private Stage stage1,stage2;

    private Detailled_Intermediaire_controller detailled_intermediaire_controller;
    private detailled_interm_sec detailled_interm_sec;

    /**
     * Méthode qui retourne à la page précédente.
     * Elle arrète le Task si celui-ci etait en train de tourner et ferme toutes les scènes
     * créées pour afficher les graphiques.
     * @param actionEvent
     * @throws IOException
     */
    public void Back(ActionEvent actionEvent) throws IOException {


        Parent root = FXMLLoader.load(getClass().getResource("Detailled_project.fxml"));
        Scene scene = new Scene(root);

        if(loaderTask != null )
            if(loaderTask.isRunning()){
            loaderTask.cancel();
        }
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        if(stage1!= null ) {
            if (stage1.isShowing()) {
                if (detailled_intermediaire_controller != null) {
                    for (Stage s : detailled_intermediaire_controller.stages
                    ) {
                        if (s != null) {
                            s.close();
                        }
                    }
                }
                stage1.close();
            }
        }

        if(stage2!= null) {
            if (stage2.isShowing()) {
                if (detailled_interm_sec != null) {
                    for (Stage s : detailled_interm_sec.stages
                    ) {
                        if (s != null) {
                            s.close();
                        }
                    }
                }
            }
            stage2.close();
        }
    }

    /**
     * En appuyant sur le bouton line chart, cette méthode ouvre la page de configuration des
     * lines charts en verifiant que le repertoire n'est pas vide.
     * @param actionEvent
     * @throws IOException
     */
    public void lineCharts(ActionEvent actionEvent) throws IOException {
        if(stage1 == null || (!stage1.isShowing())) {
            if (this.repository != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailled_intermediaire.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.detailled_intermediaire_controller = loader.getController();
                //System.out.println("taille "+ repository.get_All_builds().size());
                this.detailled_intermediaire_controller.pass(this.repository);

                stage1 = new Stage();

                stage1.setTitle("Line Chart: "+ repository.getRef()+ "tot "+ repository.get_All_builds().size());
                stage1.setScene(scene);
                stage1.setResizable(false);
                stage1.show();
                stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                    if (detailled_intermediaire_controller != null) {
                        for (Stage s : detailled_intermediaire_controller.stages
                        ) {
                            if (s != null) {
                                s.close();
                            }
                        }
                    }
                    }
                });
            }
        }
    }

    /**
     * En appuyant sur le bouton scatter chart, cette méthode ouvre la page de configuration des
     * scatter charts en verifiant que le repertoire n'est pas vide.
     * @param actionEvent
     * @throws IOException
     */
    public void scattercharts(ActionEvent actionEvent) throws IOException {
        if(stage2 == null || (!stage2.isShowing())) {
            if(this.repository != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailled_intermediaire_sec.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                this.detailled_interm_sec =loader.getController();
                //detailled_interm.pass(this.repository);
                 this.detailled_interm_sec.passe(this.repository);
                 stage2 = new Stage();

                stage2.setTitle("Scatter Chart : "+repository.getRef()+ " tot "+ repository.get_All_builds().size());
                stage2.setScene(scene);
                stage2.setResizable(false);
                stage2.show();
                stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        if (detailled_interm_sec != null) {
                            for (Stage s : detailled_interm_sec.stages
                            ) {
                                if (s != null) {
                                    s.close();
                                }
                            }
                        }
                    }
                });

        }
        }
    }

    public void setUrl(String path) {
        this.Url = path;
    }

    public void setPather(String pather) {
        this.pather = new File(pather);
    }

    /**
     * Permet de faire le lien entre la page précédente de selection d'un projet
     * et passe celui-ci pour pouvoir charger les données.
     * @param Url
     * @param rep
     * @throws Exception
     */
    public void pass(String Url ,String rep ) throws Exception {
        this.s =rep;
        this.Url = Url;
        textprincipal.setText(rep);
        textprincipal.setVisible(true);

    }

    /**
     * En appuyant sur le bouton Extract csv permet d'extraire u fichier au format csv.
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void extractcsv(ActionEvent actionEvent) throws FileNotFoundException {

            if (this.repository != null) {
                repository.Extract_csv();
                Extractcsv.setDisable(true);
            }

    }
    /**
     * En appuyant sur le bouton Extract csv permet d'extraire u fichier au format json.
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void Extractjson(ActionEvent actionEvent) throws FileNotFoundException {
        if(this.repository!= null){
            repository.Extract_json();
            extractJson.setDisable(true);
        }
    }
    /**
     * En appuyant sur le bouton Extract csv permet d'extraire u fichier au format txt.
     * @param actionEvent
     * @throws FileNotFoundException
     */
    public void extracttxt(ActionEvent actionEvent) throws FileNotFoundException {
        if(this.repository!= null){
            repository.Extract_txt();
            extract_txt.setDisable(true);
        }
        progressbar.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * Initialisation de la tabview
         */

        Branche.setCellValueFactory(new PropertyValueFactory<Build,String>("branch"));

        committer.setCellValueFactory(new PropertyValueFactory<Build,String>("commiter"));

        nbr_offiles.setCellValueFactory(new PropertyValueFactory<Build,Integer>("nbr_files"));

        coveredlines.setCellValueFactory(new PropertyValueFactory<Build,Integer>("covered_lines"));

        totlines.setCellValueFactory(new PropertyValueFactory<Build,Integer>("total_lines"));

        percentage.setCellValueFactory(new PropertyValueFactory<Build,Double>("percentage"));

    }

    /**
     * Cette méthode invoque un nouveau thread pour extraire les données d'un projet.
     * @param actionEvent
     * @throws Exception
     */
    public void load(ActionEvent actionEvent) throws Exception {
        this.repository = new Repository(new URL(Url),s);
        invokeLoaderTask();

    }

    /**
     * Cette méthode ouvre un Thread pour extraire les données.
     * Si un thread est entrain de tourner il arrète celui-ci.
     * @throws Exception
     */
    private void invokeLoaderTask() throws Exception {
        if (loaderTask != null) {
            if (loaderTask.isRunning()) {
                loaderTask.cancel();
                progressbar.setVisible(false);
                load.setDisable(false);
                repository.get_All_builds().addAll(tabview.getItems());
                loaderTask = null;
            }
        } else {
            tabview.getItems().clear();
            progressbar.setVisible(true);
            loaderTask = new LoaderTask(s);
            loaderTask.valueProperty().addListener((observableValue, build, t1) -> {
                try {
                    tabview.getItems().add(t1);
                    this.repository.add_new_build(t1);
                    labdetail.setText(String.valueOf(tabview.getItems().size()));
                } catch (Exception e) {
                }
            });
            progressbar.progressProperty().bind(loaderTask.progressProperty());

            loaderTask.titleProperty().addListener((observableValue, s, t1) -> {
                if (!tabview.getItems().isEmpty()){

                    progressbar.setVisible(false);
                Extractcsv.setDisable(false);
                extract_txt.setDisable(false);
                extractJson.setDisable(false);
                linecharts.setDisable(false);
                scatter.setDisable(false);
                }
            });

            Thread th = new Thread(loaderTask);
            th.setDaemon(true);
            th.start();
        }
    }
}
