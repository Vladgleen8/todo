package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    public final int id;
    public String title;
    public String description;
    public String status;

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = "Сделать";
    }

}
