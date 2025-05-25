package todo.enums;

import todo.exceptions.InvalidInputException;

public enum Status {
    TO_DO("to do"), IN_PROGRESS("in progress"), DONE("done");
    private String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status fromString(String text) throws InvalidInputException {
        for (Status s : Status.values()) {
            if (s.getStatus().equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new InvalidInputException("Unknown status: " + text);
    }
}
