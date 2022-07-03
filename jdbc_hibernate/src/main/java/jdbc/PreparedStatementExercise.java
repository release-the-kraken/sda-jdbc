package jdbc;

import java.sql.*;
import java.util.List;

import static jdbc.Constants.*;

public class PreparedStatementExercise {

    public static void main(String[] args) {
        List<String> userNames = List.of("user1", "user2", "user3", "user4");
        List<String> userPasswords = List.of("password1", "password2", "password3", "password4");
        final int NUMBER_OF_USERS = userNames.size();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(username, password)" +
                        "VALUES" +
                        "(?, ?)");

            for (int i = 0; i < NUMBER_OF_USERS; i++){
                preparedStatement.setString(1, userNames.get(i));
                preparedStatement.setString(2, userPasswords.get(i));
                preparedStatement.executeUpdate();
            }
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String password = resultSet.getString("password");
                System.out.println("id: %s, user: %s, password: %s".formatted(id, name, password));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
