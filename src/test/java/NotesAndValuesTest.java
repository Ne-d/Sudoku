import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.prepro.model.Notes;

/**
 * A class to test the Notes class, including the way it handles values.
 */
public class NotesAndValuesTest {

    /**
     * Tests the getNumber method.
     */
    @Test
    public void testNotesGetNumber() {
        Notes notes = new Notes(9);

        Assertions.assertEquals(9, notes.getNumber());

        notes.delete(1);
        notes.delete(2);
        notes.delete(3);
        notes.delete(4);
        notes.delete(5);
        Assertions.assertEquals(4, notes.getNumber());

        notes.delete(6);
        notes.delete(7);
        notes.delete(8);
        Assertions.assertEquals(1, notes.getNumber());

        notes.delete(9);
        Assertions.assertEquals(0, notes.getNumber());
    }

    /**
     * Tests the getUniqueNote method for the value 9.
     */
    @Test
    public void testUniqueNote9() {
        Notes notes = new Notes(9);

        Assertions.assertEquals(0, notes.getUniqueNote());

        notes.delete(1);
        notes.delete(2);
        notes.delete(3);
        notes.delete(4);
        notes.delete(5);
        notes.delete(6);
        notes.delete(7);
        notes.delete(8);
        Assertions.assertEquals(9, notes.getUniqueNote());

        notes.delete(9);
        Assertions.assertEquals(0, notes.getUniqueNote());
    }

    /**
     * Tests the getUniqueNote method for the value 1.
     */
    @Test
    public void testUniqueNote1() {
        Notes notes = new Notes(9);

        Assertions.assertEquals(0, notes.getUniqueNote());

        notes.delete(2);
        notes.delete(3);
        notes.delete(4);
        notes.delete(5);
        notes.delete(6);
        notes.delete(7);
        notes.delete(8);
        notes.delete(9);
        Assertions.assertEquals(1, notes.getUniqueNote());

        notes.delete(1);
        Assertions.assertEquals(0, notes.getUniqueNote());
    }

    /**
     * Tests the getUniqueNote method for the value 5.
     */
    @Test
    public void testUniqueNote5() {
        Notes notes = new Notes(9);

        Assertions.assertEquals(0, notes.getUniqueNote());

        notes.delete(1);
        notes.delete(2);
        notes.delete(3);
        notes.delete(4);

        notes.delete(6);
        notes.delete(7);
        notes.delete(8);
        notes.delete(9);
        Assertions.assertEquals(5, notes.getUniqueNote());

        notes.delete(5);
        Assertions.assertEquals(0, notes.getUniqueNote());
    }

    /**
     * Tests the add, delete and isPresent methods.
     */
    @Test
    public void testNoteAddDeleteIsPresent() {
        int size = 9;
        Notes n = new Notes(size);

        for (int i = 1; i <= size; i++) {
            Assertions.assertTrue(n.isPresent(i));
        }

        for (int i = 1; i <= size; i++) {
            n.delete(i);
            Assertions.assertFalse(n.isPresent(i));
        }
    }
}
