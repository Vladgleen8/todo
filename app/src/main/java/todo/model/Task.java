package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private final int id;
    private String title;
    private String description;
    private String status;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "Сделать";
    }

}
