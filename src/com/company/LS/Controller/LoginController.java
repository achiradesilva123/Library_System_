package com.company.LS.Controller;

import com.company.LS.Main;
import com.company.LS.Model.Preferences;
import com.company.LS.Model.SetIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private AnchorPane pnLoginmain;

    @FXML
    private JFXButton btLogin;

    @FXML
    private JFXButton btCancel;

    @FXML
    private JFXTextField txtUserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    void Cancel(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void LoginAccess(ActionEvent event) throws IOException {
        String []p=Preferences.GetValues();
        if(p[3].equals(DigestUtils.shaHex(txtPassword.getText()))){
            System.out.println("hello");
            LoadMain();
        }else{
            txtPassword.getStyleClass().add("wrongdata");
            txtUserName.getStyleClass().add("wrongdata");
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Check Password and UserName and Try agian");
            alert.showAndWait();
        }
    }

    private void LoadMain() throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("..//View/Form/MainForm.fxml"));
        Stage st=new Stage();
        st.setScene(new Scene(root));
        SetIcon.setDeafaultStageIcon(st);
        st.show();
        ((Stage)pnLoginmain.getScene().getWindow()).close();

        //Main.st.close();
    }
    

}
