package pl.lodz.p.it.ftims;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class Consumer extends Thread {

    private BufferFacade facade;

    private int startIdx = 0;

    private int bufferSize;

    public Consumer(BufferFacade facade, int bufferSize) {
        this.facade = facade;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        while(true) {
            facade.removeMessage(startIdx);
            startIdx = (startIdx + 1) % bufferSize;
        }
    }
}
