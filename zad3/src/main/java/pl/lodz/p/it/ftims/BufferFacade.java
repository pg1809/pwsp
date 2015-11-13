package pl.lodz.p.it.ftims;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.Condition;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class BufferFacade {

    private String host = "localhost";

    private int port;

    public BufferFacade(int port) {
        this.port = port;
    }

    public void putMessage(String message, int compartmentIdx) {
        Task task = new Task(message, compartmentIdx, Task.TaskType.INSERT);
        sendTask(task);
    }

    public void removeMessage(int compartmentIdx)  {
        Task task = new Task(null, compartmentIdx, Task.TaskType.REMOVE);
        sendTask(task);
    }

    private void sendTask(Task task) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
            outputStream.writeObject(task);
            boolean result = inputStream.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
