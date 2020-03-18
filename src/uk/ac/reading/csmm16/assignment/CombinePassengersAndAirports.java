package uk.ac.reading.csmm16.assignment;

import uk.ac.reading.csmm16.assignment.core.Combiner;

import java.util.List;

public class CombinePassengersAndAirports extends Combiner {


    public CombinePassengersAndAirports(List mapperOutputList) {
        super(mapperOutputList);
    }

    @Override
    public void run() {
        combine();
    }

    void combine(){

    }
}
