package todo.model;
import lombok.Data;
import todo.enums.Status;

import java.time.LocalDate;

@Data
public class Task {
    public final long id;
    public String description;
    public Status status;
    public String title;
    public LocalDate createdOn;

    public Task(String title, String description, long id, LocalDate createdOn) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
        this.id = id;
        this.createdOn = createdOn;
    }

}
