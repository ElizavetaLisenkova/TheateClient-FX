package controllers;

import ApiService.ActorsJsonParser;
import ApiService.TroupsJsonParser;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.ActorsModel;
import models.TroupsModel;

public class Actors {

    @FXML
    private TableView<ActorsModel> actorsTable;

    @FXML
    private TableColumn<ActorsModel, String> idColumn;

    @FXML
    private TableColumn<ActorsModel, String> nameColumn;

    @FXML
    private TableColumn<ActorsModel, String> troupColumn;

    @FXML
    private TextField searchTf;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

    @FXML
    private ComboBox troupCb;

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

    private ActorsJsonParser actorsJsonParser = new ActorsJsonParser();
    private TroupsJsonParser troupsJsonParser = new TroupsJsonParser();


    public void initialize() {
        initTable();
    }

    private void initTable() {

        ObservableList actors = FXCollections.observableList(actorsJsonParser.getActors());
        actorsTable.setItems(actors);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        troupColumn.setCellValueFactory(new PropertyValueFactory<>("troupName"));

        idTf.setDisable(true);
        actorsTable.setPlaceholder(new Label("Нет значений."));

        ObservableList troups = FXCollections.observableList(troupsJsonParser.getTroups());
        troupCb.setItems(troups);

//  перемещение выделенного значения в поля для редактирования
        actorsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTf.setText(String.valueOf(newSelection.getId()));
                nameTf.setText(newSelection.getFullName());
                message.setText("");
                createBtn.setDisable(true);
            } else {
                createBtn.setDisable(false);
            }
        });

//  поиск
        FilteredList<ActorsModel> filteredData = new FilteredList<>(actors, p -> true);
        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getFullName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getTroupName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } return false;
            });
        });

        SortedList<ActorsModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(actorsTable.comparatorProperty());
        actorsTable.setItems(sortedData);

    }

    //  создание
    @FXML
    private void createActor() {
        if (isInputValid()) {
            ActorsModel newActor = new ActorsModel((long) 999999999, nameTf.getText(), (TroupsModel) troupCb.getSelectionModel().getSelectedItem());
            actorsJsonParser.createActor(newActor);
            clearTextFields();
            message.setText("Успешно создано.");
            initTable();
        }
    }

    //  удаление
    @FXML
    private void deleteActor(){
        if (idTf.getText().isEmpty()) {
            message.setText("Выберите элемент для удаления.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Вы действительно хотите удалить элемент с id: " + idTf.getText() + "?" );
            alert.showAndWait();
            if (alert.getResult()==ButtonType.OK){
                ActorsModel newActor = new ActorsModel(Long.parseLong(idTf.getText()), nameTf.getText(), (TroupsModel) troupCb.getSelectionModel().getSelectedItem());
                actorsJsonParser.deleteActor(newActor);
                clearTextFields();
                message.setText("Успешно удалено.");
                initTable();
            }
        }
    }

    //  редактирование
    @FXML
    private void editActor() {
        if (idTf.getText().isEmpty()){
            message.setText("Выберите элемент для изменения.");
        } else {
            if (isInputValid()){
                ActorsModel newActor = new ActorsModel(Long.parseLong(idTf.getText()), nameTf.getText(), (TroupsModel) troupCb.getSelectionModel().getSelectedItem());
                actorsJsonParser.updateActor(newActor);
                clearTextFields();
                message.setText("Успешно изменено.");
                initTable();
            }
        }
    }

    //  проверка корректности введенных данных
    private boolean isInputValid() {
        if (nameTf.getText().isEmpty()) {
            message.setText("Введите ФИО.");
            return false;
        }
        if (troupCb.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите труппу.");
            return false;
        }
        else {
            return true;
        }
    }

    //  очистка текстовых полей снизу
    @FXML
    private void clearTextFields() {
        idTf.clear();
        nameTf.clear();
        message.setText("");
        actorsTable.getSelectionModel().clearSelection();
    }

    //  очистка поля поиска
    @FXML
    private void clearSearchTf(){
        searchTf.clear();
    }


}
