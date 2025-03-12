package todo.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TaskRepository {
    private Map<String, Task> tasks = new HashMap<>();

    public boolean addTaskToRepository(String title, String description) {
        tasks.put(title, new Task(description));
    }

    public boolean removeTaskFromRepository(String title) {
        return tasks.remove(title) != null;
    }

    public void editTaskInRepository(Task task) {




        // проверяем наличие поля
        String fieldToEdit;
        while (true) {
            String inputField = scanner.nextLine();
            if (FIELDS_LIST.contains(inputField)) {
                fieldToEdit = inputField;
                break;
            }
        }



        final String finalFieldData = fieldData;
    }


}
