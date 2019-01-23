package sample;

import java.util.Date;

public class Password implements java.io.Serializable {
    Date registrationDate;
    private String password;
    private boolean isActive;

    public Password() {
    }

    public Password(String password) {
        this.password = password;
        this.registrationDate = new Date();
        this.isActive = true;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static int isValid(String password) {
        int counter = 0;
        if (password.length() < Constants.PASSWORD_LENGTH) {
            return 1;
        }
        for (char c : password.toCharArray()) {
            if (Constants.UpperCaseSymbols.contains(c + "")) {
                counter++;
                break;
            }
        }
        if (counter == 0) return 2;
        counter = 0;
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                counter++;
                break;
            }
        }
        if (counter == 0) return 3;
        counter = 0;
        for (char c : password.toCharArray()){
            if (Character.isDigit(c)){
                counter++;
                break;
            }
        }
        if (counter==0) return 4;
        counter=0;
        for (char c : password.toCharArray()){
            if (!Character.isDigit(c) && !Character.isAlphabetic(c)){
                counter++;
                break;
            }
        }
        if (counter==0) return 5;
        else  return 0;
    }
    public static boolean isPasswordContains(String password, Password password1){

        if (password1.getPassword().equals(password)) return true;
        else return false;

    }
}
