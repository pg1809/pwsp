import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Created by Lukasz on 2015-10-15.
 */
public class Main {

    public static void main(String[] args) {
        double[] array = {1.0, 6.0, 200.0, 99.0, 0.3, 5.0, 4.0, 3.0, 0.1};

        int s = 4;
        int t = 5;

//        Semaphore semaphore1 = new Semaphore(1, true);
//        Semaphore semaphore2 = new Semaphore(1, true);
//
//        Thread firstSetThread = new Thread(new FirstSet(array, 0, s, semaphore1, semaphore2));
//        Thread secondSetThread = new Thread(new SecondSet(array, s, s+t, semaphore1, semaphore2));
//
//        firstSetThread.start();
//        secondSetThread.start();

        SetManager firstSetManager = new SetManager(0, s, array);
        firstSetManager.setElementsComparator((Double first, Double second) -> {
            if (first < second) {
                return 1;
            } else if (first == second) {
                return 0;
            } else {
                return -1;
            }
        });

        SetManager secondSetManager = new SetManager(s, t, array);
        secondSetManager.setElementsComparator((Double first, Double second) -> {
            if (first > second) {
                return 1;
            } else if (first == second) {
                return 0;
            } else {
                return -1;
            }
        });

        firstSetManager.setAnother(secondSetManager);
        secondSetManager.setAnother(firstSetManager);

        firstSetManager.start();
        secondSetManager.start();
    }
}
