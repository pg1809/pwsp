import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-16.
 */
public class FirstSet extends CustomSet {

    public FirstSet(Double[] array, int startIndex, int endIndex, Semaphore semaphore1, Semaphore semaphore2) {
        super(array, startIndex, endIndex, semaphore1, semaphore2);
    }

    @Override
    public void run() {
        System.out.println("pierwszy run");
        try {
            semaphore1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
        semaphore1.release();
        try {
            semaphore2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore2.release();
        int i=0;
        while(array[endIndex-1-i] > array[endIndex+i]){
            System.out.println("zamiana");
            swap(array, endIndex-1-i, endIndex+i);
            System.out.println(Arrays.toString(array));
            i++;
        }

    }

    public static final <T> void swap (T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
