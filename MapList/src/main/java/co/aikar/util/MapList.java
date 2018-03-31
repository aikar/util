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

public class MapList <K, V> implements DelegatingMap<K, List<V>>, Iterable<MapList.Entry<K, V>> {
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

    @NotNull
    public List<V> get(Object key) {
        //noinspection unchecked
        return backingMap.computeIfAbsent((K) key, listSupplier);
    }

    public boolean has(K key) {
        List<V> values = backingMap.get(key);
        return values != null && !values.isEmpty();
    }

    public int size(K key) {
        List<V> values = backingMap.get(key);
        return values != null ? values.size() : 0;
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

    public interface ForEachEntry<K, V>  {
        void accept(K key, V val);
    }

    public void forEachEntry(ForEachEntry<K, V> action) {
        backingMap.forEach((key, value) -> value.forEach(v -> action.accept(key, v)));
    }
    public void forEach(K key, ForEachEntry<K, V> action) {
        List<V> values = backingMap.get(key);
        if (values == null) {
            return;
        }
        values.forEach(v -> action.accept(key, v));
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

    public interface Entry <K, V> {
        K getKey();
        V getValue();
    }

    public Iterator<Entry<K, V>> iterator() {
        return new EntryIterator();
    }

    private class EntryIterator implements Iterator<Entry<K, V>> {

        private final Iterator<Map.Entry<K, List<V>>> iter;
        private K curKey;
        private Iterator<V> listIter;
        private Entry<K, V> next;

        EntryIterator() {
            this.iter = MapList.this.backingMap.entrySet().iterator();
            this.listIter = null;
            this.next = getNext();
        }

        private Entry<K, V> getNext() {
            if (listIter == null || !listIter.hasNext()) {
                if (!iter.hasNext()) {
                    return null;
                }
                Map.Entry<K, List<V>> next = iter.next();
                curKey = next.getKey();
                listIter = next.getValue().iterator();
            }
            if (!listIter.hasNext()) {
                return null;
            }
            V val = listIter.next();
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
