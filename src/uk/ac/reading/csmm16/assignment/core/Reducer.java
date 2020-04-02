package uk.ac.reading.csmm16.assignment.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** 3- Reduce phase: combine(merge) the results of the map phase to one array where each element has one result unit as map object*/

public abstract class Reducer<T,E> implements Runnable {

    protected Iterator inputList;
    private List reducerOutput = new LinkedList<>();


    /**
     * The constructor receives an input list of type Map from the shuffle phase.
     * @param inputList
     */
    public Reducer(Iterator inputList) {
        this.inputList = inputList;
    }

    public void addToReducerOutputList(Object outputList){
        reducerOutput.add(outputList);
    }

    public List getReducerOutput() {
        return reducerOutput;
    }

    public abstract void reduce();

    /**
     * This is where the a thread look for code to run for each reducer instance.
     * @return void
     */
    @Override
    public void run() {
        reduce();
    }
}
