public class Parser {

    String time;
    String desc;
    boolean timing;
    boolean firstInDescription;
    boolean firstInTime;
    Task task = null;
    static int count = 0;

    public Parser(){};

    public Command process(String line) {
        String[] commands = line.split(" ");
        String first = commands[0];
        try {
            switch (first) {
                case "bye":
                    return new Command(CommandType.EXIT);
                case "list":
                    return new Command(CommandType.PRINTLIST);
                case "todo":
                case "deadline":
                case "event":
                    return new Command(CommandType.ADD, line);
                case "done":
                   return new Command(CommandType.DONE, line);
                case "delete":
                    return new Command(CommandType.DELETE, line);
                default:
                    throw new InvalidCommandException();
            }
        } catch (DukeException e) {
            e.printError();
        }
        return new Command();
    }

    public Task createTask(String line) {
        String[] description = line.split(" ");
        String eventType = description[0];
        try {
            if (description.length <= 1) {
                throw new MissingInputException(eventType);
            }
        } catch (MissingInputException e) {
            e.printError();
        }
        count++;
        return createNewTask(count, eventType, description);
    }

    public int getTaskNo(String line) {
        String[] description = line.split(" ");
        String eventType = description[0];
        try {
            if (description.length <= 1) {
                throw new MissingInputException(eventType);
            }
        } catch (MissingInputException e) {

        }
        return Integer.parseInt(description[1]);
    }

    public Task createNewTask(int taskNo, String eventType, String...arr) {
        firstInDescription = true;
        Date date = null;
        Time time = null;
        for (int i = 1; i < arr.length; i++) {
            if (firstInDescription) {
                desc += arr[i];
                firstInDescription = false;
            } else if (arr[i].startsWith("/")) {
                break;
            } else {
                desc += " " + arr[i];
            }
        }
        switch (eventType) {
            case "todo":
                task = new Todo(taskNo, desc, "T");
                break;
            case "event":
                date = Date.processDate(arr[arr.length-2]);
                time = Time.processTime(arr[arr.length-1]);
                task = new Event(taskNo, desc, date, time, "E");
                break;
            case "deadline":
                date = Date.processDate(arr[arr.length-2]);
                time = Time.processTime(arr[arr.length-1]);
                task = new Deadline(taskNo, desc, date, time, "D");
                break;
        }
        return task;
    }


}