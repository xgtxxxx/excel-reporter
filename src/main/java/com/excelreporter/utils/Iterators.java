package com.excelreporter.utils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Iterators {

    /**
     * forEach with index for java 8 lambda
     *
     * @param elements elements to be consume
     * @param action consumer action
     * @param <E> element type
     */
    public static <E> void forEachWithIndex(
        final Iterable<? extends E> elements,
        final BiConsumer<Integer, ? super E> action) {

        Objects.requireNonNull(elements);
        Objects.requireNonNull(action);

        int index = 0;
        for (final E element : elements) {
            action.accept(index, element);
            index++;
        }
    }

    /**
     * Map an Iterable object to Stream with index
     *
     * @param elements the elements to be map
     * @param mapper mapper function (index, element)
     * @param <E> element type in Iterable
     * @param <R> result Stream type
     * @return
     */
    public static <E, R> Stream<R> mapWithIndex(
        final Iterable<? extends E> elements,
        final BiFunction<Integer, ? super E, R> mapper) {

        Objects.requireNonNull(elements);
        Objects.requireNonNull(mapper);

        int index = 0;
        final Stream.Builder<R> builder = Stream.builder();
        for (final E element : elements) {
            builder.accept(mapper.apply(index, element));
            index++;
        }

        return builder.build();
    }

    /**
     * Map an Iterable object to List with index
     *
     * @param elements the elements to be map
     * @param mapper mapper function (index, element)
     * @param <E> element type in Iterable
     * @param <R> result List type
     * @return
     */
    public static <E, R> List<R> mapToListWithIndex(
        final Iterable<? extends E> elements,
        final BiFunction<Integer, ? super E, R> mapper) {

        return mapWithIndex(elements, mapper)
            .collect(Collectors.toList());
    }

}