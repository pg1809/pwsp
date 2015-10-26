import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Lukasz on 2015-10-25.
 */
public class Main {

    private static Scanner scanner = new Scanner(System.in).useLocale(Locale.ENGLISH);

    public static void main(String[] args){
        boolean generateInputs = false;

        if(generateInputs){
            generateInputs();
        } else {
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

                long start = System.nanoTime();

                Collections.sort(S, Collections.reverseOrder());
                Collections.sort(T);

                Queue<Double> SToChange = new ArrayDeque<>();
                Queue<Double> TToChange = new ArrayDeque<>();
                S.stream().forEach(element -> SToChange.add(element));
                T.stream().forEach(element -> TToChange.add(element));

                while (true) {
                    if(SToChange.isEmpty() || TToChange.isEmpty()){
                        break;
                    }
                    double SCandidate = SToChange.poll();
                    double TCandidate = TToChange.poll();

                    if (SCandidate > TCandidate) {
                        S.add(TCandidate);
                        S.remove(SCandidate);
                        T.add(SCandidate);
                        T.remove(TCandidate);
                    } else {
                        break;
                    }
                }

                long elapsedTime = System.nanoTime() - start;

//                System.out.println(Arrays.toString(S.toArray()));
//                System.out.println(Arrays.toString(T.toArray()));
                System.out.println(elapsedTime);
            }
        }
    }

    private static void generateInputs(){
        try {
            File testFile = new File("tests.txt");
            PrintWriter writer = new PrintWriter(testFile);
            Random random = new Random();
            Set<Double> SSet = new HashSet<>();
            Set<Double> TSet = new HashSet<>();

            int testsCount = scanner.nextInt();
            for (int i = 0; i < testsCount; i++) {
                int SSize = scanner.nextInt();
                writer.write(String.valueOf(SSize) + "\r\n");
                for (int j = 0; j < SSize; j++) {
                    double newElement = Math.round(random.nextDouble() * 1000000.0) / 100.0;
                    while(SSet.contains(newElement)){
                        newElement = Math.round(random.nextDouble() * 1000000.0) / 100.0;
                    }
                    SSet.add(newElement);
                    writer.write(String.valueOf(newElement) + " ");
                }
                writer.write("\r\n");
                int TSize = scanner.nextInt();
                writer.write(String.valueOf(TSize) + "\r\n");
                for (int j = 0; j < TSize; j++) {
                    double newElement = Math.round(random.nextDouble() * 1000000.0) / 100.0;
                    while(TSet.contains(newElement)){
                        newElement = Math.round(random.nextDouble() * 1000000.0) / 100.0;
                    }
                    TSet.add(newElement);
                    writer.write(String.valueOf(newElement) + " ");
                }
                writer.write("\r\n");
            }
            writer.close();
        }catch (FileNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
