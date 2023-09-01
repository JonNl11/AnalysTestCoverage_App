package com.example.analystestcoverage_app;

import Connect.Url_validator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ComparisonFirst_Controller {
    public ListView listview;
    public Button clear;
    public Button remove;
    public Button entrer;
    public Button Next;
    public Text warning;
    public Button back;
    public ObservableList<String> items = FXCollections.observableArrayList();
    public TextField texttext;

    /**
     * Cette méthode effance le contenu de la listeView.
     * @param actionEvent : Click sur lebouton clear.
     */
    public void clear(ActionEvent actionEvent) {
        items.clear();
        listview.getItems().clear();
       warning.setVisible(false);
    }

    /**
     * Cette méthode rétire l'élément selectionné de la listview.
     * @param actionEvent : Click sur le bouton remove.
     */
    public void remove(ActionEvent actionEvent) {
        try {
            int Id = listview.getSelectionModel().getSelectedIndex();
            listview.getItems().remove(Id);
            warning.setVisible(false);
        }catch (Exception e){
            warning.setText("the removal action is not available, the selection is null");
            warning.setVisible(true);
        }
    }
    /**
     * Méthode qui ouvre la page de comparaison des projets.
     * vérifie également qu'il y'a 2 projets passés dans la listView.
     * @param actionEvent : Click sur le bouton next.
     * @throws IOException
     */
    public void next(ActionEvent actionEvent) throws IOException {
        if(!listview.getItems().isEmpty() && (listview.getItems().size()==2)){
            FXMLLoader loader =new FXMLLoader(getClass().getResource("Comparison_sec.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            texttext.clear();

            Comparison_sec_Controller comparison_sec_controller = loader.getController();
            comparison_sec_controller.pass(listview.getItems().get(0).toString(),
                    listview.getItems().get(1).toString());
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(true);

        window.show();
        warning.setVisible(false);
        }else{
            warning.setText("Action next impossible : entrez 2 nom de projet.");
            warning.setVisible(true);
        }
    }

    /**
     * Cette méthode vérifie que le text passé dans le textfield est bien le nom d'un projet.
     * @param actionEvent : click sur le bouton enter.
     * @throws IOException
     */
    public void entrer(ActionEvent actionEvent) throws IOException {
        if(! texttext.getText().isEmpty()) {
            try {
                String s = texttext.getText();
                String Url = Url_validator.start + texttext.getText();
                Url_validator url = new Url_validator(Url);
                if(items.size()<2) {
                    if (url.isOk()) {
                        if(DetailledProjectController.is_repository(Url)) {
                            warning.setText("This project is a repository not a project : please enter correct project name.");
                            warning.setVisible(true);
                            texttext.clear();
                        }else{
                            if(!items.isEmpty()){
                                if(items.contains(s)){
                                    warning.setText("Cannot compare same projet");
                                    warning.setVisible(true);
                                    texttext.clear();
                                }else{
                                    items.add(s);
                                    texttext.clear();
                                    listview.setItems(items);
                                    warning.setVisible(false);
                                }
                            }else {
                                items.add(s);
                                texttext.clear();
                                listview.setItems(items);
                                warning.setVisible(false);
                            }
                            }
                    } else {
                        warning.setText("This project is probably not using test coverage : url incorrect");
                        warning.setVisible(true);
                        texttext.clear();
                        // textfprojectname.clear();
                    }
                }else {
                    warning.setText("Maximum atteint");
                    warning.setVisible(true);
                    texttext.clear();
                }
            }catch(Exception e ){
                warning.setText("This project is probably not using test coverage ");
                warning.setVisible(true);
                //textfprojectname.clear();
            }

        }else{
            warning.setText("The file name can't be Empty");
            warning.setVisible(true);

        }
    }

    /**
     * Action qui charge et renvoie la page précédente.
     * @param actionEvent: click sur le bouton back.
     * @throws IOException
     */

    public void back(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
