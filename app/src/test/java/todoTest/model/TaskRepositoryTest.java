package todoTest.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import todo.exceptions.InvalidInputException;
import todo.enums.Status;
import todo.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.*;

public class TaskRepositoryTest {

    private TaskRepository repository;

    @BeforeEach
    void setUp() {
        repository = new TaskRepository();
    }

    @Nested
    class AddTaskToRepositoryTests {

        @Test
        void testAddSingleTask() {
            repository.addTaskToRepository("Title1", "Desc1");

            assertEquals(1, repository.getTasks().size());
        }

        @Test
        void testAddMultipleTasks() {
            repository.addTaskToRepository("Title1", "Desc1");
            repository.addTaskToRepository("Title2", "Desc2");

            assertEquals(2, repository.getTasks().size());
        }

        @Test
        void testTaskIdsAreIncremented() {
            repository.addTaskToRepository("Title1", "Desc1");
            repository.addTaskToRepository("Title2", "Desc2");

            assertTrue(repository.getTasks().containsKey("0"));
            assertTrue(repository.getTasks().containsKey("1"));
        }

    }

    @Nested
    class removeTaskFromRepositoryTests {

        @Test
        void testRemoveExistingTask() {
            repository.addTaskToRepository("Title1", "Desc1");

            InvalidInputException exception = assertThrows(InvalidInputException.class,
                    () -> repository.removeTaskFromRepository("0"));
            assertEquals("Task not found", exception.getMessage());

            assertFalse(repository.getTasks().containsKey("0"));
        }

        @Test
        void testRemoveNonExistingTask() {
            InvalidInputException exception = assertThrows(InvalidInputException.class,
                    () -> repository.removeTaskFromRepository("999"));
            assertEquals("Task not found", exception.getMessage());
        }
    }

    @Nested
    class editTaskInRepositoryTests {

        @Test
        void testEditExistingTaskTitle() throws InvalidInputException {
            repository.addTaskToRepository("Title1", "Desc1");
            boolean edited = repository.editTaskInRepository("0", "title", "New Title");
            assertTrue(edited);
            assertEquals("New Title", repository.getTasks().get("0").getTitle());
        }

        @Test
        void testEditExistingTaskDescription() throws InvalidInputException {
            repository.addTaskToRepository("Title1", "Desc1");
            boolean edited = repository.editTaskInRepository("0", "description", "New Description");
            assertTrue(edited);
            assertEquals("New Description", repository.getTasks().get("0").getDescription());
        }

        @Test
        void testEditExistingTaskValidStatus() throws InvalidInputException {
            repository.addTaskToRepository("Title1", "Desc1");
            boolean edited = repository.editTaskInRepository("0", "status", "in progress");
            assertTrue(edited);
            assertEquals(Status.IN_PROGRESS, repository.getTasks().get("0").getStatus());
        }

        @Test
        void testEditExistingTaskInvalidStatus() throws InvalidInputException {
            repository.addTaskToRepository("Title1", "Desc1");
            boolean edited = repository.editTaskInRepository("0", "status", "INVALID_STATUS");
            assertFalse(edited);
        }

        @Test
        void testEditTaskInvalidField() {
            repository.addTaskToRepository("Title", "Desc");

            assertThrows(InvalidInputException.class, () -> {
                repository.editTaskInRepository("0", "nonexistent", "value");
            });
        }

        @Test
        void testIsKeyExistTrue() {
            repository.addTaskToRepository("Title", "Desc");

            assertTrue(repository.isKeyExist("0"));
        }

        @Test
        void testIsKeyExistFalse() {
            assertFalse(repository.isKeyExist("0"));
        }

    }
}
