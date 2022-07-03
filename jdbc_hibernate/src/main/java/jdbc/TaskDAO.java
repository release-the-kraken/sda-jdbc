package jdbc;

import jdbc.model.Task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static jdbc.Configuration.*;

public class TaskDAO implements AutoCloseable {

    private Connection connection = null;

    public TaskDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS task(id BIGINT NOT NULL, description VARCHAR(255), user_id BIGINT, PRIMARY KEY (id), CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user(id))");
            statement.executeUpdate("DELETE from task");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Task task) throws SQLException {
        // tworzymy nowy task w bazie danych na podstawie informacji z argumentu
    }

    public Optional<Task> read(long id) throws SQLException {
        // wyciągamy dane z bazy na podstawie id taska i przypisujemy do obiektu klasy Task
        // jeśli znajdzie wiersz to zwracamy Optional.of(new Task(...))
        // jeśli nie znajdzie to Optional.empty()
        return Optional.empty();
    }

    public List<Task> readAll() throws SQLException {
        // wyciągamy wszystkie wiersze z bazy danych
        // wyniki zapisujemy w liście obiektów klasy Task
        return Collections.emptyList();
    }

    public void update(Task task) throws SQLException {
        // aktualizujemy description i user_id na podstawie id taska
    }

    public void delete(long id) throws SQLException {
        // usuwamy wiersz z bazy na podstawie id taska
    }

    public List<Task> readAllForUser(String username) throws SQLException {
        // dla ochotników
        // konstruujemy query z użyciem JOIN i odwołaniem do tabeli user
        return Collections.emptyList();
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}