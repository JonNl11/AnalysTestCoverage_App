package com.example.analystestcoverage_app;

import Connect.Url_validator;
import Efficient.Metrics_Mnger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class Global_Sec_step implements Initializable {
    public Button BarCharts;
    public Button Back;
    public Tab tab1;
    public PieChart piech1;
    public ListView lv1;
    public Button detailled1;
    public Button Extract1;
    public Tab tab2;
    public PieChart piechar2;
    public ListView lv2;
    public Button detailled2;
    public Button Extract2;
    public Tab tab3;
    public PieChart piechart3;
    public ListView lv3;
    public Button detailled3;
    public Button Extract3;
    public Tab tab4;
    public PieChart piechar4;
    public ListView lv4;
    public Button detailled4;
    public Button extract4;
    public Tab tab5;
    public PieChart piechart5;
    public ListView lv5;
    public Button detailled5;
    public Button extract5;
    public ObservableList<String> ob1,ob2,ob3,ob4,ob5 = FXCollections.observableArrayList();

    public String path1,path2,path3,path4,path5;
    public Button charge1;
    public Button charge2;
    public Button charge3;
    public Button charge4;
    public Button charge5;
    public Text chargingdata;
    public Text chargingdata1;
    public Text chargingdata11;
    public Text chargedata5;
    public Text chargingdata5;
    public ProgressBar progress5;
    public ProgressBar progressbar4;
    public ProgressBar progressbar3;
    public ProgressBar progressbar2;
    public ProgressBar progress1;
    private Metrics_Mnger charger1,charger2,charger3,charger4,charger5;
    private Stage stage;


    /**
     *Méthode qui ouvre la fenêtre des barrescharts et tue tous les processus en cours.
     * @param actionEvent: action d'appuyer sur le bouton Barrecharts.
     * @throws IOException
     */
    public void Goestobarcharts(ActionEvent actionEvent) throws IOException {

        if(charger1!= null){
            if(charger1.isRunning()){
                charger1.cancel();
            }
        }
        if(charger2!= null){
            if(charger2.isRunning()){
                charger2.cancel();
            }
        }
        if(charger3!= null){
            if(charger3.isRunning()){
                charger3.cancel();
            }
        }
        if(charger4!= null){
            if(charger4.isRunning()){
                charger4.cancel();
            }
        }
        if(charger5!= null){
            if(charger5.isRunning()){
                charger5.cancel();
            }
        }
        progress1.setVisible(false);
        progressbar2.setVisible(false);
        progressbar3.setVisible(false);
        progressbar4.setVisible(false);
        progress5.setVisible(false);

        FXMLLoader loader =new FXMLLoader(getClass().getResource("barcharts.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        ListView<String> [] lvews= new ListView []{lv1,lv2,lv3,lv4,lv5};
        Text [] texts = new Text[]{chargingdata,chargingdata1,chargingdata11,chargedata5,chargingdata5};
        BarChartsController barChartsController = loader.getController();
        barChartsController.pass(lvews,texts);

        stage = new Stage();

        stage.setTitle("BarChart");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
        BarCharts.setDisable(true);

    }

    /**
     *  Cette méthode ouvre la page précédente.
     * @param actionEvent : Action d'appuyer sur le bouton back.
     * @throws IOException
     */

    public void Goes_back(ActionEvent actionEvent) throws IOException {
        if(charger1!= null){
            if(charger1.isRunning()){
                charger1.cancel();
            }
        }
        if(charger2!= null){
            if(charger2.isRunning()){
                charger2.cancel();
            }
        }
        if(charger3!= null){
            if(charger3.isRunning()){
                charger3.cancel();
            }
        }
        if(charger4!= null){
            if(charger4.isRunning()){
                charger4.cancel();
            }
        }
        if(charger5!= null){
            if(charger5.isRunning()){
                charger5.cancel();
            }
        }
        if(stage != null){
            stage.close();
        }

        Parent root = FXMLLoader.load(getClass().getResource("Global_First_step.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void Extract1(ActionEvent actionEvent) {
        try {
            Extract(lv1, path1,Extract1);
            Extract1.setDisable(true);
        }catch (FileNotFoundException e) {
            chargingdata.setText("Outer File issue: Not found");
            chargingdata.setVisible(true);
        }catch(Exception e) {
            chargingdata.setText("Outer File issue");
            chargingdata.setVisible(true);

        }
    }
    public void Detailled1(ActionEvent actionEvent) {
        try {
           Go_Detailled(lv1,actionEvent,path1);
        }catch (Exception e){

        }
    }

    public void detailled2(ActionEvent actionEvent) {
        try {
            Go_Detailled(lv2,actionEvent,path2);
        }catch (Exception e){

        }
    }

    public void Extract2(ActionEvent actionEvent) {
        try {
            Extract(lv2, path2,Extract2);
            Extract2.setDisable(true);
        }catch (FileNotFoundException e) {
            chargingdata1.setText("Outer File issue: Not found");
            chargingdata1.setVisible(true);
        }catch(Exception e) {
            chargingdata1.setText("Outer File issue");
            chargingdata1.setVisible(true);
        }

    }

    public void detailled3(ActionEvent actionEvent) {
        try {
            Go_Detailled(lv3,actionEvent,path3);
        }catch (Exception e){

        }
    }
    public void Extract3(ActionEvent actionEvent) {
        try {
            Extract(lv3, path3,Extract3);
            Extract3.setDisable(true);
        }catch (FileNotFoundException e) {
           chargingdata11.setText("Outer File issue: Not found");
            chargingdata11.setVisible(true);
        }catch(Exception e) {
            chargingdata11.setText("Outer File issue");
            chargingdata11.setVisible(true);
        }
    }
    public void detailled4(ActionEvent actionEvent) {
        try {
            Go_Detailled(lv4,actionEvent,path4);
        }catch (Exception e){

        }
    }
    public void extract4(ActionEvent actionEvent) {
        try {
            Extract(lv4, path4,extract4);
            extract4.setDisable(true);
        }catch (FileNotFoundException e) {
            chargedata5.setText("Outer File issue: Not found");
            chargedata5.setVisible(true);
        }catch(Exception e) {
            chargedata5.setText("Outer File issue/: liste vide");
            chargedata5.setVisible(true);
        }
    }
    public void Detailled5(ActionEvent actionEvent) {
        try {
            Go_Detailled(lv5,actionEvent,path5);
        }catch (Exception e){

        }
    }
    public void Extract5(ActionEvent actionEvent)  {
        try {
            Extract(lv5, path5,extract5);
            extract5.setDisable(true);
        }catch (FileNotFoundException e) {
            chargingdata5.setText("Outer File issue: Not found");
            chargingdata5.setVisible(true);
        }catch(Exception e) {
            chargingdata5.setText("Outer File issue");
            chargingdata5.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void charge1(ActionEvent actionEvent) throws IOException {
        try {
            charge1.setDisable(true);
            chargingdata.setVisible(false);
         invoke_charging(Extract1,detailled1,chargingdata,piech1,charge1
                 ,path1,lv1,charger1,progress1,Extract1);
         progress1.setVisible(true);
        }catch(Exception e){
            chargingdata.setText("Reload !!!!Echec du chargement");
            chargingdata.setVisible(true);
        }
    }
    public void charge2(ActionEvent actionEvent) throws IOException {
        try {
            charge2.setDisable(true);
            chargingdata1.setVisible(false);
          invoke_charging(Extract2,detailled2,chargingdata1,piechar2,charge2,path2,
                  lv2,charger2,progressbar2,Extract2);
          progressbar2.setVisible(true);
        }catch (Exception e){
            chargingdata1.setText("Reload !!!!Echec du chargement");
            chargingdata1.setVisible(true);
        }
    }

    public void charge3(ActionEvent actionEvent) throws IOException {
        try {
            charge3.setDisable(true);
            chargingdata11.setVisible(false);
            invoke_charging(Extract3,detailled3,chargingdata11,piechart3,charge3
                    ,path3,lv3,charger3,progressbar3,Extract3);
            progressbar3.setVisible(true);
        }catch(Exception e){
            chargingdata11.setText("Reload !!!!Echec du chargement");
            chargingdata11.setVisible(true);
        }
    }

    public void charge4(ActionEvent actionEvent) throws IOException {
        try {
            charge4.setDisable(true);
            chargedata5.setVisible(false);
            invoke_charging(extract4,detailled4,chargedata5,piechar4,charge4,
                    path4,lv4,charger4,progressbar4,extract4);
            progressbar4.setVisible(true);
        }catch(Exception e){
            chargedata5.setText("Reload !!!!Echec du chargement");
            chargedata5.setVisible(true);
        }

    }

    public void charge5(ActionEvent actionEvent) throws IOException {
        try {
            charge5.setDisable(true);
            chargingdata5.setVisible(false);
          invoke_charging(extract5,detailled5,chargingdata5,piechart5,charge5,path5
                  ,lv5,charger5,progress5,extract5);
          progress5.setVisible(true);
        }catch(Exception e){
            chargingdata5.setText("Reload !!!!Echec du chargement");
            chargedata5.setVisible(true);
        }
    }

    /**
     * Méthode qui recopie dans un fichier txt les élémnts issus de l'analyse globale.
     * @param s : Liste de élémént à copier.
     * @param path1 : nom de la destination.
     * @param b : Button d'extraction afin de le bloquer après utilisation.
     * @throws FileNotFoundException
     */
    private void Extract(ListView s,String path1,Button b) throws FileNotFoundException {
              File pl = new File(path1);
              String name ="Resources"+File.separator+"Outer"+ File.separator+pl.getName()+"_covered"+".txt";
              File csv = new File(name);
              PrintWriter out = new PrintWriter(csv);
              int i = 0;
              out.printf("%s\n","name(s)" );
              //out.println();
              for (int j = 0; j < s.getItems().size(); j++) {
                  out.printf("%s\n",s.getItems().get(j));
              }
              out.close();
              b.setDisable(true);
    }

    /**
     * /**
     * Cette méthode charge les fichiers et ajoute les elements trouvés dans
     * les ListViews.
     * Elle lance un Thread qui extrait les informations démandés et initialise le pieChart.
     *
     * @param Extract : Bouton d'extraction qui reste bloqué pendant le processus.
     * @param detail : button détail qui reste bloqué pendant le processus.
     * @param text : Commentaire à afficher à la fin du processus contenant les informations.
     * @param pie : Le piechart de la fenetre correspondante afin de l'initialiser.
     * @param b : bouton load qui disparait après son utilisation.
     * @param path : nom du fichier analysé.
     * @param lv : Listeview qui va contenir les éléments trouvés.
     * @param charge : le code qui effectue l'extraction.
     * @param pb : la barre de progrès de la fenètre en chargement afin de suivre l'évolution.
     * @param extrac : Bouton d'extraction bloqué pendant le processus.
     * @throws FileNotFoundException
     */
    private void invoke_charging( Button Extract,Button detail ,Text text,
                                 PieChart pie ,Button b,String path,
                                 ListView<String>lv, Metrics_Mnger charge,
                                 ProgressBar pb,
                                  Button extrac
                                  ) throws FileNotFoundException {

        File f = new File(path);
        charge =new Metrics_Mnger(f,",");

        ListView<String > temp=new ListView<>();
        charge.valueProperty().addListener((observableValue, s, t1) -> {
            if(t1 != null){
                lv.getItems().add(t1);
                BarCharts.setDisable(true);
                b.setDisable(true);
            }
        });
        charge.messageProperty().addListener((observableValue, s, t1) -> {
            temp.getItems().add(t1);
        });
        charge.titleProperty().addListener((observableValue, s, t1) -> {
            b.setDisable(true);
            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Covered ", lv.getItems().size()),
                    new PieChart.Data("Non Covered ", (temp.getItems().size()-lv.getItems().size()))
            );
            File fr = new File(path);
            pie.setTitle("Resultat fichier " + fr.getName());
            pie.setData(pieData);
            int sum = temp.getItems().size();
            pie.getData().forEach(data -> {
                String percentage = String.format("Tot = %s file(s),%.2f%%",String.valueOf(Integer.valueOf((int) data.getPieValue())) ,((data.getPieValue() * 100) / sum));
                Tooltip tool = new Tooltip(percentage);
                tool.install(data.getNode(), tool);
            });
            b.setDisable(true);
            detail.setDisable(false);
            Extract.setDisable(false);
            text.setText(lv.getItems().size() +" over "+ temp.getItems().size()+" projects covered.");
            pb.setVisible(false);
          text.setVisible(true);
          extrac.setVisible(true);
          detail.setVisible(true);
          BarCharts.setDisable(false);
        });
        Thread th = new Thread(charge);
        th.setDaemon(true);
        th.start();

    }

    /**
     * Cette méthode ouvre la partie single project analysis avec le projet selectionné.
     * @param lv : liste des éléments (nom de projet) utilisant une couverture de test.
     * @param actionEvent : Action de presser sur le bouton detailled.
     * @param path : nom du fichier.
     * @throws IOException
     */
    private void Go_Detailled(ListView<String>lv,ActionEvent actionEvent,String path) throws IOException {
                try{
                    String s =lv.getSelectionModel().getSelectedItem().toString();
                    FXMLLoader loader =new FXMLLoader(getClass().getResource("Detailled_One.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    try{
                        String Url = Url_validator.start +s;
                        Url_validator url = new Url_validator(Url);
                        if(url.isOk()){
                            DetailledOne_Controller detailledOne_controller = loader.getController();

                            detailledOne_controller.textprincipal.setText(path);
                            detailledOne_controller.pass(Url,s);
                            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            window.setScene(scene);
                            window.setResizable(true);
                            window.show();
                        }

                    }catch (Exception e){    }
                }catch (Exception e){ }
    }
}
