package com.ppt.utils;

/**
 * Created by alshevchuk on 01.03.2015.
 */
public class Tuple<K, V> {
    private K k;
    private V v;

    public Tuple(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public K getK(){
        return k;
    }

    public V getV() {
        return v;
    }
}
