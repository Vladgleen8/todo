package todo.service;
import lombok.Data;
import todo.exceptions.InvalidInputException;
import todo.enums.Status;
import todo.model.Task;
import todo.repository.TaskRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
public class TaskService {
    TaskRepository taskRepository = new TaskRepository();

    public void addTask(String title, String description) {
        taskRepository.addTaskToRepository(title, description);
    }

    public void removeTask(String id) throws InvalidInputException {
        try {
            taskRepository.removeTaskFromRepository(id);
        } catch (InvalidInputException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }


    public boolean editTask(String id, String fieldToEdit, String value) throws InvalidInputException {

        if (!taskRepository.isKeyExist(id)) {
            throw new InvalidInputException("Задача не существует");
        }

        return taskRepository.editTaskInRepository(id, fieldToEdit, value);
    }

    public List<Task> getTasks(String fieldName, String fieldValue) throws InvalidInputException {
        List<Task> filteredTasks = new ArrayList<>();

        if (!taskRepository.isKeyExist(fieldName)) {
            throw new InvalidInputException("Поле не существует");
        }

        switch (fieldName) {
            case "status":
                Status status = Status.fromString(fieldValue);

                taskRepository.getTasks().forEach((id, task) -> {
                    if (task.getStatus().equals(status)) {
                        filteredTasks.add(task);
                    }
                });
                break;

            case "createdOn":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate parsedDate = LocalDate.parse(fieldValue, formatter);

                taskRepository.getTasks().forEach((id, task) -> {
                    if (task.getCreatedOn().equals(parsedDate)) {
                        filteredTasks.add(task);
                    }
                });
                break;

        }

        return filteredTasks;
    }

    public List<Task> getTasks() {
        return new ArrayList<>(taskRepository.getTasks().values());
    }

    public ArrayList<Task> sortTasks(String fieldName, String sortingCriteria) throws InvalidInputException {
        ArrayList<Task> taskList = new ArrayList<>(taskRepository.getTasks().values());
        Comparator<Task> comparator = null;

        switch (fieldName) {
            case "id" -> comparator = Comparator.comparing(Task::getId);
            case "title" -> comparator = Comparator.comparing(task -> task.getTitle().toLowerCase());
            case "description" -> comparator = Comparator.comparing(task -> task.getDescription().toLowerCase());
            case "status" -> comparator = Comparator.comparing(Task::getStatus); 
            case "createdOn" -> comparator = Comparator.comparing(Task::getCreatedOn);
            default -> throw new InvalidInputException("Некорректное поле для сортировки: " + fieldName);
        }

        if ("desc".equalsIgnoreCase(sortingCriteria)) {
            comparator = comparator.reversed();
        }

        taskList.sort(comparator);


        return taskList;
    }

}
