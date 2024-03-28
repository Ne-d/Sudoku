import org.prepro.ExecutionTime;

/**
 * Tests the ExecutionTime class.
 */
public class TestExecutionTime {
    /**
     * Test if the functions return the good time in 3 different formats
     *
     * @param args The arguments given to the program. They do literally nothing.
     */
    public static void main(String[] args) {
        try {
            System.out.println(
                    ExecutionTime.measureNanosecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                            " ns");

            System.out.println(
                    ExecutionTime.measureMillisecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                            " ms");

            System.out.println(
                    ExecutionTime.measureSecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                            " s");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Waits one second. Simple as that.
     */
    public static void waitOneSecond() {
        final long INTERVAL = 1_000_000_000;

        long start = System.nanoTime();
        long end;
        do {
            end = System.nanoTime();
        } while (start + INTERVAL >= end);
    }
}
