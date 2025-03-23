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

    public enum AvailableFields {
        TITLE("заголовок"),
        DESCRIPTION("описание"),
        STATUS("статус");

        private final String value;

        AvailableFields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean isFieldExist(String text) {
            for (AvailableFields field : AvailableFields.values()) {
                if (field.getValue().equalsIgnoreCase(text)) {
                    return true;
                }
            }
            return false;
        }
    }

    public enum TaskStatus {
        TODO("сделать"),
        IN_PROGRESS("в работе"),
        DONE("сделано");

        private final String displayName;

        TaskStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getStatus() {
            return displayName;
        }

        public static boolean isStatusExist(String text) {
            for (TaskStatus status : TaskStatus.values()) {
                if (status.getStatus().equalsIgnoreCase(text)) {
                    return true;
                }
            }
            return false;
        }
    }

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
            if (!Utils.isKeyExist(taskRepository, titleToEdit)) {
                System.out.println("Задачи с таким названием не существует, введите другое название ");
            } else {
                dataToEdit.put("title", titleToEdit);
                break;
            }
        }

        System.out.println("Какое поле нужно изменить? заголовок, описание, статус");
        while (true) {
            String fieldToEdit = scanner.nextLine();
            if (!AvailableFields.isFieldExist(fieldToEdit)) {
                System.out.println("Такого поля не существует. Какое поле нужно изменить? заголовок, описание, статус");
            } else {
                dataToEdit.put("field", fieldToEdit);
                break;
            }
        }

        String fieldData = "";

        if (dataToEdit.get("field").equals("статус")) {
            System.out.println("Введите один из статусов: сделать, в работе, сделано");

            fieldData = scanner.nextLine();
            while (!TaskStatus.isStatusExist(fieldData)) {
                System.out.println("Такого статуса не существует. Введите один из статусов: сделать, в работе, сделано");
                fieldData = scanner.nextLine();
            }
        } else if (dataToEdit.get("status").equals("заголовок")) {
            System.out.println("Введите новый заголовок");
            fieldData = scanner.nextLine();
            // проверить наличие такого заголовка
        } else {
            System.out.println("Введите новое описание");
            fieldData = scanner.nextLine();
        }

        dataToEdit.put("fieldData", fieldData);

        taskRepository.editTaskInRepository(dataToEdit);
    }

    public void filterTasks() {
        System.out.println("Фильтровать по статусу задачи: сделать, в работе, сделано ");
        String filterType = scanner.nextLine();

        while (!TaskStatus.isStatusExist(filterType)) {
            System.out.println("Не возможно ввести такой статус, введите допустимый статус");
        }

        taskRepository.getTasks().forEach((title, taskDescription) -> {
            if (taskDescription.getStatus().equals(filterType)) {
                System.out.println("Задача: " + title + " / Описание: " + taskDescription.getDescription() + " / Статус: " + taskDescription.getStatus());
            }
        });

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


    public void exit() {
        System.out.println("Программа завершена. До свидания!");
        scanner.close();
        System.exit(0);
    }
}
