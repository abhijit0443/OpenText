package assignment;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class TaskAction implements Callable<String> {
    Logger logger = Logger.getLogger(TaskAction.class.getName());
    private final String taskName;

    public TaskAction(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {

        //System.out.println("Task '" + taskName + "' is executing...");
        logger.info("Task '" + taskName + "' is executing...");
        Thread.sleep(2000); // Simulate some task execution time
        return "Task '" + taskName + "' completed";
    }
}
