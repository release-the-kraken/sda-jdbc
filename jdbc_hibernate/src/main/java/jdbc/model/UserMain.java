package jdbc.model;

import java.sql.*;

import static jdbc.Constants.*;
import static jdbc.model.Users.users;

public class UserMain {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()){
                long id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String password = resultSet.getString("password");
                users.add(new User(id, name, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for(User user : users){
            System.out.println(user);
        }
    }
}
