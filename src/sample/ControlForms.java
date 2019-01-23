package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;

import static sample.Main.users;


public class ControlForms implements java.io.Serializable {
    public static String thisLogin= "";
    public static User thisUser = new User();

    @FXML
    Button btn_Register, btn_RegisterReg, btn_Login, btn_Back, btn_Replace, btn_Close;
    @FXML
    Label lbl_Login, lbl_Password, lbl_Finish, lbl_LoginReg, lbl_PasswordReg, lbl_FinishReg,
            lbl_OldPassword, lbl_NewPassword, lbl_NewPasswordRepeat, lbl_FinishReplace;
    @FXML
    TextField txt_Login, txt_Password, txt_LoginReg, txt_PasswordReg, txt_OldPassword, txt_NewPassword, txt_NewPasswordRepeat;


    public void onBtnRegisterReg(ActionEvent actionEvent) {
        String login = txt_LoginReg.getText();
        String password = txt_PasswordReg.getText();
        lbl_LoginReg.setText("");
        lbl_PasswordReg.setText("");
        lbl_FinishReg.setText("");
        for (User user : users) {
            if (user.login.equals(login)) {
                lbl_LoginReg.setText("Login is already used!");
                return;
            }

        }
        int errCodeLogin = Login.isValid(login);
        switch (errCodeLogin) {
            case 1: {
                lbl_LoginReg.setText("Minimal login length is: " + Constants.LOGIN_LENGTH);
                break;
            }
            case 2: {
                lbl_LoginReg.setText("Login must contain abc");
                break;
            }
            case 3: {
                lbl_LoginReg.setText("Unknown symbol ");
                break;
            }
            default: {
                lbl_LoginReg.setText("✓");
                break;
            }
        }
        if (errCodeLogin > 0) return;
        int errCodePassword = Password.isValid(password);
        switch (errCodePassword) {
            case 1: {
                lbl_PasswordReg.setText("Minimal password length is: " + Constants.PASSWORD_LENGTH);
                break;

            }
            case 2: {
                lbl_PasswordReg.setText("Please add UpperCase char");
                break;

            }
            case 3: {
                lbl_PasswordReg.setText("Please add lowerCase char");
                break;

            }
            case 4: {
                lbl_PasswordReg.setText("Please add digit char");
                break;

            }
            case 5: {
                lbl_PasswordReg.setText("add 1 non-symbol and non-digit");
                break;

            }
            default: {
                lbl_PasswordReg.setText("✓");
                break;

            }
        }
        if (errCodePassword > 0) return;
        lbl_FinishReg.setText("You are registered!");
        Password password1 = new Password(password);
        ArrayList<Password> passwords = new ArrayList<>();
        passwords.add(password1);
        User user = new User(login, passwords);
        users.add(user);
        System.out.println("Total users: " + users.size());
    }

    public void onBtnLogin(ActionEvent actionEvent) {

        String login = txt_Login.getText();
        String password = txt_Password.getText();
        int counter = 0;
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                thisUser = user;
                counter++;
                for (Password password1 : user.getPasswords()) {
                    if (password1.getPassword().equals(password)) {
                        counter++;
                        if (password1.isActive()) {
                            counter++;
                            break;
                        }
                    }
                }
            }
        }
        switch (counter) {
            case 0: {
                lbl_Login.setText("This user is not registered");
                lbl_Password.setText("");
                break;
            }
            case 1: {
                lbl_Login.setText("");
                lbl_Password.setText("This password is wrong");
                break;
            }
            case 2: {
                lbl_Login.setText("");
                lbl_Password.setText("This password is not active");
                break;
            }
            case 3: {
                lbl_Login.setText("");
                lbl_Password.setText("");
                ReplacePassword.toReplacePassword();
                lbl_Finish.setText("Welcome to you page !!!");
                thisLogin = txt_Login.getText();

                for (User user: users){

                    System.out.println(user.getLogin());
                    for (Password password1 : user.getPasswords()){
                        System.out.println("password - " + password1.getPassword() + ", registration date - " + password1.getRegistrationDate() + ", activation is -" + password1.isActive());
                    }
                }

                break;
            }
        }
    }

    public void onBtnReplace(ActionEvent actionEvent) {
  //      String login = txt_Login.getText();
        String passwordThis = txt_OldPassword.getText();
        String passwordNew = txt_NewPassword.getText();
        String passwordNewRepeat = txt_NewPasswordRepeat.getText();
        Password password1 = new Password(passwordNew);
        int counter = 0; // 0 user not active
        for (User user : users) {
            if (user.getLogin().equals(thisLogin)) {
                counter++; // 1 input last password
                for (Password password2 : user.getPasswords()) {
                    if (password2.getPassword().equals(passwordThis)) {
                        counter++;//2 input active password
                        if (password2.isActive()) {
                            counter++; // 3 New password is valid
                            if (Password.isValid(passwordNew) == 0) {
                                counter++; //4 Repeat password is different
                                for (Password password3 : user.getPasswords()) {
                                    if (password3.getPassword().equals(passwordNew)) {
                                        counter++; // 5 This password has already been used
                                        break;
                                    }
                                }
                                if (counter == 4) {
                                    if (passwordNew.equals(passwordNewRepeat)) {
                                        for (Password password4 : user.getPasswords()){
                                            password4.setActive(false);
                                            counter = 6;
                                        }
                                        user.getPasswords().add(password1);
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }
            }

        }
        switch (counter) {
            case 0: {
                lbl_OldPassword.setText("0");
                lbl_NewPassword.setText("0");
                lbl_NewPasswordRepeat.setText("0");
                lbl_FinishReplace.setText("User not active");
                break;
            }
            case 1: {
                lbl_OldPassword.setText("Please input last password");
                lbl_NewPassword.setText("");
                lbl_NewPasswordRepeat.setText("");
                lbl_FinishReplace.setText("");
                break;
            }
            case 2: {
                lbl_OldPassword.setText("Please input active password");
                lbl_NewPassword.setText("");
                lbl_NewPasswordRepeat.setText("");
                lbl_FinishReplace.setText("");
                break;
            }
            case 3: {
                lbl_OldPassword.setText("✓");
                lbl_NewPassword.setText("New password is not valid");
                lbl_NewPasswordRepeat.setText("");
                lbl_FinishReplace.setText("");
                break;
            }
            case 4: {
                lbl_OldPassword.setText("✓");
                lbl_NewPassword.setText("");
                lbl_NewPasswordRepeat.setText("Repeat password is different");
                lbl_FinishReplace.setText("");
                break;
            }
            case 5: {
                lbl_OldPassword.setText("✓");
                lbl_NewPassword.setText("This password has already been used");
                lbl_NewPasswordRepeat.setText("");
                lbl_FinishReplace.setText("");
                break;
            }
            default: {
                lbl_OldPassword.setText("✓");
                lbl_NewPassword.setText("✓");
                lbl_NewPasswordRepeat.setText("✓");
                lbl_FinishReplace.setText("Password has been replaced");
                break;
            }
        }

    }

    public void onBtnRegister(ActionEvent actionEvent) {
        getWindow(2);
        btn_Register.getScene().getWindow().hide();

    }
    public void onBtnBack(ActionEvent actionEvent) {
        getWindow(1);
        btn_Back.getScene().getWindow().hide();
    }
    public void onBtnReplaceForm(ActionEvent actionEvent) {
        getWindow(3);
    }
    public void onBtnCloce(ActionEvent actionEvent) {
        btn_Close.getScene().getWindow().hide();
    }
    public static void getWindow(int code) {
        Parent parent = null;

        try {
            switch (code) {
                case 1:
                    parent = FXMLLoader.load(ControlForms.class.getResource("fxml/loginForm.fxml"));
                    break;
                case 2:
                    parent = FXMLLoader.load(ControlForms.class.getResource("fxml/registrationForm.fxml"));
                    break;
                case 3:
                    parent = FXMLLoader.load(ControlForms.class.getResource("fxml/replacePasswordForm.fxml"));
                    break;
                default:
                    return;
            }
            Stage stageForm = new Stage();
            stageForm.setScene(new Scene(parent, 650, 300));
            switch (code) {
                case 1:
                    stageForm.setTitle("Login form");
                    stageForm.getScene().getStylesheets().add(ControlForms.class.getResource("css/loginStyle.css").toExternalForm());
                    break;
                case 2:
                    stageForm.setTitle("Registration form");
                    stageForm.getScene().getStylesheets().add(ControlForms.class.getResource("css/registrationStyle.css").toExternalForm());
                    break;
                case 3:
                    stageForm.initModality(Modality.APPLICATION_MODAL);
                    stageForm.setTitle("Replace password");
                    stageForm.getScene().getStylesheets().add(ControlForms.class.getResource("css/replaceStyle.css").toExternalForm());
                    break;
                default:
                    return;
            }
            stageForm.show();
            stageForm.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (code!=3){
                        System.out.println("Windows is closed!!");
                        try {
                            FileOutputStream fileOutput = new FileOutputStream("./users.ser");
                            ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
                            objectOutput.writeObject(users);
                            objectOutput.flush();
                            objectOutput.close();
                            fileOutput.flush();
                            fileOutput.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
