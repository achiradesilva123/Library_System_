package com.company.LS.Controller;

import com.company.LS.Model.Preferences;
import com.company.LS.Setting_Loader;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private JFXTextField txtNofWithoutFine;

    @FXML
    private JFXTextField txtFineperDay;

    @FXML
    private JFXTextField txtuserName;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private JFXButton btSave;

    @FXML
    private JFXButton btCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String [] list= Preferences.GetValues();
            txtNofWithoutFine.setText(list[0].toString());
            txtFineperDay.setText(list[1].toString());
            txtuserName.setText(list[2].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void CancelAction(ActionEvent event) {

    }

    @FXML
    void SetSetting(ActionEvent event) throws Exception {

        Preferences.SetDefault(Integer.parseInt(txtNofWithoutFine.getText()),Integer.parseInt(txtFineperDay.getText()));
        Preferences.initConfig();
        Setting_Loader st=new Setting_Loader();
        Stage s=new Stage();
        st.start(s);

    }

}
