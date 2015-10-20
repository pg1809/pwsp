import java.util.Scanner;

/**
 * Created by Lukasz on 2015-10-20.
 */
public class Main {

    private static int producersCount;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        producersCount = scanner.nextInt();
        Buffer buffer = new Buffer(producersCount);
        for(int i=0; i<producersCount; i++){
            Producer producer = new Producer(buffer, i);
            Thread producerThread = new Thread(producer);
            producerThread.start();
        }
        Consumer consumer = new Consumer(buffer);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    }
}
