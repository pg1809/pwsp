import java.util.Random;
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

    public Client(int maxDemand, int allocation, int clientNumber, Banker banker) {
        this.maxDemand = maxDemand;
        this.allocation = allocation;
        this.clientNumber = clientNumber;
        this.banker = banker;
    }

    @Override
    public void run() {
        Random r = new Random();
        AtomicInteger grantedRequestNum = new AtomicInteger(0);
        while (true) {
            if (allocation == maxDemand) {
                banker.releaseResources(clientNumber, maxDemand);
                allocation = 0;
            }
            request = r.nextInt(maxDemand - allocation) + 1;
            banker.requestResources(clientNumber, request);
            allocation += request;
        }
    }
}
