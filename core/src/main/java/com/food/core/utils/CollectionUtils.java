package com.food.core.utils;

import java.util.Arrays;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class CollectionUtils {

    @SafeVarargs
    public static <T> Set<T> asSet(T...objs) {
        return Arrays.stream(objs).collect(toSet());
    }

}
