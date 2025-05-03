package todo.model;

public enum Status {
    TO_DO("to do"), IN_PROGRESS("in progress"), DONE("done");
    private String status;

    private Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static Status fromString(String text) {
        for (Status s : Status.values()) {
            if (s.getStatus().equalsIgnoreCase(text)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Unknown status: " + text);
    }
}
