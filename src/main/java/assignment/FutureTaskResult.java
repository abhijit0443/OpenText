package assignment;

import java.util.UUID;
import java.util.concurrent.*;

class FutureTaskResult implements Future<TaskResult> {

    private final UUID taskId;
    private TaskResult result;
    private Exception exception;
    private boolean isDone;
    private final CountDownLatch latch = new CountDownLatch(1);

    public FutureTaskResult(UUID taskId) {
        this.taskId = taskId;
    }

    public void set(TaskResult result) {
        this.result = result;
        this.isDone = true;
        latch.countDown();
    }

    public void setException(Exception exception) {
        this.exception = exception;
        this.isDone = true;
        latch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return mayInterruptIfRunning;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {

        return isDone;
    }

    @Override
    public TaskResult get() throws InterruptedException, ExecutionException {

        if (exception != null) {
            throw new ExecutionException(exception);
        }
        return result;
    }

    @Override
    public TaskResult get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (latch.await(timeout, unit)) {
            if (exception != null) {
                throw new ExecutionException(exception);
            }
            return result;
        } else {
            throw new TimeoutException("Timeout while waiting for task result");
        }
    }
}
