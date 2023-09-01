package com.example.analystestcoverage_app;

import Elements.Build;
import Elements.Repository;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

public class Detailled_linescharts_Controller implements Initializable {
    public LineChart linecharts;
    public Button save;
    public NumberAxis pourcentage;
    public CategoryAxis Builds_Number;
    public Label labdetail;
    private Repository repository;
    private String name;

    /**
     * Cette méthode sauvegarde la visualisation sous deux formats.(png et gif)
     * @param actionEvent
     */
    public void save(ActionEvent actionEvent) {
        BarChartsController.SAVEIMAGE(linecharts,name);
        save.setDisable(true);
    }

    /**
     * Cette méthode déssine un line chart en fonction des données selectionnées.
     * Le code est initialisé à la page de configuration en choisissant les checksbox.
     * @param rep : le repertoire contenant les builds à afficher.
     * @param combobox1 : contenant le nom de la branche choisie.
     * @param o :  code utilisé pour selectionner les données à afficher.
     * @param local : détermine les données de l'axe des x.(loc ref ou date)
     */
    public void pass(Repository rep, String combobox1, int o,int local) {
        this.repository = rep;

        if (rep != null){
            System.out.println("not null");
        }

        this.name ="Linechart_"+ rep.getRef().replaceAll("[^\\w\\s]","");
        ArrayList<String> branches = rep.getBranch();
        Iterator<Build> it = this.repository.get_All_builds().iterator();
        Date d = new Date();
        switch (o){
            case 0 :

            case 1 :
                XYChart.Series<String,Integer> series = new XYChart.Series<>();
                series.setName("Nbr of files"+combobox1);
                //Iterator<Build> it = this.repository.get_All_builds().iterator();

                while(it.hasNext()){
                    Build b = it.next();
                        if(b.getBranch().equalsIgnoreCase(combobox1)){
                            if(local == 0){
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files());
                                series.getData().add(f1);
                                f1.setExtraValue(b);

                            }else{
                                if(!d.equals(b.getDate())) {
                                    XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                            b.getDate().toLocaleString().split(",")[0],
                                            b.getNbr_files());
                                    series.getData().add(f1);
                                    f1.setExtraValue(b);
                                }
                                d = b.getDate();
                            }
                        }
                    }
                linecharts.getData().addAll(series);
                labeliser(series);
                break;
            case 10 :
                XYChart.Series<String,Integer> series1 = new XYChart.Series<>();
                series1.setName("Total line  branch "+combobox1);
                while(it.hasNext()){
                    Build b = it.next();
                        if(b.getBranch().equalsIgnoreCase(combobox1)){
                            if(local == 0){
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                                series1.getData().add(f1);
                                f1.setExtraValue(f1);
                            }else {
                                if (!d.equals(b.getDate())) {
                                    XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                            b.getDate().toLocaleString().split(",")[0],
                                            b.getTotal_lines());
                                    series1.getData().add(f1);
                                    f1.setExtraValue(b);
                                }
                                d =b.getDate();
                            }
                        }
                    }
                linecharts.getData().addAll(series1);
                labeliser(series1);
                break;
            case 11 :
                XYChart.Series<String,Integer> series4 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series5 = new XYChart.Series<>();
                series4.setName("Total line  branch "+combobox1);
                series5.setName("Number of file "+combobox1);
                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                            series4.getData().add(f1);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files());
                            series5.getData().add(f2);
                            f1.setExtraValue(b);
                            f2.setExtraValue(b);
                        }else {
                            if (!d.equals(b.getDate())) {
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());
                                series4.getData().add(f1);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getNbr_files());
                                series5.getData().add(f2);
                                f1.setExtraValue(b);
                                f2.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series4,series5);
                labeliser(series4);
                labeliser(series5);
                break;

            case 100 :
                XYChart.Series<String,Integer> series2 = new XYChart.Series<>();
                series2.setName("Covered line  branch "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(),b.getCovered_lines()) ;
                            series2.getData().add(f1);
                            f1.setExtraValue(b);
                        }else {
                            if (!d.equals(b.getDate())) {
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series2.getData().add(f1);
                                f1.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series2);
                labeliser(series2);
                break;
            case 101 :
                XYChart.Series<String,Integer> series6 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series7 = new XYChart.Series<>();
                series6.setName("Covered line  branch "+combobox1);
                series7.setName("Number of file "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getCovered_lines());
                            series6.getData().add(f1);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files());
                            series7.getData().add(f2);
                            f1.setExtraValue(b);
                            f2.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series6.getData().add(f1);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getNbr_files());
                                series7.getData().add(f2);
                                f1.setExtraValue(b);
                                f2.setExtraValue(b);
                            }
                            d= b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series6,series7);
                labeliser(series6);
                labeliser(series7);
                break;

            case 110 :
                XYChart.Series<String,Integer> series8 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series9 = new XYChart.Series<>();
                series8.setName("Covered line  branch "+combobox1);
                series9.setName("Total of file "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getCovered_lines());
                            series8.getData().add(f1);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                            series9.getData().add(f2);
                            f1.setExtraValue(b);
                            f2.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series8.getData().add(f1);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());
                                series9.getData().add(f2);
                                f1.setExtraValue(b);
                                f2.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series8,series9);
                labeliser(series8);
                labeliser(series9);
                break;

            case 111 :
                XYChart.Series<String,Integer> series10 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series11 = new XYChart.Series<>();
                XYChart.Series<String, Integer>series12 = new XYChart.Series();
                series10.setName("Covered line  branch "+combobox1);
                series11.setName("Total line  branch "+ combobox1);
                series12.setName("Number of file "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Integer> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getCovered_lines());
                            series10.getData().add(f1);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                            series11.getData().add(f2);
                            XYChart.Data<String, Integer> f3 = new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files());
                            series12.getData().add(f3);
                            f1.setExtraValue(b);
                            f2.setExtraValue(b);
                            f3.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Integer> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series10.getData().add(f1);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());
                                series11.getData().add(f2);
                                XYChart.Data<String, Integer> f3 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getNbr_files());
                                series12.getData().add(f3);
                                f1.setExtraValue(b);
                                f2.setExtraValue(b);
                                f3.setExtraValue(b);
                            }
                            d =b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series10,series11,series12);
                labeliser(series10);
                labeliser(series11);
                labeliser(series12);
                break;
            case 1000 :
                XYChart.Series<String,Double> series3 = new XYChart.Series<>();
                series3.setName("Percentage  branch "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getPercentage());
                            series3.getData().add(f1);
                            f1.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Double> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getPercentage());
                                series3.getData().add(f1);
                                f1.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series3);
                labelise(series3);
                break;
            case 1001 :
                XYChart.Series<String,Double> series17 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series18 = new XYChart.Series<>();
                series17.setName("Percentage  branch "+combobox1);
                series18.setName("Number of file "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getPercentage());
                            series17.getData().add(f1);
                            f1.setExtraValue(b);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files());
                            series18.getData().add(f2);
                            f2.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Double> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getPercentage());
                                series17.getData().add(f1);
                                f1.setExtraValue(b);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getNbr_files());
                                series18.getData().add(f2);
                                f2.setExtraValue(b);
                            }
                            d= b.getDate();

                        }
                    }
                }
                linecharts.getData().addAll(series17,series18);
                labelise(series17);
                labeliser(series18);
                break;
            case 1010:
                XYChart.Series<String,Double> series19 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series20 = new XYChart.Series<>();
                series19.setName("Percentage  branch "+combobox1);
                series20.setName("Total lines "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getPercentage());
                            series19.getData().add(f1);
                            f1.setExtraValue(b);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                            series20.getData().add(f2);
                            f2.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Double> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getPercentage());
                                series19.getData().add(f1);
                                f1.setExtraValue(b);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());
                                series20.getData().add(f2);
                                f2.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series19,series20);
                labelise(series19);
                labeliser(series20);
                break;
            case 1100 :
                XYChart.Series<String,Double> series25 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series26 = new XYChart.Series<>();
                series25.setName("Percentage  branch "+combobox1);
                series26.setName("Covered line  branch "+ combobox1);
                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getPercentage());
                            series25.getData().add(f1);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(),b.getCovered_lines());
                            series26.getData().add(f2);
                            f1.setExtraValue(b);
                            f2.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Double> f1 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getPercentage());
                                series25.getData().add(f1);
                                f1.setExtraValue(b);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series26.getData().add(f2);
                                f2.setExtraValue(b);
                            }
                            d = b.getDate();
                        }
                    }
                }
                linecharts.getData().addAll(series25,series26);
                labelise(series25);
                labeliser(series26);
                break;
            case 1110:
                XYChart.Series<String,Double> series22 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series23 = new XYChart.Series<>();
                XYChart.Series<String, Integer>series24 = new XYChart.Series();

                series22.setName("Percentage  branch "+combobox1);
                series23.setName("Covered line  branch "+ combobox1);
                series24.setName("Total lines "+combobox1);
                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getLocal_reference(), b.getPercentage());
                            series22.getData().add(f1);
                            f1.setExtraValue(b);
                            XYChart.Data<String, Integer> f2 = new XYChart.Data<>(b.getLocal_reference(), b.getCovered_lines());
                            series23.getData().add(f2);
                            f2.setExtraValue(b);
                            XYChart.Data<String, Integer> f3 = new XYChart.Data<>(b.getLocal_reference(), b.getTotal_lines());
                            series24.getData().add(f3);
                            }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String, Double> f1 = new XYChart.Data<>(b.getDate().toLocaleString(),
                                        b.getPercentage());
                                series22.getData().add(f1);
                                f1.setExtraValue(b);
                                XYChart.Data<String, Integer> f2 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series23.getData().add(f2);
                                f2.setExtraValue(b);
                                XYChart.Data<String, Integer> f3 = new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());
                                series24.getData().add(f3);
                                f3.setExtraValue(b);
                            }
                            d = b.getDate();
                            }
                    }
                }
                linecharts.getData().addAll(series22,series23,series24);
                labelise(series22);
                labeliser(series23);
                labeliser(series24);

                break;
            case 1111 :
                XYChart.Series<String,Double> series13 = new XYChart.Series<>();
                XYChart.Series<String,Integer> series14 = new XYChart.Series<>();
                XYChart.Series<String, Integer>series15 = new XYChart.Series();
                XYChart.Series<String, Integer>series16 = new XYChart.Series();
                series13.setName("Percentage  branch "+combobox1);
                series14.setName("Covered line  branch "+ combobox1);
                series15.setName("Total lines "+combobox1);
                series16.setName("Number of file "+combobox1);

                while(it.hasNext()){
                    Build b = it.next();
                    if(b.getBranch().equalsIgnoreCase(combobox1)){
                        if(local == 0){
                            XYChart.Data<String , Double > f1 =new XYChart.Data<>(b.getLocal_reference(),b.getPercentage()) ;
                            series13.getData().add(f1);
                            f1.setExtraValue(b);
                            XYChart.Data<String , Integer > f2 =new XYChart.Data<>(b.getLocal_reference(),b.getCovered_lines());
                            series14.getData().add(f2);
                            f2.setExtraValue(b);
                            XYChart.Data<String , Integer > f3 =new XYChart.Data<>(b.getLocal_reference(),b.getTotal_lines()) ;
                            series15.getData().add(f3);
                            f3.setExtraValue(b);
                            XYChart.Data<String , Integer > f4 =new XYChart.Data<>(b.getLocal_reference(), b.getNbr_files()) ;
                            series16.getData().add(f4);
                            f4.setExtraValue(b);
                        }else{
                            if(!d.equals(b.getDate())) {
                                XYChart.Data<String,Double> f1 =new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getPercentage());
                                series13.getData().add(f1);
                                f1.setExtraValue(b);

                                XYChart.Data<String,Integer> f2 =new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getCovered_lines());
                                series14.getData().add(f2);
                                f2.setExtraValue(b);

                                XYChart.Data<String,Integer> f3 =new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getTotal_lines());

                                series15.getData().add(f3);
                                f3.setExtraValue(b);

                                XYChart.Data<String,Integer> f4 =new XYChart.Data<>(
                                        b.getDate().toLocaleString().split(",")[0],
                                        b.getNbr_files());
                                series16.getData().add(f4);
                                f4.setExtraValue(b);
                            }
                            d =b.getDate();
                        }
                    }
                }

                linecharts.getData().addAll(series13,series14,series15,series16);
                labelise(series13);
                labeliser(series14);
                labeliser(series15);
                labeliser(series16);
                break;
            default:
                break;
        }
    }

    /**
     * Méthode qui initialise le Linechart combiné des deux projets passés en argument.
     * @param rep1 : contenant les données du premier projet.
     * @param rep2 : contenant les informations du second projet.
     * @param branche1 : branche à visualiser du premier projet.
     * @param branch2 : branche à visualiser du second projet.
     * @param data1 : données a visualiser du premier projet.
     * @param data2 : données à visualiser du second projet.
     */
    public void draw(Repository rep1 , Repository rep2,
                     String branche1, String branch2, String data1,String data2){
            this.name ="Linechart_compare"+rep1.getRef().replaceAll("[^\\w\\s]","")
                    +"-"+rep2.getRef().replaceAll("[^\\w\\s]",""); ;
            XYChart.Series<String, Double> series13 = new XYChart.Series<>();
            series13.setName(data1 + "-" + branche1 + "-" + rep1.getRef());
            XYChart.Series<String, Double> series14 = new XYChart.Series<>();
            series14.setName(data2 + "-" + branch2 + "-" + rep2.getRef());
            Builds_Number.setLabel("Date");

            TreeSet<Build> general = new TreeSet<>();
            general.addAll(rep1.get_All_builds());
            general.addAll(rep2.get_All_builds());

            Iterator<Build> itt = general.iterator();
            Date d = new Date();
            Date h = new Date();
            while (itt.hasNext()) {
                Build b = itt.next();
                if (rep1.contains(b)) {
                    if (b.getBranch().equalsIgnoreCase(branche1)) {
                        String one1;
                        double two = 0;
                        one1 = b.getDate().toLocaleString().split(",")[0];
                        if (data1.equalsIgnoreCase("covered_lines")) {
                            two = Double.valueOf(b.getCovered_lines());
                        }
                        if (data1.equalsIgnoreCase("Total_lines")) {
                            two = Double.valueOf(b.getTotal_lines());
                        }
                        if (data1.equalsIgnoreCase("Nbr_of_lines")) {
                            two = Double.valueOf(b.getNbr_files());
                        }
                        if (data1.equalsIgnoreCase("Percentage")) {
                            two = Double.valueOf(b.getPercentage());
                        }
                        if(!d.equals(b.getDate())) {
                            XYChart.Data<String, Double> fl = new XYChart.Data<>(one1, two);
                            series13.getData().add(fl);
                            fl.setExtraValue(b);
                        }
                        d = b.getDate();
                    }
                } else {
                    if (b.getBranch().equalsIgnoreCase(branch2)) {
                        String one1;
                        double two = 0;
                        one1 = b.getDate().toLocaleString().split(",")[0];
                        if (data2.equalsIgnoreCase("covered_lines")) {
                            two = Double.valueOf(b.getCovered_lines());
                        }
                        if (data2.equalsIgnoreCase("Total_lines")) {
                            two = Double.valueOf(b.getTotal_lines());
                        }
                        if (data2.equalsIgnoreCase("Nbr_of_Files")) {
                            two = Double.valueOf(b.getNbr_files());
                        }
                        if (data2.equalsIgnoreCase("Percentage")) {
                            two = Double.valueOf(b.getPercentage());
                        }
                        if(!h.equals(b.getDate())) {
                            XYChart.Data<String, Double> fg = new XYChart.Data<>(one1, two);
                            series14.getData().add(fg);
                            fg.setExtraValue(b);
                        }
                        h= b.getDate();
                    }
                }
            }
            linecharts.getData().addAll(series13, series14);
            labelise(series13);
            labelise(series14);
    }

    /**
     * Méthode qui rajoute un label aux points du Line charts.
     * @param fll : liste des points.
     */
    private void labelise(XYChart.Series<String, Double> fll){
        for (final XYChart.Data<String, Double> d : fll.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    labdetail.setText(d.getExtraValue().toString());
                    labdetail.setLayoutX(mouseEvent.getSceneX());
                    labdetail.setLayoutY(mouseEvent.getSceneY());
                    labdetail.setVisible(true);
                    labdetail.setWrapText(true);
                    labdetail.setTextFill(Color.BLUE);
                }
            });
            d.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    labdetail.setText("");
                    labdetail.setVisible(false);
                }
            });
        }
    }
    /**
     * Méthode qui rajoute un label aux points du Line charts.
     * @param fll : liste des points.
     */
    private void labeliser(XYChart.Series<String, Integer> fll){
        for (final XYChart.Data<String, Integer> d : fll.getData()) {
            d.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    labdetail.setText(d.getExtraValue().toString());
                    labdetail.setLayoutX(mouseEvent.getSceneX());
                    labdetail.setLayoutY(mouseEvent.getSceneY());
                    labdetail.setVisible(true);
                    labdetail.setWrapText(true);
                    labdetail.setTextFill(Color.BLUE);
                }
            });
            d.getNode().addEventHandler(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    labdetail.setText("");
                    labdetail.setVisible(false);
                }
            });
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Builds_Number.setLabel("Data");
        pourcentage.setLabel("Amount");
    }
}
