import java.util.Scanner;

/**
 * Created by Lukasz on 2015-10-30.
 */
public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        // Banker initialization
        int bankerCapital = scanner.nextInt();
        Banker banker = new Banker(bankerCapital);
        Thread bankerThread = new Thread(banker);
        bankerThread.start();

        // Clients initialization
        int clientCount = scanner.nextInt();
        for(int i=0; i<clientCount; i++){
            int clientMax = scanner.nextInt();
            while(clientMax > bankerCapital){
                System.out.println("Client's max request higher than banker capital!");
                clientMax = scanner.nextInt();
            }
            Client client = new Client(clientMax, banker);
            Thread clientThread = new Thread(client);
            clientThread.start();
        }
    }
}
