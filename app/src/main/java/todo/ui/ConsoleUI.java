package todo.ui;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import lombok.Data;
import todo.model.Task;
import todo.model.Utils;
import todo.service.TaskService;

@Data
public class ConsoleUI {
    private final TaskService taskService;
    private final Scanner scanner;
    private final Map<String, String> commandsWithDescription;

    public ConsoleUI() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        this.taskService = new TaskService();
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.commandsWithDescription = new HashMap<>();

        commandsWithDescription.put("add", "добавить задачу");
        commandsWithDescription.put("delete", "удалить задачу");
        commandsWithDescription.put("edit", "редактировать задачу");
        commandsWithDescription.put("list", "вывести список всех задач");
        commandsWithDescription.put("filter", "отфильтровать задачи по статусу");
        commandsWithDescription.put("sort", "отсортировать задачи");
        commandsWithDescription.put("exit", "выйти");
    }

    public void showTasks(List<Task> tasks) {
        tasks.forEach(task -> {
            System.out.println("id = " + task.getId() + " title = " + task.getTitle() + " description = " + task.getDescription() + " status = " + task.getStatus() + " createdOn = " + Utils.getDateString(task.getCreatedOn()));
        });
    }

    public void start() {
        printCommands();
        String choice = scanner.nextLine(); // Читает всю строку

        while (true) {
            switch (choice) {
                case "add":
                    System.out.println("Введите название задачи: ");
                    String title = scanner.nextLine();
                    System.out.println("Введите описание задачи: ");
                    String description = scanner.nextLine();
                    taskService.addTask(title, description);
                    System.out.println("Задача добавлена\n");

                    break;

                case "delete":
                    showTasks(taskService.getTasks());
                    System.out.println("Введите Id задачи для удаления: ");
                    String idToDelete = scanner.nextLine();
                    if (taskService.removeTask(idToDelete)) {
                        System.out.println("Успешно удалено");
                    } else {
                        System.out.println("Не удалось удалить");
                    }
                    break;

                case "edit":
                    showTasks(taskService.getTasks());
                    System.out.println("Введите Id задачи: ");
                    String idToEdit = scanner.nextLine();

                    System.out.println("Какое поле хотите изменить?: ");
                    String fieldToEdit = scanner.nextLine();

                    System.out.println("Введите новое значение поля: ");
                    String newValue = scanner.nextLine();

                    if (!taskService.editTask(idToEdit, fieldToEdit, newValue)) {
                        System.out.println("Не получилось изменить данные");
                    }
                    break;

                case "list":
                    showTasks(taskService.getTasks());
                    break;

                case "sort":
                    showTasks(taskService.getTasks());
                    System.out.println("Введите поле для сортировки: ");
                    String fieldToSort = scanner.nextLine();

                    String sortingCriteria = null;

                    if (Objects.equals(fieldToSort, "title") || Objects.equals(fieldToSort, "description")) {
                            System.out.println("Как сортировать: ASC (в алфавитном порядке) / DESC (в обратном порядке)?");
                            sortingCriteria = scanner.nextLine();
                    } else if (Objects.equals(fieldToSort, "createdOn")) {
                            System.out.println("Как сортировать: ASC (от старых к новым) / DESC (от новых к старым)?");
                            sortingCriteria = scanner.nextLine();
                    }

                    showTasks(taskService.sortTasks(fieldToSort, sortingCriteria));
                    break;

                case "filter":
                    showTasks(taskService.getTasks());
                    System.out.println("Введите поле для фильтрации: status / createdOn ");
                    String fieldName = scanner.nextLine();
                    System.out.println("Введите значние поля: ");
                    String fieldValue = scanner.nextLine();

                    List<Task> filteredTasks = taskService.getTasks(fieldName, fieldValue);
                    if (filteredTasks.isEmpty()) {
                        System.out.println("Такие задачи отсутсвуют");
                    } else {
                        showTasks(filteredTasks);
                    }
                    break;

                case "exit":
                    exit();
                    return;

                default:
                    System.out.println("Неизвестная команда!");
                    printCommands();
                    break;
            }

            printCommands();
            choice = scanner.nextLine();
        }

    }

    public void exit() {
        System.out.println("Программа завершена. До свидания!");
        scanner.close();
        System.exit(0);
    }

    private void printCommands() {
        System.out.println("Доступные команды: ");
        commandsWithDescription.forEach((command, description) ->
                System.out.println(command + " - " + description)
        );
        System.out.println("Введите команду для выполнения: ");
    }
}
