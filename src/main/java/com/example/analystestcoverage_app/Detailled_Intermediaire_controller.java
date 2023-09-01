package com.example.analystestcoverage_app;

import Elements.Repository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Detailled_Intermediaire_controller implements Initializable {
    /**
     * Cette classe rassemble toutes les méthodes utilisées pour configurer un line Chart.
     * Elle controle ainsi la page de configuration du lineChart
     */
    public Button linecharts;
    public ComboBox combobox1;
    public CheckBox checklocalref;
    public CheckBox checkdate;
    public CheckBox percentage;
    public CheckBox coveredlines;
    public CheckBox total_lines;
    public CheckBox numberfiles;
    public Text warningtext;
    private Repository rep ;
    private int number=0;
    private int localref = 0;

    public List<Stage> stages = new ArrayList<>();


    /**
     * Cette méthode ouvre la fenetre de Line Chart et affiche le resultat de la selection.
     * @param actionEvent
     * @throws IOException
     */
    public void linecharts(ActionEvent actionEvent) throws IOException {
        if (this.rep != null) {
            try {
                String s =combobox1.getSelectionModel().getSelectedItem().toString();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Det_lineschats.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Detailled_linescharts_Controller detailled_linescharts_controller = loader.getController();
            detailled_linescharts_controller.pass(rep,s,number,localref);

           Stage  stage1 = new Stage();
           stages.add(stage1);

            stage1.setTitle("Line Chart: "+ rep.getRef()+ "tot "+ rep.get_All_builds().size());
            stage1.setScene(scene);
            stage1.setResizable(true);
            stage1.show();
            stage1.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
               //     stages.remove(stage1);
                }
            });
            warningtext.setVisible(false);
            }catch(Exception e){
                warningtext.setText("You must select a branch.");
                warningtext.setVisible(true);
                e.printStackTrace();
            }
        }
    }

    /**
     * Code qui initialise le combobox avec les branches extraites du repertoire passé en paramètre.
     * @param rep : repertoire contenant tous les builds.
     */
    public void pass(Repository rep){
        this.rep = rep;
        for (String s: rep.getBranch()
             ) {
            combobox1.getItems().add(s);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    /**
     * Cette méthode permet de selectionner alternativement entre la local reference et la date.
     * Elle met également à jour le code de à passer dans le line chart.
     * @param actionEvent
     */
    public void selectdate(ActionEvent actionEvent) {
       if(checkdate.isSelected()) {
           checklocalref.setSelected(false);
           localref =1;
       }else{
           if(localref != 0) {
               localref = 0;
           }
       }
    }
    /**
     * Cette méthode permet de selectionner alternativement entre la local reference et la date.
     * Elle met également à jour le code de à passer dans le line chart.
     * @param actionEvent
     */
    public void localrefff(ActionEvent actionEvent) {
        if( checklocalref.isSelected()){
            checkdate.setSelected(false);
            localref = 0;
        }else{
         localref =1;
        }
    }

    /**
     * Méthode qui modifie le code de l'affichage en fonction des métriques sélectionnées.
     * Si le Nombre de fichier est selectionné il rajoute 1 dans le code.
     * @param actionEvent
     */

    public void fn(ActionEvent actionEvent) {
        if(numberfiles.isSelected()){
            number+=1;
        }else{
            number-=1;
        }
    }

    /**
     * Méthode qui modifie le code de l'affichage en fonction des métriques sélectionnées.
     *  Si le Nombre Total de lignes est selectionné il rajoute 10 dans le code.
     * @param actionEvent
     */
    public void totolines(ActionEvent actionEvent) {
        if (total_lines.isSelected()) {
            number += 10;
        }else{
            number-=10;
            }
    }
    /**
     * Méthode qui modifie le code de l'affichage en fonction des métriques sélectionnées.
     *  Si le Nombre de lignes couvertes est selectionné il rajoute 100 dans le code.
     * @param actionEvent
     */
    public void covererrlines(ActionEvent actionEvent) {
        if(coveredlines.isSelected()){
            number+= 100;
        }else{
            number-=100;
        }
    }
    /**
     * Méthode qui modifie le code de l'affichage en fonction des métriques sélectionnées.
     *  Si le pourcentage est selectionné il rajoute 1000 dans le code.
     * @param actionEvent
     */
    public void percentee(ActionEvent actionEvent) {
        if(percentage.isSelected()){
            number += 1000;
        }else{
            number-= 1000;
        }
    }
}
