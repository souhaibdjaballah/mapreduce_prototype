package uk.ac.reading.csmm16.assignment.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


class WriteToFile {
    private List<Reducer> recudersList;
    private String outputPath;
    private String outputDirPath;
    private String fileExtention;

    /**
     * @param recuderOutputList
     */
    public WriteToFile(List<Reducer> recuderOutputList) {
        this.recudersList = recuderOutputList;
    }

    public void setOutputDirPath(String outputDirPath) {
        this.outputDirPath = outputDirPath;
    }

    public void setFileExtention(String fileExtention) {
        this.fileExtention = fileExtention;
    }


    void fileCheck(File file) throws ErrorHandler {
        if (file.exists()) {
            Helper.errorMsg("a File with the same name already exists in this path => " + outputPath);
            System.exit(1);
        }
        //if the file does not exist
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Helper.errorMsg("The file " + outputPath + " creation failed.");
                throw new ErrorHandler("The file " + outputPath + " creation failed.");
            }
        }
    }

    protected void write() throws ErrorHandler {
        int fileCounter = 0;
        int dirCounter = 0;
        if(outputPath == null) {
            outputPath = "output-file-" + System.currentTimeMillis();
        }

        try {
            File file = new File(outputDirPath);
            while(!file.mkdir()){
                Helper.errorMsg("Directory " + outputDirPath + " already exists.");
                if(dirCounter == 0) {
                    outputDirPath = outputDirPath + "_" + "(" + dirCounter++ + ")";
                }else {
                    outputDirPath = outputDirPath.replaceAll("[(][\\d]*[)]$", "(" + dirCounter++ + ")");
                }
                file = new File(outputDirPath);
            }
            file = new File(outputDirPath+"/"+outputPath  + this.fileExtention);
            fileCheck(file);

            Helper.promptMsg("Writing to " + outputPath + " file(s).");
            //Creating a file writer
            FileWriter fileWriter = new FileWriter(file);

            for (Reducer reducer : recudersList) {
                for (Object reducerOutput : reducer.getReducerOutput()){

                    if(reducerOutput instanceof Map){
                        Map ouputEntry = (Map) reducerOutput;
                        Set set = ouputEntry.entrySet();
                        Iterator keyValue = set.iterator();

                        while(keyValue.hasNext()) {
                            //Converting to Map.Entry so that we can get key and value separately
                            Map.Entry entry = (Map.Entry) keyValue.next();
                            //write the key and value
                            fileWriter.write(entry.getKey() + ", "
                                    + entry.getValue()
                                    + '\n');
                        }
                    }else{
                        KeyValueObject kVObject = (KeyValueObject) reducerOutput;
                        fileWriter.write(kVObject.getKey() + ", "
                                + kVObject.getValue()
                                + '\n');
                    }
                }
            }

            fileWriter.close();
        }catch(Exception e){
            Helper.errorMsg("Writing to file: " + e.getMessage());
            throw new ErrorHandler("Writing to file failed.");
        }
    }
}