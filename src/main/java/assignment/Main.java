package assignment;


import java.util.logging.Logger;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


public class Main {
    static Logger logger =  Logger.getLogger(Main.class.getName());
    public static final  String GROUP="GROUP" ;
    public static final  String RESULT="Result";
    public static final  int NUM=15;
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TaskExecutor executorService = new TaskExecutorServiceImpl(10);

        List<Future<TaskResult> > futureResultlist=new LinkedList<>();

        for(int index=0;index<NUM;index++) {
            int number=index+1;
            //Future<TaskResult> future   = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(UUID.fromString("123e4567-e89b-42d3-a456-556642440000")), TaskType.READ, new TaskAction(""+number)));
            String group=GROUP+number;
            Future<TaskResult> future   = executorService.submitTask(new Task(UUID.randomUUID(), new TaskGroup(UUID.nameUUIDFromBytes(group.getBytes())), TaskType.READ, new TaskAction(""+number)));

            futureResultlist.add(future);
        }

        for(int position=0;position<futureResultlist.size();position++) {
            int number=position+1;
            Future<TaskResult> future = futureResultlist.get(position);
            while (!future.isDone())
            {
                Thread.sleep(100);
            }

            logger.info(RESULT+" "+number+ " :" + future.get());

        }

        ((TaskExecutorServiceImpl) executorService).shutdown();
    }
}
