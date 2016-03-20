import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {
    private class Node {
        int value;

        Node prev;
        Node next;

        ReentrantLock lock = new ReentrantLock();

        Node() {}

        Node(int value, Node prev, Node next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        this.head = new Node();
        this.tail = new Node();

        this.head.next = this.tail;
        this.tail.prev = this.head;
    }


    public void insert(int value) {
        Node current = head;

        current.lock.lock();

        Node next = current.next;

        try {
            while(true) {
                next.lock.lock();

                try {
                    if (next == tail || next.value < value) {
                        Node node = new Node(value, current, next);

                        next.prev = node;
                        current.next = node;
                        return;
                    }
                } finally {
                    current.lock.unlock();
                }

                current = next;
                next = current.next;
            }
        } finally {
            next.lock.unlock();
        }
    }

    public int size() {
        Node current = head;

        int count = 0;

        while (current.next != tail) {
            ReentrantLock lock = current.lock;

            lock.lock();

            try {
                count++;

                current = current.next;
            } finally {
                lock.unlock();
            }
        }

        return count;
    }

    public boolean isSorted() {
        Node current = head;

        while (current.next.next != tail) {
            current = current.next;

            if (current.value < current.next.value) {
                return false;
            }
        }
        return true;
    }
}
