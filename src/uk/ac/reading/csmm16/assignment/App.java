package uk.ac.reading.csmm16.assignment;


import java.io.*;
import java.util.*;

public class App { /** 0- git and github submition*/

    public static void main(String[] args) {

        /** 1- read csv file in buffer*/
        String userDir = System.getProperty("user.dir");
        userDir =  userDir.replace("\\", "/");
        String csvFile = userDir+"/AComp_Passenger_data_no_error.csv";
        String line = "";
        //String cvsSplitBy = ",";
        List blocksList = new LinkedList<LinkedList>(); // a list of LinkedLists to store each created block of the csv file
        int blockSize = 10;
        List block = new LinkedList<String>(); // a list of stings to store the segment of the csv file
        int blockCount=0; // to count each line appended to a block by adding 1 i.e blockCount++

        // step 1-2: split csv file into blocks
        // split the file to multiple buffers where each buffer has n-size (10 or more) lines the last buffer can have n<= n lines

        LinkedList<String> _unit = null; // testing
        String [] splitedLine = null;

        // here I used "try resources" from JDK 7 to handle the file resources automatically
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

            //******************  Testing start 1 ********************

            System.out.println("Number of blocks = " + blocksList.size());
            _unit = (LinkedList<String>)blocksList.get(38);
            System.out.println(_unit.get(0));
            System.out.println(_unit.get(1));
            System.out.println(_unit.get(4));
            System.out.println(_unit.get(6));
            System.out.println(_unit.get(8));
            System.out.println("Number of blocks = " + _unit.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //******************  Testing ends 1 ********************



        /** 2- Map phase(each line): filter each buffer and store the results in map(key, value) object*/
        // 1st implement for one buffer (can be the whole file for the testing phase) with RegExp
        // Use threadpools to allocate each buffer(each block) to a thread.

        //*************** Testing start 2 ***************
//        if (_unit.size()>0){
//            //System.out.println("_unit is not empty | size =" + _unit.size());
//            splitedLine = _unit.get(0).split(",");
//            for (String col : splitedLine) {
//                System.out.println("for each loop:");
//                if(col.matches("\\w*"))
//                    System.out.println("Match: "+col);
//                else
//                    System.out.println("Not matched: " + col);
//            }
//        }

        // Map key value for to count the number flights
        Map<String, Integer> numFlights = new HashMap<>();
        int totalFlightNumber = 0; // Counter + numFlights

        if(!blocksList.isEmpty()) {
            for (Iterator it = blocksList.iterator(); it.hasNext(); ) { // using for loop with iterator
                _unit = (LinkedList<String>)it.next();
                //_unit = (LinkedList<String>) blocksList.get(j);
                if (!_unit.isEmpty()) {
                    //System.out.println("_unit is not empty | size =" + _unit.size());
                    for (int i = 0; i < _unit.size(); i++) {
                        splitedLine = _unit.get(i).split(",");

                        if (splitedLine[2].matches("\\w*")) {
                            if (!numFlights.containsKey(splitedLine[2])) {
                                numFlights.put(splitedLine[2], 1);
                            } else {
                                numFlights.put(splitedLine[2], numFlights.get(splitedLine[2]).intValue() + 1);
                            }
                            System.out.println("---> Match: " + i + " " + splitedLine[2]);
                        }else {
                            System.out.println("!--- Not matched: " + splitedLine[2]);
                        }
                    }
                }
            }
        }
        //*************** Testing ends 2 ***************


        // ... add more features later... (Shuffler, Combiner,...)

        /** 3- Reduce phase: combine(merge) the results of the map phase to one array where each element has one result unit as map object*/


        //*************** Testing Start 3 ***************
        // displaying the final results
        Set set = numFlights.entrySet(); //Converting the Map object to a Set object so that we can traverse it
        Iterator itr = set.iterator();

        while(itr.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry=(Map.Entry)itr.next();
            System.out.println("Number of flights for " + entry.getKey()+ " is = " + entry.getValue());

            // Counting the total number of flights
            totalFlightNumber += (int)entry.getValue();
        }

        // Printing the total number of flights
        System.out.println("**********The total number of flights is " + totalFlightNumber);

        //*************** Testing ends 3 ***************

        /** 4- Missing value correction (Error Handling)*/



    }

//    public static LinkedList map(Integer integer, String s){
//
//        //return list;
//    }

}