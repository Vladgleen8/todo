package todo.model;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Task {
    public String id;
    public String description;
    public Status status;
    public String title;
    public LocalDate createdOn;

    public Task(String title, String description, String id, LocalDate createdOn) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
        this.id = id;
        this.createdOn = createdOn;
    }

}
