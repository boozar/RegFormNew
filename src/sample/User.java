package sample;

import java.util.ArrayList;


public class User implements java.io.Serializable {
    String login;
    public  ArrayList<Password> passwords;

    public User() {
    }

    public User(String login, ArrayList<Password> passwords) {
        this.login = login;
        this.passwords = passwords;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public ArrayList<Password> getPasswords() {
        return passwords;
    }

}
