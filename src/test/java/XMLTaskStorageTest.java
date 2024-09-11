import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.task.manager.models.Status;
import org.task.manager.models.Task;
import org.task.manager.models.ToDoList;
import org.task.manager.storage.TaskStorageFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class XMLTaskStorageTest {
    private static final String TEST_SOURSE_PATH = "src/test/resources/";

    @Test
    void testGeneration() {
        var fabric = Assertions.assertDoesNotThrow(TaskStorageFactory::new);
        assertDoesNotThrow(() -> fabric.createTaskStorage("xml"));

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                fabric.createTaskStorage("somethingelse"));
    }

    @Test
    void testSuccess() {
        var storage = (new TaskStorageFactory()).createTaskStorage("xml");

        ToDoList toDoList = Assertions.assertDoesNotThrow(
                () -> storage.getTasks(new File(TEST_SOURSE_PATH + "/sample.xml"))
        );
        assertEquals(2, toDoList.size());
        Task task = Assertions.assertDoesNotThrow(() -> toDoList.get(1));
        assertEquals(1, task.getId());
        assertEquals("Заголовок задачи", task.getCaption());
        assertEquals("Описание задачи", task.getDescription());
        assertEquals(8, task.getPriority());
        assertEquals(LocalDate.of(2017, Month.FEBRUARY, 12), task.getDeadline());
        assertEquals(Status.DONE, task.getStatus());
        assertEquals(LocalDate.of(2017, Month.FEBRUARY, 19), task.getComplete());

        ToDoList toDoList1 = Assertions.assertDoesNotThrow(
                () -> storage.getTasks(new File(TEST_SOURSE_PATH + "/empty_todo_list.xml"))
        );
        assertEquals(0, toDoList1.size());
    }

    @Test
    void testFailure() {
        var storage = (new TaskStorageFactory()).createTaskStorage("xml");

        Assertions.assertThrows(IllegalArgumentException.class, () -> storage.getTasks(new File(TEST_SOURSE_PATH + "/invalid_file.xml")));
        Assertions.assertThrows(FileNotFoundException.class, () -> storage.getTasks(new File(TEST_SOURSE_PATH + "/no_such_file.xml")));
        Assertions.assertThrows(IllegalArgumentException.class, () -> storage.getTasks(new File(TEST_SOURSE_PATH + "/valid_file_structure_invalid_fields.xml")));
    }
}