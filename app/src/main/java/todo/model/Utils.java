package todo.model;

import java.util.Objects;

public class Utils {
    public static boolean isKeyExist(TaskRepository repository, String value) {
        if (repository.getTasks().isEmpty()) {
            return false;
        }

        return repository.getTasks().containsKey(value);

    }
}