package controllers;

import ApiService.ActorsJsonParser;
import ApiService.TroupsJsonParser;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import models.ActorsModel;
import models.TroupsModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Statistic {

    @FXML
    private BarChart<?, ?> myChart;

    @FXML
    private CategoryAxis troupX;

    private TroupsJsonParser troupsJsonParser = new TroupsJsonParser();
    private List<TroupsModel> troups = troupsJsonParser.getTroups();
    private ActorsJsonParser actorsJsonParser = new ActorsJsonParser();
    private List<ActorsModel> actors = actorsJsonParser.getActors();

    public Statistic() {

    }

    public void initialize() {
        ObservableList<String> troupsName = FXCollections.observableArrayList();
        int[] actorCounter = new int[troups.size()];

        for (int i=0; i<troups.size();i++){
            troupsName.add(troups.get(i).getName());
        }
        troupX.setCategories(troupsName);

        for (int i=0; i<troups.size();i++){
            for (int a=0; a<actors.size();a++){
                if (actors.get(a).getTroupName().equals(troups.get(i).getName())){
                    actorCounter[i]++;
                }
            }
        }
        XYChart.Series series = new XYChart.Series<>();
        for (int i = 0; i < actorCounter.length; i++) {
            series.getData().add(new XYChart.Data<>(troupsName.get(i), actorCounter[i]));
        }
        myChart.getData().add(series);

    }
    public static void show() {
        try{
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(Login.class.getResource("/views.fxml/Statistic.fxml"));
            Parent view = loader.load();
            stage.setScene(new Scene(view));
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
