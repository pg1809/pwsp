import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Client implements Runnable {

    private int clientNumber;

    private int request;

    private Banker banker;

    private Phaser phaser;

    public Client(int clientNumber, Banker banker, Phaser phaser) {
        this.clientNumber = clientNumber;
        this.banker = banker;
        this.phaser = phaser;

        phaser.register();
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            int allocation = banker.getClientAllocation(clientNumber);
            int maxDemand = banker.getClientMaxDemand(clientNumber);
            if (allocation == maxDemand){
                banker.releaseResources(clientNumber, maxDemand);
                phaser.arriveAndDeregister();
                System.out.println("Client " + clientNumber + " acquired all demanded resources.");
                return;
            }
            request = random.nextInt(maxDemand - allocation) + 1;
            banker.requestResources(clientNumber, request);

            phaser.arriveAndAwaitAdvance();
        }
    }
}
