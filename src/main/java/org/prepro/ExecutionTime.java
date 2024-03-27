package org.prepro;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExecutionTime {
    static public long measureNanosecond(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        long startTime = System.nanoTime();
        method.invoke(object, args);

        return System.nanoTime() - startTime;
    }

    static public long measureMillisecond(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        return measureNanosecond(object, method, args) / 1_000_000;
    }

    static public long measureSecond(Object object, Method method, Object... args) throws InvocationTargetException, IllegalAccessException {
        return measureNanosecond(object, method, args) / 1_000_000_000;
    }
}
