package org.prepro;

import java.lang.reflect.Method;

public class ExecutionTime {
    /**
     * Return a long that represent in nanoseconds the time of execution of a method
     * @param object
     * @param method
     * @param args
     * @return a long that represent the measured time of a method
     */
    static public long measureNanosecond(Object object, Method method, Object... args) {
        long startTime = System.nanoTime();

        try {
            method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return System.nanoTime() - startTime;
    }

    /**
     *
     * Return a long that represent in milliseconds the time of execution of a method
     * @param object
     * @param method
     * @param args
     * @return a long that represent the measured time of a method
     */
    static public long measureMillisecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000;
    }

    /**
     * Return a long that represent in seconds the time of execution of a method
     * @param object
     * @param method
     * @param args
     * @return a long that represent the measured time of a method
     */
    static public long measureSecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000_000;
    }
}
