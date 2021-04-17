package main.java.controllers;

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
import main.java.exceptions.AppException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class LoginController {

    public static void loadView(Stage stage){
        try {

            FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("/views.fxml/login.fxml"));
            Parent view = loader.load();

            stage.setScene(new Scene(view));

            LoginController controller = loader.getController();
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

                else {
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
//                MainFrame.show();
                System.out.println("-------------------------------------ВЕРНЫЙ ЛОГИН И ПАРОЛЬ----------");
                //закрытие формы логина
                close();


            } if (loginTf.getText().isEmpty()) {
                System.out.println("пустой логин");
                throw new AppException("Пожалуйста, введите логин.");
            } if (passwordTf.getText().isEmpty()) {
                System.out.println("пустой пароль");
                throw new AppException("Пожалуйста, введите пароль.");
            } if (!loginTf.getText().equals(loginId) || !passwordTf.getText().equals(passwordId)) {
                System.out.println("Неверный логин или пароль.");
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
