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

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface DelegatingSet <T> extends Set<T> {

    Set<T> delegate();

    @Override
    default int size() {
        return delegate().size();
    }

    @Override
    default boolean isEmpty() {
        return delegate().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return delegate().contains(o);
    }

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return delegate().iterator();
    }

    @NotNull
    @Override
    default Object[] toArray() {
        return delegate().toArray();
    }

    @NotNull
    @Override
    default <T1> T1[] toArray(@NotNull T1[] a) {
        return delegate().toArray(a);
    }

    @Override
    default boolean add(T t) {
        return delegate().add(t);
    }

    @Override
    default boolean remove(Object o) {
        return delegate().remove(o);
    }

    @Override
    default boolean containsAll(@NotNull Collection<?> c) {
        return delegate().containsAll(c);
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends T> c) {
        return delegate().addAll(c);
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c) {
        return delegate().retainAll(c);
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c) {
        return delegate().removeAll(c);
    }

    @Override
    default void clear() {
        delegate().clear();
    }

    @Override
    default Spliterator<T> spliterator() {
        return delegate().spliterator();
    }

    @Override
    default boolean removeIf(Predicate<? super T> filter) {
        return delegate().removeIf(filter);
    }

    @Override
    default Stream<T> stream() {
        return delegate().stream();
    }

    @Override
    default Stream<T> parallelStream() {
        return delegate().parallelStream();
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        delegate().forEach(action);
    }
}
