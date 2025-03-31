package todo.model;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
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


    public static String createID(long idCounter)
    {
        return String.valueOf(idCounter++);
    }

    public static String getDateString(Task task, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        formatter.format(task.getCreatedOn());
    }
}