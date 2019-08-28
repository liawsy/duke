import duke.Date;
import duke.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DukeTest {

    @Test
    void testDate() {
        Date date = new Date(15, 7, 2019);
        assertEquals("15/07/2019", date.toString());
    }

   @Test
   void testTodoFormat() {
        Todo todo = new Todo(1, "read book", "T");
        assertEquals("[T][✗] read book", todo.toString());
   }
}