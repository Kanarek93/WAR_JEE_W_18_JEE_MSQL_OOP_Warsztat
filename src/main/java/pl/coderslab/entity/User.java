package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.dao.UserDAO;

import java.util.Scanner;

import static pl.coderslab.Static_Parameters.*;

public class User {

    private Integer id;
    private String email;
    private String username;
    private String password;

    public User(String email, String username, String password) {

        this.email = email;
        this.username = username;
        //hashować tutaj czy w DAO?
        this.password = hashPassword(password);
        this.id = 0;
    }

    public User () {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //sprawdza czy dane są wypełnione, mają dobre długośi i email ma odpowiedni format
    //NIE SPRAWDZA unikalności emaila
    private boolean isInsertable (String email, String username, String password){
        if (username.isEmpty() || password.isEmpty()){
            return false;
        }
        if (!isEmail(email)){
            return false;
        }
        if (email.length() > EMAIL_LENGTH || username.length() > USERNAME_LENGTH){
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

    //sprawdzanie formatu emaila
    private boolean isEmail (String address) {
        if (address.matches(
                "[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.([a-zA-Z]{2,}){1}")){
            return true;
        }
        else {
            System.out.println("Wprowadzona dana nie ma formatu adresu email");
            return false;
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

