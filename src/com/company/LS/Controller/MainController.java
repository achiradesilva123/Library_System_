package com.company.LS.Controller;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.AlertMaker;
import com.company.LS.Model.SetIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import com.sun.javafx.css.Style;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private StackPane rootPane;

    @FXML
    private Text MemberName;

    @FXML
    private JFXButton btSubmission;

    @FXML
    private JFXButton btIssue;

    @FXML
    private Text Contact;
    @FXML
    private Text BookName;

    @FXML
    private Text Author;
    @FXML
    private TextField txtMemberId;

    @FXML
    private Text BookStatus;

    @FXML
    private TextField txtBookId;

    @FXML
    private Button btAddmember;

    @FXML
    private Button btAddBook;

    @FXML
    private Button btView;

    @FXML
    private Button btViewBook;

    @FXML
    private Button btSetting;

    @FXML
    private HBox Book_Info;

    @FXML
    private JFXTextField txtRenwTabBookId;

    @FXML
    private HBox member_Info;

    @FXML
    private javafx.scene.control.ListView<String> ListView;

    @FXML
    private JFXButton btTabRenew;

    @FXML
    private JFXHamburger Hamburger;

    @FXML
    private JFXDrawer Drawer;


    DatabaseHandler handler;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler=DatabaseHandler.getInstance();
        JFXDepthManager.setDepth(Book_Info,1);
        JFXDepthManager.setDepth(member_Info,1);
        initDrawer();
    }

    private void initDrawer() {
        try {
            VBox tool=FXMLLoader.load(getClass().getResource("../View/Form/Toolbar.fxml"));
            Drawer.setSidePane(tool);

        } catch (IOException e) {
            e.printStackTrace();
        }
        HamburgerSlideCloseTransition task=new HamburgerSlideCloseTransition(Hamburger);
        task.setRate(-1);
       Hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (EventHandler<Event>) event -> {
           task.setRate(task.getRate()*-1);
           if (Drawer.isHidden()) {
              Drawer.open();
           }else{
               Drawer.close();
           }

       });
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
    @FXML
    void LoadBookInfo(ActionEvent event) throws SQLException {
        BookClearCache();
        String sql="Select * from BOOK where Book_id='"+txtBookId.getText()+"'";
        ResultSet set=handler.execQuery(sql);
        boolean Exists=false;
        while (set.next()){
            Exists=true;
            BookName.setText(set.getString(2));
            Author.setText(set.getString(3));
            txtMemberId.setEditable(true);
            txtMemberId.requestFocus();
              if(set.getString(5).equalsIgnoreCase("true")){
                  BookStatus.setText("Available");
              }else{
                  BookStatus.setText("Not Available");
              }
        }
        if(!Exists){
            txtBookId.setStyle("-fx-text-inner-color: red;");
            BookName.setStyle("-fx-text-inner-color: red;");
            BookName.setText("Book is not Exist");
            txtMemberId.setEditable(false);
        }
    }

    @FXML
    void LoadMemberInfo(ActionEvent event) throws SQLException {
        MemberClearCache();
         String sql="Select * from MEMBER where Member_id='"+txtMemberId.getText()+"'";
         ResultSet set=handler.execQuery(sql);
        boolean Exists=false;
         while(set.next()){
             Exists=true;
             MemberName.setText(set.getString(2));
             Contact.setText(set.getString(3));
         }
        if(!Exists){
            MemberName.setText("Not Available");
            AlertMaker.TypeError("Failed",null,"Member Is not Available Try again");
         }
    }

    @FXML
    void LoadISsueOp(ActionEvent event) throws SQLException {
        String MemberId=txtMemberId.getText();
        String BookId=txtBookId.getText();
         if(MemberId.equalsIgnoreCase("") || BookId.equalsIgnoreCase("")){
             AlertMaker.TypeError("Unselected",null,"Select Fist Book And Member Befor Issue");
             return;
         }
         if(handler.IsAlreadyissued(BookId)){
             AlertMaker.TypeError("Failed",null,"Book "+BookId+" has already issued");
             ClearIssuedtextCache();
             return;
         }

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue");
        alert.setHeaderText(null);
        alert.setContentText("Are You Sure Issue Book "+BookName.getText()+"to\n"+MemberName.getText());

        Optional<ButtonType> Response=alert.showAndWait();
        if(Response.get()==ButtonType.OK){
            String sql="INSERT INTO ISSUE(Book_id,Member_id)"+
                    "Values("+"'"+BookId+"',"+
                    "'"+MemberId+"')";
            String sql2="UPDATE BOOK SET isAvail=false WHERE Book_ID='"+BookId+"'";
            if(handler.execAction(sql) && handler.execAction(sql2)){

                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Complete");
                alert1.showAndWait();
                ClearIssuedtextCache();
            }else{
                Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Canselled");
                alert1.showAndWait();
                txtBookId.setStyle("-fx-text-inner-color: red;");

            }
        }else{
           ClearIssuedtextCache();
        }

    }

    @FXML
    void LoadFullyData(ActionEvent event) throws SQLException {
        ObservableList<String> list= FXCollections.observableArrayList();
        String BookId=txtRenwTabBookId.getText();
        String qu="select * from ISSUE where Book_id='"+BookId+"'";
        ResultSet set=handler.execQuery(qu);
        while(set.next()){
           list.add("Issue Date and Time :"+set.getTimestamp(3).toGMTString());
           list.add("Renew Count :"+set.getInt(4));
           ResultSet set1=handler.execQuery("Select * from BOOK where Book_id='"+BookId+"'");
             while(set1.next()){
                 list.add("BOOK INFO :-");
                 list.add("Book Name :"+set1.getString(2));
                 list.add("Author :"+set1.getString(3));
             }
           ResultSet set2=handler.execQuery("select* from MEMBER where Member_id='"+set.getString(2)+"' ");
              while(set2.next()){
                  list.add("MEMBER INFO :-");
                  list.add("Member Name :"+set2.getString(2));
                  list.add("Contact :"+set2.getString(3));
              }
        }
        ListView.getItems().setAll(list);

    }
    @FXML
    void BookSubmission(ActionEvent event) throws SQLException {
        if(IsAvail(txtRenwTabBookId.getText(),"BOOK")){
            Alert alret=new Alert(Alert.AlertType.ERROR);
            alret.setHeaderText(null);
            alret.setTitle("Error");
            alret.setContentText("Book already has been Submitted or Invalid BookId\n Try Again");
            alret.showAndWait();
            return;
        }
        Alert alret1=new Alert(Alert.AlertType.CONFIRMATION);
        alret1.setTitle("Submition");
        alret1.setHeaderText(null);
        alret1.setContentText("Cornfirm the Submition");
        Optional<ButtonType> response=alret1.showAndWait();
if(response.get()==ButtonType.OK) {
    String qu = "Delete from ISSUE where Book_id='" + txtRenwTabBookId.getText() + "'";
    String qu1 = "Update BOOK set isAvail=true where Book_id='" + txtRenwTabBookId.getText() + "'";
    if (handler.execAction(qu) && handler.execAction(qu1)) {
        Alert alret = new Alert(Alert.AlertType.INFORMATION);
        alret.setTitle("Success");
        alret.setHeaderText(null);
        alret.setContentText("Book has been Submitted");
        alret.show();
    } else {
        Alert alret = new Alert(Alert.AlertType.INFORMATION);
        alret.setTitle("Failes");
        alret.setHeaderText(null);
        alret.setContentText("Submitted Failed");
        alret.show();
    }
}else{
    return;
}
    }

    private boolean IsAvail(String Id,String Name) throws SQLException {
        if(Name.equals("BOOK")) {
            ResultSet set = handler.execQuery("Select isAvail from BOOK where Book_id='" + Id+"'");
            while(set.next()){
                return  set.getString("isAvail").equals("true");
            }
        }else if(Name.equals("MEMBER")){
           // ResultSet set = handler.execQuery("Select isAvail from Book where Book_id='" + Id);
        }
        return false;
    }


    @FXML
    void LoadRenewOp(ActionEvent event) throws SQLException {
       if(!IsAvail(txtRenwTabBookId.getText(),"BOOK")){
           Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
           alert.setTitle("Renew Book");
           alert.setHeaderText(null);
           alert.setContentText("Confirm Renew Book "+txtRenwTabBookId.getText());

           Optional<ButtonType> response=alert.showAndWait();
           if(response.get()==ButtonType.OK){
               if(handler.execAction("Update ISSUE SET IssueTime=CURRENT_TIMESTAMP, RenewCount=RenewCount+1 where Book_id='"+txtRenwTabBookId.getText()+"'")){
                   Alert al=new Alert(Alert.AlertType.INFORMATION);
                   al.setTitle("Success");
                   al.setHeaderText(null);
                   al.setContentText("Process Sucesses");
                   al.showAndWait();
                   ListView.getItems().clear();
                   LoadFullyData(event);
               }else{
                   Alert al=new Alert(Alert.AlertType.INFORMATION);
                   al.setTitle("Failed");
                   al.setHeaderText(null);
                   al.setContentText("Process Failed");
                   al.showAndWait();
                   ListView.getItems().clear();
               }
           }

       }else{
           Alert al=new Alert(Alert.AlertType.ERROR);
           al.setTitle("Failed");
           al.setHeaderText(null);
           al.setContentText("Renew Failed");
           al.showAndWait();
       }
    }


    void BookClearCache(){
        BookName.setText(null);
        Author.setText(null);
        BookStatus.setText(null);
     }
     void MemberClearCache(){
        MemberName.setText(null);
        Contact.setText(null);
     }


    @FXML
    void menuAddBookHandle(ActionEvent event) {
       LoadAddBokk(event);
    }

    @FXML
    void menuAddMemberHandle(ActionEvent event) {
       LoadAddmember(event);
    }

    @FXML
    void menuCloseHandle(ActionEvent event) {
        ((Stage)rootPane.getScene().getWindow()).close();
    }
    @FXML
    void menuViewBookHandle(ActionEvent event) throws SQLException {
       ViewBookList(event);
    }

    @FXML
    void menuViewMemberHandle(ActionEvent event) {
     ViewmemberLis(event);
    }

    @FXML
    void menuFullScreenHandle(ActionEvent event) {
     Stage st=(Stage)rootPane.getScene().getWindow();
     st.setFullScreen(true);
    }
    @FXML
    void menuExitFullScreenHandle(ActionEvent event) {
        Stage st=(Stage)rootPane.getScene().getWindow();
        st.setFullScreen(false);
    }
    void ClearIssuedtextCache(){
        txtBookId.setText(null);
        txtMemberId.setText(null);
    }
}
