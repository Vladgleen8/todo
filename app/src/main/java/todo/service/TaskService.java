package todo.service;
import lombok.Data;
import todo.model.Task;
import todo.model.TaskRepository;
import todo.model.Utils;

import java.util.*;
import java.util.stream.Stream;

@Data
public class TaskService {
    TaskRepository taskRepository = new TaskRepository();

    Scanner scanner = new Scanner(System.in, "UTF-8");
    enum availableFields {
        TITLE,
        DESCRIPTION,
        STATUS
    }
    public static final Map<String, String> STATUS_MAP = Map.of(
            "сделать", "todo",
            "в работе", "progress",
            "сделано", "done"
    );

    public void addTask() {
        System.out.println("Введите название задачи: ");
        String title = scanner.nextLine();
        boolean titleExist = taskRepository.getTasks().containsKey(title);

        if (titleExist) {
            System.out.println("Задача с таким названием существует, введите другое название ");
            addTask();
            return;
        }

        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();

        taskRepository.addTaskToRepository(title, description);
        System.out.println("Задача добавлена\n");
    }

    public void removeTask() {
        System.out.println("Введите название задачи: ");
        String titleToDelete = scanner.nextLine();

        if (!taskRepository.removeTaskFromRepository(titleToDelete)) {
            System.out.println("Задачи с таким названием не существует, введите другое название");
            removeTask();
        }
    }

    public void showAllTasks() {
        taskRepository.getTasks().forEach((title, taskDescription) -> {
            System.out.println("Задача: " + title + " / Описание: " + taskDescription.getDescription() + " / Статус: " + taskDescription.getStatus());
        });
    }

    public void editTask() {

        Map<String, String> dataToEdit = new HashMap<>();

        System.out.println("Введите название задачи: ");
        while (true) {
            String titleToEdit = scanner.nextLine();
            if (!Utils.isValueExist(taskRepository, titleToEdit, "key")) {
                System.out.println("Задачи с таким названием не существует, введите другое название ");
            } else {
                dataToEdit.put("title", titleToEdit);
                break;
            }
        }

        System.out.println("Какое поле нужно изменить? заголовок, описание, статус");
        while (true) {
            String fieldToEdit = scanner.nextLine();
            if (!Utils.AvailableFields.isFieldExist(fieldToEdit)) {
                System.out.println("Такого поля не существует. Какое поле нужно изменить? заголовок, описание, статус");
            } else {
                dataToEdit.put("field", fieldToEdit);
                break;
            }
        }

        if (dataToEdit.get("field").equals()) {
            System.out.println("Введите один из статусов: сделать, в работе, сделано");

            while (!STATUS_MAP.containsKey(fieldData)) {
                System.out.println("Такого статуса не существует. Введите один из статусов: сделать, в работе, сделано");
                fieldData = scanner.nextLine();
            }
        } else if (fieldToEdit.equals("заголовок")) {
            System.out.println("Введите новый заголовок");
            fieldData = scanner.nextLine();
            // проверить наличие такого заголовка
        } else {
            System.out.println("Введите новое описание");
            fieldData = scanner.nextLine();
        }



        tasks.forEach(task -> {
            if (task.getTitle().equals(titleData)) {
                switch (fieldToEdit) {
                    case "заголовок":
                        task.setTitle(finalFieldData);
                        break;
                    case "описание":
                        task.setDescription(finalFieldData);
                        break;
                    case "статус":
                        task.setStatus(finalFieldData);
                        break;
                }
            }
        });
    }

    public void filterTasks() {
        System.out.println("Фильтровать по статусу задачи: сделать, в работе, сделано ");
        String filterType = scanner.nextLine();

        while (!STATUS_MAP.containsKey(filterType)) {
            System.out.println("Не возможно ввести такой статус, введите допустимый статус");
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
