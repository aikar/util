package co.aikar.util;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Table <R, C, V> {

    private final Map<R, Map<C, V>> backingRowMap;
    private final Supplier<Map<C, V>> columnMapSupplier;

    public Table() {
        this(new HashMap<>(), HashMap::new);
    }

    public Table(Map<R, Map<C, V>> backingRowMap, Supplier<Map<C, V>> columnMapSupplier) {
        this.backingRowMap = backingRowMap;
        this.columnMapSupplier = columnMapSupplier;
    }

    @Nullable
    public Map<C, V> put(R r, C col, V val) {
        return null;
    }


    public interface ForEachStoppableConsumer <R, C, V> {
        boolean accept(R row, C col, V val);
    }
    public interface ForEachConsumer <R, C, V> {
        void accept(R row, C col, V val);
    }

    public void forEach(ForEachConsumer<R, C, V> consumer) {
        for (Map.Entry<R, Map<C, V>> rowEntry : backingRowMap.entrySet()) {
            R row = rowEntry.getKey();
            for (Map.Entry<C, V> colEntry : rowEntry.getValue().entrySet()) {
                C col = colEntry.getKey();
                consumer.accept(row, col, colEntry.getValue());
            }
        }
    }

    public void forEach(ForEachStoppableConsumer<R, C, V> consumer) {
        for (Map.Entry<R, Map<C, V>> rowEntry : backingRowMap.entrySet()) {
            R row = rowEntry.getKey();
            for (Map.Entry<C, V> colEntry : rowEntry.getValue().entrySet()) {
                C col = colEntry.getKey();
                if (!consumer.accept(row, col, colEntry.getValue())) {
                    return;
                }
            }
        }
    }

    public void replaceAll(BiFunction biFunction) {

    }

    public boolean remove(Object o, Object o1) {
        return false;
    }

    @Nullable
    public Object replace(Object o, Object o2) {
        return null;
    }

    public Object computeIfAbsent(Object o, Function function) {
        return null;
    }

    public Object computeIfPresent(Object o, BiFunction biFunction) {
        return null;
    }

    public Object compute(Object o, BiFunction biFunction) {
        return null;
    }

    public Object merge(Object o, Object o2, BiFunction biFunction) {
        return null;
    }

    public boolean replace(R r, Map<C, V> cvMap, Map<C, V> v1) {
        return false;
    }

    public Map<C, V> getOrDefault(Object o, Map<C, V> cvMap) {
        return null;
    }

    @Nullable
    public Map<C, V> putIfAbsent(R r, Map<C, V> cvMap) {
        return null;
    }


}
