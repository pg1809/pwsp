import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-15.
 */
public class CustomSet implements Runnable {

    protected Double[] array;

    private int startIndex;

    private int endIndex;

    protected Semaphore semaphore1;

    protected Semaphore semaphore2;

    public void run(){
        sort();
    }

    private void sort() {
        System.out.println(Arrays.toString(array));

        Arrays.sort(array, startIndex, endIndex);

        System.out.println(Arrays.toString(array));
    }

    public CustomSet(Set<Double> set, int startIndex, int endIndex, Semaphore semaphore1, Semaphore semaphore2) {
        array = set.toArray(new Double[set.size()]);
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.semaphore1 = semaphore1;
        this.semaphore2 = semaphore2;
    }
}
