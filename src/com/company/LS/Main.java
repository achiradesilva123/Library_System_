package com.company.LS;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.SetIcon;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
	launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root= FXMLLoader.load(getClass().getResource("View/Form/Login.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("login");
        SetIcon.setDeafaultStageIcon(primaryStage);
        primaryStage.show();

        new Thread(() -> DatabaseHandler.getInstance()).start();
    }
}
