package jdbc;

import jdbc.model.Task;
import jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static jdbc.Configuration.*;
import static jdbc.model.TaskDAOHelper.populateTaskList;
import static jdbc.model.Users.users;

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
        Task task = null;
        if(resultSet.next()) {
            long task_id = resultSet.getLong("id");
            String description = resultSet.getString("description");
            long user_id = resultSet.getLong("user_id");
            task = new Task(task_id, description, user_id);
        }
        preparedStatement.close();
        resultSet.close();
        return Optional.ofNullable(task);
    }

    public List<Task> readAll() throws SQLException {
        // wyciągamy wszystkie wiersze z bazy danych
        // wyniki zapisujemy w liście obiektów klasy Task
        List<Task> tasks = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
        while (resultSet.next()){
            populateTaskList(resultSet, tasks);
        }
        statement.close();
        resultSet.close();
        return tasks;
    }

    public void update(Task task) throws SQLException {
        if(task == null){
            System.out.println("Failed to insert task. Value is null.");
            return;
        }
        PreparedStatement preparedStatement;
        if(task.getDescription() == null || task.getDescription().equals("")){
            preparedStatement = connection.prepareStatement("UPDATE task SET user_id=? WHERE id=?");
            preparedStatement.setLong(1, task.getUserId());
            preparedStatement.setLong(2, task.getId());
        }else if(task.getUserId() == null){
            preparedStatement = connection.prepareStatement("UPDATE task SET description=? WHERE id=?");
            preparedStatement.setString(1, task.getDescription());
            preparedStatement.setLong(2, task.getId());
        }else {
            preparedStatement = connection.prepareStatement("UPDATE task SET description=?, user_id=? WHERE id=?");
            preparedStatement.setString(1, task.getDescription());
            preparedStatement.setLong(2, task.getUserId());
            preparedStatement.setLong(3, task.getId());
        }
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(long id) throws SQLException {
        if(id <= 0){
            throw new IllegalArgumentException("Id cannot be zero or a negative number");
        }
        // usuwamy wiersz z bazy na podstawie id taska
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM task WHERE id=?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public List<Task> readAllForUser(String username) throws SQLException {
        // dla ochotników
        // konstruujemy query z użyciem JOIN i odwołaniem do tabeli user
        if(username == null || username.equals("")){
            throw new IllegalArgumentException("User name cannot be null or empty string");
        }
        List<Task> usersTasks = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user " +
                "JOIN task ON user.id = task.user_id " +
                "WHERE user.username=?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            populateTaskList(resultSet, usersTasks);
        }
        preparedStatement.close();
        resultSet.close();
        return usersTasks;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}