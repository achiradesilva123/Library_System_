package com.company.LS.Model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMaker {

   public static void TypeInfromation(String title,String header,String contentText){
       Alert al=new Alert(Alert.AlertType.INFORMATION);
       al.setTitle(title);
       al.setHeaderText(header);
       al.setContentText(contentText);
       al.showAndWait();
   }

   public static void TypeError(String title,String header,String contentText){
       Alert al=new Alert(Alert.AlertType.ERROR);
       al.setTitle(title);
       al.setHeaderText(header);
       al.setContentText(contentText);
       al.showAndWait();
   }

   public static boolean TypeConfimIsOk(String title,String header,String contentText){
       Alert al=new Alert(Alert.AlertType.CONFIRMATION);
       al.setTitle(title);
       al.setHeaderText(header);
       al.setContentText(contentText);
       Optional<ButtonType>Res=al.showAndWait();
       return Res.get()==ButtonType.OK;
   }
}
