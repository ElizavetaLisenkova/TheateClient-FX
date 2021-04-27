package controllers;

import ApiService.TroupsJsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import models.TroupsModel;

public class Troups {

    @FXML
    private TableView<TroupsModel> troupsTable;

    @FXML
    private TableColumn<TroupsModel, String> idColumn;

    @FXML
    private TableColumn<TroupsModel, String> nameColumn;

    @FXML
    private TextField searchTf;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

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

    private TroupsJsonParser troupsJsonParser = new TroupsJsonParser();


    public void initialize() {
        initTable();
    }


    private void initTable() {

        ObservableList troups = FXCollections.observableList(troupsJsonParser.getTroups());
        troupsTable.setItems(troups);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        idTf.setDisable(true);
        troupsTable.setPlaceholder(new Label("Нет значений."));

//  перемещение выделенного значения в поля для редактирования
        troupsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTf.setText(String.valueOf(newSelection.getId()));
                nameTf.setText(newSelection.getName());
                message.setText("");
                createBtn.setDisable(true);
            } else {
                createBtn.setDisable(false);
            }
        });

//  поиск
        FilteredList<TroupsModel> filteredData = new FilteredList<>(troups, p -> true);
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
                } return false;
            });
        });

        SortedList<TroupsModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(troupsTable.comparatorProperty());
        troupsTable.setItems(sortedData);

    }

    //  создание
    @FXML
    private void createTroup() {
        if (isInputValid()) {
            TroupsModel newTroup = new TroupsModel((long) 999999999, nameTf.getText());
            troupsJsonParser.createTroup(newTroup);
            clearTextFields();
            message.setText("Успешно создано.");
            initTable();
        }
    }

    //  удаление
    @FXML
    private void deleteTroup(){
        if (idTf.getText().isEmpty()) {
            message.setText("Выберите элемент для удаления.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Вы действительно хотите удалить элемент с id: " + idTf.getText() + "?" );
            alert.showAndWait();
            if (alert.getResult()==ButtonType.OK){
                TroupsModel newTroup = new TroupsModel(Long.parseLong(idTf.getText()), nameTf.getText());
                troupsJsonParser.deleteTroup(newTroup);
                clearTextFields();
                message.setText("Успешно удалено.");
                initTable();
            }
        }
    }

    //  редактирование
    @FXML
    private void editTroup() {
        if (idTf.getText().isEmpty()){
            message.setText("Выберите элемент для изменения.");
        } else {
            if (isInputValid()){
                TroupsModel newTroup = new TroupsModel(Long.parseLong(idTf.getText()), nameTf.getText());
                troupsJsonParser.updateTroup(newTroup);
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
        troupsTable.getSelectionModel().clearSelection();
    }

    //  очистка поля поиска
    @FXML
    private void clearSearchTf(){
        searchTf.clear();
    }


}
