package todo.ui;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ConsoleUI {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        Scanner scanner = new Scanner(System.in, "UTF-8"); // Читаем с клавиатуры

        System.out.print(
                "Доступные команды:\n" +
                "add - добавить задачу\n"+
                "delete - удалить задачу\n"+
                "edit - редактировать задачу\n" +
                "list - вывести список всех задач\n" +
                "filter - офтильтровать задачи по статусу\n" +
                "sort - отсоритровать задачи\n" +
                "exit - выйти\n" +
                "Введите команду для выполнения: " );

        String name = scanner.nextLine(); // Читает всю строку



        System.out.println("Твой выбор " + name);
    }
}
