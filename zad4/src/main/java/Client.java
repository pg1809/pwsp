/**
 * Created by Lukasz on 2015-10-30.
 */
public class Client implements Runnable{

    private int max;
    private int allocated;
    private final int need = 1;

    private Banker banker;

    public Client(int max, Banker banker) {
        this.max = max;
        this.banker = banker;
    }

    @Override
    public void run() {
        while(allocated < max){
            allocated += banker.lend(need);
        }
        banker.getBack(max);
    }
}
