package uk.ac.reading.csmm16.assignment.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** 3- Reduce phase: combine(merge) the results of the map phase to one array where each element has one result unit as map object*/

public abstract class Reducer<T,E> implements Runnable {

    protected Map<T,E> inputList;
    private List reducerOutput = new LinkedList<>();


    public void addToReducerOutputList(Object outputList){
        reducerOutput.add(outputList);
    }

    public List getReducerOutput() {
        return reducerOutput;
    }

}
