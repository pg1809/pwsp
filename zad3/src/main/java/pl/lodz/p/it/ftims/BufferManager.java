package pl.lodz.p.it.ftims;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class BufferManager extends Thread {

    private String[] buffer;

    private ServerSocket selectSocket;

    public BufferManager(int bufferSize, int port) throws IOException {
        super();
        buffer = new String[bufferSize];
        selectSocket = new ServerSocket(port);
    }

    @Override
    public void run() {

        while (true) {
            try (Socket socket = selectSocket.accept();
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());) {
                Task task = (Task) inputStream.readObject();

                if (task.getType() == Task.TaskType.INSERT) {
                    putMessage(task.getMessage(), task.getIndex());
                } else if (task.getType() == Task.TaskType.REMOVE) {
                    removeMessage(task.getIndex());
                }
                outputStream.writeBoolean(true);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void putMessage(String message, int compartmentIdx) {
        if (buffer[compartmentIdx] == null) {
            buffer[compartmentIdx] = message;
            System.out.println("Wstawiono wiadomosc: " + message + " do skrytki: " + compartmentIdx);
        } else {
            System.out.println("Skrytka: " + compartmentIdx + " jest pelna. Nie wstawiono wiadomosci.");
        }
    }

    private void removeMessage(int compartmentIdx) {
        if (buffer[compartmentIdx] != null) {
            System.out.println("Usuwanie wiadomosci: " + buffer[compartmentIdx] + " ze skrytki: " + compartmentIdx);
            buffer[compartmentIdx] = null;
        } else {
            System.out.println("Skrytka: " + compartmentIdx + " jest pusta. Nie ma co usuwac.");
        }
    }
}
