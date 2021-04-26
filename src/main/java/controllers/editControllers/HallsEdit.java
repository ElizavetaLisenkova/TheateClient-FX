package controllers.editControllers;

import ApiService.HallsJsonParser;
import controllers.MainFrame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.HallsModel;

public class HallsEdit {


    @FXML
    private Label title;

    @FXML
    private TextField idTf;

    @FXML
    private TextField nameTf;

    @FXML
    private TextField seatsNumberTf;

    @FXML
    private Button сreateBtn;

    @FXML
    private Button closeBtn;

    private HallsJsonParser hallsJsonParser = new HallsJsonParser();

    @FXML
    private void close() {
        title.getScene().getWindow().hide();
    }


    public static void showAddView() {
        try {

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(HallsEdit.class.getResource("/editPages/HallsEdit.fxml"));
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void create() {
        System.out.println(idTf.getText());
        System.out.println(Long.parseLong(idTf.getText()));
        System.out.println(nameTf.getText());
        System.out.println(Integer.parseInt(seatsNumberTf.getText()));
        HallsModel newHall = new HallsModel(Long.parseLong(idTf.getText()), nameTf.getText(), Integer.parseInt(seatsNumberTf.getText()));
        System.out.println(newHall.toJson());
        hallsJsonParser.createHall(newHall);
        close();
    }
}
