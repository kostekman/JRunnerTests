import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Use passing a copy of list to print()
 */
public class SynchronizedListTutor {
    static boolean failed = false;
    static StringBuffer buf = new StringBuffer();
    static String[] animals = { "Cow", "Goose", "Cat", "Dog",
            "Elephant", "Rabbit", "Snake", "Chicken",
            "Horse", "Human" };
    List<String> randomAnimals =
            new CopyOnWriteArrayList(new ArrayList<String>());

    static void log(String s) {
        buf.append(s+"\n");
    }

    static void err(String s) {
        buf.append("<span style='color:red'><b>"+s+"</b></span>\n");
        failed = true;
    }

    public String getRandomAnimal() {
        int index = (int)(Math.random()*animals.length);
        return animals[index];
    }

    public void print(Collection<?> c) {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iterator = c.iterator();
        while(iterator.hasNext()) {
            builder.append(iterator.next());
            builder.append(" ");
        }
        //log(builder.toString());
    }

    @Test
    public void testThread() {
        List<Thread> threads = new ArrayList<Thread>();
        for (int i=0;i<100;i++) {
            threads.add(new Thread(new TestThread("t"+i)));
        }
        System.out.println("Starting threads");
        for (int i=0;i<100;i++) {
            threads.get(i).start();
        }
        System.out.println("Waiting for threads");
        try {
            for (int i=0;i<100;i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(buf);
        assertFalse(failed);
    }

    class TestThread implements Runnable {
        String threadName;

        public TestThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            try {
                for (int i=0;i<20;i++) {
                    randomAnimals.add(getRandomAnimal());
                    print(randomAnimals);
                }
            } catch(Exception e) {
                err(e.getClass().getName());
            }
        }
    }


}
