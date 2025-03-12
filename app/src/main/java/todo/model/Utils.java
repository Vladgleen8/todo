package todo.model;

import java.util.Objects;

public class Utils {
    public static boolean isKeyExist(TaskRepository repository, String valueType) {
        if (repository.getTasks().isEmpty()) {
            return false;
        }

        if (Objects.equals(valueType, "key")) {
            return repository.getTasks().containsKey(value);
        }

        return false;
    }

    public enum AvailableFields {
        TITLE("заголовок"),
        DESCRIPTION("описание"),
        STATUS("статус");

        private final String value;

        AvailableFields(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static boolean isFieldExist(String text) {
            for (AvailableFields field : AvailableFields.values()) {
                if (field.getValue().equalsIgnoreCase(text)) {
                    return true;
                }
            }
            return false;
        }
    }

}