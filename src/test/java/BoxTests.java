import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.prepro.model.Box;

public class BoxTests {
    /**
     * Tests that the add, delete and isPresent methods work as expected.
     */
    @Test
    public void testBoxAddDeleteIsPresent() {
        int size = 9;
        Box b = new Box(size);

        for (int i = 1; i <= size; i++) {
            Assertions.assertTrue(b.isNotePresent(i));
        }

        for (int i = 1; i <= size; i++) {
            b.deleteNote(i);
            Assertions.assertFalse(b.isNotePresent(i));
        }
    }

    /**
     * Tests that the getValue and setValue methods work as expected.
     */
    @Test
    public void testGetSetValue() {
        int size = 9;
        Box b = new Box(size);

        for (int i = 0; i <= size; i++) {
            System.out.printf("Setting value to %d.\n", i);
            b.setVal(i);

            System.out.print("Notes: ");
            b.afficheNote();

            Assertions.assertEquals(i, b.getVal());
        }
    }
}
