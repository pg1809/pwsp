import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
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

    List<Condition> conditions;

    public Buffer(int capacity) {
        this.capacity = capacity;
        messages = new String[capacity];
        conditions = new ArrayList<>(capacity);
        for(int i=0; i<capacity; i++){
            conditions.add(lock.newCondition());
        }
    }

    public void put(String message, int index){
        lock.lock();

        try{
            conditions.get(index).await();
            if(messages[index] == null) {
                messages[index] = message;
                System.out.println("Wsadzono: " + message);
            }
        } catch(InterruptedException exception){
            exception.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public String fetch(){
        lock.lock();

        try{
            String result = messages[actualIndex];
            messages[actualIndex] = null;
            conditions.get(actualIndex).signal();
            actualIndex = ++actualIndex % capacity;
            return result;
        } finally {
            lock.unlock();
        }
    }

}
