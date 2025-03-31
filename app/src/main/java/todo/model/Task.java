package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    public String id;
    public String description;
    public String status;
    public String title;
    public Date createdOn;

    public Task(String title, String description, String id, Date createdOn) {
        this.title = title;
        this.description = description;
        this.status = "to do";
        this.id = id;
        this.createdOn = createdOn;
    }

}
