package me.bubbles.murderder.util;

import java.util.Arrays;
import java.util.HashSet;

public class HashSetBuilder<T> {

    private final HashSet<T> hashSet;

    public HashSetBuilder() {
       hashSet = new HashSet<>();
    }

    public HashSetBuilder(HashSet<T> hashSet) {
        this.hashSet=hashSet;
    }

    public HashSetBuilder<T> add(T... items) {
        hashSet.addAll(Arrays.asList(items));
        return this;
    }

    public HashSet<T> get() {
        return hashSet;
    }

}
