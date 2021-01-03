package com.company.LS.Controller;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.AlertMaker;
import com.company.LS.Model.Member;
import com.company.LS.Model.SetIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class memberListController implements Initializable {
    @FXML
    private AnchorPane pntableMain;

    @FXML
    private TableView<Member> tbleView;

    @FXML
    private TableColumn<Member, String> col_MemberId;

    @FXML
    private TableColumn<Member, String> col_Name;

    @FXML
    private TableColumn<Member, String> col_Mobile;

    @FXML
    private TableColumn<Member, String> col_Email;

    private DatabaseHandler handler;

   ObservableList<Member> list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler=DatabaseHandler.getInstance();
      InitColumn();
        try {
            LoadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void LoadData() throws SQLException {
       ResultSet set= handler.execQuery("Select * from MEMBER");
       while(set.next()){
           list.add(new Member(set.getString(1),set.getString(2),set.getString(3),set.getString(4)));
       }
       tbleView.getItems().setAll(list);
    }

    private void InitColumn() {
        col_MemberId.setCellValueFactory(new PropertyValueFactory<>("MemberId"));
        col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        col_Mobile.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        col_Email.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }
    @FXML
    void DeleteMember(ActionEvent event) throws SQLException {
        Member m = tbleView.getSelectionModel().getSelectedItem();
        if (m == null) {
            AlertMaker.TypeInfromation("Access Failed", null, "Please select Memeber befor Delete");
            return;
        }
        if (handler.IsMemberHasBook(m.getMemberId())) {
            AlertMaker.TypeError("Cant Delete",null,"Member Already Taken a Book");
            return;
        }
            if (AlertMaker.TypeConfimIsOk("Confirmation", null, "Do You want delete Memeber " + m.getName())) {
               if(handler.DeleteMember(m.getMemberId())){
                   AlertMaker.TypeInfromation("Sucess",null,"Deleted Complete");
                   tbleView.getItems().remove(m);
                }else{
                   AlertMaker.TypeError("Failed",null,"Delete Failed Try Again");
               }
            } else {
                return;
            }

    }


    @FXML
    void EditMember(ActionEvent event) throws IOException {
        Member m=tbleView.getSelectionModel().getSelectedItem();
        if(m==null){
            AlertMaker.TypeInfromation("Access Failed", null, "Please select Memeber befor Update ");
            return;
        }
        FXMLLoader fx= new FXMLLoader(getClass().getResource("..//View/Form/AddMember.fxml"));
        Parent root=fx.load();
        AddMemberController controller=(AddMemberController) fx.getController();
        controller.init(m);
        Stage st=new Stage(StageStyle.DECORATED);
        st.setScene(new Scene(root));
        st.setTitle("Edit");
        SetIcon.setDeafaultStageIcon(st);
        st.show();
    }

    @FXML
    void Refesh(ActionEvent event) throws SQLException {
    list.clear();
    LoadData();
    }
}
