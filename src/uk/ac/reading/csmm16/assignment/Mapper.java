package uk.ac.reading.csmm16.assignment;

import java.util.LinkedList;
import java.util.Map;

public abstract class Mapper<T, E> implements Runnable {

    public LinkedList<T> block = null;
    public Map<T, E> mapperOutput = null;

//    public abstract void map(LinkedList<String> block);
}
