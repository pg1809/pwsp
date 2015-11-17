import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Client implements Runnable {

    private int maxDemand;

    private int allocation;

    private int clientNumber;

    private int request;

    private Banker banker;

    private CyclicBarrier barrier;

    public Client(int maxDemand, int allocation, int clientNumber, Banker banker, CyclicBarrier barrier) {
        this.maxDemand = maxDemand;
        this.allocation = allocation;
        this.clientNumber = clientNumber;
        this.banker = banker;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        Random r = new Random();
        try {
            while (true) {
                if (allocation == maxDemand) {
                    banker.releaseResources(clientNumber, maxDemand);
                    allocation = 0;
                    barrier.await();
                }
                request = r.nextInt(maxDemand - allocation) + 1;
                if (banker.requestResources(clientNumber, request)) {
                    allocation += request;
                }
            }
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
