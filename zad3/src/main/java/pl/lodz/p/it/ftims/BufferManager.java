package pl.lodz.p.it.ftims;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class BufferManager extends Thread {

    private String[] buffer;

    private Lock lock = new ReentrantLock();

    private Queue<Task> tasksQueue = new LinkedList<Task>();

    public BufferManager(int bufferSize) {
        super();
        buffer = new String[bufferSize];
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            if (tasksQueue.isEmpty()) {
                lock.unlock();
                continue;
            }
            Task task = tasksQueue.poll();
            lock.unlock();

            if (task.getType() == Task.TaskType.INSERT) {
                putMessage(task.getMessage(), task.getIndex());
            } else {
                removeMessage(task.getIndex());
            }

            lock.lock();
            task.getTaskCompleted().signal();
            lock.unlock();
        }
    }

    public void processTask(Task task) {
        try {
            lock.lock();
            tasksQueue.add(task);
            task.getTaskCompleted().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Condition getCondition() {
        return lock.newCondition();
    }

    private void putMessage(String message, int compartmentIdx) {
        if (buffer[compartmentIdx] == null) {
            buffer[compartmentIdx] = message;
            System.out.println("Wstawiono wiadomosc: " + message);
        }
    }

    private void removeMessage(int compartmentIdx) {
        if (buffer[compartmentIdx] != null) {
            System.out.println("Usuwanie wiadomosci: " + buffer[compartmentIdx]);
            buffer[compartmentIdx] = null;
        }
    }
}
