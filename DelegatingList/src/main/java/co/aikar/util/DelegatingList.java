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
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface DelegatingList <T> extends List<T> {
    
    List<T> delegate(boolean readOnly);

    @Override
    default int size() {
        return delegate(true).size();
    }

    @Override
    default boolean isEmpty() {
        return delegate(true).isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return delegate(true).contains(o);
    }

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return delegate(false).iterator();
    }

    @NotNull
    @Override
    default Object[] toArray() {
        return delegate(true).toArray();
    }

    @NotNull
    @Override
    default <T1> T1[] toArray(@NotNull T1[] a) {
        return delegate(true).toArray(a);
    }

    @Override
    default boolean add(T t) {
        return delegate(false).add(t);
    }

    @Override
    default boolean remove(Object o) {
        return delegate(false).remove(o);
    }

    @Override
    default boolean containsAll(@NotNull Collection<?> c) {
        return delegate(false).containsAll(c);
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends T> c) {
        return delegate(false).addAll(c);
    }

    @Override
    default boolean addAll(int index, @NotNull Collection<? extends T> c) {
        return delegate(false).addAll(index, c);
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c) {
        return delegate(false).removeAll(c);
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c) {
        return delegate(false).retainAll(c);
    }

    @Override
    default void replaceAll(UnaryOperator<T> operator) {
        delegate(false).replaceAll(operator);
    }

    @Override
    default void sort(Comparator<? super T> c) {
        delegate(false).sort(c);
    }

    @Override
    default void clear() {
        delegate(false).clear();
    }

    @Override
    default T get(int index) {
        return delegate(true).get(index);
    }

    @Override
    default T set(int index, T element) {
        return delegate(false).set(index, element);
    }

    @Override
    default void add(int index, T element) {
        delegate(false).add(index, element);
    }

    @Override
    default T remove(int index) {
        return delegate(false).remove(index);
    }

    @Override
    default int indexOf(Object o) {
        return delegate(true).indexOf(o);
    }

    @Override
    default int lastIndexOf(Object o) {
        return delegate(true).lastIndexOf(o);
    }

    @NotNull
    @Override
    default ListIterator<T> listIterator() {
        return delegate(false).listIterator();
    }

    @NotNull
    @Override
    default ListIterator<T> listIterator(int index) {
        return delegate(false).listIterator(index);
    }

    @NotNull
    @Override
    default List<T> subList(int fromIndex, int toIndex) {
        return delegate(false).subList(fromIndex, toIndex);
    }

    @Override
    default Spliterator<T> spliterator() {
        return delegate(false).spliterator();
    }

    @Override
    default boolean removeIf(Predicate<? super T> filter) {
        return delegate(false).removeIf(filter);
    }

    @Override
    default Stream<T> stream() {
        return delegate(false).stream();
    }

    @Override
    default Stream<T> parallelStream() {
        return delegate(false).parallelStream();
    }

    @Override
    default void forEach(Consumer<? super T> action) {
        delegate(true).forEach(action);
    }
}
