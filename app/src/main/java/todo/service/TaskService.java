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

        if (Utils.isKeyExist(taskRepository, id) || !Utils.hasField(fieldToEdit)) {
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

    public void sortTasks() {
        System.out.println("Сортировать по статусу: сделать, в работе, сделано");
        String sortType = scanner.nextLine().trim().toLowerCase();

        List<Task> taskList = new ArrayList<>(taskRepository.getTasks().values());
        List<String> defaultStatusOrder = Arrays.asList("сделать", "в работе", "сделано");

        List<String> sortedStatusOrder = new ArrayList<>(defaultStatusOrder);
        if (defaultStatusOrder.contains(sortType)) {
            sortedStatusOrder.remove(sortType);
            sortedStatusOrder.add(0, sortType); // Перемещаем выбранный статус в начало
        } else {
            System.out.println("Некорректный ввод! Сортировка будет по умолчанию (сделать → в работе → сделано).");
        }

        Comparator<Task> comparator = Comparator.comparing(task -> sortedStatusOrder.indexOf(task.getStatus()));

        taskList.sort(comparator);

        taskList.forEach(task -> System.out.println(
                "Задача: " + task.getTitle() +
                        " / Описание: " + task.getDescription() +
                        " / Статус: " + task.getStatus()
        ));
    }



}
