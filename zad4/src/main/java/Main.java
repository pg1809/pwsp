import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Phaser;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int clientsNumber = scanner.nextInt();

        System.out.println("Number of clients: " + clientsNumber);

        int maxAvailable = scanner.nextInt();

        System.out.println("Max available: " + maxAvailable);

        int[] maxDemand = new int[clientsNumber];

        for (int i = 0; i < clientsNumber; i++) {
            maxDemand[i] = scanner.nextInt();
            while (maxDemand[i] > maxAvailable) {
                maxDemand[i] = scanner.nextInt();
            }
        }

        System.out.println("Max array: " + Arrays.toString(maxDemand));

        int[] allocation = new int[clientsNumber];

        for (int i = 0; i < clientsNumber; i++) {
            allocation[i] = scanner.nextInt();
            while (allocation[i] > maxDemand[i]) {
                allocation[i] = scanner.nextInt();
            }
        }

        System.out.println("Allocation array: " + Arrays.toString(allocation));

        Banker banker = new Banker(clientsNumber, maxAvailable, allocation, maxDemand);
        CyclicBarrier barrier = new CyclicBarrier(clientsNumber);

        for (int i = 0; i < clientsNumber; i++) {
            Client client = new Client(maxDemand[i], allocation[i], i, banker, barrier);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }
}
