import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        int clientsCount = scanner.nextInt();

        System.out.println("Number of clients: " + clientsCount);

        int maxAvailable = scanner.nextInt();

        System.out.println("Max available: " + maxAvailable);

        int[] maxDemand = new int[clientsCount];

        for(int i=0; i<clientsCount; i++){
            maxDemand[i] = scanner.nextInt();
            while(maxDemand[i] > maxAvailable){
                maxDemand[i] = scanner.nextInt();
            }
        }

        System.out.println("Max array: " + Arrays.toString(maxDemand));

        int[] allocation = new int[clientsCount];

        for(int i=0; i<clientsCount; i++){
            allocation[i] = scanner.nextInt();
            while(allocation[i] > maxDemand[i]){
                allocation[i] = scanner.nextInt();
            }
        }

        System.out.println("Allocation array: " + Arrays.toString(allocation));

        Banker banker = new Banker(clientsCount, maxAvailable, allocation, maxDemand);

        for(int i=0; i<clientsCount; i++){
            Client client = new Client(maxDemand[i], i, banker);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }
}
