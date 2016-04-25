/**
 * Created by AKoscinski on 2016-04-25.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.Assert.*;

import org.junit.Test;

public class AtomicCounterTutor {
    final int ITERATIONS = 1000000;
    AtomicInteger counter = new AtomicInteger(0);

    @Test
    public void testThread() {
        List<Thread> threads = new ArrayList<>();
        for (int i=0;i<100;i++) {
            threads.add(new Thread(new TestThread("t"+i)));
        }
        System.out.println("Starting threads");
        for (int i=0;i<100;i++) {
            threads.get(i).start();
        }
        try {
            for (int i=0;i<100;i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Counter="+counter);

        assertTrue(counter.get()==ITERATIONS*100);
    }

    class TestThread implements Runnable {
        String threadName;

        public TestThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i=0;i<ITERATIONS;i++) {
                counter.incrementAndGet();
            }
        }
    }

}
