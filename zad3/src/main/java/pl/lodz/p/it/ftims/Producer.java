package pl.lodz.p.it.ftims;

import java.nio.Buffer;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class Producer extends Thread {

    private static int nextIndex = 0;

    private int index;

    private BufferFacade bufferFacade;

    public Producer(BufferFacade facade) {
        this.bufferFacade = facade;
        index = nextIndex++;
    }

    @Override
    public void run() {
        while(true){
            bufferFacade.putMessage("Proces " + index, index);
        }
    }
}
