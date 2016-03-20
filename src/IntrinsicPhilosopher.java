import java.util.Random;

public class IntrinsicPhilosopher extends Thread {
    private IntrinsicPhilosopher left, right;
    private Random random;
    private boolean eating;
    private Object table;

    public IntrinsicPhilosopher(Object table) {
        this.eating = false;
        this.table = table;
        this.random = new Random();
    }

    public void run() {
        try {
            while (true) {
                this.think();
                this.eat();
            }
        } catch (InterruptedException e) {
        }
    }

    private void think() throws InterruptedException {
        synchronized (table) {
            this.eating = false;
        }
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        synchronized (table) {
            this.eating = true;
        }

        Thread.sleep(1000);
    }
}
