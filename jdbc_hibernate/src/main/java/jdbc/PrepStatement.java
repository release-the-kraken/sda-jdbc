package jdbc;

import java.sql.*;

import static jdbc.Constants.*;

public class PrepStatement {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT age FROM animal WHERE name =?");
            preparedStatement.setString(1, "Mruczek");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int age = resultSet.getInt("age");
                System.out.println(age);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
