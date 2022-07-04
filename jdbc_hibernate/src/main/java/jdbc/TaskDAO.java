package jdbc;

import jdbc.model.Task;

import java.sql.*;
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
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS task(id BIGINT NOT NULL AUTO_INCREMENT, description VARCHAR(255), user_id BIGINT, PRIMARY KEY (id), CONSTRAINT user_id FOREIGN KEY (user_id) REFERENCES user(id))");
            statement.executeUpdate("DELETE from task");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Task task) throws SQLException {
        if(task == null){
            System.out.println("Failed to insert task. Value is null.");
            return;
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task(id, description, user_id) VALUES (?, ?, ?)");
        preparedStatement.setLong(1, task.getId());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setLong(3, task.getUserId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Optional<Task> read(long id) throws SQLException, IllegalArgumentException{
        // wyciągamy dane z bazy na podstawie id taska i przypisujemy do obiektu klasy Task
        // jeśli znajdzie wiersz to zwracamy Optional.of(new Task(...))
        // jeśli nie znajdzie to Optional.empty()
        if(id <= 0){
            throw new IllegalArgumentException("Id cannot be zero or a negative number");
        }
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE id=?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Optional<Task> task;
        if(resultSet.next()){
            long task_id = resultSet.getLong("id");
            String description = resultSet.getString("description");
            long user_id = resultSet.getLong("user_id");
            task = Optional.of(new Task(task_id, description, user_id));
        }else{
            task = Optional.empty();
        }
        return task;
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