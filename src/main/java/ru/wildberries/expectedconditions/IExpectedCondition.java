package ru.wildberries.expectedconditions;

import java.util.function.Function;

public interface IExpectedCondition<K, V> extends Function<K, V> {

    boolean isTrue(K k);
}