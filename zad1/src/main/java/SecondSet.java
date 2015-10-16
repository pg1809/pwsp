import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-16.
 */
public class SecondSet extends CustomSet {

    public SecondSet(Double[] array, int startIndex, int endIndex, Semaphore semaphore1, Semaphore semaphore2) {
        super(array, startIndex, endIndex, semaphore1, semaphore2);
    }

    @Override
    public void run() {
        System.out.println("drugi run");
        try {
            semaphore2.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
        semaphore2.release();
        try {
            semaphore1.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        semaphore1.release();
    }
}
