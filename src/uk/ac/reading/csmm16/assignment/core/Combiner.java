package uk.ac.reading.csmm16.assignment.core;

import java.util.LinkedList;
import java.util.List;

/**
 * This is the base class is responsible for the Combine phase. (Optional)
 */
public abstract class Combiner extends Mapper implements Runnable {

    private List combinerOutputList = new LinkedList<>();;

    public Combiner(List mapperOutputList) {
        this.mapperOutputList = mapperOutputList;
    }

    public void addCombinerOutputList(Object obj) {
        this.combinerOutputList.add(obj);
    }

    public List getCombinerOutputList() {
        return combinerOutputList;
    }

    /**
     * Using the 'combine' method signature instead 'map' to make it more intuitive
     */
    public abstract void combine();

    @Override
    public void map() {
        combine();
    }
}
