import java.util.List;

/**
 * Created by Lukasz on 2015-10-20.
 */
public class Consumer implements Runnable {

    private List<Compartment> buffer;

    public Consumer(List<Compartment> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int messageToFetchIdx = 0;
        while(true){
            buffer.get(messageToFetchIdx).removeMessage();
            messageToFetchIdx = (messageToFetchIdx + 1) % buffer.size();
        }
    }
}
