package com.company.LS.Model;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;

public  class SetIcon {
    private static  String path;

    public static void  setDeafaultStageIcon(Stage st){
        path="com/company/LS/Assests/MianIcon.png";
        st.getIcons().add(new Image(path));
    }
    public static void setNewStageicon(Stage st,String p){
        path=p;
        st.getIcons().add(new Image(String.valueOf(path)));
    }
}
