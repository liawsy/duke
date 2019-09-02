package UI;

import Data.Command;
import Data.Parser;
import Data.Storage;
import Exceptions.InvalidCommandException;
import Exceptions.InvalidInputException;
import Exceptions.MissingInputException;
import Task.Task;
import Task.TaskList;

import java.io.IOException;
import java.util.Scanner;

public class UI {

    private Parser parser = new Parser();
    private TaskList tasks = new TaskList();
    private boolean isExit = false;
    private Storage storage;
    private Scanner sc = new Scanner(System.in);

    /**
     * Processes commands from the user to interact with given file.
     * @param fileInput String that indicates file path.
     */
    public UI(String fileInput) {
        storage = new Storage(fileInput);
    }

    /**
     * Exits program in Duke by changing boolean.
     */
    private void exit() {
        isExit = true;
    }

    /**
     * @return boolean to indicate whether program should be exited.
     */
    public boolean isExit() {
        return isExit;
    }

    /**
     * Processes file in storage and adds tasks to program.
     */
    public void processFile() {
        for (Task task: storage.loadTasks().getTaskList()) {
            tasks.loadTask(task);
        }
    }

    /**
     * Processes input from the Command Line made by user.
     * This makes changes to the program's task list and file.
     */
    public void processInput() throws InvalidCommandException, InvalidInputException {
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            processCommand(parser.process(line));
        }
    }

    /**
     * Intermediate method to process command.
     * This updates and writes the file.
     * @param command command created from parser.
     */
    private void processCommand(Command command) throws InvalidInputException{
            try {
                switch (command.type) {
                case EXIT:
                    exit();
                    break;
                case PRINTLIST:
                    tasks.printList();
                    break;
                case ADD:
                    tasks.addTask(parser.createTask(command));
                    break;
                case DELETE:
                    tasks.deleteTask(parser.getTaskNo(command));
                    break;
                case DONE:
                    tasks.setDone(parser.getTaskNo(command));
                    break;
                case FIND:
                    tasks.findMatchingTasks(parser.getKeyword(command));
                    break;
                default:
                    throw new InvalidCommandException();
                }
            } catch (InvalidCommandException| MissingInputException e) {
                e.printError();
            }
            try {
                storage.updateTaskList(tasks);
            } catch (IOException e) {
                System.out.println("Something went wrong!");
            }

        }
    }