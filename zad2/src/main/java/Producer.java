/**
 * Created by Lukasz on 2015-10-20.
 */
public class Producer implements Runnable {

    private int index;

    private Buffer buffer;

    public Producer(Buffer buffer, int index) {
        this.buffer = buffer;
        this.index = index;
    }

    @Override
    public void run() {
        while(true){
            buffer.put("Proces " + index, index);
        }
    }
}
