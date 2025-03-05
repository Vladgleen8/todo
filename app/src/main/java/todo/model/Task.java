package todo.model;
import lombok.Data;

import java.util.Date;

@Data
public class Task {
    private final int id;
    private final String title;
    private final String description;
    private final Date dueDate;
    private final boolean completed;
}
