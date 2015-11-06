package pl.lodz.p.it.ftims;

import java.util.concurrent.locks.Condition;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class Task {

    public enum TaskType {
        INSERT, REMOVE;
    }

    private String message;

    private int index;

    private TaskType type;

    private Condition taskCompleted;

    public Task(String message, int index, TaskType type, Condition taskCompleted) {
        this.message = message;
        this.index = index;
        this.type = type;
        this.taskCompleted = taskCompleted;
    }

    public String getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }

    public Condition getTaskCompleted() {
        return taskCompleted;
    }

    public TaskType getType() {
        return type;
    }
}
