package todo.ui;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import lombok.Data;
import todo.model.Task;
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

    public void start() {
        printCommands();
        String choice = scanner.nextLine(); // Читает всю строку

        while (true) {
            switch (choice) {
                case "add":
                    taskService.addTask();
                    break;

                case "delete":

                    taskService.removeTask();
                    break;

                case "edit":
                    taskService.editTask();
                    break;

                case "list":
                    taskService.showAllTasks();
                    break;

                case "sort":

                    taskService.sortTasks();
                    break;

                case "filter":
                    taskService.filterTasks();
                    break;

                case "exit":
                    System.out.println("Пока!");
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

    private void printCommands() {
        System.out.println("Доступные команды: ");
        commandsWithDescription.forEach((command, description) ->
                System.out.println(command + " - " + description)
        );
        System.out.println("Введите команду для выполнения: ");
    }
}
