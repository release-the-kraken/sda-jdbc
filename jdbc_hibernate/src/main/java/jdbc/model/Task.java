package jdbc.model;

public class Task {

    private Long id;
    private String description;
    private Long userId;

    public Task(long id, String description, Long userId) {
        if(id < 0){
            throw new IllegalArgumentException("Id cannot be a negative number");
        }
        this.id = id;
        this.description = description;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                '}';
    }
}

