package todo.ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.*;

import lombok.Data;
import todo.enums.Command;
import todo.exceptions.InvalidInputException;
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
        this.taskService = new TaskService();
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
    }

    public void showTasks(List<Task> tasks) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        tasks.forEach(task -> {
            System.out.println("id = " + task.getId() + " title = " + task.getTitle() + " description = " +
                    task.getDescription() + " status = " + task.getStatus() + " createdOn = " +
                    task.getCreatedOn().format(formatter));
        });
    }

    public void start() {
        try {
            while (true) {
                printCommands();
                String input = scanner.nextLine().trim(); // Читает всю строку

                Command command = Command.fromString(input);
                switch (command) {
                    case ADD -> {
                        System.out.println("Введите название задачи: ");
                        String title = scanner.nextLine();
                        System.out.println("Введите описание задачи: ");
                        String description = scanner.nextLine();
                        taskService.addTask(title, description);
                        System.out.println("Задача добавлена\n");
                    }
                    case DELETE -> {
                        showTasks(taskService.getTasks());
                        System.out.println("Введите Id задачи для удаления: ");
                        String idToDelete = scanner.nextLine();
                        taskService.removeTask(idToDelete);
                        System.out.println("Успешно удалено");
                    }
                    case EDIT -> {
                        showTasks(taskService.getTasks());
                        System.out.println("Введите Id задачи: ");
                        String idToEdit = scanner.nextLine();
                        System.out.println("Какое поле хотите изменить?: ");
                        String fieldToEdit = scanner.nextLine();
                        System.out.println("Введите новое значение поля: ");
                        String newValue = scanner.nextLine();
                        taskService.editTask(idToEdit, fieldToEdit, newValue);
                    }
                    case LIST -> showTasks(taskService.getTasks());
                    case SORT -> {
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
                    }
                    case FILTER -> {
                        showTasks(taskService.getTasks());
                        System.out.println("Введите поле для фильтрации: status / createdOn ");
                        String fieldName = scanner.nextLine();
                        System.out.println("Введите значение поля: ");
                        String fieldValue = scanner.nextLine();
                        List<Task> filteredTasks = taskService.getTasks(fieldName, fieldValue);
                        showTasks(filteredTasks);
                    }
                    case EXIT -> {
                        exit();
                        return;
                    }
                }
            }
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("Ввод завершён, выход из программы.");
        } catch (Exception e) {
            System.out.println("Неизвестная ошибка: " + e.getMessage());
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
