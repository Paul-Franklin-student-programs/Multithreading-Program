import java.util.concurrent.locks.ReentrantLock;

public class IncrementingClass {
    static int count = 0;
    public static final ReentrantLock lock = new ReentrantLock();

    static void countToTwenty() {
        lock.lock();
        try {
            for (int i = 0; i < 20; i++) {
                count++;
            }
        } finally {
            lock.unlock();
        }
    }
}

class DriverClass {
    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new Thread(IncrementingClass::countToTwenty);
        t2.start();
        {
            try {
                IncrementingClass.lock.lock();
                for (int p = 0; p < 20; p++) {
                    IncrementingClass.count--;
                }
            } finally {
                IncrementingClass.lock.unlock();
            }
        }
        t2.join();
        System.out.println("Final value of \"count\" variable: " + IncrementingClass.count);
    }
}