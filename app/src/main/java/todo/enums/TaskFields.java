package todo.enums;

import todo.exceptions.InvalidInputException;

public enum TaskFields {
    TITLE("title"),
    DESCRIPTION("description"),
    STATUS("status");

    private final String field;

    TaskFields(String field) {
        this.field = field;
    }

    public static TaskFields fromString(String value) {
        for (TaskFields f : TaskFields.values()) {
            if (f.field.equalsIgnoreCase(value)) {
                return f;
            }
        }
        throw new InvalidInputException("Некорректное поле для редактирования: " + value);
    }
}
