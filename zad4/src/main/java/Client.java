import java.util.concurrent.Phaser;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Client implements Runnable{

    private int maxDemand;

    private int allocation;

    private int clientNumber;

    private int request;

    private Banker banker;

    private Phaser phaser;

    public Client(int maxDemand, int allocation, int clientNumber, Banker banker, Phaser phaser) {
        this.maxDemand = maxDemand;
        this.allocation = allocation;
        this.clientNumber = clientNumber;
        this.banker = banker;
        this.phaser = phaser;

        phaser.register();
    }

    @Override
    public void run() {
        while(true) {
            request = maxDemand - allocation;

            if (banker.requestResources(clientNumber, request)) {
                banker.releaseResources(clientNumber, maxDemand);
                phaser.arriveAndDeregister();
                return;
            }
            phaser.arriveAndAwaitAdvance();
        }
    }
}
