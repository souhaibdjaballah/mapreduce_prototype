package uk.ac.reading.csmm16.assignment;

import java.util.ArrayList;

public class Configuration {
    private ArrayList<String> inputFiles = new ArrayList<>();


    public static int NUMBER_OF_THREADS = 10;
    public static int BLOCK_SIZE = 100;
    private int reducersNumber = 1;

    public void setReducersNumber(int n){
        reducersNumber = n;
    }
    public int getReducersNumber(){
        return reducersNumber;
    }
}
