package uk.ac.reading.csmm16.assignment;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;


class Sorter implements Runnable {
    private Map<String, String> sorter;


    /**
     * Sorting by key
     * @return void
     */
    void sort(){
        this.sorter
            .entrySet().stream()
            .sorted(comparingByKey())
            .collect(
                    toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v2,
                            LinkedHashMap::new
                    )
            );
    }

    @Override
    public void run() {
        sort();
    }
}
