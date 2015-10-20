/**
 * Created by Lukasz on 2015-10-20.
 */
public class Main {

    private static final int producersCount = 5;

    public static void main(String[] args){
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
