package com.company.LS.Database;
import com.company.LS.Model.Book;
import com.company.LS.Model.Member;

import java.lang.Boolean;
import javax.swing.*;
import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler;
    private static final String DB_url = "jdbc:derby:database/Library;create=true";
    private static Connection con;
    private static Statement stmt;

    private DatabaseHandler() {
        createConnection();
        setupBooktable();
        setupMembertable();
        setupIssueTable();
    }
    public static DatabaseHandler getInstance(){
        return (handler==null) ? (handler=new DatabaseHandler()) : handler;
    }

    void createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            con= DriverManager.getConnection(DB_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void setupBooktable(){
        String Table_Name="BOOK";
        try {
            stmt=con.createStatement();
            DatabaseMetaData dbm=con.getMetaData();
            ResultSet tables=dbm.getTables(null,null,Table_Name.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+Table_Name+" Already Exists");
            }else{
                stmt.execute("CREATE TABLE "+Table_Name+"("
                        +"Book_id VARCHAR(200) PRIMARY KEY,\n"
                        +"title VARCHAR(200) ,\n"
                        +"Author VARCHAR(200) ,\n"
                        +"publisher VARCHAR(200),\n"
                        +"isAvail Boolean default true"
                        +")");
            }
        } catch (SQLException e) {
           System.err.println(e.getMessage()+".....Setup");

        }finally {

        }
    }
    private void setupMembertable() {
        String Table_Name="MEMBER";
        try {
            stmt=con.createStatement();
            DatabaseMetaData dbm=con.getMetaData();
            ResultSet tables=dbm.getTables(null,null,Table_Name.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+Table_Name+" Already Exists");
            }else{
                stmt.execute("CREATE TABLE "+Table_Name+"("
                        +"Member_id VARCHAR(200) PRIMARY KEY,\n"
                        +"Name VARCHAR(200) ,\n"
                        +"Mobile VARCHAR(200) ,\n"
                        +"Email VARCHAR(200)"
                        +")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage()+".....Setup");

        }finally {

        }
    }

    void setupIssueTable(){
        String Table_Name="ISSUE";
        try {
            stmt=con.createStatement();
            DatabaseMetaData dbm=con.getMetaData();
            ResultSet tables=dbm.getTables(null,null,Table_Name.toUpperCase(),null);
            if(tables.next()){
                System.out.println("Table "+Table_Name+" Already Exists");
            }else{
                stmt.execute("CREATE TABLE "+Table_Name+"("
                        +"Book_id VARCHAR(200) PRIMARY KEY,\n"
                        +"Member_id VARCHAR(200) ,\n"
                        +"IssueTime TIMESTAMP default CURRENT_TIMESTAMP ,\n"
                        +"RenewCount INTEGER default 0 ,\n"
                        +"FOREIGN KEY (Book_id) REFERENCES BOOK(Book_id),\n"
                        +"FOREIGN KEY (Member_id) REFERENCES MEMBER(Member_id) "
                        +")");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage()+".....Setup");

        }finally {

        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
          //  stmt = con.createStatement();
            result = con.createStatement().executeQuery(query);
        }
        catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        }
        finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = con.createStatement();
            stmt.execute(qu);
            return true;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        }
        finally {
        }
    }

    public boolean DeleteBook(String id) throws SQLException {
      return con.createStatement().executeUpdate("Delete from BOOK where Book_id='"+id+"'")>0;
    }

    public boolean IsAlreadyissued(String id) throws SQLException {
        ResultSet set = con.createStatement().executeQuery("Select COUNT(*) from ISSUE where Book_id='" + id + "'");
        while (set.next()) {
            return set.getInt(1) > 0;
        }
        return false;
    }

    public boolean IsMemberHasBook(String id) throws SQLException {
        ResultSet set = con.createStatement().executeQuery("Select COUNT(*) from ISSUE where Member_id='" + id + "'");
        while (set.next()) {
            return set.getInt(1) > 0;
        }
        return false;
    }
    public boolean UpdateBook(Book b){
        try {
            PreparedStatement st=con.prepareStatement("Update BOOK set title=?,Author=?,publisher=? where Book_id=?");
            st.setString(1,b.getTitle());
            st.setString(2,b.getAuthor());
            st.setString(3,b.getPublisher());
            st.setString(4,b.getBookId());
               int x=st.executeUpdate();
            System.out.println(x);
             return x >0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean DeleteMember(String id) throws SQLException {
        return con.createStatement().executeUpdate("Delete from MEMBER where Member_id='"+id+"'")>0;
    }

    public boolean UpdateMember(Member m){
        PreparedStatement st= null;
        try {
            st = con.prepareStatement("Update MEMBER set Name=?,Mobile=?,Email=? where Member_id=?");
            st.setString(1,m.getName());
            st.setString(2,m.getMobile());
            st.setString(3,m.getEmail());
            st.setString(4,m.getMemberId());
            int x=st.executeUpdate();
            System.out.println(x);
            return x >0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
