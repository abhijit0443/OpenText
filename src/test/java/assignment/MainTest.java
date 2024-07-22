package assignment;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


public class MainTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testMainMethodExecution() throws InterruptedException, ExecutionException, TimeoutException {
        ByteArrayOutputStream logOutputStream = new ByteArrayOutputStream();
        PrintStream logPrintStream = new PrintStream(logOutputStream);


        TaskExecutor executorService = new TaskExecutorServiceImpl(5);

        try {
            Main.main(new String[]{});



        } finally {
            ((TaskExecutorServiceImpl) executorService).shutdown();
        }
    }
}
