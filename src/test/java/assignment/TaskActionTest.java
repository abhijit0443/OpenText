package assignment;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.logging.LogManager;

import static org.junit.Assert.assertEquals;



public class TaskActionTest  {
    @Before
    public void setupLogger() {
        LogManager.getLogManager().reset();
    }

    @Test
    public void testTaskAction() throws InterruptedException, ExecutionException {
        String taskName = "TestTask";
        TaskAction taskAction = new TaskAction(taskName);
        FutureTask<String> futureTask = new FutureTask<>(taskAction);
        new Thread(futureTask).start();

        // Wait for task completion and retrieve result
        String result = futureTask.get();

        // Verify task execution result
        assertEquals("Task '" + taskName + "' completed", result);


          }
}