package jdbc;

import jdbc.model.Task;

import java.sql.SQLException;
import java.util.Optional;


public class TaskMain {
    public static void main(String[] args) {
        try {
            runApp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runApp() throws SQLException {
        TaskDAO taskDAO = new TaskDAO();
        Task task1 = new Task(1,"Feed the hamster", 1);
        Task task2 = new Task(2,"Take out the trash", 1);
        Task task3 = new Task(3,"Walk the dog", 2);
        Task task4 = new Task(4,"Make lunch", 3);
        Task task5 = new Task(5,"Read book", 4);
        Task task6 = new Task(6,"Watch TV", 4);
        taskDAO.create(task1);
        taskDAO.create(task2);
        taskDAO.create(task3);
        taskDAO.create(task4);
        taskDAO.create(task5);
        taskDAO.create(task6);
        taskDAO.create(null);
        Optional<Task> readExistingTask = taskDAO.read(2);
        Optional<Task> readNonexistingTask = taskDAO.read(22);
        Task invalidTask = new Task(0, "N/A", 0);
        System.out.println(readExistingTask.orElse(invalidTask));
        System.out.println(readNonexistingTask.orElse(invalidTask));
        Optional<Task> invalidArgumentTask = taskDAO.read(-2);

    }
}
