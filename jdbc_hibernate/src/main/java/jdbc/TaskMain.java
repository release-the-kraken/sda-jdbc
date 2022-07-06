package jdbc;

import jdbc.model.Task;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class TaskMain {
    private final static String SEPARATOR = "\t|\t";
    private final static String TABLE_HEADER = "|\t" + "id" + SEPARATOR + "\tdescription\t" + SEPARATOR + "user id" + "\t|";
    public static void main(String[] args) {
        try {
            runApp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void runApp() throws SQLException {
        TaskDAO taskDAO = new TaskDAO();
        Task task1 = new Task(1, "Feed the hamster", 1L);
        Task task2 = new Task(2, "Take out the trash", 1L);
        Task task3 = new Task(3, "Walk the dog\t", 2L);
        Task task4 = new Task(4, "Make lunch\t\t", 3L);
        Task task5 = new Task(5, "Read book\t\t", 4L);
        Task task6 = new Task(6, "Watch TV\t\t", 4L);
        taskDAO.create(task1);
        taskDAO.create(task2);
        taskDAO.create(task3);
        taskDAO.create(task4);
        taskDAO.create(task5);
        taskDAO.create(task6);
        Optional<Task> readExistingTask = taskDAO.read(2);
        Optional<Task> readNonexistingTask = taskDAO.read(22);
        Task invalidTask = new Task(0, "N/A", 0L);
        System.out.println("Optional with value");
        System.out.println(readExistingTask.orElse(invalidTask));
        System.out.println("Empty optional");
        System.out.println(readNonexistingTask.orElse(invalidTask));
        List<Task> tasks = taskDAO.readAll();
        System.out.println("List of added tasks");
        System.out.println(TABLE_HEADER);
        tasks.stream()
                .forEach(task -> System.out.println("|\t" + task.getId() + SEPARATOR + task.getDescription() + SEPARATOR + task.getUserId() +"\t\t|"));
        String userName = "user1";
        List<Task> usersTasks1 = taskDAO.readAllForUser(userName);
        System.out.println(userName + "s tasks:");
        System.out.println(TABLE_HEADER);
        usersTasks1.stream()
                .forEach(task -> System.out.println("|\t" + task.getId() + SEPARATOR + task.getDescription() + SEPARATOR + task.getUserId() +"\t\t|"));
        Task updateTemplateTask = new Task(5, "Read paper\t\t", 1L);
        taskDAO.update(updateTemplateTask);
        usersTasks1 = taskDAO.readAllForUser(userName);
        System.out.println(userName + "s updated tasks:");
        System.out.println(TABLE_HEADER);
        usersTasks1.stream()
                .forEach(task -> System.out.println("|\t" + task.getId() + SEPARATOR + task.getDescription() + SEPARATOR + task.getUserId() +"\t\t|"));
        userName = "Jerzy";
        List<Task> usersTasks2 = taskDAO.readAllForUser(userName);
        System.out.println(userName + "s tasks:");
        System.out.println(TABLE_HEADER);
        usersTasks2.stream()
                .forEach(task -> System.out.println("|\t" + task.getId() + SEPARATOR + task.getDescription() + SEPARATOR + task.getUserId() +"\t\t|"));
        Task updateDescriptionTemplateTask = new Task(5, "Read shampoo label", null);
        taskDAO.update(updateDescriptionTemplateTask);
        Task updateUserIDTemplateTask = new Task(1, null, 3L);
        taskDAO.update(updateUserIDTemplateTask);
        tasks = taskDAO.readAll();
        taskDAO.delete(3);
        System.out.println("Updated tasks:");
        System.out.println(TABLE_HEADER);
        tasks.stream()
                .forEach(task -> System.out.println("|\t" + task.getId() + SEPARATOR + task.getDescription() + SEPARATOR + task.getUserId() +"\t\t|"));

    }
}


