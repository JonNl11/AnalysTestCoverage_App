package com.example.analystestcoverage_app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Global_First_Step_Controller implements Initializable {


    public Button three;
    public Button one;
    public Button two;
    public ListView<String> lview;
    public TextField textfield;
    public Text text_warning;
    public Text entreevide;
    public Text introuvable;

    public ObservableList<String> items = FXCollections.observableArrayList();
    public Text maximum_warning;
    public Button fives;
    public Button browse;

    private FileChooser fc = new FileChooser();

    /**
     *  Ce code vérifie que le fichier passé en paramètre
     *  a bien une extension  .csv.
     * @param path : le chemin du fichier .
     * @return /true si le fichier respecte le format.
     */
    private boolean check_csv(String path){
        String [] spl = path.split("\\.");
        if(spl[spl.length-1].equalsIgnoreCase("csv")){
            return true;
        }
        return false;
    }

    /**
     * Ce code ouvre et initialise la fenêtre suivante ainsi que tous les tabs.
     *  Elle vérifie également que la listview ne soit pas vide.
     *
     * @param actionEvent : Action d'appuyer sur le bouton next.
     * @throws IOException
     */
    public void Going_next(ActionEvent actionEvent) throws IOException {
        //System.out.println("Going to the next page");

        if(!lview.getItems().isEmpty()){
            FXMLLoader loader =new FXMLLoader(getClass().getResource("Global_Sec_step.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            textfield.clear();

            Global_Sec_step global = loader.getController();
            global.BarCharts.setDisable(true);

            switch (lview.getItems().size()){
                case 1:
                    global.tab1.setDisable(false);
                    global.path1 = lview.getItems().get(0);

                    break;
                case 2:
                    global.tab1.setDisable(false);
                    global.path1 = lview.getItems().get(0);
                    global.tab2.setDisable(false);
                    global.path2 = lview.getItems().get(1);
                    break;
                case 3:
                    global.tab1.setDisable(false);
                    global.path1 = lview.getItems().get(0);
                    global.tab2.setDisable(false);
                    global.path2 = lview.getItems().get(1);
                    global.tab3.setDisable(false);
                    global.path3 = lview.getItems().get(2);
                    break;
                case 4:
                    global.tab1.setDisable(false);
                    global.path1 = lview.getItems().get(0);
                    global.tab2.setDisable(false);
                    global.path2 = lview.getItems().get(1);
                    global.tab3.setDisable(false);
                    global.path3 = lview.getItems().get(2);
                    global.tab4.setDisable(false);
                    global.path4 = lview.getItems().get(3);
                    break;
                case 5:
                    global.tab1.setDisable(false);
                    global.path1 = lview.getItems().get(0);
                    global.tab2.setDisable(false);
                    global.path2 = lview.getItems().get(1);
                    global.tab3.setDisable(false);
                    global.path3 = lview.getItems().get(2);
                    global.tab4.setDisable(false);
                    global.path4 = lview.getItems().get(3);
                    global.tab5.setDisable(false);
                    global.path5 = lview.getItems().get(4);
                    break;
            }
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.setResizable(true);

            window.show();
        }
    }

    /**
     * Ce code rajoute un fichier dans la liste view en verifiant le format.
     * @param actionEvent: action d'appuyer sur le bouton enter.
     */
    public void Enter(ActionEvent actionEvent) {
        if (textfield.getText().isEmpty()) {
            entreevide.setVisible(true);
            textfield.clear();
        }else {
            try {
                if (check_csv(textfield.getText())) {
                    if (items.size() < 5) {
                        if (!items.contains(textfield.getText())) {
                            File f = new File(textfield.getText());
                            if(f.exists()) {
                                items.add(textfield.getText());
                                lview.setItems(items);
                                textfield.clear();
                                text_warning.setVisible(false);
                                entreevide.setVisible(false);
                                introuvable.setVisible(false);
                            }else {
                                textfield.clear();
                                introuvable.setVisible(true);
                            }
                        } else {
                            textfield.clear();
                            introuvable.setVisible(false);
                        }
                    } else {
                        textfield.clear();
                        maximum_warning.setVisible(true);
                    }
                } else {
                    text_warning.setVisible(true);
                    textfield.clear();
                }
            } catch (Exception e) {
                textfield.clear();
                text_warning.setVisible(true);
            }
        }
    }

    /**
     * Cette action supprime tous les éléments de la ListView.
     * @param actionEvent : action d'appuyer sur le bouton clear;
     */
    public void Clear_listView(ActionEvent actionEvent) {
       items.clear();
        lview.getItems().clear();
        entreevide.setVisible(false);
        text_warning.setVisible(false);
    }

    /**
     * Code qui charge la page précédente de la scène.
     * @param actionEvent: Action d'appuyer sur le bouton back.
     * @throws IOException
     */
    public void Going_back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        textfield.clear();
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     *Ce code retire l'élément selectionné de la listview.
     * @param actionEvent: action d'appuyer sur le bouton remove.
     */
    public void removefromlist(ActionEvent actionEvent) {
        try {
            int Id = lview.getSelectionModel().getSelectedIndex();
            lview.getItems().remove(Id);
        }catch (Exception e){

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     *Ce code permet d'ouvrir une boîte de dialogue pour sélectionner les fichiers.
     * @param actionEvent : appuyer sur le bouton Browse.
     */

    public void browser(ActionEvent actionEvent) {
        fc.setTitle("Make selection");
        fc.setInitialDirectory(new File(System.getProperty("user.home")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files","*.csv"));

        List<File> files = fc.showOpenMultipleDialog(null);
        if(files != null) {
            for (int i = 0; i < files.size(); i++) {
                if (files != null) {
                    if(lview.getItems().size()<5) {
                        lview.getItems().add(files.get(i).getAbsolutePath());
                    }else{
                        text_warning.setText("5 files maximum selected");
                        text_warning.setVisible(true);
                    }
                } else {
                    text_warning.setText("This file is not valid!");
                    text_warning.setVisible(true);
                }
            }
        }
    }
}
