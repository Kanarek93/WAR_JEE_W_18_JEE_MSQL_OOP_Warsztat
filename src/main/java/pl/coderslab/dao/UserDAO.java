package pl.coderslab.dao;

import pl.coderslab.DbUtil;
import pl.coderslab.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;


public class UserDAO {

    //insert
    private final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    //update
    private final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    //delete
    private final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    //read
    private final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private final String READ_ALL_QUERY =
            "SELECT * FROM users";

    public User create (User u) {

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                u.setId(rs.getInt(1));
            }
            System.out.println("Użytkownik "+ u.getUsername() + " poprawnie wprowadzony do bazy.");
        } catch (SQLIntegrityConstraintViolationException ex){
            System.out.println("Email musi być unikanlny");
        } catch (SQLException e){
            System.out.println("Nie udało się dodać użytkownika " + u.getUsername());
            e.printStackTrace();
        }

        return u;
    }

    public User read (Integer id) {
        User u = new User();
            try (Connection conn = DbUtil.getConnection();
                 PreparedStatement ps = conn.prepareStatement(READ_USER_QUERY)) {
                ps.setInt(1, id );
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        u.setId(rs.getInt("id"));
                        u.setEmail(rs.getString("email"));
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return u;
    }

    public List<User> readAll (){
        List<User> resultList = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(READ_ALL_QUERY);
        ResultSet rs = ps.executeQuery()){
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                resultList.add(u);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return  resultList;
    }

    public void update(User u){
        try (Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_USER_QUERY)){
            ps.setString(1,u.getUsername());
            ps.setString(2,u.getEmail());
            ps.setString(3,u.getPassword());
            ps.setInt(4, u.getId());
            ps.executeUpdate();
            System.out.println("Zmieniono dane używonika");
        } catch (SQLException e){
            System.out.println("Nie udało się zaktualizować danych użytkownika");
            e.printStackTrace();
        }
    }

    public void delete (Integer id){
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER_QUERY)){
            ps.setInt(1, id);
            int howMany = ps.executeUpdate();

            if (howMany == 0) {
                System.out.println("Nie ma takiego użytkownika");
            } else if (howMany == 1) {
                System.out.println("Poprawnie usunięto użytkownika");
            } else {
                throw new SQLException("Operacja zakończyła się modyfikacją więcej niż jednego wiersza tabeli");
            }
        } catch (SQLException e){
            System.out.println("W trakcie usuwania użytkownika wystąpił błąd");
            e.printStackTrace();
        }
    }
}
