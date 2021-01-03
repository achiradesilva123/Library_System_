package com.company.LS;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Setting_Loader extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("View/Form/Setting.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("login");
        primaryStage.show();
        new Thread(() -> DatabaseHandler.getInstance()).start();
        Preferences.initConfig();
    }
}
