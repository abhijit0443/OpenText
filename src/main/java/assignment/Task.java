package assignment;

import java.util.UUID;
import java.util.concurrent.Callable;

public class Task<T> {
    private UUID taskUUID;
    private TaskGroup taskGroup;
    private TaskType taskType;
    private Callable<T> taskAction;

    public Task(UUID taskUUID, TaskGroup taskGroup, TaskType taskType, Callable<T> taskAction) {
        if (taskUUID == null || taskGroup == null || taskType == null || taskAction == null) {
            throw new IllegalArgumentException("All parameters must not be null");
        }
        this.taskUUID = taskUUID;
        this.taskGroup = taskGroup;
        this.taskType = taskType;
        this.taskAction = taskAction;
    }

    public UUID getTaskId() {
        return taskUUID;
    }
    public TaskGroup getTaskGroup() {
        return taskGroup;
    }
    public TaskType getTaskType() {
        return taskType;
    }


    public Callable<T> getTaskAction() {
        return taskAction;
    }

}

