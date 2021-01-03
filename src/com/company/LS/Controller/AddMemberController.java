package com.company.LS.Controller;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.AlertMaker;
import com.company.LS.Model.Member;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable{
    @FXML
    private AnchorPane pnAddMemberMain;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtMemberId;

    @FXML
    private JFXTextField txtMobile;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXButton btAddMember;

    @FXML
    private JFXButton btCancel;

    private Boolean IsGonnaEdit=Boolean.FALSE;
    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          databaseHandler=DatabaseHandler.getInstance();

    }
    @FXML
    void AddMember(ActionEvent event) {

        String MemberName=txtName.getText();
        String Memberid=txtMemberId.getText();
        String Mobile=txtMobile.getText();
        String Email=txtEmail.getText();

        if(MemberName.equals("") || Memberid.equals("") || Mobile.equals("") || Email.equals("")) {
            AlertMaker.TypeError("Error",null,"Please Fill All values");
            return;
        }
        if(IsGonnaEdit){
            if(AlertMaker.TypeConfimIsOk("Confirmation", null, "Do You want Update Memeber " + txtMemberId.getText())) {
                Update();
                return;
            }else{
                return;
            }
        }
        String sql="INSERT INTO MEMBER "+
                "Values("+"'"+Memberid+"',"+
                "'"+MemberName+"',"+
                "'"+Mobile+"',"+
                "'"+Email+"'"+")";
        System.out.println(sql);
        if(databaseHandler.execAction(sql)){
            AlertMaker.TypeInfromation("Sucess",null,"Adding Complete");
        }else{
            AlertMaker.TypeError("Failed",null,"Adding failed Try Again");
        }
    }

    private void Update() {
    Member m=new Member(txtMemberId.getText(),txtName.getText(),txtMobile.getText(),txtEmail.getText());
    if(databaseHandler.UpdateMember(m)){
        AlertMaker.TypeInfromation("Sucess", null, "Update Complete");
    }else{
        AlertMaker.TypeError("Failed",null,"Update Failed Try Again");
    }

    }

    @FXML
    void Cancel(ActionEvent event) {
        Stage st= (Stage) pnAddMemberMain.getScene().getWindow();
        st.close();
    }
   void init(Member m){
        txtName.setText(m.getName());
        txtMemberId.setText(m.getMemberId());
        txtMobile.setText(m.getMobile());
        txtEmail.setText(m.getEmail());
        txtMemberId.setEditable(false);
        IsGonnaEdit=Boolean.TRUE;
   }
}
