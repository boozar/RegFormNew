package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Main extends Application implements java.io.Serializable {


    @Override
    public void start(Stage loginForm) throws Exception {
        ControlForms.getWindow(1);
    }

    public static ArrayList<User> users;

    public static void main(String[] args) {
        users = new ArrayList<>();
        try {
            FileInputStream fileInput = new FileInputStream("./users.ser");
            ObjectInputStream objectInput = new ObjectInputStream(fileInput);

            users = (ArrayList<User>) objectInput.readObject();

            File file = new File("./users.ser");
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        launch(args);
    }

}
