import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Lukasz on 2015-10-20.
 */
public class Buffer {

    private String[] messages;

    private int capacity;

    private int actualIndex;

    private final Lock lock = new ReentrantLock();

    public Buffer(int capacity) {
        this.capacity = capacity;
        messages = new String[capacity];
    }

    public void put(String message, int index){
        lock.lock();

        try{
            if(messages[index] == null) {
                messages[index] = message;
                System.out.println("Wsadzono: " + message);
            }
        } finally {
            lock.unlock();
        }
    }

    public String fetch(){
        lock.lock();

        try{
            String result = messages[actualIndex];
            messages[actualIndex] = null;
            actualIndex = ++actualIndex % capacity;
            return result;
        } finally {
            lock.unlock();
        }
    }

}
