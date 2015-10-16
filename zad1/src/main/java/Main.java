import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-15.
 */
public class Main {

    public static void main(String [] args){
        Double[] array = {1.0, 6.0, 2.0, 5.0, 4.0, 3.0};

        int s = 3;
        int t = 3;

        Semaphore semaphore1 = new Semaphore(1, true);
        Semaphore semaphore2 = new Semaphore(1, true);

        Thread firstSetThread = new Thread(new FirstSet(array, 0, s, semaphore1, semaphore2));
        Thread secondSetThread = new Thread(new SecondSet(array, s, s+t, semaphore1, semaphore2));

        firstSetThread.start();
        secondSetThread.start();

    }
}