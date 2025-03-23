package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    public String description;
    public String status;
    public String title;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = "сделать";
    }

}
