import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordCount {
    private static final int NUM_COUNTERS = 2;

    public static void main(String[] args) throws Exception {
        ArrayBlockingQueue<Page> queue = new ArrayBlockingQueue<>(100);
        HashMap<String, Integer> counts = new HashMap<>();
        ExecutorService executor = Executors.newCachedThreadPool();

        Thread counter = new Thread(new Counter(queue, counts));

        for (int i = 0; i < NUM_COUNTERS; i++) {
            executor.execute(new Counter(queue, counts));
        }

        Thread parser = new Thread(new Parser(queue));
        parser.join();

        executor.shutdown();
        executor.awaitTermination(10L, TimeUnit.MINUTES);
    }
}
