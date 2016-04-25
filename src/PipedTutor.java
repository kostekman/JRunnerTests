/**
 * Created by AKoscinski on 2016-04-25.
 */
import static org.junit.Assert.*;
import java.io.*;
import org.junit.*;

public class PipedTutor {
    private static final String TEST_LINE = "test line";
    PipedWriter pipedWriter = new PipedWriter();
    PipedReader pipedReader;
    String pipeReaderResult = null;
    Object waiter = new Object();

    @Test
    public void testPipe() throws InterruptedException {
        try {
            pipedReader = new PipedReader(pipedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread t1 = new Thread(new PipeWriterThread());
        Thread t2 = new Thread(new PipeReaderThread());

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        assertEquals(TEST_LINE, pipeReaderResult);
    }

    class PipeWriterThread implements Runnable {
        public void run() {
            BufferedWriter out = new BufferedWriter(pipedWriter);
            try {
                synchronized (waiter) {
                    out.write(TEST_LINE);
                    out.close();
                    waiter.notify();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class PipeReaderThread implements Runnable {
        public void run() {
            BufferedReader in = new BufferedReader(pipedReader);
            try {

                synchronized (waiter) {
                    while((pipeReaderResult = in.readLine()) == null) {
                        waiter.wait();
                    }
                    waiter.notify();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

