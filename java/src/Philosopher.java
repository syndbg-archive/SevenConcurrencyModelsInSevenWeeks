import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private Philosopher left, right;
    private Random random;
    private boolean eating;
    private ReentrantLock table;
    private Condition tableCondition;

    public Philosopher(ReentrantLock table) {
        this.eating = false;
        this.table = table;
        this.tableCondition = table.newCondition();
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
        table.lock();

        try {
            this.eating = false;
            this.left.tableCondition.signal();
            this.right.tableCondition.signal();
        } finally {
            table.unlock();
        }

        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        table.lock();

        try {
            while (left.eating || right.eating) {
                this.tableCondition.await();
            }

            this.eating = true;
        } finally {
            table.unlock();
        }

        Thread.sleep(1000);
    }
}
