package uk.ac.reading.csmm16.assignment.core;


import java.util.*;

/**
 * The Shuffle and Sort phase happened before the Reduce phase.
 * This class get a list of outputs of all the executed Mappers.
 * After shuffling and sorting the content the output of this class
 * is stored in a Map list of keys and values.
 */
class ShuffleAndSort implements Runnable {
    private Map<Object, Object> shuffledList = new HashMap<>();
    private List<Mapper> mappersOuptputList;


    private static TreeMap<Object, Object> sortedList;

    public ShuffleAndSort(List mappersOuptputList) {
        this.mappersOuptputList = mappersOuptputList;
    }


    /**
     * This method shuffle the input list received from the Map phase sort by key.
     * TreeMaps perform ascending ordering by default with an optimised comparison algorithm.
     * @return void
     */
    private void shuffleAndSort(){
        KeyValueObject keyValueObject;
        Collections.shuffle(this.mappersOuptputList); // Shuffling the order of the keyValue objects
        for (Mapper singleMapperList : this.mappersOuptputList){
            for(Object keyValue : singleMapperList.getMapperOutputList()){
                keyValueObject = (KeyValueObject) keyValue;
                this.shuffledList.put(keyValueObject.getKey(), keyValueObject.getValue());
            }
        }

        this.sortedList = new TreeMap<>(this.shuffledList);
    }


    public static Map<Object, Object> getSortedList() {
        return sortedList;
    }

    @Override
    public void run() {
        shuffleAndSort();
    }
}
