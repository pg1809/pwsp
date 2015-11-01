/**
 * Created by Lukasz on 2015-10-30.
 */
public class Banker implements Runnable{

    private int capital;

    public Banker(int capital) {
        this.capital = capital;
    }

    @Override
    public void run() {

    }

    public int lend(int clientNeed){
        System.out.println("Client needs: " + clientNeed);
        capital -= clientNeed;
        System.out.println("Banker capital after lending: " + capital);
        return clientNeed;
    }

    public void getBack(int clientMax){
        capital += clientMax;
    }
}
