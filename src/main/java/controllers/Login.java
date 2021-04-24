package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import exceptions.AppException;

import java.io.IOException;

public class Login {

    public static void loadView(Stage stage){
        try {

            FXMLLoader loader = new FXMLLoader(Login.class.getResource("/views.fxml/Login.fxml"));
            Parent view = loader.load();

            stage.setScene(new Scene(view));

            Login controller = loader.getController();
            controller.attachEvent();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    private void attachEvent() {
        loginTf.getScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                if (closeBtn.isFocused()) {
                    close();
                }

                if (loginBtn.isFocused()) {
                    login();
                }
            }
        });
    }

    @FXML
    private Label message;

    @FXML
    private TextField loginTf;

    @FXML
    private PasswordField passwordTf;

    @FXML
    private Button closeBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private void close() {
        loginBtn.getScene().getWindow().hide();
    }

    @FXML
    private void login() {
        try {

            String loginId = "admin";
            String passwordId = "m";

            if (loginTf.getText().equals(loginId) && passwordTf.getText().equals(passwordId)) {

                //открытие приложения
                MainFrame.show();
                //закрытие формы логина
                close();


            } if (loginTf.getText().isEmpty()) {
                throw new AppException("Пожалуйста, введите логин.");
            } if (passwordTf.getText().isEmpty()) {
                throw new AppException("Пожалуйста, введите пароль.");
            } if (!loginTf.getText().equals(loginId) || !passwordTf.getText().equals(passwordId)) {
                throw new AppException("Неверный логин или пароль.");
            }
        }
        catch (AppException e) {
            message.setText(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

}
