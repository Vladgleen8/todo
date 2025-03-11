package todo.service;
import lombok.Data;
import todo.model.Task;

import java.util.*;
import java.util.stream.Stream;

@Data
public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    int id = 1;
    Scanner scanner = new Scanner(System.in, "UTF-8");

    public static final Map<String, String> STATUS_MAP = Map.of(
            "сделать", "todo",
            "в работе", "progress",
            "сделано", "done"
    );

    public static final List<String> FIELDS_LIST = List.of("заголовок", "описание", "статус");


    public void addTask() {
        System.out.println("Введите название задачи: ");
        String title = scanner.nextLine();
        boolean titleExist = tasks.stream().anyMatch(task -> task.getTitle().equals(title));

        if (titleExist) {
            System.out.println("Задача с таким названием существует, введите другое название ");
            addTask();
            return;
        }

        System.out.println("Введите описание задачи: ");
        String description = scanner.nextLine();
        tasks.add(new Task(id, title, description));
        id++;
        System.out.println("Задача добавлена\n");
    }

    public void removeTask() {
        System.out.println("Введите название задачи: ");
        String titleToDelete = scanner.nextLine();
        boolean isDeleted = tasks.removeIf(task -> task.getTitle().equals(titleToDelete));
        if (!isDeleted) {
            System.out.println("Задачи с таким названием не существует, введите другое название");
            removeTask();
        }
    }

    public void showAllTasks() {
        tasks.forEach(task -> {
            System.out.println(task.getTitle() + ": " + task.getDescription() + " - статус: " + task.getStatus() + "\n");
        });
    }

    public void editTask() {
        System.out.println("Введите название задачи: ");

        // проверяем наличие заголовка
        String titleData;
        while (true) {
            String titleToEdit = scanner.nextLine();
            if (tasks.stream().anyMatch(task -> task.getTitle().equals(titleToEdit))) {
                titleData = titleToEdit;
                break;
            }
            System.out.println("Задачи с таким названием не существует, введите другое название ");
        }

        // проверяем наличие поля
        System.out.println("Какое поле нужно изменить? заголовок, описание, статус");
        String fieldToEdit;
        while (true) {
            String inputField = scanner.nextLine();
            if (FIELDS_LIST.contains(inputField)) {
                fieldToEdit = inputField;
                break;
            }
            System.out.println("Такого поля не существует. Какое поле нужно изменить? заголовок, описание, статус");
        }

        String fieldData;

        if (fieldToEdit.equals("статус")) {
            System.out.println("Введите один из статусов: сделать, в работе, сделано");
            fieldData = scanner.nextLine();

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

        final String finalFieldData = fieldData;



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
