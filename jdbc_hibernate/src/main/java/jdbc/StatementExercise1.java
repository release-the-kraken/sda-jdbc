package jdbc;

import java.sql.*;

import static jdbc.Constants.*;

public class StatementExercise1 {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            statement.execute("CREATE TABLE IF NOT EXISTS user(" +
                    "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) UNIQUE," +
                    "password VARCHAR(50))");

            statement.executeUpdate("INSERT INTO user(username, password)" +
                    "VALUES" +
                    "('user1', 'password1')," +
                    "('user2', 'password2')," +
                    "('user3', 'password3')," +
                    "('user4', 'password4')," +
                    "('user5', 'password5')," +
                    "('user6', 'password6');");
            ResultSet resultSet = statement.executeQuery("SELECT username FROM user");
            while (resultSet.next()){
                String userName = resultSet.getString("username");
                System.out.println(userName);
            }
            boolean hasContent = statement.execute("TRUNCATE TABLE user");
            System.out.println(hasContent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
