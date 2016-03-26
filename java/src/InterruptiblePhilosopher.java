import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class InterruptiblePhilosopher extends Thread {
    private ReentrantLock leftChopstick;
    private ReentrantLock rightChopstick;

    private Random random;


    public InterruptiblePhilosopher(ReentrantLock leftChopstick, ReentrantLock rightChopstick) {
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
    }

    public void run() {
        try {
            while(true) {
                Thread.sleep(random.nextInt(1000));

                leftChopstick.lock();

                try {
                    if (rightChopstick.tryLock(1000, TimeUnit.MILLISECONDS)) {
                        try {
                            Thread.sleep(random.nextInt(1000));
                        } finally {
                            rightChopstick.unlock();
                        }
                    } else {
                        // Nothing to do since the chopstick
                        // wasn't acquired (locked)
                    }
                } finally {
                    leftChopstick.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
