package controllers;

import ApiService.HallsJsonParser;
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
    private TableColumn<HallsModel, String> idColumn;

    @FXML
    private TableColumn<HallsModel, String> nameColumn;

    @FXML
    private TableColumn<HallsModel, String> seatsNumberColumn;

    @FXML
    private TextField searchTf;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField seatsNumberTf;

    @FXML
    private VBox clearBtn;

    @FXML
    private Button createBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Label message;

    private HallsJsonParser hallsJsonParser = new HallsJsonParser();


    public void initialize() {
       initTable();
    }


    private void initTable() {

        ObservableList halls = FXCollections.observableList(hallsJsonParser.getHalls());
        hallsTable.setItems(halls);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        seatsNumberColumn.setCellValueFactory(new PropertyValueFactory<>("seatsNumber"));

        idTf.setDisable(true);
        hallsTable.setPlaceholder(new Label("Нет значений."));

//  перемещение выделенного значения в поля для редактирования
        hallsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTf.setText(String.valueOf(newSelection.getId()));
                nameTf.setText(newSelection.getName());
                seatsNumberTf.setText(String.valueOf(newSelection.getSeatsNumber()));
                message.setText("");
                createBtn.setDisable(true);
            } else {
                createBtn.setDisable(false);
            }
        });

//  поиск
        FilteredList<HallsModel> filteredData = new FilteredList<>(halls, p -> true);
        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
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

//  создание
    @FXML
    private void createHall() {
        if (isInputValid()) {
            HallsModel newHall = new HallsModel((long) 999999999, nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
            hallsJsonParser.createHall(newHall);
            clearTextFields();
            message.setText("Успешно создано.");
            initTable();
        }
    }

//  удаление
    @FXML
    private void deleteHall(){
        if (idTf.getText().isEmpty()) {
            message.setText("Выберите элемент для удаления.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Вы действительно хотите удалить элемент с id: " + idTf.getText() + "?" );
            alert.showAndWait();
            if (alert.getResult()==ButtonType.OK){
                HallsModel newHall = new HallsModel(Long.parseLong(idTf.getText()), nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
                hallsJsonParser.deleteHall(newHall);
                clearTextFields();
                message.setText("Успешно удалено.");
                initTable();
            }
        }
    }

//  редактирование
    @FXML
    private void editHall() {
        if (idTf.getText().isEmpty()){
            message.setText("Выберите элемент для изменения.");
        } else {
            if (isInputValid()){
                HallsModel newHall = new HallsModel(Long.parseLong(idTf.getText()), nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
                hallsJsonParser.updateHall(newHall);
                clearTextFields();
                message.setText("Успешно изменено.");
                initTable();
            }
        }
    }

//  проверка корректности введенных данных
    private boolean isInputValid() {
        String errorMessage = "";
        if (nameTf.getText().isEmpty()) {
            errorMessage += "Введите название.";
            message.setText(errorMessage);
            return false;
        }
        if (seatsNumberTf.getText().isEmpty()) {
            errorMessage += "Введите количество мест.";
            message.setText(errorMessage);
            return false;
        }
        if (checkForInteger(seatsNumberTf)) {
            errorMessage += "Поле количество мест должно содержать число.";
            message.setText(errorMessage);
            return false;
        }
        else {
            return true;
        }
    }

//  проверка на число
    private Boolean checkForInteger(TextField textField){
        if (!textField.getText().matches("^[0-9]*$")) {
            return true;
        } else {
            return false;
        }
    }

//  очистка текстовых полей снизу
    @FXML
    private void clearTextFields() {
        idTf.clear();
        nameTf.clear();
        seatsNumberTf.clear();
        message.setText("");
        hallsTable.getSelectionModel().clearSelection();
    }

//  очистка поля поиска
    @FXML
    private void clearSearchTf(){
        searchTf.clear();
    }


}
