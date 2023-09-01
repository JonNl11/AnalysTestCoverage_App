package com.example.analystestcoverage_app;

import Elements.Repository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class detailled_interm_sec implements Initializable {

    public ComboBox firstmtric;
    public ComboBox secondmetric;
    public Button scatterchart;
    public Text warningtext;
    public ComboBox combe;
    public List<Stage> stages= new ArrayList<>();


    private Repository repository;

    public static String[] metrics = new String[]{"Covered_lines","Total_lines","Nbr_of_Files","Percentage"};

    /**
     * Fonction qui fait le lien entre la page actuelle et la page précédente.
     * @param rep : le répertoire à analyser.
     */
    public void passe(Repository rep){
        repository = rep;
        combe.getItems().addAll(repository.getBranch());
    }

    /**
     * Cette méthode ouvre une nouvelle fenetre sur laquelle s'affiche un scatter plot.
     * @param actionEvent
     * @throws IOException
     */
    public void scatter(ActionEvent actionEvent) throws IOException {
        if(this.repository != null) {
           // System.out.println("scattt");
            try{
                String s1 = combe.getSelectionModel().getSelectedItem().toString();
                String s2 = firstmtric.getSelectionModel().getSelectedItem().toString();
                String s3 = secondmetric.getSelectionModel().getSelectedItem().toString();

                if(!s2.equalsIgnoreCase(s3)){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Scattercharts.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    //System.out.println("bikooookokok");
                    ScatterchartsController scatterchartsController = loader.getController();
                    scatterchartsController.pass(repository,s1,s2,s3);

                    Stage  stage2 = new Stage();
                    stages.add(stage2);
                    stage2.setTitle("Scatter Chart : "+repository.getRef()+ " tot "+
                            repository.get_All_builds().size());
                    stage2.setScene(scene);
                    stage2.setResizable(true);
                    stage2.show();
                    stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent windowEvent) {
                            stages.remove(stage2);
                        }
                    });

                    warningtext.setVisible(false);
                }else{
                    warningtext.setText("Metrics must be different.");
                    warningtext.setVisible(true);
                }
            }catch (Exception e){

                warningtext.setText("Make sure you select all fields.");
                warningtext.setVisible(true);

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        firstmtric.getItems().addAll(metrics);
        secondmetric.getItems().addAll(metrics);
    }

    public void secondmetricselected(ActionEvent actionEvent) {
    }

    public void firstmetrselected(ActionEvent actionEvent) {

    }
}
