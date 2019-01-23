package sample;


import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

import static sample.ControlForms.thisUser;
import static sample.Main.users;

public class ReplacePassword implements java.io.Serializable {

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
    public static void toReplacePassword(){
        Date date = new Date();
        for (Password password : thisUser.getPasswords()){
            if (password.isActive()){
                if (daysBetween(password.getRegistrationDate(), date) > Constants.DAYS_FOR_REPLACE_PASSWORD){
                    ControlForms.getWindow(3);
                }
            }
        }
    }
}
