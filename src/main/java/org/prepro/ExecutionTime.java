package org.prepro;

import java.lang.reflect.Method;

public class ExecutionTime {
    static public long measureNanosecond(Object object, Method method, Object... args) {
        long startTime = System.nanoTime();

        try {
            method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return System.nanoTime() - startTime;
    }

    static public long measureMillisecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000;
    }

    static public long measureSecond(Object object, Method method, Object... args) {
        return measureNanosecond(object, method, args) / 1_000_000_000;
    }
}
