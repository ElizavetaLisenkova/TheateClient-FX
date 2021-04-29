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

//  загружает форму логина
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

//  проверяет нажаты ли кнопки
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


//  закрывает форму логина при нажатии на кнопку закрыть
    @FXML
    private void close() {
        loginBtn.getScene().getWindow().hide();
    }

//  закрывает форму логина и открывает mainFrame(вход в приложение), если логин и пароль верный
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
            //проверка на правильность ввода логина и пароля
            } if (loginTf.getText().isEmpty()) {
                message.setText("Пожалуйста, введите логин.");
            } if (passwordTf.getText().isEmpty()) {
                message.setText("Пожалуйста, введите пароль.");
            } if (!loginTf.getText().equals(loginId) || !passwordTf.getText().equals(passwordId)) {
                message.setText("Неверный логин или пароль.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            close();
        }
    }

}
