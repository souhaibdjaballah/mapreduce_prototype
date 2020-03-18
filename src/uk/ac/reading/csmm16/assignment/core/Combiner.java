package uk.ac.reading.csmm16.assignment.core;

import java.util.List;

public abstract class Combiner extends Mapper implements Runnable {

    List combinerOutputList = null;

    public Combiner(List mapperOutputList) {
        this.mapperOutputList = mapperOutputList;
    }

    public List getCombinerOutputList() {
        return combinerOutputList;
    }
}
