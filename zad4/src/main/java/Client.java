import java.util.Random;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Client implements Runnable{

    private int maxDemand;

    private int clientNumber;

    private int request;

    private Banker banker;

    private Random random;

    public Client(int maxDemand, int clientNumber, Banker banker) {
        this.maxDemand = maxDemand;
        this.clientNumber = clientNumber;
        this.banker = banker;
        random = new Random();
    }

    @Override
    public void run() {
        while(true) {

            try {
                Thread.sleep(random.nextInt(2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            request = random.nextInt(maxDemand) + 1;

            if (banker.requestResources(clientNumber, request)) {
                try {
                    Thread.sleep(random.nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                banker.releaseResources(clientNumber, request);
            }
        }
    }
}
