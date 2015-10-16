import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-16.
 */
public class FirstSet extends CustomSet {

    public FirstSet(Set<Double> set, int startIndex, int endIndex, Semaphore semaphore1, Semaphore semaphore2) {
        super(set, startIndex, endIndex, semaphore1, semaphore2);
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
        System.out.println("Tutaj bedzie zamienianie par");
    }
}
