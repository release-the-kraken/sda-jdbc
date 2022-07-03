package jdbc;

import java.sql.*;

import static jdbc.Constants.*;

public class StatementMethods {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            int recordsAffected = statement.executeUpdate("UPDATE animal SET name = 'Rambo' WHERE id = 2");
            System.out.println("Records affected: " + recordsAffected);
            ResultSet resultSet = statement.executeQuery("SELECT name FROM animal");
            while (resultSet.next()){
                String name = resultSet.getString("name");
                System.out.println(name);
            }
            boolean hasResult = statement.execute("TRUNCATE TABLE animal");
            System.out.println(hasResult);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
