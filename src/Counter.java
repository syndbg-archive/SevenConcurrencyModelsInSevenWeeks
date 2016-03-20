import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class Counter implements Runnable {
    private BlockingQueue<Page> queue;
    private ConcurrentMap<String, Integer> counts;
    private Map<String, Integer> localCounts;

    public Counter(BlockingQueue<Page> queue, Map<String, Integer> counts) {
        this.queue = queue;
        this.counts = counts;

        this.localCounts = new HashMap<>();
    }

    public void run() {
        try {
            while (true) {
                Page page = queue.take();

                Iterable<String> words = new Words(page.getText());

                for (String word : words) {
                    countWord(word);
                }
            }

            mergeCounts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void countWord(String word) {
        Integer currentCount = localCounts.get(word);

        if (currentCount == null) {
            localCounts.put(word, 1);
        } else {
            localCounts.put(word, currentCount + 1);
        }
    }

    private void mergeCounts() {
        for (Map.Entry<String, Integer> e: localCounts.entrySet()) {
            String word = e.getKey();
            Integer count = e.getValue();

            while (true) {
                Integer currentCount = counts.get(word);

                if (currentCount == null && counts.putIfAbsent(word, count) == null) {
                    break;
                } else if (counts.replace(word, currentCount, currentCount + count)) {
                    break;
                }
            }
        }
    }
}
