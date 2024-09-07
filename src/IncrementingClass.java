import java.util.concurrent.locks.ReentrantLock;

/**responsible for creating count variable, creating new lock, and housing
 * countToTwenty() method
 */
public class IncrementingClass {
    static int count = 0; //initialize count variable for future incrementation
    public static final ReentrantLock lock = new ReentrantLock();// instantiate a new ReentrantLock
/**
Increments int variable count from 0 to 20 after locking mutex
 */
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

/**
 * creates new thread to call countToTwenty method in IncrementingClass,
 * and subsequently decrements count variable from 20 to 0
 */
class DriverClass {
    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new Thread(IncrementingClass::countToTwenty); //new thread to call countToTwenty()
        t2.start(); //sets thread t2 in motion
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
        t2.join(); //waits for thread t2 finishes before program flow continues
        System.out.println("Final value of \"count\" variable: " + IncrementingClass.count);//outputs final int value of count
    }
}