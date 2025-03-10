package todo.service;
import lombok.Data;
import todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    public static final Map<String, String> STATUS_MAP = Map.of(
            "сделать", "todo",
            "в работе", "progress",
            "сделано", "done"
    );
    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(String title) {
        tasks.removeIf(task -> task.getTitle().equals(title));
    }

    public void showAllTasks() {
        tasks.forEach(task -> {
            System.out.println(task.getTitle() + ": " + task.getDescription() + " - статус: " + task.getStatus() + "\n");
        });
    }

    public void editTask(String title, String fieldToEdit, String newData) {
        tasks.forEach(task -> {
            if (task.getTitle().equals(title)) {
                switch (fieldToEdit) {
                    case "Заголовок":
                        task.setTitle(newData);
                        break;
                    case "Описание":
                        task.setDescription(newData);
                        break;
                    case "Статус":
                        task.setStatus(newData);
                }
            }
        });
    }

    public void filterTasks(String filterType) {
        if (!STATUS_MAP.containsKey(filterType)) {
            System.out.println("Такого статуса нет, введите другой статус");
        }
        String status = STATUS_MAP.get(filterType);
        tasks.forEach(task -> {
           if (Objects.equals(task.getStatus(), status)) {
               System.out.println(task.getTitle() + ": " + task.getDescription() + " - статус: " + task.getStatus() + "\n");
           }
        });
    }

    public void sortTasks(String sortByField, String sortType) {

    }

    public void exit() {

    }
}
