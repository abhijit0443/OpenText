package assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;


public class TaskExecutorServiceImpl implements TaskExecutor {
    Logger logger = LoggerFactory.getLogger(TaskExecutorServiceImpl.class.getName());
    ExecutorService executorService;
    Queue<FutureTaskResult> taskResults = new ConcurrentLinkedQueue<>();

    private final Map<String, Semaphore> semaphoresMap = new ConcurrentHashMap<>();


    private Map<TaskGroup, Object> locks = new HashMap<>();
    FutureTaskResult futureTaskResult;
    public TaskExecutorServiceImpl(int maxConcurrency) {
        executorService = Executors.newFixedThreadPool(maxConcurrency);

    }

    @Override
    public Future<TaskResult> submitTask(Task task){
        futureTaskResult = new FutureTaskResult(task.getTaskId());
        /*Semaphore used for maximum allowed concurrency to be restricted: <permit:5>*/
        Semaphore semaphore = semaphoresMap.computeIfAbsent(task.getTaskGroup().toString(), k -> new Semaphore(5));

            try {
                semaphore.acquire();
                /* System.out.println("Executing task: " + task.getTaskId() + " in group: " + task.getTaskGroup());*/
                logger.info("Executing task: " + task.getTaskId() + " in group: " + task.getTaskGroup());
                Future<TaskResult>  future=  executorService.submit(task.getTaskAction());

                while (!(future.isDone() )) {
                    Thread.sleep(1);
                }
                Object taskResult = future.get();
                TaskResult result = new TaskResult(task.getTaskId(), taskResult);
                futureTaskResult.set(result);
                taskResults.offer(futureTaskResult);

        }catch (Exception e) {
            logger.error("An exception occurred!", new RuntimeException("Runnable exception"));

        }
         finally {
               /* Release the semaphore regardless of success or failure*/
                semaphore.release();
            }

        return futureTaskResult ;
    }
    /* cancel method have been added for future,but we need to modify TaskExecutor
     but the Assignment does not allow any modification */

    public boolean cancel(ExecutorService executorService) {
        return futureTaskResult.cancel(executorService.isTerminated());
    }
    public boolean isCancelled(ExecutorService executorService) {
        return futureTaskResult.isCancelled();
    }

    public void shutdown() {
        executorService.shutdown();
    }


}
