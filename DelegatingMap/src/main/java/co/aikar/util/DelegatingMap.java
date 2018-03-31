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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface DelegatingMap <K, V> extends Map<K, V> {
    Map<K, V> delegate();

    @Override
    default int size() {
        return delegate().size();
    }

    @Override
    default boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override
    default boolean containsKey(Object key) {
        return delegate().containsKey(key);
    }

    @Override
    default boolean containsValue(Object value) {
        return delegate().containsValue(value);
    }

    @Override
    default V get(Object key) {
        return delegate().get(key);
    }

    @Nullable
    @Override
    default V put(K key, V value) {
        return delegate().put(key, value);
    }

    @Override
    default V remove(Object key) {
        return delegate().remove(key);
    }

    @Override
    default void putAll(@NotNull Map<? extends K, ? extends V> m) {
        delegate().putAll(m);
    }

    @Override
    default void clear() {
        delegate().clear();
    }

    @NotNull
    @Override
    default Set<K> keySet() {
        return delegate().keySet();
    }

    @NotNull
    @Override
    default Collection<V> values() {
        return delegate().values();
    }

    @NotNull
    @Override
    default Set<Entry<K, V>> entrySet() {
        return delegate().entrySet();
    }

    @Override
    default V getOrDefault(Object key, V defaultValue) {
        return delegate().getOrDefault(key, defaultValue);
    }

    @Override
    default void forEach(BiConsumer<? super K, ? super V> action) {
        delegate().forEach(action);
    }

    @Override
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        delegate().replaceAll(function);
    }

    @Nullable
    @Override
    default V putIfAbsent(K key, V value) {
        return delegate().putIfAbsent(key, value);
    }

    @Override
    default boolean remove(Object key, Object value) {
        return delegate().remove(key, value);
    }

    @Override
    default boolean replace(K key, V oldValue, V newValue) {
        return delegate().replace(key, oldValue, newValue);
    }

    @Nullable
    @Override
    default V replace(K key, V value) {
        return delegate().replace(key, value);
    }

    @Override
    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return delegate().computeIfAbsent(key, mappingFunction);
    }

    @Override
    default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return delegate().computeIfPresent(key, remappingFunction);
    }

    @Override
    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return delegate().compute(key, remappingFunction);
    }

    @Override
    default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return delegate().merge(key, value, remappingFunction);
    }
}
