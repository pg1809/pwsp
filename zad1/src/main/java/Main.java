import java.util.*;

/**
 * Created by Lukasz on 2015-10-15.
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);
        List<Double> S = new ArrayList<>();
        List<Double> T = new ArrayList<>();

        while (scanner.hasNext()) {

            int sSize = scanner.nextInt();
            for (int i = 0; i < sSize; ++i) {
                S.add(scanner.nextDouble());
            }
            int tSize = scanner.nextInt();
            for (int i = 0; i < tSize; ++i) {
                T.add(scanner.nextDouble());
            }

            SetManager SManager = new SetManager(S);
            SetManager TManager = new SetManager(T);

            SManager.setAnother(TManager);
            TManager.setAnother(SManager);

            SManager.setElementsComparator((Double first, Double second) -> {
                if (first < second) {
                    return 1;
                } else if (first == second) {
                    return 0;
                } else {
                    return -1;
                }
            });
            TManager.setElementsComparator((Double first, Double second) -> {
                if (first > second) {
                    return 1;
                } else if (first == second) {
                    return 0;
                } else {
                    return -1;
                }
            });

            long start = System.nanoTime();

            SManager.start();
            TManager.start();

            SManager.join();
            TManager.join();

            long elapsedTime = System.nanoTime() - start;

//            System.out.println(Arrays.toString(S.toArray()));
//            System.out.println(Arrays.toString(T.toArray()));
            System.out.println(elapsedTime);
            S.clear();
            T.clear();
        }
    }
}
