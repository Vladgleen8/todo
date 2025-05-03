package todo.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.Data;
import todo.model.Command;
import todo.model.InvalidInputException;
import todo.model.Task;
import todo.service.TaskService;

@Data
public class ConsoleUI {
    private final TaskService taskService;
    private final Scanner scanner;
    private final Map<Command, String> commandDescriptions = Map.of(
            Command.ADD, "добавить задачу",
            Command.DELETE, "удалить задачу",
            Command.EDIT, "редактировать задачу",
            Command.LIST, "вывести список всех задач",
            Command.FILTER, "отфильтровать задачи по статусу",
            Command.SORT, "отсортировать задачи",
            Command.EXIT, "выйти"
    );


    public ConsoleUI() {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        this.taskService = new TaskService();
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void showTasks(List<Task> tasks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        tasks.forEach(task -> {
            System.out.println("id = " + task.getId() + " title = " + task.getTitle() + " description = " + task.getDescription() + " status = " + task.getStatus() + " createdOn = " + task.getCreatedOn().format(formatter));
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

                    try {
                        taskService.editTask(idToEdit, fieldToEdit, newValue);
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
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

                    try {
                        showTasks(taskService.sortTasks(fieldToSort, sortingCriteria));
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
                        showTasks(taskService.getTasks());
                    }
                    break;

                case "filter":
                    showTasks(taskService.getTasks());
                    System.out.println("Введите поле для фильтрации: status / createdOn ");
                    String fieldName = scanner.nextLine();
                    System.out.println("Введите значение поля: ");
                    String fieldValue = scanner.nextLine();

                    try {
                        List<Task> filteredTasks = taskService.getTasks(fieldName, fieldValue);
                        showTasks(filteredTasks);
                    } catch (InvalidInputException e) {
                        System.out.println(e.getMessage());
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
        commandDescriptions.forEach((command, description) ->
                System.out.println(command.getCommand() + " - " + description)
        );
        System.out.println("Введите команду для выполнения: ");
    }
}
