package com.chanhonlun.basecms.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtil {

    @SafeVarargs
    public static <T> List<T> getIntersection(Class<T> classOfT, List<T>... arrayOfList) {
        return getIntersection(classOfT, Arrays.asList(arrayOfList));
    }

    public static <T> List<T> getIntersection(Class<T> classOfT, List<List<T>> listOfList) {

        if (listOfList.isEmpty()) {
            return Collections.emptyList();
        }

        List<T> intersection = listOfList.get(0);

        for (List<T> list : listOfList) {
            // find the intersection of two list
            // intersect itself and rest of the list and store the result back to itself
            // ref: https://stackoverflow.com/questions/31683375/java-8-lambda-intersection-of-two-lists#answer-31683592
            intersection = intersection.stream().filter(list::contains).collect(Collectors.toList());
        }

        return intersection;
    }
}
