package pl.coderslab;

import pl.coderslab.dao.UserDAO;
import pl.coderslab.entity.User;

public class Main {

    public static void main(String[] args) {

        User user1 = new User("daria", "Canary", "daria123");
        User user2 = new User("daria@daria.pl", "Canary", "daria123");
        User user3 = new User("daria@daria.pl", "Canary", "daria123");

        user2.setPassword("123456");
        user1.setEmail("daria@kowalik.pl");
        user3.setEmail("daria2@kowalik.pl");

        System.out.println(user1.getId());
        user1.delete();
        System.out.println(user1.getId());
      //  System.out.println(String.valueOf(123));

     //   long i = UserDAO.create(user1);
      //  System.out.println(i);
    }
}
