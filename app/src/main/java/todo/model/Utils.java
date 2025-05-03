package todo.model;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utils {
    public static boolean isKeyExist(TaskRepository repository, String value) {
        if (repository.getTasks().isEmpty()) {
            return false;
        }

        return repository.getTasks().containsKey(value);

    }

    public static boolean hasField(String fieldName) {
        Field[] fields = Task.class.getDeclaredFields(); // Получаем все поля класса Task
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true; // Нашли нужное поле
            }
        }
        return false; // Такого поля нет
    }

    public static boolean isValidStatus(String status) {
        return status.equalsIgnoreCase("to do") ||
                status.equalsIgnoreCase("in progress") ||
                status.equalsIgnoreCase("done");
    }


    public static String createID(long idCounter)
    {
        return String.valueOf(idCounter);
    }

    public static String getDateString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}