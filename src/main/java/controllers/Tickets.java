package controllers;

import ApiService.HallsJsonParser;
import ApiService.PerformancesJsonParser;
import ApiService.TicketsJsonParser;
import ApiService.TroupsJsonParser;
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
import models.TicketsModel;
import models.TroupsModel;

public class Tickets {
    @FXML
    private TextField searchTf;

    @FXML
    private TableView<TicketsModel> ticketsTable;

    @FXML
    private TableColumn<TicketsModel, String> idColumn;

    @FXML
    private TableColumn<TicketsModel, String> priceColumn;

    @FXML
    private TableColumn<TicketsModel, String> performanceColumn;

    @FXML
    private TableColumn<TicketsModel, String> placeColumn;

    @FXML
    private TableColumn<TicketsModel, String> availabilityColumn;

    @FXML
    private TextField idTf;

    @FXML
    private TextField priceTf;

    @FXML
    private ComboBox<?> performanceCb;

    @FXML
    private TextField placeTf;

    @FXML
    private TextField availabilityTf;

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


    private TicketsJsonParser ticketsJsonParser = new TicketsJsonParser();
    private PerformancesJsonParser performancesJsonParser = new PerformancesJsonParser();


    public void initialize() {
        initTable();
    }

    private void initTable() {

        ObservableList tickets = FXCollections.observableList(ticketsJsonParser.getTickets());
        ticketsTable.setItems(tickets);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        performanceColumn.setCellValueFactory(new PropertyValueFactory<>("performanceName"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        idTf.setDisable(true);
        ticketsTable.setPlaceholder(new Label("Нет значений."));

        ObservableList performances = FXCollections.observableList(performancesJsonParser.getPerformances());
        performanceCb.setItems(performances);


//  перемещение выделенного значения в поля для редактирования
        ticketsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idTf.setText(String.valueOf(newSelection.getId()));
                priceTf.setText(String.valueOf(newSelection.getPrice()));
                placeTf.setText(String.valueOf(newSelection.getPlace()));
                availabilityTf.setText(newSelection.getAvailability());
                message.setText("");
                createBtn.setDisable(true);
            } else {
                createBtn.setDisable(false);
            }
        });

//  поиск
        FilteredList<TicketsModel> filteredData = new FilteredList<>(tickets, p -> true);
        searchTf.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(myObject.getId()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getPrice()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getPerformanceName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getPlace()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(myObject.getAvailability()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }return false;
            });
        });

        SortedList<TicketsModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(ticketsTable.comparatorProperty());
        ticketsTable.setItems(sortedData);

    }

    //  создание
    @FXML
    private void createTicket() {
        if (isInputValid()) {
            TicketsModel newTicket = new TicketsModel((long) 99999999, Integer.parseInt(priceTf.getText()), (PerformancesModel) performanceCb.getSelectionModel().getSelectedItem(), Integer.parseInt(placeTf.getText()), availabilityTf.getText());
            ticketsJsonParser.createTicket(newTicket);
            clearTextFields();
            message.setText("Успешно создано.");
            initTable();
        }
    }

    //  удаление
    @FXML
    private void deleteTicket(){
        if (idTf.getText().isEmpty()) {
            message.setText("Выберите элемент для удаления.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение");
            alert.setHeaderText("Вы действительно хотите удалить элемент с id: " + idTf.getText() + "?" );
            alert.showAndWait();
            if (alert.getResult()== ButtonType.OK){
                TicketsModel newTicket = new TicketsModel(Long.parseLong(idTf.getText()));
                ticketsJsonParser.deleteTicket(newTicket);
                clearTextFields();
                message.setText("Успешно удалено.");
                initTable();
            }
        }
    }

    //  редактирование
    @FXML
    private void editTicket() {
        if (idTf.getText().isEmpty()){
            message.setText("Выберите элемент для изменения.");
        } else {
            if (isInputValid()){
                TicketsModel newTicket = new TicketsModel(Long.parseLong(idTf.getText()), Integer.parseInt(priceTf.getText()), (PerformancesModel) performanceCb.getSelectionModel().getSelectedItem(), Integer.parseInt(placeTf.getText()), availabilityTf.getText());
                ticketsJsonParser.updateTicket(newTicket);
                clearTextFields();
                message.setText("Успешно изменено.");
                initTable();
            }
        }
    }
    //TODO сделать проверку на соответсвие шаблону даты и времени
//    TODO сделать статус combobox
//    TODO сделать аккаунты и нормальную регистрацию
    //  проверка корректности введенных данных
    private boolean isInputValid() {
        if (priceTf.getText().isEmpty()) {
            message.setText("Введите цену.");
            return false;
        }
        if (checkForInteger(priceTf)){
            message.setText("Поле \"Цена\" должно быть числом.");
            return false;
        }
        if (performanceCb.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите представление.");
            return false;
        }
        if (placeTf.getText().isEmpty()) {
            message.setText("Введите место.");
            return false;
        }
        if (checkForInteger(placeTf)){
            message.setText("Поле \"Место\" должно быть числом.");
            return false;
        }
        if (availabilityTf.getText().isEmpty()) {
            message.setText("Введите свободно место или нет.");
            return false;
        }
        if (!availabilityTf.getText().matches("^(true|false)$") )  {
            message.setText(" Введите тру или фалсе");
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
        priceTf.clear();
        placeTf.clear();
        availabilityTf.clear();
        message.setText("");
        ticketsTable.getSelectionModel().clearSelection();
    }

    //  очистка поля поиска
    @FXML
    private void clearSearchTf(){
        searchTf.clear();
    }


}
