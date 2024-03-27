import org.prepro.ExecutionTime;

import java.lang.reflect.InvocationTargetException;

public class TestExecutionTime {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.out.println(
                ExecutionTime.measureNanosecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                        " ns");

        System.out.println(
                ExecutionTime.measureMillisecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                        " ms");

        System.out.println(
                ExecutionTime.measureSecond(null, TestExecutionTime.class.getMethod("waitOneSecond")) +
                        " s");
    }

    public static void waitOneSecond() {
        final long INTERVAL = 1_000_000_000;

        long start = System.nanoTime();
        long end = 0;
        do {
            end = System.nanoTime();
        } while (start + INTERVAL >= end);
    }
}
