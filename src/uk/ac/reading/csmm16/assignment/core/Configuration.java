package uk.ac.reading.csmm16.assignment.core;


public class Configuration {

    public static boolean DEV_MODE = true;
    // Maximum number of threads in thread pool
    public static int NUMBER_OF_THREADS = 3;
    public static int BLOCK_SIZE = 10;
    private String outputFileExtension = ".csv";
    private boolean multiOuputFiles = false;


    public void setOutputFileExtension(String outputFileExtension) {
        this.outputFileExtension = outputFileExtension;
    }

    public String getOutputFileExtension() {
        return outputFileExtension;
    }

    public void setMultiOuputFiles(boolean multiOuputFiles) {
        this.multiOuputFiles = multiOuputFiles;
    }

    public boolean isMultiOuputFiles() {
        return multiOuputFiles;
    }


}
