package uk.ac.reading.csmm16.assignment.core;

import java.util.LinkedList;
import java.util.List;

public abstract class Mapper<T, E> implements Runnable {

    public List<T> block = null;
    public List<KeyValueObject<T,E>> mapperOutputList = null;

    public void setBlock(LinkedList<T> block) {
        this.block = block;
    }

    public List<KeyValueObject<T, E>> getMapperOutputList() {
        return mapperOutputList;
    }

}
