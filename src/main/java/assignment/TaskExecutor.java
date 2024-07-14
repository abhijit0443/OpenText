package assignment;

import java.util.concurrent.Future;
/**
 * Submit new task to be queued and executed.
 *
 * @param task Task to be executed by the executor. Must not be null.
 * @return Future for the task asynchronous computation result.

 */
public interface TaskExecutor {
    Future<TaskResult> submitTask(Task task);

}
