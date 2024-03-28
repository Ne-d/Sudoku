package org.prepro;

import java.lang.reflect.Method;

/**
 * A class to measure the time a method takes to run.
 */
public class ExecutionTime {
    /**
     * Measures the time a method takes to run, in nanoseconds.
     *
     * @param object The object to run the method on (can be null).
     * @param method The method to run.
     * @param args   The arguments to give to the method.
     * @return The amount of nanoseconds the method took to run.
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
     * Measures the time a method takes to run, in milliseconds.
     *
     * @param object The object to run the method on (can be null).
     * @param method The method to run.
     * @param args   The arguments to give to the method.
     * @return The amount of milliseconds the method took to run.
     */
    static public long measureMillisecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000;
    }

    /**
     * Measures the time a method takes to run, in seconds.
     *
     * @param object The object to run the method on (can be null).
     * @param method The method to run.
     * @param args   The arguments to give to the method.
     * @return The amount of seconds the method took to run.
     */
    static public long measureSecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000_000;
    }
}
