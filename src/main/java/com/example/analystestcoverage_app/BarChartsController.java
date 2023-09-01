package com.example.analystestcoverage_app;

import com.github.jaiimageio.impl.plugins.gif.GIFImageWriter;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.*;


public class BarChartsController implements Initializable {
    public Button back;
    public Button save;
    public BarChart barcharts;
    public NumberAxis yaxis;
    public CategoryAxis xAxis;


    private ListView<String>[] lvews;
    private  int[] totals;

    public void pass(ListView<String>[] lvews, Text[] totals) {

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Number of project");


        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName("Covered project(s)");
        series2.setName("Total number of projects");

        //series1.setName("Covered");
        for (int i =0;i< lvews.length;i++){
            if(!lvews[i].getItems().isEmpty()){
                String o = "File "+(i+1);
                XYChart.Data<String,Number> d1 =new XYChart.Data<>(o,lvews[i].getItems().size());
                series1.getData().add(d1);
                int dex =Integer.parseInt(totals[i].getText().split(" ")[2]);
                XYChart.Data<String,Number> d2 =new XYChart.Data<>(o,dex) ;
                series2.getData().add(d2);
            }
        }
        barcharts.getData().addAll(series1,series2);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList
                ("File 1", "File 2", "File 3", "File 4", "File 5")));
        xAxis.setLabel("category");
        yaxis.setLabel("Total");
    }

    public void save(ActionEvent actionEvent) {
        SAVEIMAGE(barcharts, barcharts.getId());
        save.setDisable(true);

    }
    public static void SAVEIMAGE(Chart chart , String nam){
        WritableImage image = chart.snapshot(new SnapshotParameters(),null);
        String name ="Resources"+File.separator+"Images"+File.separator+"png"+File.separator
                +nam
                +".png";
        String namee ="Resources"+File.separator+"Images"+File.separator+"gif"+File.separator
                +nam
                +".gif";
        File f = new File(name);
        File fg = new File(namee);

        try{
            ImageIO.write(SwingFXUtils.fromFXImage(image,null),"png",f);
            ImageIO.write(SwingFXUtils.fromFXImage(image,null),"gif",fg);
        }catch(IOException exception){

        }
    }
}
