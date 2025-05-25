package todo.enums;

import todo.exceptions.InvalidInputException;

public enum Command {
    ADD("add"),
    DELETE("delete"),
    EDIT("edit"),
    LIST("list"),
    FILTER("filter"),
    SORT("sort"),
    EXIT("exit");

    private final String command;

    Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public static Command fromString(String input) throws InvalidInputException {
        for (Command cmd : Command.values()) {
            if (cmd.getCommand().equalsIgnoreCase(input)) {
                return cmd;
            }
        }
        throw new InvalidInputException("Неизвестная команда: " + input);
    }
}
