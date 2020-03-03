package uk.ac.reading.csmm16.assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class ReadAndStore {

    /**
     * 1- read csv file in buffer
     */
    String csvFile;
    String line = "";

    public ReadAndStore(String csvFile){
        this.csvFile = csvFile;
    }

    //String cvsSplitBy = ",";
    List blocksList = new LinkedList<LinkedList>(); // a list of LinkedLists to store each created block of the csv file
    int blockSize = 10;
    List block = new LinkedList<String>(); // a list of stings to store the segment of the csv file
    int blockCount = 0; // to count each line appended to a block by adding 1 i.e blockCount++

    // step 1-2: split csv file into blocks
    // split the file to multiple buffers where each buffer has n-size (e.g. 10) lines the last buffer can have n<= n lines

    LinkedList<String> _unit = null; // testing

    public List splitIntoBlocks(){
        // here I used "try resources" from JDK 7 to handle the file resources automatically
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile)))
        {
            while ((line = br.readLine()) != null) {

                if (blockCount < blockSize) {
                    block.add(line);
                    blockCount++;
                    // create blocks with size e.g 10 lines and put the remaining records in the last block
                    if (blockCount == blockSize) {
                        // linked list for all records
                        blocksList.add(block);
                        block = new LinkedList<String>();
                        blockCount = 0;
                    }
                }
            }
            if (block.size() > 0) {
                // this is to add the remaining block in the case when the size of the data set is not divisible by the blockSize
                blocksList.add(block);
            }

        } catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }

        return blocksList;
    }
}
