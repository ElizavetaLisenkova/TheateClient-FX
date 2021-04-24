package controllers;

import ApiService.HallsApi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.HallsModel;

public class Halls {

    @FXML
    private TableView<HallsModel> hallsTable;

    @FXML
    private TableColumn<HallsModel, String> id;

    @FXML
    private TableColumn<HallsModel, String> name;

    @FXML
    private TableColumn<HallsModel, String> totalSeats;

    private HallsApi hallsApi = new HallsApi();


    public void initialize() {
       initTable();

    }

    private void initTable(){
        ObservableList halls = FXCollections.observableList(hallsApi.getHalls());
        hallsTable.setItems(halls);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalSeats.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
    }




}
