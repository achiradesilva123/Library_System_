package com.company.LS.Controller;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.AlertMaker;
import com.company.LS.Model.Book;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddbookController implements Initializable {

    @FXML
    private AnchorPane pnAddbookMain;
    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtAuthor;

    @FXML
    private JFXTextField txtPublisher;

    @FXML
    private JFXButton btAddBook;

    @FXML
    private JFXButton btCancel;

    DatabaseHandler databaseHandler;

    private Boolean IsGonnaEdit=Boolean.FALSE;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      databaseHandler=DatabaseHandler.getInstance();
    }
    @FXML
    void Addbook(ActionEvent event) throws SQLException {
        String BookId=txtId.getText();
        String Title=txtTitle.getText();
        String Author=txtAuthor.getText();
        String Publisher=txtPublisher.getText();
        System.out.println();
        if(BookId.equals("") || Title.equals("") ||Author.equals("") || Publisher.equals("")){
           AlertMaker.TypeError("Error",null,"Please Fill All values");
            return;
        }
        if(IsGonnaEdit){
            if(!databaseHandler.IsAlreadyissued(txtId.getText())){
                if(AlertMaker.TypeConfimIsOk("Conrimation",null,"Are You sure Update Book"+txtTitle.getText())){
               UpdateBook();
               return;
           }else{
            return;
           }
            }else {
                AlertMaker.TypeError("Failed",null,"Update Failed Try Again");
                return;
            }
        }
        String sql="INSERT INTO BOOK "+
                "Values("+"'"+BookId+"',"+
                            "'"+Title+"',"+
                           "'"+Author+"',"+
                           "'"+Publisher+"',"+"'"+"true"+"'"+")";
        System.out.println(sql);
        if(databaseHandler.execAction(sql)){
            AlertMaker.TypeInfromation("Sucess",null,"Adding Complete");
        }else{
            AlertMaker.TypeError("Failed",null,"Adding failed Try Again");
        }
    }

    private void UpdateBook() {
        Book b=new Book(txtId.getText(),txtTitle.getText(),txtAuthor.getText(),txtPublisher.getText(),"true");
        System.out.println(b.getBookId());
        if(databaseHandler.UpdateBook(b)){
            AlertMaker.TypeInfromation("Sucess", null, "Update Complete");
        }else{
            AlertMaker.TypeError("Failed",null,"Update Failed Try Again");
        }
    }

    @FXML
    void Cancel(ActionEvent event) {
        Stage st= (Stage) pnAddbookMain.getScene().getWindow();
        st.close();
    }

    public void init(Book book){
        txtTitle.setText(book.getTitle());
        txtId.setText(book.getBookId());
        txtAuthor.setText(book.getAuthor());
        txtPublisher.setText(book.getPublisher());
        txtId.setEditable(false);
        IsGonnaEdit=Boolean.TRUE;
    }
}
