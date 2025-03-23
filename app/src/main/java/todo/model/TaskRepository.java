package todo.model;

import lombok.Data;

import java.util.*;

@Data
public class TaskRepository {
    private Map<String, Task> tasks = new HashMap<>();

    public void addTaskToRepository(String title, String description) {
        tasks.put(title, new Task(title, description));
    }

    public boolean removeTaskFromRepository(String title) {
        return tasks.remove(title) != null;
    }

    public void editTaskInRepository(Map<String, String> dataToEdit) {
        String title = dataToEdit.get("title");

        Task task = tasks.get(title);

        String field = dataToEdit.get("field");

        if (Objects.equals(field, "заголовок")) {
            String newTitle = dataToEdit.get("fieldData");
            tasks.remove(title);  // Удаляем старый ключ
            tasks.put(newTitle, task); // Добавляем с новым ключом
        }

        if (Objects.equals(field, "описание")) {
            task.setDescription(dataToEdit.get("fieldData"));
        }

        if (Objects.equals(field, "статус")) {
            task.setStatus(dataToEdit.get("fieldData"));
        }
    }


}
