package assignment;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TaskResultTest {

    @Test
    public void testTaskResult() {
        UUID taskId = UUID.randomUUID();
        Object expectedResult = "Task completed";
        TaskResult taskResult = new TaskResult(taskId, expectedResult);
        assertNotNull(taskResult);
        assertEquals(taskId, taskResult.getTaskId());
        assertEquals(expectedResult, taskResult.getResult());
    }

}
