package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    public String description;
    public String status;

    public Task(String description) {
        this.description = description;
        this.status = "Сделать";
    }

}
