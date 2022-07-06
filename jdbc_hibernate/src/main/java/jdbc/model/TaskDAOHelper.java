package jdbc.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TaskDAOHelper {
    public static void populateTaskList(ResultSet resultSet, List<Task> taskList) throws SQLException {
        long id = resultSet.getInt("task.id");
        String description = resultSet.getString("description");
        Long user_id = resultSet.getLong("user_id");
        taskList.add(new Task(id, description, user_id));
    }

}
