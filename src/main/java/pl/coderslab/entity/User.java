package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.UserDAO;

import java.util.Scanner;

import static pl.coderslab.Static_Parameters.*;

public class User {

    private long id;
    private String email;
    private String username;
    private String password;

    public User(String email, String username, String password) {

        this.email = email;
        this.username = username;
        this.password = hashPassword(password);

        if (isInsertable()){
            this.id = UserDAO.create(this);
        }
        else {
            this.id = 0;
        }
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (checkPassword("Aby zmienić email wprowadź hasło: ")) {
            if (isEmail(email)) {
                this.email = email;
                if (isInsertable()) {
                    if (isInBase()) {
                        UserDAO.updateEmail(this);
                    } else {
                        UserDAO.create(this);
                    }
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (checkPassword("Aby zmienić nazwę użytkownika wprowadź hasło: ")) {
            this.username = username;
            if (isInsertable()) {
                if (isInBase()) {
                    UserDAO.updateUsername(this);
                } else {
                    UserDAO.create(this);
                }
            }
        }

    }

    //nie podoba mi się, że to jest publiczne, ale nie mam pomysłu jak je włożyć do bazy danych inaczej,
    //a jest zahashowane więc mniejsze zło...
    public String getPassword() {
        return password;
    }

    //zmiana hasła
    public void setPassword(String password) {
        if (checkPassword("Aby zmienić hasło wpisz stare hasło:")){
            this.password = password;
            System.out.println("Hasło zostało zmienione");
            if (isInsertable()){
                if (isInBase()){
                    UserDAO.updatePassword(this);
                }
                else {
                    UserDAO.create(this);
                }
            }
        }

    }

    //sprawdza czy można włożyć usera do bazy danych bez błędu
    private boolean isInsertable (){
        if(!UserDAO.isEmailUnique(this.email)) {
            System.out.println("W bazie jest już ten email");
            return false;
        }
        else if (!isEmail(this.email)){

        }
        else if (this.email.length() > EMAIL_LENGTH || this.username.length() > USERNAME_LENGTH){
            System.out.println("Wprowadzone dane są za długie. Parametry:\n" +
                    "username - " + USERNAME_LENGTH + "\n" +
                    "email - " + EMAIL_LENGTH
            );
            return false;
        }

        return true;
    }

    //zahashowie hasła
    private String hashPassword(String password) {

        return BCrypt.hashpw(password, BCrypt.gensalt());

    }

    //sprawdzenie hasła przy próbie zmiany innych parametrów
    private boolean checkPassword(String message) {
        System.out.println(message);
        Scanner scan = new Scanner(System.in);
        String oldPassword = scan.nextLine();
        if (BCrypt.checkpw(oldPassword, this.password)){
            return true;
        }
        else {
            System.out.println("Podane hasło jest niepoprawne, operacja się nie powiodła.");
            return false;
        }
    }

    //sprawdzanie formatu emaila (czy to potrzebne?)
    private boolean isEmail (String address) {
        if (address.matches(
                "([a-z1-9]*@[a-z1-9]*.[a-z]*)")){
            return true;
        }
        else {
            System.out.println("Wprowadzona dana nie ma formatu adresu email");
            return false;
        }
    }

    //sprawdzanie czy element jest w bazie. Opłaca się robić takie metody które nie dają uzysku w kodzie?
    private boolean isInBase (){
        if (this.id == 0){
            return false;
        } else {
            return true;
        }
    }

    public void delete (){
        if (this.isInBase()){
            UserDAO.delete(this);
            this.id = 0;
        }
        this.email = "";
        this.password = "";
        this.username = "";
    }
}

