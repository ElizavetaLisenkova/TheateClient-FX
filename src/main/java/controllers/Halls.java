package controllers;

import ApiService.HallsJsonParser;
import controllers.editControllers.HallsEdit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.HallsModel;

public class Halls {

    @FXML
    private TableView<HallsModel> hallsTable;

    @FXML
    private TableColumn<HallsModel, String> id;

    @FXML
    private TableColumn<HallsModel, String> name;

    @FXML
    private TableColumn<HallsModel, String> seatsNumber;

    @FXML
    private TextField searchField;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button createBtn;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField seatsNumberTf;

    @FXML
    private VBox editPen;

    @FXML
    private VBox clearBtn;


    private HallsJsonParser hallsJsonParser = new HallsJsonParser();


    public void initialize() {
       initTable();
    }

    private void initTable() {

        ObservableList halls = FXCollections.observableList(hallsJsonParser.getHalls());
        hallsTable.setItems(halls);

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        seatsNumber.setCellValueFactory(new PropertyValueFactory<>("seatsNumber"));

        idTf.setDisable(false);

//      поиск
        FilteredList<HallsModel> filteredData = new FilteredList<>(halls, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                } else if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getSeatsNumber()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } return false;
            });
        });

        SortedList<HallsModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(hallsTable.comparatorProperty());
        hallsTable.setItems(sortedData);
    }

    @FXML
    private void createHall() {
        HallsModel newHall = new HallsModel(Long.parseLong(idTf.getText()), nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
        hallsJsonParser.createHall(newHall);
        clearTextFields();
        initTable();

    }

    @FXML
    private void deleteHall(){
        HallsModel selectedHall = hallsTable.getSelectionModel().getSelectedItem();
        if (selectedHall != null) {
            hallsJsonParser.deleteHall(selectedHall);
            clearTextFields();
            initTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ОШИБКА");
            alert.setHeaderText("Залы не выбраны");
            alert.setContentText("Пожалуйста выберите занятие");
            alert.showAndWait();
        }
    }

    @FXML
    private void clearTextFields() {
        idTf.clear();
        nameTf.clear();
        seatsNumberTf.clear();
        idTf.setDisable(false);
    }

    @FXML
    private void fillDataToTf() {
        HallsModel selectedHall = hallsTable.getSelectionModel().getSelectedItem();
        if (selectedHall != null) {
            idTf.setText(String.valueOf(selectedHall.getId()));
            nameTf.setText(selectedHall.getName());
            seatsNumberTf.setText(String.valueOf(selectedHall.getSeatsNumber()));
            idTf.setDisable(true);
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ОШИБКА");
            alert.setHeaderText("Залы не выбраны");
            alert.setContentText("Пожалуйста выберите занятие");
            alert.showAndWait();
        }
    }

    @FXML
    private void editHall() {
        HallsModel newHall = new HallsModel(Long.parseLong(idTf.getText()), nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
        hallsJsonParser.updateHall(newHall);
        clearTextFields();
        initTable();
    }
}
