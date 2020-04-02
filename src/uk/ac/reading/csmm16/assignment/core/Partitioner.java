package uk.ac.reading.csmm16.assignment.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class partition a Mapper output and group it by key.
 */
public class Partitioner implements Runnable {
    private Mapper mapper;
    private List<KeyValueObject> inputList;
    // Group the list of Key-Value pairs by key
    protected Map<String, Set<KeyValueObject>> partitionList = new HashMap<>();


    public Partitioner(Mapper mapper) {
        this.mapper = mapper;
    }

    public Map<String, Set<KeyValueObject>> getPartitionList() {
        return partitionList;
    }

    /**
     * This method takes a Mapper output and creates a partition for each key.
     * Each key has one or more of value stored in a Set.
     */
    void partition() {

        inputList = mapper.getMapperOutputList();
        Set set;
        for(KeyValueObject<String, Object> kVobj : inputList){
            if(!partitionList.containsKey(kVobj.key)){
                set = new HashSet();
                set.add(kVobj);
                partitionList.put(kVobj.key, set);
            }else{
                partitionList.get(kVobj.key).add(kVobj);
            }
        }

    }


    @Override
    public void run() {
        partition();
    }
}
