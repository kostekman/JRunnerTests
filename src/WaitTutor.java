import org.junit.Test;
import org.junit.Assert;

public class WaitTutor {
    static final int threadsAmount = 2;
    static StringBuffer buf = new StringBuffer();
    Thread t1, t2;
    Object monitor = new Object();
    int runningThreadNumber = 1;
    int t1Counter = 0, t2Counter = 0;
    int maxCounter = 0;
    /**
     * This code to check for the correctness of next counter.
     * Counters should be ordered: 0, 0, 1, 1, 2, 2, etc.
     */
    boolean wrongCounter = false;
    int counter = 0;
    int counterOccured = 0;

    static void log(String s) {
        buf.append(s+"\n");
    }

    @Test
    public void testThread() {
        t1 = new Thread(new TestThread("t1", 1));
        t2 = new Thread(new TestThread("t2", 2));
        System.out.println("Starting threads");
        t1.start();
        t2.start();

        System.out.println("Waiting for threads");
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(buf);
        Assert.assertFalse(wrongCounter);
    }

    private void logAndCheckCounter(String threadName, int c) {
        System.out.println(threadName+":"+c);
        if (counter != c) wrongCounter = true;
        counterOccured++;

        if (counterOccured == threadsAmount) {
            counter++;
            counterOccured = 0;
        }
    }

    class TestThread implements Runnable {
        String threadName;
        int n;

        public TestThread(String threadName, int n) {
            this.threadName = threadName;
            this.n = n;
        }

        @Override
        public void run() {
            for (int i=0;i<10;i++) {

                synchronized(monitor) {

                    if (n==1) t1Counter = i;
                    if (n==2) t2Counter = i;
                    Thread.yield();
                    try {
                        if (n==1) {
                            if (i>t2Counter) {
                                System.out.println("t1 is ahead with i="+i+", wait for t2Counter="+t2Counter);
                                monitor.wait();
                            }
                        }
                        if (n==2) {
                            if (i>t1Counter) {
                                System.out.println("t2 is ahead with i="+i+", wait for t1Counter="+t1Counter);
                                monitor.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logAndCheckCounter(threadName, i);
                    monitor.notify();
                }

                Thread.yield();
            }
        }
    }


}
