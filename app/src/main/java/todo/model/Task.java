package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private final int id;
    private final String title;
    private final String description;
    private final boolean completed;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = false;
    }

}
