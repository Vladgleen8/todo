package todo.repository;

import lombok.Data;
import todo.enums.Status;
import todo.enums.TaskFields;
import todo.exceptions.InvalidInputException;
import todo.model.Task;

import java.time.LocalDate;
import java.util.*;

@Data
public class TaskRepository {
    private Map<String, Task> tasks = new HashMap<>();
    private long idCounter;

    public void addTaskToRepository(String title, String description) {
        tasks.put(String.valueOf(idCounter), new Task(title, description, idCounter, LocalDate.now()));
        idCounter++;
    }

    public void removeTaskFromRepository(String id) throws InvalidInputException {
        Task removedTask = tasks.remove(id);
        if (removedTask == null) {
            throw new InvalidInputException("Task not found");
        }
    }


    public boolean editTaskInRepository(String id, String fieldToEdit, String value) throws InvalidInputException  {
        Task task = tasks.get(id);
        if (task == null) {
            throw new InvalidInputException("Задача с id " + id + " не найдена");
        }

        TaskFields field = TaskFields.fromString(fieldToEdit);

        switch (field) {
            case TITLE -> task.setTitle(value);
            case DESCRIPTION -> task.setDescription(value);
            case STATUS -> task.setStatus(Status.fromString(value)); // тоже выбрасывает InvalidInputException, если статус не найден
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
