package uk.ac.reading.csmm16.assignment.core;

import java.util.*;

public class Partitioner implements Runnable {
    private Map<Object, Object> sortedList;
    private static List<Map> partitionedBlocksList = new LinkedList<>();
    private Map<Object, Object> blockList = new HashMap<>();
    private Map<Object, Object> blockListAirports = new HashMap<>();

    public Partitioner(Map<Object, Object> sortedList) {
        this.sortedList = sortedList;
    }

    public static List<Map> getPartitionedBlocksList() {
        return partitionedBlocksList;
    }

    void partition(){
        Set set = sortedList.entrySet();
        Iterator keyValueLoop = set.iterator();

        while(keyValueLoop.hasNext()) {
            Iterator keyValue = keyValueLoop;
            Map.Entry entry1 = (Map.Entry) keyValueLoop.next();
            String composedKey = (String) entry1.getKey();
            while (keyValue.hasNext()) {
                Map.Entry entry = (Map.Entry) keyValue.next();
                String entryKey = (String) entry.getKey();
                if (entryKey.matches("[A-Z]{3}"))
                    blockListAirports.put(entryKey, entry.getValue());
                else if (entryKey.contains("-")) {
                    String[] s = entryKey.split("-");
                    if (entryKey.contains(s[0])) {
                        this.blockList.put(entry.getKey(), entry.getValue());
                    } else {
                        partitionedBlocksList.add(blockList);
                        blockList = new HashMap<>();
                    }
                }


            }
        }
        if (!blockList.isEmpty())
            partitionedBlocksList.add(blockList);
        partitionedBlocksList.add(blockListAirports);
    }


    @Override
    public void run() {
        partition();
    }
}
