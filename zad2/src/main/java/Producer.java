/**
 * Created by Lukasz on 2015-10-20.
 */
public class Producer implements Runnable {

    private static int nextIndex = 0;

    private int index;

    private Compartment myCompartment;

    public Producer(Compartment myCompartment) {
        this.myCompartment = myCompartment;
        index = nextIndex++;
    }

    @Override
    public void run() {
        while(true){
            myCompartment.putMessage("Proces " + index);
        }
    }
}
