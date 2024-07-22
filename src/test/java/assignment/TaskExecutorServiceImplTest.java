package assignment;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

    public class TaskExecutorServiceImplTest {

        private TaskExecutorServiceImpl taskExecutorService;
        private ExecutorService mockExecutorService;

        public TaskExecutorServiceImplTest() throws NoSuchFieldException {
        }


        @Before
        public void setUp() {
            mockExecutorService = Executors.newFixedThreadPool(1);
            taskExecutorService = new TaskExecutorServiceImpl(2);
            taskExecutorService.executorService = mockExecutorService;
        }

        @After
        public void tearDown() {
            taskExecutorService.shutdown();
        }

        @Test
        public void testSubmitTask() throws Exception {
            UUID taskId = UUID.randomUUID();
            TaskGroup taskGroup = new TaskGroup(UUID.randomUUID());
            Callable<String> taskAction = () -> {
                Thread.sleep(100);
                return "Task completed";
            };
            Task<String> task = new Task<>(taskId, taskGroup, TaskType.READ, taskAction);
            Future<TaskResult> future= taskExecutorService.submitTask(task);
            Thread.sleep(200);
            while(!future.isDone()){
                Thread.sleep(1);
            }
            Assert.assertEquals("Task completed",future.get());

        }
    }
