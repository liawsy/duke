import java.util.Scanner;

public class Duke {
    static Task[] arr = new Task[100];
    static int count = 1;

    public static void main(String[] args) {
        Formatter formatter = new Formatter();
        MessageGenerator greeter = new MessageGenerator();
        Storage storage = new Storage();
        Parser parser = new Parser();
        greeter.greet();
        Scanner sc = new Scanner(System.in);
        loop:
        while (sc.hasNext()) {
            String first = sc.next();
            try {
                switch (first) {
                    case "bye":
                        greeter.bye();
                        break loop;
                    case "list":
                        storage.printList();
                        break;
                    case "done":
                        int number = sc.nextInt();
                        storage.setDone(number);
                        break;
                    case "todo":
                    case "deadline":
                    case "event":
                        String line = sc.nextLine();
                        String[] description = line.split(" ");
                        try {
                            if (description.length <= 1) {
                                throw new DukeException(ErrorType.MISSING, first);
                            }
                            storage.addTask(parser.createNewTask(count, first, description));
                            count++;
                        } catch (DukeException e) {
                            e.printError();
                        }
                        break;
                    case "delete":
                        int listNo = sc.nextInt();
                        storage.deleteTask(listNo);
                        break;
                    default:
                        throw new DukeException(ErrorType.COMMAND);
                }
            } catch (DukeException e) {
                e.printError();
            }
        }

        }
}

