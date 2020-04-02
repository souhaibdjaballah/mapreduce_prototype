package uk.ac.reading.csmm16.assignment.core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * This class reads and splits the input file into multiple blocks.
 * The block size is set in the Configuration class.
 * Each block is then fed to a mapper.
 */
public class ReadAndStore {

    String inputFile;
    String line = "";

    /**
     * The constractor receives input file path and call the splitIntoBlocks() method for instance.
     * @param inputFile
     * @throws ErrorHandler
     */
    public ReadAndStore(String inputFile) throws ErrorHandler {
        this.inputFile = inputFile;
        splitIntoBlocks();
    }

    private LinkedList blocksList = new LinkedList<LinkedList>(); // a list of LinkedLists to store each created block of the csv file
    private List block = new LinkedList<String>(); // a list of stings to store the segment of the csv file
    private int blockCount = 0; // to count each line appended to a block by adding 1 i.e blockCount++


    public LinkedList getBlocksList() {
        return blocksList;
    }

    /**
     * Read and split the input file into blocks.
     * Each block has 'n' number (e.g. 10) of lines the last block can have the size of n <= BLOCK_SIZE.
     * @throws ErrorHandler
     */
    public void splitIntoBlocks() throws ErrorHandler {
        // here I used "try resources" from JDK 7 to handle the file resources automatically
        try(BufferedReader br = new BufferedReader(new FileReader(inputFile))) // Read the input file in buffer
        {
            while ((line = br.readLine()) != null) {

                if (blockCount < Configuration.BLOCK_SIZE) {
                    block.add(line);
                    blockCount++;
                    // create blocks with size e.g 10 lines and put the remaining records in the last block
                    if (blockCount == Configuration.BLOCK_SIZE) {
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

        } catch(Exception e){
            throw new ErrorHandler("Reading and splitting data failed!");
        }
    }
}
