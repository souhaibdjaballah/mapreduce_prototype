package uk.ac.reading.csmm16.assignment.core;


public class Configuration {

    public static final boolean DEV_MODE = true;
    public static final int BLOCK_SIZE = 10;


    private boolean singleReducer = false;
    private int numberOfReducers = 4;
    private String outputFileExtension = ".csv";

    public void setSingleReducer(boolean singleReducer) {
        this.singleReducer = singleReducer;
    }

    public boolean isSingleReducer() {
        return singleReducer;
    }

    public int getNumberOfReducers() {
        return numberOfReducers;
    }

    public void setNumberOfReducers(int numberOfReducers) {
        this.numberOfReducers = numberOfReducers;
    }

    public void setOutputFileExtension(String outputFileExtension) {
        this.outputFileExtension = outputFileExtension;
    }

    public String getOutputFileExtension() {
        return outputFileExtension;
    }

}
