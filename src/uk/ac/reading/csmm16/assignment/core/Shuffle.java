package uk.ac.reading.csmm16.assignment.core;


import java.util.*;

/**
 * The Shuffle and Sort phase happened before the Reduce phase.
 * This class get a list of outputs of all the executed Mappers.
 * After shuffling and sorting the content the output of this class
 * is stored in a Map list of key-value pairs.
 */
class Shuffle implements Runnable {
    private static Map<String, Set<KeyValueObject>> singleList = new TreeMap<>();
    private static List mergedList = new LinkedList();
    private List<Partitioner> inputList;
    private Configuration conf;

    public Shuffle(List<Partitioner> inputList, Configuration conf) {
        this.inputList = inputList;
        this.conf = conf;
    }


    /**
     * This method shuffle the input list received from the Map phase.
     * @return void
     */
    private void shuffle(){
        Set set;
        Iterator iterator;
        Map.Entry mapEntry;
        String entryKey;
        Set entryValue;

        for (Partitioner lst : this.inputList){
            set = lst.getPartitionList().entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()){
                mapEntry = (Map.Entry<String, Set<KeyValueObject>>)iterator.next();
                entryKey = (String) mapEntry.getKey();
                entryValue = (Set)mapEntry.getValue();
                if(!singleList.containsKey(mapEntry.getKey())){
                    singleList.put(entryKey, entryValue);
                }else{
                    singleList.get(entryKey).addAll(entryValue);
                }
            }
        }



        if(!conf.isSingleReducer()){

            int blockSize = singleList.size()/conf.getNumberOfReducers();
            int blockCount = 0;
            Map block = new TreeMap();
            set = singleList.entrySet();
            iterator = set.iterator();
            while (iterator.hasNext()) {
                mapEntry = (Map.Entry<String, Set<KeyValueObject>>)iterator.next();
                entryKey = (String) mapEntry.getKey();
                entryValue = (Set)mapEntry.getValue();
                if (blockCount < blockSize) {
                    block.put(entryKey, entryValue);
                    blockCount++;

                    // create blocks with size e.g 10 lines and put the remaining records in the last block
                    if (blockCount == blockSize) {
                        // linked list for all records
                        mergedList.add(block);
                        block = new TreeMap<String, Set<KeyValueObject>>();
                        blockCount = 0;
                    }
                }
            }
            if (block.size() > 0) {
                // this is to add the remaining block in the case when the size of the data set is not divisible by the blockSize
                mergedList.add(block);
            }
        }
    }

    public static Map<String, Set<KeyValueObject>> getSingleList() {
        return singleList;
    }

    public static List<Map<String, Set<KeyValueObject>>> getMergedList() {
        return mergedList;
    }

    @Override
    public void run() {
        shuffle();
    }
}
