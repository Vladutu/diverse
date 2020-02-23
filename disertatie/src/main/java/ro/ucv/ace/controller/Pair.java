package ro.ucv.ace.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair<T, V> {

    private T left;
    private V right;

    public static <T, V> Pair<T, V> of(T t, V v) {
        return new Pair<>(t, v);
    }
}
