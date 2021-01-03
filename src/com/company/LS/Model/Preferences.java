package com.company.LS.Model;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Preferences {
    public static final String Config_File="config.txt";
    private static String filename="Listdata.txt";
    int nDaywithoufine;
    int Fineperday;
    String UserName;
    String Password;

    public static int without=14;
    public static int fine=2;

    public Preferences() {
        nDaywithoufine = without;
        Fineperday = fine;
        UserName = "admin";
        Password = "admin";
    }
    public static void SetDefault(int x,int y){
        without=x;
        fine=y;
    }
    public int getnDaywithoufine() {
        return nDaywithoufine;
    }

    public void setnDaywithoufine(int nDaywithoufine) {
        this.nDaywithoufine = nDaywithoufine;
    }

    public int getFineperday() {
        return Fineperday;
    }

    public void setFineperday(int fineperday) {
        Fineperday = fineperday;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return DigestUtils.shaHex(Password);
    }

    public void setPassword(String password) {
        Password = password;
    }

    public static void initConfig() throws IOException {
      /*  try {
            Writer writer=null;

            Preferences p=new Preferences();
            Gson gson=new Gson();
            writer = new FileWriter(Config_File);
            gson.toJson(p,writer);

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
        ObservableList<Preferences> lis= FXCollections.observableArrayList();
        Preferences p=new Preferences();
        lis.add(p);
        Path path= Paths.get(filename);
        BufferedWriter bw= Files.newBufferedWriter(path);

     try{
         Iterator<Preferences> list= lis.iterator();
        while(list.hasNext()){
           Preferences item=list.next();
            bw.write(String.format("%s\t%s\t%s\t%s\t",
                    item.getnDaywithoufine(),
                    item.getFineperday(),
                    item.getUserName(),
                    item.getPassword()));
            bw.newLine();

        }
    }finally {
        if(bw!=null){
            bw.close();
        }
    }

    }

    public static String [] GetValues() throws IOException {
        List<String> list=new ArrayList<>();
         Path path= Paths.get(filename);
         BufferedReader br= Files.newBufferedReader(path);

         String input;
       //  String [] itemPeices=new String[10];
         try{
             while((input=br.readLine())!=null){
                 String []   itemPeices=input.split("\t");
                 list.add(input);
                 return itemPeices;
                 /*String s=itemPeices[0];
                 String details=itemPeices[1];
                 String dateString=itemPeices[2];
                 String*/

             }


         }finally {
             if(br!=null){
                 br.close();
             }
         }
       return null;
     }
}
