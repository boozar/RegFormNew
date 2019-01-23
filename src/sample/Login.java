package sample;

public class Login implements java.io.Serializable {

    String login;

    public Login(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public static int isValid(String login) {
        int counter = 0;
        if (login.length() < Constants.LOGIN_LENGTH)
            return 1;
        for (char c : login.toCharArray()) {
            if (!Character.isAlphabetic(c) && !Character.isDigit(c)) {
                counter++;
                break;
            }
        }
        if (counter != 0) return 3;
        counter=0;
        for (char c : login.toCharArray()) {
            if (Character.isAlphabetic(c)){
                counter++;
                break;
            }
        }
        if (counter==0) return 2;

        else return 0;
    }
}
