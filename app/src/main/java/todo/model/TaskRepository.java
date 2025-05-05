package todo.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.*;

@Data
public class TaskRepository {
    private Map<String, Task> tasks = new HashMap<>();
    private long idCounter;

    public void addTaskToRepository(String title, String description) {
        String newId = String.valueOf(idCounter);
        idCounter++;
        tasks.put(newId, new Task(title, description, newId, LocalDate.now()));
    }

    public boolean removeTaskFromRepository(String id) {
        return tasks.remove(id) != null;
    }

    public boolean editTaskInRepository(String id, String fieldToEdit, String value) throws InvalidInputException {
        Task task = tasks.get(id);

        switch (fieldToEdit) {
            case "title":
                task.setTitle(value);
                break;
            case "description":
                task.setDescription(value);
                break;
            case "status":
                try {
                    Status status = Status.fromString(value);
                    task.setStatus(status);
                } catch (InvalidInputException ex) {
                    System.out.println("Некорректное поле для сортировки: " + ex.getMessage());
                    return false;
                }
                break;
            default: throw new InvalidInputException("Некорректное поля для редактирования");
        }
        return true;
    }

    public boolean isKeyExist(String value) {
        if (this.getTasks().isEmpty()) {
            return false;
        }

        return this.getTasks().containsKey(value);

    }


}
