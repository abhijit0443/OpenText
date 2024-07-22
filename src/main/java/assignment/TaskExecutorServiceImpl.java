package assignment;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.*;
import java.util.concurrent.*;


public class TaskExecutorServiceImpl implements TaskExecutor {
    Logger logger = Logger.getLogger(TaskExecutorServiceImpl.class.getName());
    ExecutorService executorService;

    private final Map<String, Semaphore> semaphoresMap = new ConcurrentHashMap<>();


    private Map<TaskGroup, Object> locks = new HashMap<>();
    public TaskExecutorServiceImpl(int maxConcurrency) {
        executorService = Executors.newFixedThreadPool(maxConcurrency);

    }

    @Override
    public Future<TaskResult> submitTask(Task task){
        Future<TaskResult>  future = null;
        /*Semaphore used for maximum allowed concurrency to be restricted: <permit:5>*/
        Semaphore semaphore = semaphoresMap.computeIfAbsent(task.getTaskGroup().toString(), k -> new Semaphore(5));

            try {
                semaphore.acquire();
                logger.info("Executing Task ID :[ " + task.getTaskId() + "] in Group: " + task.getTaskGroup());
                  future=  executorService.submit(task.getTaskAction());

        }catch (Exception e) {
                logger.log(Level.SEVERE,"An exception occurred!", new RuntimeException("Runnable exception"));

        }
         finally {

                semaphore.release();
            }

        return future ;
    }


    public void shutdown() {
        executorService.shutdown();
    }


}
