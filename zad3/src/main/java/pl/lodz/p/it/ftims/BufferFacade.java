package pl.lodz.p.it.ftims;

import java.util.concurrent.locks.Condition;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class BufferFacade {

    private Condition taskCompleted;

    private BufferManager bufferManager;

    public BufferFacade(BufferManager bufferManager) {
        this.bufferManager = bufferManager;
        taskCompleted = bufferManager.getCondition();
    }

    public void putMessage(String message, int compartmentIdx) {
        Task task = new Task(message, compartmentIdx, Task.TaskType.INSERT, taskCompleted);
        bufferManager.processTask(task);
    }

    public void removeMessage(int compartmentIdx) {
        Task task = new Task(null, compartmentIdx, Task.TaskType.REMOVE, taskCompleted);
        bufferManager.processTask(task);
    }
}
