package uk.ac.reading.csmm16.assignment.core;

import java.security.Key;

public class KeyValueObject<K, V>{

    K key;
    V value;

    public KeyValueObject() {}

    public KeyValueObject(K key, V  value) {
        this.key = key;
        this.value = value;
    }

    public void setValue(V value) {
        this.value = value;
    }
    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }
    public K getKey() {
        return key;
    }

    public void setKeyValue(K key, V value){
        this.key = key;
        this.value = value;
    }
}