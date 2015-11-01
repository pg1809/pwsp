import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Lukasz on 2015-10-20.
 */
public class Main {

    private static int producersCount;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        producersCount = scanner.nextInt();

        List<Compartment> buffer = new ArrayList<>(producersCount);
        List<Thread> producers = new ArrayList<>(producersCount);

        for (int i = 0; i < producersCount; i++) {
            Compartment compartment = new Compartment();
            buffer.add(compartment);
            Thread producerThread = new Thread(new Producer(compartment));
            producers.add(producerThread);
        }

        Thread consumerThread = new Thread(new Consumer(buffer));

        producers.stream().forEach(thread -> thread.start());
        consumerThread.start();
    }
}
