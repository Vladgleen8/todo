package todo.ui;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.Data;
import todo.model.Task;
import todo.service.TaskService;

@Data
public class ConsoleUI {
    public static void main(String[] args) throws UnsupportedEncodingException {

        TaskService taskService = new TaskService();

        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Scanner scanner = new Scanner(System.in, "UTF-8"); // Читаем с клавиатуры

        String mainMenu = "Доступные команды:\n" +
                "add - добавить задачу\n"+
                "delete - удалить задачу\n"+
                "edit - редактировать задачу\n" +
                "list - вывести список всех задач\n" +
                "filter - офтильтровать задачи по статусу\n" +
                "sort - отсоритровать задачи\n" +
                "exit - выйти\n" +
                "Введите команду для выполнения: ";
        System.out.println(mainMenu);

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
                    System.out.println("Введите название поля для сортировки: ");
                    String sortByField = scanner.nextLine();
                    System.out.println("Сортировать по возрастанию (ASC) или по убыванию (DESC): ");
                    String sortType = scanner.nextLine();
                    taskService.sortTasks(sortByField, sortType);
                    break;

                case "filter":
                    taskService.filterTasks();
                    break;

                case "exit":
                    System.out.println("Пока!");
                    break;
                default:
                    System.out.println(mainMenu);
                    break;
            }

            System.out.println(mainMenu);
            choice = scanner.nextLine();
        }


    }
}
