package jdbc;

import java.sql.*;

import static jdbc.Constants.*;

public class SQLInjection {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);){
            System.out.println(login(connection, "user1", "password1"));//true
            System.out.println(login(connection, "user1", "password2"));//false
            System.out.println(login(connection, "user1", "' OR '1'='1"));//fake true
            System.out.println(secureLogin(connection, "user1", "' OR '1'='1"));//false
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static boolean secureLogin(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM user WHERE username=? AND password=?";
        PreparedStatement preparedStatement= connection.prepareStatement(query);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isValidUser = resultSet.next();
        preparedStatement.close();
        resultSet.close();
        return isValidUser;
    }
    private static boolean login(Connection connection, String username, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE username='" + username + "' AND password='" + password + "'";
        ResultSet resultSet = statement.executeQuery(query);
        boolean isValidUser = resultSet.next();
        statement.close();
        resultSet.close();
        return isValidUser;
    }
}
