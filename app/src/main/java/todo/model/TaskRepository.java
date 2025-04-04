package todo.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class TaskRepository {
    private Map<String, Task> tasks = new HashMap<>();
    private long idCounter;

    public void addTaskToRepository(String title, String description) {
        String newId = Utils.createID(idCounter);
        idCounter++;
        tasks.put(newId, new Task(title, description, newId, LocalDate.now()));
    }

    public boolean removeTaskFromRepository(String id) {
        return tasks.remove(id) != null;
    }

    public boolean editTaskInRepository(String id, String fieldToEdit, String value) {
        Task task = tasks.get(id);

        switch (fieldToEdit) {
            case "title":
                task.setTitle(value);
                break;
            case "description":
                task.setDescription(value);
                break;
            case "status":
                if (!Utils.isValidStatus(value)) {
                    System.out.println("Некорректное поле для сортировки");
                    return false;
                }
                task.setStatus(value);
                break;
        }
        return true;
    }


}
