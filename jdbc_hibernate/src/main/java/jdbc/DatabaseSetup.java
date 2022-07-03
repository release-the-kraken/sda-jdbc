package jdbc;

import java.sql.*;

import static jdbc.Constants.*;

public class DatabaseSetup {
    public static void main(String[] args) {
        try ( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
              Statement statement = connection.createStatement();
        ){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM animal");

            while(resultSet.next()){
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("id: %s, name: %s, age: %s".formatted(id, name, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
