import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.Semaphore;

/**
 * Created by Piotr Grzelak on 2015-10-17.
 */
public class SetManager extends Thread {

    private static Semaphore changeSemaphore = new Semaphore(1, true);

    private static ReusableBarrier barrier = new ReusableBarrier(2);

    private double[] spaceOfElements;

    private int setBeginningIdx;

    private int setSize;

    private SetManager another;

    private int elemToChangeIdx;

    private Semaphore readWriteSemaphore;

    private Comparator<Double> elementsComparator;

    public SetManager(int setBeginningIdx, int setSize, double[] spaceOfElements) {
        this.setBeginningIdx = setBeginningIdx;
        this.setSize = setSize;
        this.spaceOfElements = spaceOfElements;

        readWriteSemaphore = new Semaphore(1, true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                readWriteSemaphore.acquire();
                elemToChangeIdx = findElementToChangeIdx();

                barrier.waitAtBarrier();
                barrier.resumeFromBarrier();

                if (elementsComparator.compare(spaceOfElements[elemToChangeIdx], spaceOfElements[another.elemToChangeIdx]) > 0) {
                    System.out.println("After execution: " + Arrays.toString(spaceOfElements));
                    readWriteSemaphore.release();
                    return;
                }
                readWriteSemaphore.release();

                another.readWriteSemaphore.acquire();
                changeSemaphore.acquire();
                if (elementsComparator.compare(spaceOfElements[elemToChangeIdx], spaceOfElements[another.elemToChangeIdx]) <= 0) {
                    double tmp = spaceOfElements[elemToChangeIdx];
                    spaceOfElements[elemToChangeIdx] = spaceOfElements[another.elemToChangeIdx];
                    spaceOfElements[another.elemToChangeIdx] = tmp;
                }
                changeSemaphore.release();
                another.readWriteSemaphore.release();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }

    private int findElementToChangeIdx() {
        int idx = setBeginningIdx;
        for (int i = setBeginningIdx; i < setBeginningIdx + setSize; ++i) {
            if (elementsComparator.compare(spaceOfElements[i], spaceOfElements[idx]) <= 0) {
                idx = i;
            }
        }

        return idx;
    }

    public void setAnother(SetManager another) {
        this.another = another;
    }

    public void setElementsComparator(Comparator<Double> elementsComparator) {
        this.elementsComparator = elementsComparator;
    }
}
