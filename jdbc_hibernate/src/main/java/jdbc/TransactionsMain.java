package jdbc;

import java.sql.*;

import static jdbc.Configuration.*;

public class TransactionsMain {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
        ){
            connection.setAutoCommit(false);
            statement.executeUpdate("UPDATE user SET username='Ambroży' WHERE id=2");
            Savepoint savepoint = connection.setSavepoint();
            statement.executeUpdate("UPDATE user SET username='Jerzyk' WHERE id=4");
            try {
                statement.executeUpdate("UPDATE user SET user='Jurek' WHERE id=6");
            }catch (SQLException e){
                System.out.println(e.getMessage());
                connection.rollback(savepoint);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
