import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by Piotr Grzelak on 2015-10-20.
 */
public class SetManager extends Thread {

    private List<Double> set;

    private Double candidateForChange;

    private SetManager another;

    private Semaphore bufferNotEmpty;

    private Semaphore bufferNotFull;

    private Comparator<Double> elementsComparator;

    private Queue<Double> toChange;

    public SetManager(List<Double> set) {
        super();
        this.set = set;
        bufferNotEmpty = new Semaphore(0);
        bufferNotFull = new Semaphore(1);
        toChange = new ArrayDeque<>();
    }

    @Override
    public void run() {
        try {
            set.sort(elementsComparator);
            set.stream().forEach(element -> toChange.add(element));
            while (true) {
                bufferNotFull.acquire();
                if (!toChange.isEmpty()) {
                    candidateForChange = toChange.poll();
                }
                bufferNotEmpty.release();

                another.bufferNotEmpty.acquire();
                if (elementsComparator.compare(candidateForChange, another.candidateForChange) > 0) {
                    return;
                } else {
                    set.add(another.candidateForChange);
                }
                another.bufferNotFull.release();
                set.remove(candidateForChange);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAnother(SetManager another) {
        this.another = another;
    }

    public void setElementsComparator(Comparator<Double> elementsComparator) {
        this.elementsComparator = elementsComparator;
    }
}
