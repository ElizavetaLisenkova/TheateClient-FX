package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.ApiService.ApiService;
import main.java.controllers.Login;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RunFXApplication extends Application {



    @Override
    public void start(Stage stage) throws Exception{
        stage.setResizable(false);
        Login.loadView(stage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
