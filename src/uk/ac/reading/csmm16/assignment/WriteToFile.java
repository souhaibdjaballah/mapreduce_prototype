package uk.ac.reading.csmm16.assignment;

import java.io.*;
import java.util.ArrayList;


class WriteToFile {
    private final ArrayList<Context> contexts = new ArrayList<>();//store a list of contexts from the reducers
    private String outputPath;


    void setOutputPath(String outputPath){
        this.outputPath = outputPath;
    }


    void addContext(Context context){
        contexts.add(context);
    }


    void write(){
        if(outputPath != null) {
            File file = new File(outputPath);
            if (file.exists()) {
                Helper.errorMsg("a File with the same name already exists in this path => " + outputPath);
                System.exit(1);
            }
            try {
                if (!file.exists()) {//if the file does not exist
                    if (!file.createNewFile()) {
                        Helper.errorMsg("The file " + outputPath + " creation failed.");
                        System.exit(1);
                    }
                }
                Helper.promptMsg("Writing to " + outputPath + " File.");
                FileWriter fileWriter = new FileWriter(file);//create new file writer
                for (Context c : contexts) {
                        fileWriter.write("Key: " + c.getKey() + " Value: " + c.getValue() + '\n');//write the key and value
                }
                fileWriter.close();
            }catch(Exception e){
                Helper.errorMsg("while writing to file: " + e.getMessage());
                System.exit(1);
            }
        }else{
            Helper.errorMsg("No output path found. Please include output file name and try again.");
            System.exit(1);
        }
    }
}