package pl.lodz.p.it.ftims;

import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int producersNumber = scanner.nextInt();

        List<Producer> producers = new ArrayList<>(producersNumber);
        BufferManager bufferManager = new BufferManager(producersNumber, 20000);

        for (int i = 0; i < producersNumber; i++) {
            BufferFacade facade = new BufferFacade(20000);
            Producer producer = new Producer(facade);
            producers.add(producer);
        }

        BufferFacade facade = new BufferFacade(20000);
        Consumer consumer = new Consumer(facade, producersNumber);

        bufferManager.start();
        producers.stream().forEach(thread -> thread.start());
        consumer.start();
    }
}
