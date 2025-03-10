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
        int id = 1;
        while (true) {
            switch (choice) {
                case "add":
                    System.out.println("Введите название задачи: ");
                    String title = scanner.nextLine();
                    System.out.println("Введите описание задачи: ");
                    String description = scanner.nextLine();
                    taskService.addTask(new Task(id, title, description));
                    id++;

                    System.out.println("Задача добавлена\n");
                    break;
                case "delete":
                    System.out.println("Введите название задачи: ");
                    String titleToDelete = scanner.nextLine();
                    taskService.removeTask(titleToDelete);
                case "edit":
                    System.out.println("Введите название задачи: ");
                    String titleToEdit = scanner.nextLine();
                    taskService.editTask(titleToEdit);
                case "list":
                    taskService.showAllTasks();
                case "filter":
                    System.out.println("Введите название посля для сортировки: ");
                default:
                    System.out.println(mainMenu);
                    break;
            }

            System.out.println(mainMenu);
        }


    }
}
