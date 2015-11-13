package pl.lodz.p.it.ftims;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;

/**
 * Created by Piotr Grzelak on 2015-11-06.
 */
public class Task implements Serializable {

    public enum TaskType implements Serializable {
        INSERT, REMOVE;
    }

    private String message;

    private int index;

    private TaskType type;

    public Task(String message, int index, TaskType type) {
        this.message = message;
        this.index = index;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public int getIndex() {
        return index;
    }

    public TaskType getType() {
        return type;
    }
}
