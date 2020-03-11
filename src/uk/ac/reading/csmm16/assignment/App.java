package uk.ac.reading.csmm16.assignment;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App { /** 0- git and github submition*/

    // Map <key, value> for each to count the number flights
    public static Map<String, Integer> mapperOutput = new ConcurrentHashMap<>();
    // Maximum number of threads in thread pool
    static final int MAX_T = 3;

    public static void main(String[] args) { // Main program

        /** Output commandLine selection
         *  1 --> Get flights number by airport Code
         *  2 --> Get a list of flights based on the Flight id
         *  3 --> Get the number of passengers on each flight
         *  4 --> the line-of-sight (nautical) miles for each flight, the total travelled by each passenger
         *        and the passenger having earned the highest air miles.
         * */
        int outPutMode = 2;

        String userDir = System.getProperty("user.dir");
        userDir =userDir.replace("\\","/");
        String ACompPassengerDataFile = userDir + "/AComp_Passenger_data_no_error.csv"; // testing

        ReadAndStore readAndStore = new ReadAndStore(ACompPassengerDataFile);

        List blocksList = readAndStore.splitIntoBlocks();

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

        // creates a thread pool with MAX_T no. of
        // threads as the fixed pool size
        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
        if(!blocksList.isEmpty()) {
            switch (outPutMode){
                case 1:
                    /** 2-1- Get the number of flights for each airport in a block using its Code {include a list of any airports not used} */

                    for (Iterator it = blocksList.iterator(); it.hasNext(); ) { // using for loop with iterator
                        LinkedList<String> _unit1 = (LinkedList<String>)it.next();
                        Mapper mapper = new MapFlightsNumberByAirportCode(_unit1, mapperOutput);
                        pool.execute(mapper);
                    };
                    break;
                case 2:
                    /** 2-2- Create a list of flights based on the Flight id, this output should include the
                     * passenger Id,
                     * relevant IATA/FAA codes,
                     * the departure time,
                     * the arrival time (times to be converted to HH:MM:SS format), and
                     * the flight times.
                     * */

                    for (Iterator it = blocksList.iterator(); it.hasNext(); ) { // using for loop with iterator
                        LinkedList<String> _unit1 = (LinkedList<String>)it.next();
                        Mapper mapper = new MapFlightsListByID(_unit1, mapperOutput);
                        pool.execute(mapper);
                    };
                    break;
                case 3:
                    /** 2-3- Calculate the number of passengers on each flight. */


                    break;
                default:
                    System.out.println("Please select a number from the list: "); // print the list again and wait for input

            }

            pool.shutdown();
        }

        //*************** Testing ends 2 ***************



        // Map <key, value> for each to count the number flights
//        Map<String, Integer> numFlights = Collections.synchronizedMap(new HashMap<>());
//        int totalFlightNumber = 0; // Counter + numFlights
//
//        // creates a thread pool with MAX_T no. of
//        // threads as the fixed pool size
//        ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
//
//        if(!blocksList.isEmpty()) {
//
//            for (Iterator it = blocksList.iterator(); it.hasNext(); ) { // using for loop with iterator
//                LinkedList<String> _unit1 = (LinkedList<String>)it.next();
//                Runnable runnable = () -> {
//                    //System.out.println("****************** this is the entry *** " + Thread.currentThread().getName()); // testing
//                    String [] splitedLine1 = null;
//                    //_unit = (LinkedList<String>) blocksList.get(j); // testing
//                    if (!_unit1.isEmpty()) {
//                        //System.out.println("_unit is not empty | size =" + _unit.size()); // testing
//                        int unitSize = _unit1.size();
//                        for (int i = 0; i < unitSize; i++) {
//                            splitedLine1 = _unit1.get(i).split(",");
//
//                            if (splitedLine1[2].matches("\\w*")) {
//                                if (!numFlights.containsKey(splitedLine1[2])) {
//                                    numFlights.put(splitedLine1[2], 1);
//                                } else {
//                                    // The get function doesn't not support locking-- ConcurrentHashMap
//                                    numFlights.put(splitedLine1[2], (numFlights.get(splitedLine1[2]).intValue() + 1));
//                                }
//                                System.out.println("---> Match: " + i + " " + splitedLine1[2]);
//                            }else {
//                                System.out.println("!--- Not matched: " + splitedLine1[2]);
//                            }
//                        }
//                    }
//                };
//
//                // passes the Task objects to the pool to execute
//                pool.execute(runnable);
//            }
//            // pool shutdown
//            pool.shutdown();
//        }

        /** 2-3- Calculate the number of passengers on each flight. */

        /** 2-4- Calculate
         * the line-of-sight (nautical) miles for each flight and
         * the total travelled by each passenger
         * and thus output the passenger having earned the highest air miles.
         * */


        // ... add more features later... (Shuffler, Combiner,...)

        /** 3- Reduce phase: combine(merge) the results of the map phase to one array where each element has one result unit as map object*/

        /** 3-1- Get the number of flights for each airport from ALL the blocks using the its Code
         * {include a list of any airports not used}
         * Map(<String> airportCode, <LinkedList> numFlightsList) ---> Reduce(<String> airportCode, <Integer> numFlights)
         * */
        //..

        /** 3-2- */

        /** 3-3- */

        /** 3-2- */


        /** 3-[Testing] --> 2-2- Create a list of flights based on the Flight id, this output should include the */
        //*************** Testing Start 3 ***************


        try { // testing
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
        }


        switch (outPutMode){
            case 1: flightNumberByAirportCodeOutput();
                break;
            case 2: flightListByIDOutput();
                break;
            default:
                // log the error
        }

        //*************** Testing ends 3 ***************

        /** 3-[Testing] --> 2-1- get the total number of flights for all airport by adding all the value from the map phase
         * passenger Id,
         * relevant IATA/FAA codes,
         * the departure time,
         * the arrival time (times to be converted to HH:MM:SS format), and
         * the flight times.
         * */
        //*************** Testing Start 4 ***************

        //*************** Testing Ends 4 ***************

        /** 4- Missing value correction (Error Handling)*/



    }


    public static void flightNumberByAirportCodeOutput (){
        int totalFlightNumber = 0; // Counter + numFlights
        // displaying the final results
        Set set = mapperOutput.entrySet(); //Converting the Map object to a Set object so that we can traverse it
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
    }

    public static void flightListByIDOutput (){
        int totalFlightNumber = 0; // Counter + numFlights
        // displaying the final results
        Set set = mapperOutput.entrySet(); //Converting the Map object to a Set object so that we can traverse it
        Iterator itr = set.iterator();

        while(itr.hasNext()) {
            //Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry=(Map.Entry)itr.next();
            System.out.println("Number of flights with id = " + entry.getKey()+ " is = " + entry.getValue());

            // Counting the total number of flights
            totalFlightNumber += (int)entry.getValue();
        }

        // Printing the total number of flights
        System.out.println("@@@@@@@ The total number of flights 'ID' is " + totalFlightNumber);
    }

}
