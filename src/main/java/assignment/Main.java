package assignment;

import com.fasterxml.uuid.Generators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class.getName());
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TaskExecutor executorService = new TaskExecutorServiceImpl(10); // Max concurrency of 3

        List<Future<TaskResult> > list=new LinkedList<>();
        for(int i=0;i<15;i++) {
            int number=i+1;
            //Future<TaskResult> future   = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(UUID.fromString("123e4567-e89b-42d3-a456-556642440000")), TaskType.READ, new TaskAction(""+number)));

            Future<TaskResult> future   = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(Generators.nameBasedGenerator().generate("GROUP"+number)), TaskType.READ, new TaskAction(""+number)));
            list.add(future);
        }

        for(int i=0;i<list.size();i++) {
            int number=i+1;
            TaskResult result = list.get(i).get();
            logger.info("Result  "+number+ " :" + result.getResult());
        }

        /*Future<TaskResult> future2 = executorService.submitTask(new Task(UUID.randomUUID(),new TaskGroup(Generators.nameBasedGenerator().generate("GROUP2")), TaskType.WRITE, new TaskAction("2")));
        Future<TaskResult> future3 = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(Generators.nameBasedGenerator().generate("GROUP3")), TaskType.READ, new TaskAction("3")));
        Future<TaskResult> future4 = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(Generators.nameBasedGenerator().generate("GROUP1")), TaskType.WRITE, new TaskAction("4")));
        Future<TaskResult> future5 = executorService.submitTask(new Task(UUID.randomUUID(),new TaskGroup(Generators.nameBasedGenerator().generate("GROUP4")), TaskType.READ, new TaskAction("5")));
        Future<TaskResult> future6 = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(Generators.nameBasedGenerator().generate("GROUP1")), TaskType.READ, new TaskAction("6")));


        *//* Wait for tasks to complete and get results*//*
        TaskResult result1 = future1.get();
        TaskResult result2 = future2.get();
        TaskResult result3 = future3.get();
        TaskResult result4 = future4.get();
        TaskResult result5 = future5.get();
        TaskResult result6 = future6.get();

        logger.info("Result 1: " + result1.getResult());
        logger.info("Result 2: " + result2.getResult());
        logger.info("Result 3: " + result3.getResult());
        logger.info("Result 4: " + result4.getResult());
        logger.info("Result 5: " + result5.getResult());
        logger.info("Result 6: " + result6.getResult());*/

        ((TaskExecutorServiceImpl) executorService).shutdown();
    }
}
