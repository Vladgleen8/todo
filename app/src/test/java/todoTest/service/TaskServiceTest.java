package todoTest.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import todo.model.InvalidInputException;
import todo.model.Task;
import todo.service.TaskService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    private TaskService service;

    @BeforeEach
    void setUp() {
        service = new TaskService();
    }

    @Test
    void testAddTask() {
        service.addTask("Title1", "Description1");
        List<Task> tasks = service.getTasks();
        assertEquals(1, tasks.size());
        assertEquals("Title1", tasks.get(0).getTitle());
    }

    @Test
    void testRemoveExistingTask() {
        service.addTask("Title1", "Description1");
        String id = service.getTasks().get(0).getId();

        boolean removed = service.removeTask(id);
        assertTrue(removed);
        assertTrue(service.getTasks().isEmpty());
    }

    @Test
    void testRemoveNonExistingTask() {
        boolean removed = service.removeTask("22");
        assertFalse(removed);
    }

    @Nested
    class EditTaskTests {

        @Test
        void testEditTaskTitle() throws InvalidInputException {
            service.addTask("OldTitle", "Description");
            String id = service.getTasks().get(0).getId();

            boolean result = service.editTask(id, "title", "NewTitle");
            assertTrue(result);
            assertEquals("NewTitle", service.getTasks().get(0).getTitle());
        }

        @Test
        void testEditTaskInvalidId() {
            InvalidInputException exception = assertThrows(InvalidInputException.class,
                    () -> service.editTask("invalid_id", "title", "NewTitle"));
            assertEquals("Задача не существует", exception.getMessage());
        }

        @Test
        void testEditTaskInvalidField() {
            service.addTask("OldTitle", "Description");
            String id = service.getTasks().get(0).getId();

            InvalidInputException exception = assertThrows(InvalidInputException.class,
                    () -> service.editTask(id, "INVALID_TITLE", "NewTitle"));
            assertEquals("Некорректное поля для редактирования", exception.getMessage());
        }
    }

    @Nested
    class GetTasksWithFilterTests {

        @Test
        void testGetTasksByStatus() throws InvalidInputException {
            service.addTask("Title1", "Desc1");
            String id = service.getTasks().get(0).getId();
            service.editTask(id, "status", "IN_PROGRESS");

            InvalidInputException exception = assertThrows(InvalidInputException.class,
                    () -> service.getTasks("nonexistent", "value"));
            assertEquals("Поле не существует", exception.getMessage());

            List<Task> result = service.getTasks("status", "IN_PROGRESS");
            assertEquals(1, result.size());
            assertEquals("Title1", result.get(0).getTitle());
        }

        @Test
        void testGetTasksByCreatedOn() throws InvalidInputException {
            service.addTask("Title1", "Desc1");
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            String id = service.getTasks().get(0).getId();
            List<Task> result = service.getTasks("createdOn", today);

            assertEquals(1, result.size());
            assertEquals(id, result.get(0).getId());
        }
    }

    @Nested
    class SortTasksTests {

        @Test
        void testSortByTitleAsc() throws InvalidInputException {
            service.addTask("B title", "desc");
            service.addTask("A title", "desc");

            List<Task> sorted = service.sortTasks("title", "asc");
            assertEquals("A title", sorted.get(0).getTitle());
        }

        @Test
        void testSortByTitleDesc() throws InvalidInputException {
            service.addTask("A title", "desc");
            service.addTask("B title", "desc");

            List<Task> sorted = service.sortTasks("title", "desc");
            assertEquals("B title", sorted.get(0).getTitle());
        }

        @Test
        void testSortInvalidField() {
            assertThrows(InvalidInputException.class,
                    () -> service.sortTasks("nonexistent", "asc"));
        }
    }

}
