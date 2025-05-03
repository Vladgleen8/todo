package todo.service;
import lombok.Data;
import todo.model.Status;
import todo.model.Task;
import todo.model.TaskRepository;
import todo.model.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class TaskService {
    TaskRepository taskRepository = new TaskRepository();

    public void addTask(String title, String description) {
        taskRepository.addTaskToRepository(title, description);
    }

    public boolean removeTask(String id) {
        return taskRepository.removeTaskFromRepository(id);
    }


    public boolean editTask(String id, String fieldToEdit, String value) {

        if (!Utils.isKeyExist(taskRepository, id) || !Utils.hasField(fieldToEdit)) {
            return false;
        }

        return taskRepository.editTaskInRepository(id, fieldToEdit, value);
    }

    public List<Task> getTasks(String fieldName, String fieldValue) {
        List<Task> filteredTasks = new ArrayList<>();

        if (!Utils.isKeyExist(taskRepository, fieldName)) {
            return filteredTasks;
        }
        switch (fieldName) {
            case "status":
                taskRepository.getTasks().forEach((id, task) -> {
                    if (task.getStatus().equals(Status.fromString(fieldValue))) {
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

    public ArrayList<Task> sortTasks(String fieldName, String sortingCriteria) {
        ArrayList<Task> taskList = new ArrayList<>(taskRepository.getTasks().values());
        Comparator<Task> comparator = null;

        switch (fieldName) {
            case "id":
                comparator = Comparator.comparing(Task::getId);
                break;
            case "title":
                comparator = Comparator.comparing(task -> task.getTitle().toLowerCase());
                break;
            case "description":
                comparator = Comparator.comparing(task -> task.getDescription().toLowerCase());
                break;
            case "status":
                if (!Utils.isValidStatus(sortingCriteria)) {
                    System.out.println("Некорректное поле для сортировки");
                    return taskList; // Возвращаем список без изменений
                }
                comparator = Comparator.comparing(task -> getStatusOrder(task.getStatus()));
                break;
            case "createdOn":
                comparator = Comparator.comparing(Task::getCreatedOn);
                break;
            default:
                System.out.println("Некорректное поле для сортировки");
                return taskList; // Возвращаем список без изменений
        }

        if ("desc".equalsIgnoreCase(sortingCriteria)) {
            comparator = comparator.reversed();
        }

        taskList.sort(comparator);


        return taskList;
    }


    private int getStatusOrder(String status) {
        switch (status.toLowerCase()) {
            case "to do": return 1;
            case "in progress": return 2;
            case "done": return 3;
            default: return -1;
        }
    }

}
