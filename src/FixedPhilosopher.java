import java.util.Random;

public class FixedPhilosopher extends Thread {
    private Chopstick first, second;
    private Random random;
    private int thinkCount;

    public FixedPhilosopher(Chopstick left, Chopstick right) {
        if (left.getId() < right.getId()) {
            this.first = left;
            this.second = right;
        } else {
            this.first = right;
            this.second = left;
        }

        this.random = new Random();
    }

    public void run() {
        try {
            while(true) {
                thinkCount++;
                if (thinkCount % 10 == 0) {
                    System.out.println("Philosopher " + this + " has thought " + thinkCount + " times");
                }
                Thread.sleep((random.nextInt(1000)));

                synchronized (first) {
                    synchronized (second) {
                        Thread.sleep(random.nextInt(1000));
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
