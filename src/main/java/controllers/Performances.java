package controllers;

import ApiService.HallsJsonParser;
import ApiService.PerformancesJsonParser;
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
import models.HallsModel;
import models.PerformancesModel;
import models.TroupsModel;

public class Performances {
    @FXML
    private TextField searchTf;

    @FXML
    private TableView<PerformancesModel> performancesTable;

    @FXML
    private TableColumn<PerformancesModel, String> idColumn;

    @FXML
    private TableColumn<PerformancesModel, String> nameColumn;

    @FXML
    private TableColumn<PerformancesModel, String> dateColumn;

    @FXML
    private TableColumn<PerformancesModel, String> timeColumn;

    @FXML
    private TableColumn<PerformancesModel, String> troupColumn;

    @FXML
    private TableColumn<PerformancesModel, String> hallColumn;

    @FXML
    private TableColumn<PerformancesModel, String> statusColumn;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField dateTf;

    @FXML
    private TextField timeTf;

    @FXML
    private ComboBox<?> troupCb;

    @FXML
    private ComboBox<?> hallCb;

    @FXML
    private TextField statusTf;

    @FXML
    private VBox clearBtn;

    @FXML
    private Label message;

    @FXML
    private Button createBtn;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    private PerformancesJsonParser performancesJsonParser = new PerformancesJsonParser();
    private TroupsJsonParser troupsJsonParser = new TroupsJsonParser();
    private HallsJsonParser hallsJsonParser = new HallsJsonParser();


    public void initialize() {
        initTable();
    }

    private void initTable() {

        ObservableList performances = FXCollections.observableList(performancesJsonParser.getPerformances());
        performancesTable.setItems(performances);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        troupColumn.setCellValueFactory(new PropertyValueFactory<>("troupName"));
        hallColumn.setCellValueFactory(new PropertyValueFactory<>("hallName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        idTf.setDisable(true);
        performancesTable.setPlaceholder(new Label("Нет значений."));

        ObservableList troups = FXCollections.observableList(troupsJsonParser.getTroups());
        troupCb.setItems(troups);

        ObservableList halls = FXCollections.observableList(hallsJsonParser.getHalls());
        hallCb.setItems(halls);

//  перемещение выделенного значения в поля для редактирования
        performancesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTf.setText(String.valueOf(newSelection.getId()));
                nameTf.setText(newSelection.getName());
                dateTf.setText(newSelection.getDate());
                timeTf.setText(newSelection.getTime());
                statusTf.setText(newSelection.getStatus());
                message.setText("");
                createBtn.setDisable(true);
            } else {
                createBtn.setDisable(false);
            }
        });

//  поиск
        FilteredList<PerformancesModel> filteredData = new FilteredList<>(performances, p -> true);
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
                } else if (String.valueOf(myObject.getDate()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getTime()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getTroupName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (String.valueOf(myObject.getHallName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }else if (String.valueOf(myObject.getStatus()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }return false;
            });
        });

        SortedList<PerformancesModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(performancesTable.comparatorProperty());
        performancesTable.setItems(sortedData);

    }

    //  создание
    @FXML
    private void createPerformance() {
        if (isInputValid()) {
            PerformancesModel newPerformance = new PerformancesModel((long) 99999999, nameTf.getText(), dateTf.getText(), timeTf.getText(), (TroupsModel) troupCb.getSelectionModel().getSelectedItem(), (HallsModel) hallCb.getSelectionModel().getSelectedItem(), statusTf.getText());
            System.out.println("!!!!!!"+newPerformance.toJson());
            performancesJsonParser.createPerformance(newPerformance);
            clearTextFields();
            message.setText("Успешно создано.");
            initTable();
        }
    }

    //  удаление
    @FXML
    private void deletePerformance(){
        if (idTf.getText().isEmpty()) {
            message.setText("Выберите элемент для удаления.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Вы действительно хотите удалить элемент с id: " + idTf.getText() + "?" );
            alert.showAndWait();
            if (alert.getResult()==ButtonType.OK){
                PerformancesModel newPerformance = new PerformancesModel(Long.parseLong(idTf.getText()));
                performancesJsonParser.deletePerformance(newPerformance);
                clearTextFields();
                message.setText("Успешно удалено.");
                initTable();
            }
        }
    }

    //  редактирование
    @FXML
    private void editPerformance() {
        if (idTf.getText().isEmpty()){
            message.setText("Выберите элемент для изменения.");
        } else {
            if (isInputValid()){
                PerformancesModel newPerformance = new PerformancesModel(Long.parseLong(idTf.getText()), nameTf.getText(), dateTf.getText(), timeTf.getText(), (TroupsModel) troupCb.getSelectionModel().getSelectedItem(), (HallsModel) hallCb.getSelectionModel().getSelectedItem(), statusTf.getText());
                performancesJsonParser.updatePerformance(newPerformance);
                clearTextFields();
                message.setText("Успешно изменено.");
                initTable();
            }
        }
    }

    //  проверка корректности введенных данных
    private boolean isInputValid() {
        if (nameTf.getText().isEmpty()) {
            message.setText("Введите название.");
            return false;
        }
        if (dateTf.getText().isEmpty()) {
            message.setText("Введите дату.");
            return false;
        }
        if (timeTf.getText().isEmpty()) {
            message.setText("Введите время.");
            return false;
        }
        if (troupCb.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите труппу.");
            return false;
        }
        if (hallCb.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите зал.");
            return false;
        }
        if (statusTf.getText().isEmpty()) {
            message.setText("Введите статус.");
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
        dateTf.clear();
        timeTf.clear();
        statusTf.clear();
        message.setText("");
        performancesTable.getSelectionModel().clearSelection();
    }

    //  очистка поля поиска
    @FXML
    private void clearSearchTf(){
        searchTf.clear();
    }


}
