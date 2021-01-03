package com.company.LS.Controller;

import com.company.LS.Model.SetIcon;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ToolbarController implements Initializable {
    @FXML
    private JFXButton btAddmember;

    @FXML
    private JFXButton btAddBook;

    @FXML
    private JFXButton btView;

    @FXML
    private JFXButton btViewBook;

    @FXML
    private JFXButton btSetting;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void LoadAddBokk(ActionEvent event) {
        Loadwindow("../View/Form/Addbook.fxml","Add book");
    }

    @FXML
    void LoadAddmember(ActionEvent event) {
        Loadwindow("../View/Form/AddMember.fxml","Add Member");
    }

    @FXML
    void LoadSetting(ActionEvent event) {

    }

    @FXML
    void ViewBookList(ActionEvent event) {
        Loadwindow("../View/Form/bookList.fxml","Book List");
    }

    @FXML
    void ViewmemberLis(ActionEvent event) {
        Loadwindow("../View/Form/memberList.fxml","Member List");
    }


    void Loadwindow(String path,String Title){
        try {
            Parent root= FXMLLoader.load(getClass().getResource(path));
            Stage st=new Stage(StageStyle.DECORATED);
            st.setScene(new Scene(root));
            st.setTitle(Title);
            SetIcon.setDeafaultStageIcon(st);
            st.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
