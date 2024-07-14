package assignment;

import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.Callable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class TaskTest  {

    @Test
    public void testTaskInitialization() {
        UUID uuid = UUID.randomUUID();
        TaskGroup taskGroup = new TaskGroup(UUID.randomUUID());
        TaskType taskType = TaskType.READ;
        Callable<String> taskAction = () -> "Task1 Test execution ";

        Task<String> task = new Task<>(uuid, taskGroup, taskType, taskAction);

        assertNotNull(task);
        assertEquals(uuid, task.getTaskId());
        assertEquals(taskGroup, task.getTaskGroup());
        assertEquals(taskType, task.getTaskType());
        assertEquals(taskAction, task.getTaskAction());
    }
    public void testGetTaskId() {
    }

    public void testGetTaskGroup() {
    }

    public void testGetTaskAction() {
    }
}