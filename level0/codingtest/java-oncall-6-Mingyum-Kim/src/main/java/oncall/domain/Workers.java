package oncall.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Workers {
    private Deque<Worker> workers;

    public Workers() {
        this.workers = new ArrayDeque<>();
    }

    private Workers(Deque<Worker> workers) {
        this.workers = workers;
    }

    public static Workers from(List<String> workerNames) {
        List<Worker> workers = workerNames.stream()
                .map(Worker::from)
                .toList();
        Deque<Worker> deque = new ArrayDeque<>(workers);
        return new Workers(deque);
    }

    /**
     * 제일 앞에 있는 요소를 반환하는 메서드
     */
    public Worker getFront() {
        return workers.getFirst();
    }

    /**
     * 제일 뒤에 있는 요소를 반환하는 메서드
     */
    public Worker getBack() {
        return workers.getLast();
    }

    /**
     * 제일 앞에 있는 요소를 제거하여 반환하는 메서드
     */
    public Worker popFront() {
        return workers.removeFirst();
    }

    /**
     * 제일 앞에 요소를 삽입하는 메서드
     */
    public void addFront(Worker worker) {
        workers.addFirst(worker);
    }

    /**
     * 제일 뒤에 요소를 삽입하는 메서드
     */
    public void addBack(Worker worker) {
        workers.addLast(worker);
    }

    public boolean isEmpty() {
        return workers.isEmpty();
    }
}
