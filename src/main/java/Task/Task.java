package Task;

import Exceptions.MissingInputException;

public abstract class Task {

    boolean done;
    private int no;
    String task;
    private String type;

    /**
     * Creates Task with an item number, task description, task type and done status.
     *
     * @param num Task number in the list.
     * @param task Task description
     * @param type Task type (can be Todo, Event or Deadline).
     * @param done Indicates whether task is done.
     */
    Task(int num, String task, String type, boolean done) throws MissingInputException {
        this.no = num;
        this.done = done;
        this.task = task;
        this.type = type;
        if (task.length() < 1) {
            throw new MissingInputException(type);
        }
    }

    /**
     * Creates Task with an item number, task description, task type and done status.
     *
     * Done status is set as not done by default.
     * @param num Task number in the list.
     * @param task Task description
     * @param type Task type (can be Todo, Event or Deadline).
     */
    Task(int num, String task, String type) throws MissingInputException {
        this.no = num;
        this.done = false;
        this.task = task;
        this.type = type;
        if (task.length() < 1) {
            throw new MissingInputException(type);
        }
    }

    /**
     * Sets task as done.
     */
    void setDone() {
        this.done = true;
    }

    /**
     * @return task description
     */
    String getTaskInfo() {
        return this.task;
    }

    /**
     * Formats task to be written in given file.
     *
     * @return formatted string for writing in file.
     */
    public abstract String fileFormat();

}
