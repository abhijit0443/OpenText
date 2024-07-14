package assignment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

    public class TaskExecutorServiceImplTest {

        private TaskExecutorServiceImpl taskExecutorService;
        private ExecutorService mockExecutorService;

        public TaskExecutorServiceImplTest() throws NoSuchFieldException {
        }


        @Before
        public void setUp() {
            mockExecutorService = Executors.newFixedThreadPool(1); // Mock with single thread for simplicity
            taskExecutorService = new TaskExecutorServiceImpl(2); // Example with 2 threads
            taskExecutorService.executorService = mockExecutorService; // Inject the mock ExecutorService
        }

        @After
        public void tearDown() {
            taskExecutorService.shutdown(); // Shutdown the TaskExecutorService after each test
        }

        @Test
        public void testSubmitTask() throws Exception {
            UUID taskId = UUID.randomUUID();
            TaskGroup taskGroup = new TaskGroup(UUID.randomUUID()); // Replace with your actual TaskGroup
            Callable<String> taskAction = () -> {
                Thread.sleep(100); // Simulate task execution time
                return "Task completed";
            };
            Task<String> task = new Task<>(taskId, taskGroup, TaskType.READ, taskAction);
            taskExecutorService.submitTask(task);
            Thread.sleep(200);
            assertEquals(1, taskExecutorService.taskResults.size());
        }
    }
