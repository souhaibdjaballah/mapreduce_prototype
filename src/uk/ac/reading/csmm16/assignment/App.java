package uk.ac.reading.csmm16.assignment;


import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class App {

    public static void main(String[] args) {

        // git and github submition
        // read csv file in buffer
        String userDir = System.getProperty("user.dir");
        userDir =  userDir.replace("\\", "/");
        String csvFile = userDir+"/AComp_Passenger_data_no_error.csv";
        String line = "";
        String cvsSplitBy = ",";
        List blocksList = new LinkedList<LinkedList>();
        int blockSize = 10;
        List block = new LinkedList<String>();
        int blockCount=0;

        // here I used try resources from JDK 7 to handle the file resources automatically
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {

                if (blockCount<blockSize){
                    block.add(line);
                    blockCount++;
                    // create blocks with size e.g 10 lines and put the remaining records in the last block
                    if (blockCount == blockSize){
                        // linked list for all records
                        blocksList.add(block);
                        block = new LinkedList<String>();
                        blockCount = 0;
                    }
                }
            }
            if (block.size()>0){
                // this is to add the remaining block in the case when the size of the data set is not divisible by the blockSize
                blocksList.add(block);
            }

            // Testing
            System.out.println("Number of blocks = " + blocksList.size());
            LinkedList<String> unit = (LinkedList<String>)blocksList.get(38);
            System.out.println(unit.get(0));
            System.out.println(unit.get(1));
            System.out.println(unit.get(4));
            System.out.println(unit.get(6));
            System.out.println(unit.get(8));
            System.out.println("Number of blocks = " + unit.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // use comma as separator


        // split the file to multiple buffers where each buffer has n-size (10 or more) lines the last buffer can have n<= n lines


        // Use threadpools to allocate each buffer to a thread.
        // Map phase: filter each buffer and store the results in map(key, value) object
        // 1st implement for one buffer (can be the whole file for the testing phase) with RegExp

        // ... add more features later... (Shuffler, Combiner,...)

        // Reduce phase: combine(merge) the results of the map phase to one array where eache element has one result unit as map object
        //


        // Missing value correction (Error Handling)



    }
}