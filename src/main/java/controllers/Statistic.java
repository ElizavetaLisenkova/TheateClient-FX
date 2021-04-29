package controllers;

import ApiService.ActorsJsonParser;
import ApiService.TicketsJsonParser;
import ApiService.TroupsJsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.stage.Stage;

public class Statistic {

    @FXML
    private BarChart<?,?> myChart;

    private TroupsJsonParser troupsJsonParser = new TroupsJsonParser();
    private ActorsJsonParser actorsJsonParser = new ActorsJsonParser();

    public Statistic() {

    }

    public void show() {
        try{
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(MainFrame.class.getResource("/views.fxml/Statistic.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Статистика количества актеров в каждой труппе");
            stage.show();
            initChart();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initChart(){

        ObservableList troups = FXCollections.observableList(troupsJsonParser.getTroups());
        ObservableList actors = FXCollections.observableList(actorsJsonParser.getActors());
//        myChart.setData();


    }
}
