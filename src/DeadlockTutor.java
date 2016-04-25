/**
 * Created by AKoscinski on 2016-04-25.
 */
import org.junit.Test;

public class DeadlockTutor {
    static StringBuffer buf = new StringBuffer();
    Thread t1, t2;
    Account account1 = new Account(100);
    Account account2 = new Account(50);
    Object monitor = new Object();

    static void log(String s) {
        buf.append(s+"\n");
    }

    @Test
    public void testDeadlock() {
        t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10000000;i++) {
                    synchronized (monitor) {
                        account1.transfer(account2, 30);
                    }
                }
            }
        });
        t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10000000;i++) {
                    synchronized (monitor) {
                        account2.transfer(account1, 30);
                    }
                }
            }
        });
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
    }

    class Account {
        double balance;
        int id;

        public Account(double balance) {
            this.balance = balance;
        }

        synchronized void withdraw(double amount){
            balance -= amount;
        }

        synchronized void deposit(double amount){
            balance += amount;
        }

        synchronized void transfer(Account from, double amount) {
                // block the current account
                deposit(amount);
                // block the account, from which transfer is done
                from.withdraw(amount);
        }
    }

}
