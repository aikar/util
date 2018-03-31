/*
 * Copyright (c) 2016-2018 Daniel Ennis (Aikar) - MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package co.aikar.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapList <K, V> implements DelegatingMap<K, List<V>> {
    private final Map<K, List<V>> backingMap;
    private final Function<K, List<V>> listSupplier;

    public MapList() {
        this((Supplier<List<V>>) ArrayList::new);
    }
    public MapList(Supplier<List<V>> listSupplier) {
        this(new HashMap<>(), k -> listSupplier.get());
    }
    public MapList(Function<K, List<V>> listSupplier) {
        this(new HashMap<>(), listSupplier);
    }
    public MapList(Map<K, List<V>> backingMap, Supplier<List<V>> listSupplier) {
        this(backingMap, k -> listSupplier.get());
    }
    public MapList(Map<K, List<V>> backingMap, Function<K, List<V>> listSupplier) {
        this.backingMap = backingMap;
        this.listSupplier = listSupplier;
    }
    @Override
    public Map<K, List<V>> delegate(boolean isReadOnly) {
        return backingMap;
    }

    @Nullable
    public List<V> add(K key, V value) {
        get(key).add(value);
        return null;
    }

    public List<V> get(Object key) {
        //noinspection unchecked
        return backingMap.computeIfAbsent((K) key, listSupplier);
    }

    @Nullable
    @Override
    public List<V> put(K key, List<V> value) {
        List<V> existing = get(key);
        final List<V> prev = new ArrayList<>(existing);
        existing.addAll(value);
        return prev;
    }

    @Override
    public boolean remove(Object key, Object value) {
        List<V> list = get(key);
        boolean removed = list.removeIf(it -> Objects.equals(it, value));
        if (list.isEmpty()) {
            backingMap.remove(key);
        }
        return removed;
    }

    public interface ForEach <K, V>  {
        void accept(K key, V val);
    }

    public void forEach(ForEach<K, V> action) {
        backingMap.forEach((key, value) -> value.forEach(v -> action.accept(key, v)));
    }


    @Override
    public boolean containsValue(Object value) {
        for (List<V> values : backingMap.values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends List<V>> m) {
        m.forEach((k, values) -> get(k).addAll(values));
    }

    public @NotNull List<V> allValues() {
        List<V> all = new ArrayList<>();
        for (List<V> values : this.backingMap.values()) {
            all.addAll(values);
        }
        return all;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super List<V>, ? extends List<V>> function) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public List<V> putIfAbsent(K key, List<V> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, List<V> oldValue, List<V> newValue) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public List<V> replace(K key, List<V> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<V> computeIfAbsent(K key, Function<? super K, ? extends List<V>> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<V> computeIfPresent(K key, BiFunction<? super K, ? super List<V>, ? extends List<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<V> compute(K key, BiFunction<? super K, ? super List<V>, ? extends List<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<V> merge(K key, List<V> value, BiFunction<? super List<V>, ? super List<V>, ? extends List<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }
}
