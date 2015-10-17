import java.util.concurrent.Semaphore;

/**
 * Created by Piotr Grzelak on 2015-10-17.
 */
public class ReusableBarrier {

    private Semaphore mutex;

    private Semaphore firstTurnstile;

    private Semaphore secondTurnstile;

    private int count;

    private int max;

    public ReusableBarrier(int max) {
        this.max = max;
        count = 0;
        mutex = new Semaphore(1);
        firstTurnstile = new Semaphore(0);
        secondTurnstile = new Semaphore(0);
    }

    public void waitAtBarrier() throws InterruptedException {
        mutex.acquire();
        count++;
        if (count == max) {
            firstTurnstile.release(max);
        }
        mutex.release();
        firstTurnstile.acquire();
    }

    public void resumeFromBarrier() throws InterruptedException {
        mutex.acquire();
        count--;
        if (count == 0) {
            secondTurnstile.release(max);
        }
        mutex.release();
        secondTurnstile.acquire();
    }
}
