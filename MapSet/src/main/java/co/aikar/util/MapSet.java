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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MapSet <K, V> implements DelegatingMap<K, Set<V>>, Iterable<MapSet.Entry<K, V>> {

    private final Map<K, Set<V>> backingMap;
    private final Function<K, Set<V>> setSupplier;

    public MapSet() {
        this((Supplier<Set<V>>) HashSet::new);
    }
    public MapSet(Supplier<Set<V>> setSupplier) {
        this(new HashMap<>(), k -> setSupplier.get());
    }
    public MapSet(Function<K, Set<V>> setSupplier) {
        this(new HashMap<>(), setSupplier);
    }
    public MapSet(Map<K, Set<V>> backingMap, Supplier<Set<V>> setSupplier) {
        this(backingMap, k -> setSupplier.get());
    }
    public MapSet(Map<K, Set<V>> backingMap, Function<K, Set<V>> setSupplier) {
        this.backingMap = backingMap;
        this.setSupplier = setSupplier;
    }
    @Override
    public Map<K, Set<V>> delegate(boolean isReadOnly) {
        return backingMap;
    }

    @Nullable
    public Set<V> add(K key, V value) {
        get(key).add(value);
        return null;
    }

    @NotNull
    public Set<V> get(Object key) {
        //noinspection unchecked
        return backingMap.computeIfAbsent((K) key, setSupplier);
    }

    public boolean has(K key) {
        Set<V> values = backingMap.get(key);
        return values != null && !values.isEmpty();
    }

    public int size(K key) {
        Set<V> values = backingMap.get(key);
        return values != null ? values.size() : 0;
    }


    @Nullable
    @Override
    public Set<V> put(K key, Set<V> value) {
        Set<V> existing = get(key);
        final Set<V> prev = new HashSet<>(existing);
        existing.addAll(value);
        return prev;
    }

    @Override
    public boolean remove(Object key, Object value) {
        Set<V> set = get(key);
        boolean removed = set.removeIf(it -> Objects.equals(it, value));
        if (set.isEmpty()) {
            backingMap.remove(key);
        }
        return removed;
    }

    public @NotNull Set<V> allValues() {
        Set<V> values = new HashSet<>();
        for (Set<V> entries : backingMap.values()) {
            values.addAll(entries);
        }

        return values;
    }

    public interface ForEach <K, V>  {
        void process(K key, V val);
    }

    public void forEach(ForEach<K, V> action) {
        backingMap.forEach((key, value) -> value.forEach(v -> action.process(key, v)));
    }

    @Override
    public boolean containsValue(Object value) {
        for (Set<V> values : backingMap.values()) {
            if (values.contains(value)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public void putAll(@NotNull Map<? extends K, ? extends Set<V>> m) {
        m.forEach((k, values) -> get(k).addAll(values));
    }


    public interface Entry <K, V> {
        K getKey();
        V getValue();
    }

    public Iterator<Entry<K, V>> iterator() {
        return new EntryIterator();
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {

        private final Iterator<Map.Entry<K, Set<V>>> iter;
        private K curKey;
        private Iterator<V> setIter;
        private Entry<K, V> next;

        EntryIterator() {
            this.iter = MapSet.this.backingMap.entrySet().iterator();
            this.setIter = null;
            this.next = getNext();
        }

        private Entry<K, V> getNext() {
            if (setIter == null || !setIter.hasNext()) {
                if (!iter.hasNext()) {
                    return null;
                }
                Map.Entry<K, Set<V>> next = iter.next();
                curKey = next.getKey();
                setIter = next.getValue().iterator();
            }
            if (!setIter.hasNext()) {
                return null;
            }
            V val = setIter.next();
            return new Entry<K, V>() {
                @Override
                public K getKey() {
                    return curKey;
                }

                @Override
                public V getValue() {
                    return val;
                }
            };
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Entry<K, V> next() {
            Entry<K, V> prev = this.next;
            next = getNext();
            return prev;
        }
    }

    @Nullable
    @Override
    public Set<V> putIfAbsent(K key, Set<V> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super Set<V>, ? extends Set<V>> function) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean replace(K key, Set<V> oldValue, Set<V> newValue) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public Set<V> replace(K key, Set<V> value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<V> computeIfAbsent(K key, Function<? super K, ? extends Set<V>> mappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<V> computeIfPresent(K key, BiFunction<? super K, ? super Set<V>, ? extends Set<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<V> compute(K key, BiFunction<? super K, ? super Set<V>, ? extends Set<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<V> merge(K key, Set<V> value, BiFunction<? super Set<V>, ? super Set<V>, ? extends Set<V>> remappingFunction) {
        throw new UnsupportedOperationException();
    }
}
