package uk.ac.reading.csmm16.assignment.core;

import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class Mapper<T, E> implements Runnable {

    public List<T> block;
    public List<KeyValueObject<T,E>> mapperOutputList = new LinkedList<>();

    public void setBlock(LinkedList<T> block) {
        this.block = block;
    }

    public void addMapperOutput(KeyValueObject<T, E> mapperOutput) {
        this.mapperOutputList.add(mapperOutput);
    }

    public List<KeyValueObject<T, E>> getMapperOutputList() {
        return this.mapperOutputList;
    }

    /**
     * To remove the overhead of big sort in the Shuffle phase
     */
    private void sort(){
        // Sorting the mapper result list by key
        Collections.sort(this.mapperOutputList, (o1, o2) -> Collator.getInstance().compare(o1.getKey(), o2.getKey()));
    }

    public abstract void map();

    /**
     * This is where the a thread look for code to run for each mapper instance.
     * @return void
     */
    @Override
    public void run() {
        map();
        sort();
    }
}
