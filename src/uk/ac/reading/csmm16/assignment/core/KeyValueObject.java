package uk.ac.reading.csmm16.assignment.core;


import java.util.Map;

/**
 * This is a class of pair values of key-values made for this prototype to
 * help passing data between the the Map phase and the Reducer phase.
 * @param <K>
 * @param <V>
 */

public class KeyValueObject<K, V> {

    K key;
    V value;

    public KeyValueObject(K key, V  value) {
        this.key = key;
        this.value = value;
    }

    public Object setValue(Object value) {
        this.value = (V)value;
        return value;
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

    /**
     * Overriding both the equals and hashcode methods to compare this class instances;
     * This is important to remove duplicate values.
     * @param obj
     * @return
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof KeyValueObject) {
            KeyValueObject kvObj = (KeyValueObject) obj;
            if (key != null ? !key.equals(kvObj.key) : kvObj.key != null)
                return false;
            if (value != null ? !value.equals(kvObj.value) : kvObj.value != null)
                return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }
}