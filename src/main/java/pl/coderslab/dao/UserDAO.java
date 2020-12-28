package pl.coderslab.dao;

import pl.coderslab.DbUtil;
import pl.coderslab.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class UserDAO {

    //insert
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

    //update
    private static final String UPDATE_EMAIL_QUERY =
            "UPDATE users SET email = ? WHERE id = ?";
    private static final String UPDATE_USERNAME_QUERY =
            "UPDATE users SET username = ? WHERE id = ?";
    private static final String UPDATE_PASSWORD_QUERY =
            "UPDATE users SET password = ? WHERE id = ?";

    //delete
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    //select
    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM users";

    public static long create (User u){

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            System.out.println("Użytkownik poprawnie"+ u.getUsername() + " wprowadzony do bazy.");
        } catch (SQLIntegrityConstraintViolationException ex){
            System.out.println("Email musi być unikanlny");
        } catch (SQLException e){
            System.out.println("Nie udało się dodać użytkownika " + u.getUsername());
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean isEmailUnique (String userEmail){
        try (Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(SELECT_ALL_QUERY);
        ResultSet rs = ps.executeQuery()){

            while (rs.next()){
                if (rs.getString("email").equals(userEmail)){
                    return false;
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public static void updateEmail (User u){
        try (Connection conn = DbUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(UPDATE_EMAIL_QUERY)){
            ps.setString(1,u.getEmail());
            ps.setString(2, String.valueOf(u.getId()));
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updateUsername (User u){
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_USERNAME_QUERY)){
            ps.setString(1,u.getUsername());
            ps.setString(2, String.valueOf(u.getId()));
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void updatePassword (User u){
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_PASSWORD_QUERY)){
            ps.setString(1,u.getPassword());
            ps.setString(2, String.valueOf(u.getId()));
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void delete (User u){
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_USER_QUERY)){
            ps.setString(1,String.valueOf(u.getId()));
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
