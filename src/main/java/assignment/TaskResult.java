package assignment;

import java.util.UUID;

public class TaskResult {
    private final UUID taskId;
    private final Object result;

    public TaskResult(UUID taskId, Object result) {
        this.taskId = taskId;
        this.result = result;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public Object getResult() {
        return result;
    }
}
