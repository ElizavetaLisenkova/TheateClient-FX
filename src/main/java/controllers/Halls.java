package controllers;

import ApiService.HallsJsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableColumn<HallsModel, String> seatsNumber;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button createBtn;


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

        FilteredList<HallsModel> filteredData = new FilteredList<>(halls, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {

                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches first name.
                } else if (String.valueOf(myObject.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(myObject.getSeatsNumber()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<HallsModel> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(hallsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        hallsTable.setItems(sortedData);
    }
}
