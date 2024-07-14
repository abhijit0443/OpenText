package assignment;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.*;

public class FutureTaskResultTest {

    @Test
    public void testSetAndGet() throws InterruptedException, ExecutionException {
        // Create a UUID for taskId
        UUID taskId = UUID.randomUUID();
        FutureTaskResult futureTaskResult = new FutureTaskResult(taskId);
        TaskResult taskResult = new TaskResult(taskId, "Task completed");
        futureTaskResult.set(taskResult);
        assertTrue(futureTaskResult.isDone());
        TaskResult retrievedResult = futureTaskResult.get();
        assertNotNull(retrievedResult);
        assertEquals(taskId, retrievedResult.getTaskId());
        assertEquals("Task completed", retrievedResult.getResult());
    }

    @Test
    public void testSetExceptionAndGet() throws InterruptedException {
        UUID taskId = UUID.randomUUID();
        FutureTaskResult futureTaskResult = new FutureTaskResult(taskId);
        Exception exception = new InterruptedException("Task interrupted");


        futureTaskResult.setException(exception);
        assertTrue(futureTaskResult.isDone());
        assertFalse(futureTaskResult.cancel(false));
        assertFalse(futureTaskResult.isCancelled());
        try {
            futureTaskResult.get();

            fail("Expected ExecutionException");
        } catch (ExecutionException e) {
            assertEquals(exception, e.getCause());
        }

    }

    @Test
    public void testGetWithTimeout() throws InterruptedException, TimeoutException {
        UUID taskId = UUID.randomUUID();
        FutureTaskResult futureTaskResult = new FutureTaskResult(taskId);
        try {
            futureTaskResult.get(100, TimeUnit.MILLISECONDS);
            fail("Expected TimeoutException");
        } catch (TimeoutException | ExecutionException e) {
            // Expected exception
        }
    }


}
