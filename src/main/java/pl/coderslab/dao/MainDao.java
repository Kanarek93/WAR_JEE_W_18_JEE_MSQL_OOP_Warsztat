package pl.coderslab.dao;

import pl.coderslab.entity.User;

public class MainDao {

    private static int SAMPLE = 10;

    public static void main(String[] args) {
        UserDAO ud = new UserDAO();
      //  User u = new User("name4@name.pl", "name", "pass");

        User uNew = ud.read(SAMPLE);

        uNew.setEmail("name3@name.pl");

        ud.update(uNew);

        System.out.println(ud.read(SAMPLE));

        System.out.println(ud.readAll());
        ud.delete(SAMPLE);
    }
}
