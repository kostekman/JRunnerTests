import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by AKoscinski on 2016-04-25.
 */
public class CallableTutor {
    static StringBuffer buf = new StringBuffer();
    static void log(String s) {
        buf.append(s+"\n");
    }

    @Test
    public void testCallable() {
        long start = new Date().getTime();
        List<Thread> threads = new ArrayList<>();

        ArrayList<Future<String>> results = new ArrayList<>();

        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i=0;i<10;i++) {
            results.add(executorService.submit(new StringGenerator()));
        }

        // try to cancel 5 of 10 threads here
        for (int i=0;i<5;i++) {
            log("cancelling thread "+i);
            results.get(i).cancel(true);
        }

        StringBuilder resultStr = new StringBuilder();
        for(Future<String> result: results){
            try {
                // The blocking get call
                resultStr.append(result.get());
                resultStr.append(" ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println(resultStr);

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long time =new Date().getTime()-start;
        System.out.println("Time of work:"+time);

        System.out.println(buf);
    }

    public class StringGenerator implements Callable<String> {

        public String call() throws Exception {
            String[] allStrings = { "Cow", "Goose", "Cat", "Dog",
                    "Elephant", "Rabbit", "Snake", "Chicken",
                    "Horse", "Human" };
            int index = (int)(Math.random()*100)/10;

            Thread.sleep(10);
            return allStrings[index];
        }
    }

}

