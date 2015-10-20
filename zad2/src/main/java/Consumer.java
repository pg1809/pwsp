/**
 * Created by Lukasz on 2015-10-20.
 */
public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while(true){
            String message = buffer.fetch();
            if(message != null){
                System.out.println("Pobralem: " + message);
            }
        }
    }
}
