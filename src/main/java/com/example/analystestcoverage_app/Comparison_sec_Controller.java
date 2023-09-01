package com.example.analystestcoverage_app;

import Connect.Url_validator;
import Efficient.LoaderTask;
import Elements.Repository;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Comparison_sec_Controller implements Initializable {

    public Button Load;
    public Text title;
    public Button linechart;
    public Button back;
    public Text nameprojet1;
    public Text proj2name;
    public ComboBox branches1;
    public ComboBox datas1;
    public ComboBox branches2;
    public ComboBox datas2;
    public Label label1;
    public Label label2;
    public Label label3;
    public Label label4;
    public ProgressBar progressbar1;
    public ProgressBar progressbar2;
    public Text warningtext;
    public CheckBox localref;
    public CheckBox date;
    public Label labeldate2;
    public Label labeldate1;
    private int loc;

    private String Url1,Url2, name1,name2;

    private LoaderTask loader1, loader2;
    private Repository rep1, rep2;

    public List<Stage> stages = new ArrayList<>();



    public void pass(String s1, String s2) throws MalformedURLException {
        nameprojet1.setText(s1);
        proj2name.setText(s2);
        name1 = s1;
        name2 = s2;
        Url1 = Url_validator.start+s1;
        Url2 = Url_validator.start+s2;
        rep1 = new Repository(new URL(Url1) ,name1);
        rep2 = new Repository(new URL(Url2) ,name2);
    }

    public void Loade(ActionEvent actionEvent) throws Exception {

        invokeLoaderTask(loader1,name1,progressbar1,branches1,datas1,rep1,
                label1,label2,labeldate1);
        invokeLoaderTask(loader2,name2,progressbar2,branches2,datas2,rep2,
                label3,label4,labeldate2);
        Load.setVisible(false);
    }

    public void back(ActionEvent actionEvent) throws IOException {

        if(loader1 != null){
            if(loader1.isRunning()){
               loader1.cancel();
            }
        }
        if(loader2 != null){
            if(loader2.isRunning()){
                loader2.cancel();
            }
        }

        for (Stage s: stages
             ) {
            if(s !=null){
                s.close();
            }
        }

        FXMLLoader loader =new FXMLLoader(getClass().getResource("Comparison_first.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.setResizable(true);
        window.show();
    }

    public void lined(ActionEvent actionEvent) {

        if(loader1 != null){
            if(loader1.isRunning()){
                warningtext.setText("Wait until process 1  finish");
                warningtext.setVisible(true);
                return;
            }
        }
        if(loader2 != null){
            if(loader2.isRunning()){
                warningtext.setText("Wait until process 1  finish");
                warningtext.setVisible(true);
                return;
            }
        }

        try {
            String s =branches1.getSelectionModel().getSelectedItem().toString();
            String p =branches2.getSelectionModel().getSelectedItem().toString();
            String t =datas1.getSelectionModel().getSelectedItem().toString();
            String l =datas2.getSelectionModel().getSelectedItem().toString();

            System.out.println("Going to linescharts");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Det_lineschats.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Detailled_linescharts_Controller detailled_linescharts_controller = loader.getController();
           detailled_linescharts_controller.draw(rep1,rep2,
                   s,p,t,l);

            Stage stage1 = new Stage();

            stage1.setTitle("Line Chart: "+ rep1.getRef()+" & "+rep2.getRef() +
                    "tot "+ rep1.get_All_builds().size()+"-"+rep2.get_All_builds().size());
            stage1.setScene(scene);
            stage1.setResizable(true);
            stage1.show();
            stages.add(stage1);
            warningtext.setVisible(false);
        }catch(Exception e) {
            warningtext.setText("Make sure you select each required infos : all combobox");
            warningtext.setVisible(true);
           // e.printStackTrace();
        }
    }

    private void invokeLoaderTask(LoaderTask loader, String name ,ProgressBar pg,
                                  ComboBox branches , ComboBox datas1,Repository rep,
                                  Label lab, Label lab2, Label datum
                                  ) throws Exception {
        pg.setVisible(true);
        loader = new LoaderTask(name);
        loader.valueProperty().addListener((observableValue, build, t1) -> {
            try {
                rep.add_new_build(t1);
            } catch (Exception e) {
            }
        });
        pg.progressProperty().bind(loader.progressProperty());

        loader.titleProperty().addListener((observableValue, s, t1) -> {
            pg.setVisible(false);
            branches.getItems().addAll(rep.getBranch());
            lab.setText(String.valueOf(rep.get_All_builds().size()));
            lab2.setText(String.valueOf(rep.getCommitters().size()));
            lab.setVisible(true);
            lab2.setVisible(true);
            String date =rep.get_All_builds().last().getDate().toLocaleString().split(",")[0];
            datum.setText(date);
            datum.setVisible(true);

           branches.setDisable(false);
           datas1.setDisable(false);
        });

        Thread th = new Thread(loader);
        th.setDaemon(true);
        th.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datas1.getItems().addAll(detailled_interm_sec.metrics);
        datas2.getItems().addAll(detailled_interm_sec.metrics);
    }

    public void localeset(ActionEvent actionEvent) {
        if( localref.isSelected()){
            date.setSelected(false);
            loc = 0;
        }else{
            loc =1;
        }
    }

    public void datum(ActionEvent actionEvent) {
        if( date.isSelected()){
            localref.setSelected(false);
            loc = 1;
        }else{
            loc =0;
        }
    }
}
