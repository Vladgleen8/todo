package todo.service;
import lombok.Data;
import todo.model.Task;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    public void addTask(Task task) {

    }

    public void removeTask(Task task) {

    }

    public void showAllTasks() {

    }

    public void editTask(Task task) {

    }

    public void filterTasks(Task tasks) {

    }

    public void sortTasks() {

    }

    public void exit() {

    }
}
