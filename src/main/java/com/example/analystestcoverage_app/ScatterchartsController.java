package com.example.analystestcoverage_app;

import Elements.Build;
import Elements.Repository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


public class ScatterchartsController implements Initializable {
    public ScatterChart scatter;
    public Button Save;
    public NumberAxis total;
    public NumberAxis builds;
    public Label detaillabel;
    private String name;


    public void save(ActionEvent actionEvent) {
        BarChartsController.SAVEIMAGE(scatter,name);
        Save.setDisable(true);
    }

    /**
     * Methode qui dessine un scatter chart en fonction des paramètres passés.
     * @param repository
     * @param branch
     * @param fme
     * @param secmet
     */
    public void pass(Repository repository, String branch, String fme, String secmet) {

        builds.setLabel(fme);
        total.setLabel(secmet);
        this.name = "Scatter_"+repository.getRef().replaceAll("[^\\w\\s]","");

        int minx=0;
        int miny = 0;
        int max_x =0;
        int maxY= 0;


        XYChart.Series serie = new XYChart.Series<>();
        serie.setName("Build(s)");

        Iterator<Build>it = repository.get_All_builds().iterator();
        while(it.hasNext()){
            Build b = it.next();

            if(b.getBranch().equalsIgnoreCase(branch)){
                int one =0;
                int two =0;

                if(fme.equalsIgnoreCase("covered_lines")){
                    one = b.getCovered_lines();
                   // one = Double.valueOf(b.getCovered_lines());
                    if(minx>one){
                        minx = one;
                    }
                    if(max_x <one){
                        max_x = one;
                    }
                }
                if(fme.equalsIgnoreCase("Total_lines")){
                   one = b.getTotal_lines();
                    //one = Double.valueOf(b.getTotal_lines());
                    if(minx>one){
                        minx = one;
                    }
                    if(max_x <one){
                        max_x = one;
                    }
                }
                if(fme.equalsIgnoreCase("Nbr_of_Files")){
                    one =b.getNbr_files();
                    //one = Double.valueOf(b.getNbr_files());
                    if(minx>one){
                        minx = one;
                    }
                    if(max_x <one){
                        max_x = one;
                    }
                }
                if(fme.equalsIgnoreCase("Percentage")){
                    one = (int)(b.getPercentage());
                    //one = Double.valueOf((int)(b.getPercentage()));
                    if(minx>one){
                        minx = one;
                    }
                    if(max_x <one){
                        max_x = one;
                    }
                }
                if(secmet.equalsIgnoreCase("covered_lines")){
                    two = b.getCovered_lines();
                    //two = Double.valueOf(b.getCovered_lines());
                    if(miny >two){
                        miny = two;
                    }
                    if(maxY < two){
                        maxY = two;
                    }
                }
                if(secmet.equalsIgnoreCase("Total_lines")){
                    two = b.getTotal_lines();
                    //two = Double.valueOf(b.getTotal_lines()) ;
                    if(miny >two){
                        miny = two;
                    }
                    if(maxY < two){
                        maxY = two;
                    }
                }
                if(secmet.equalsIgnoreCase("Nbr_of_Files")){
                    two = b.getNbr_files();
                    //two = Double.valueOf(b.getNbr_files());
                    if(miny >two){
                        miny = two;
                    }
                    if(maxY < two){
                        maxY = two;
                    }
                }
                if(secmet.equalsIgnoreCase("Percentage")){
                    two = (int) b.getPercentage();
                   // two =Double.valueOf( (int) b.getPercentage());
                    if(miny >two){
                        miny = two;
                    }
                    if(maxY < two){
                        maxY = two;
                    }
                }
                XYChart.Data<Number,Number> d= new XYChart.Data<>(one,two);
                serie.getData().add(d);
                d.setExtraValue(b);
            }
        }
/*
        builds.setUpperBound(max_x);
        builds.setLowerBound(minx);

        total.setUpperBound(maxY);
        total.setLowerBound(miny);
*/
        scatter.getData().add(serie);
        labeliser(serie);
    }
    /**
     * Méthode qui rajoute un label aux points du Line charts.
     * @param fll : liste des points.
     */
    private void labeliser(XYChart.Series<String, Integer> fll){
        for (final XYChart.Data<String, Integer> d : fll.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Build s = (Build) d.getExtraValue();
                    detaillabel.setText("Build{ \n loc ref : \n"+s.getLocal_reference()+  "\n"+
                            " Commiter : \n" + s.getCommiter()+" \n}"
                    );
                    detaillabel.setLayoutX(mouseEvent.getSceneX());
                    detaillabel.setLayoutY(mouseEvent.getSceneY());
                    detaillabel.setVisible(true);
                    detaillabel.setWrapText(true);
                    detaillabel.setTextFill(Color.BROWN);
                    detaillabel.setAlignment(Pos.CENTER);

                }
            });
            d.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    detaillabel.setText("");
                    detaillabel.setVisible(false);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
