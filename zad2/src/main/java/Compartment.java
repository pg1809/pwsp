import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Piotr Grzelak on 2015-10-31.
 */
public class Compartment {

    private String message;

    private Lock lock = new ReentrantLock();

    private Condition compartmentEmpty = lock.newCondition();

    public void putMessage(String message) {
        try {
            lock.lock();
            if (this.message != null) {
                compartmentEmpty.await();
            }

            this.message = message;
            System.out.println("Umieszczono wiadomosc: " + message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void removeMessage() {
        try {
            lock.lock();
            if (message != null) {
                System.out.println("Pobrano wiadomosc: " + message);
                message = null;
                compartmentEmpty.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
