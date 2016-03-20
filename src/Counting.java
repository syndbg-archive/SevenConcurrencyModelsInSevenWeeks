import java.util.concurrent.atomic.AtomicInteger;

public class Counting {
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger counter = new AtomicInteger();

        class CountingThread extends Thread {
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    // equivalent of ++counter
                    counter.incrementAndGet();
                }
            }
        }

        CountingThread c1 = new CountingThread();
        CountingThread c2 = new CountingThread();


        c1.start();
        c2.start();

        c1.join();
        c2.join();

        System.out.println(counter.get());
    }
}
