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

    private Condition tasksToProcess = lock.newCondition();

    private Queue<Task> tasksQueue = new LinkedList<>();

    public BufferManager(int bufferSize) {
        super();
        buffer = new String[bufferSize];
    }

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                if (tasksQueue.isEmpty()) {
                    tasksToProcess.await();
                }
                Task task = tasksQueue.poll();
                lock.unlock();

                if (task.getType() == Task.TaskType.INSERT) {
                    putMessage(task.getMessage(), task.getIndex());
                } else {
                    removeMessage(task.getIndex());
                }

                lock.lock();
                task.getTaskCompleted().signalAll();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void processTask(Task task) {
        try {
            lock.lock();
            tasksQueue.add(task);
            tasksToProcess.signalAll();
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
