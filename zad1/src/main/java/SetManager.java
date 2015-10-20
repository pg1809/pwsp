import java.util.Comparator;
import java.util.List;
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

    public SetManager(List<Double> set) {
        super();
        this.set = set;
        bufferNotEmpty = new Semaphore(0);
        bufferNotFull = new Semaphore(1);
    }

    @Override
    public void run() {
        try {
            while (true) {
                double tmp = findCandidateForChange();
                bufferNotFull.acquire();
                candidateForChange = tmp; //findCandidateForChange();
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

    private Double findCandidateForChange() {
        Double candidate = set.get(0);
        for (Double element : set) {
            if (elementsComparator.compare(element, candidate) <= 0) {
                candidate = element;
            }
        }
        return candidate;
    }

    public void setAnother(SetManager another) {
        this.another = another;
    }

    public void setElementsComparator(Comparator<Double> elementsComparator) {
        this.elementsComparator = elementsComparator;
    }

    public void reset() {
        bufferNotEmpty = new Semaphore(0);
        bufferNotFull = new Semaphore(1);
    }
}
