package com.company.LS.Controller;

import com.company.LS.Database.DatabaseHandler;
import com.company.LS.Model.Book;
import com.company.LS.Model.SetIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class bookListController implements Initializable {
    @FXML
    private AnchorPane pntableMain;

    @FXML
    private TableView<Book> tbleView;

    @FXML
    private TableColumn<Book, String> col_Title;

    @FXML
    private TableColumn<Book, String> col_BookId;

    @FXML
    private TableColumn<Book, String> col_Author;

    @FXML
    private TableColumn<Book, String> col_Publisher;

    @FXML
    private TableColumn<Book, Boolean> col_Availability;

    ObservableList<Book> list= FXCollections.observableArrayList();
    DatabaseHandler handler;
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
        ResultSet set=handler.execQuery("select * from BOOK");
        while(set.next()){
            list.add(new Book(set.getString(1),set.getString(2),set.getString(3),set.getString(4),set.getString(5)));
        }
        tbleView.getItems().setAll(list);


    }

    private void InitColumn() {
        col_Title.setCellValueFactory(new PropertyValueFactory<>("Title"));
        col_BookId.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        col_Author.setCellValueFactory(new PropertyValueFactory<>("Author"));
        col_Publisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        col_Availability.setCellValueFactory(new PropertyValueFactory<>("IsAvail"));
    }

    @FXML
    void DeleteData(ActionEvent event) throws SQLException {
    Book b=tbleView.getSelectionModel().getSelectedItem();
    if(b==null){
        Alert al=new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("Unselected item");
        al.setHeaderText(null);
        al.setContentText("Select book You want to Delete");
        al.showAndWait();
    }else if(handler.IsAlreadyissued(b.getBookId())){
        Alert al=new Alert(Alert.AlertType.ERROR);
        al.setTitle("Access Denied");
        al.setHeaderText(null);
        al.setContentText("     Cant delete \n Book Already has issued");
        al.showAndWait();
    }else{
        Alert al=new Alert(Alert.AlertType.CONFIRMATION);
        al.setTitle("Delete");
        al.setHeaderText(null);
        al.setContentText("Do You Want to Delete Book book_id :"+b.getBookId());
        Optional<ButtonType> Response=al.showAndWait();
        if(Response.get()==ButtonType.OK) {

            if (handler.DeleteBook(b.getBookId())) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Sucess");
                a.setHeaderText(null);
                a.setContentText("Delete Sucess");
                a.showAndWait();
                tbleView.getItems().remove(b);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("Failed");
                a.setHeaderText(null);
                a.setContentText("Delete Failed");
                a.showAndWait();
            }
        }else{
            return;
        }
    }
    }

    @FXML
    void EditData(ActionEvent event) throws IOException {
        Book b=tbleView.getSelectionModel().getSelectedItem();
        if(b==null){
            Alert al=new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("Unselected item");
            al.setHeaderText(null);
            al.setContentText("Select book You want to Delete");
            al.showAndWait();
        }else{
            FXMLLoader fx=new FXMLLoader(getClass().getResource("../View/Form/Addbook.fxml"));
            Parent root= fx.load();

            AddbookController controller=(AddbookController)fx.getController();
            controller.init(b);
            Stage st=new Stage(StageStyle.DECORATED);
            st.setScene(new Scene(root));
            st.setTitle("Edit");
            SetIcon.setDeafaultStageIcon(st);
            st.show();
        }

    }
    @FXML
    void Refesh(ActionEvent event) throws SQLException {
       //tbleView.getItems().clear();
       list.clear();
       LoadData();
    }


}
