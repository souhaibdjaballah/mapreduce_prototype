package uk.ac.reading.csmm16.assignment;


import uk.ac.reading.csmm16.assignment.Configuration;
import uk.ac.reading.csmm16.assignment.Job;

import java.util.*;

public class App {

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
        String passengerDataFile = userDir + "/AComp_Passenger_data_no_error.csv"; // testing
        String topAirportsFile = userDir + "/Top30_airports_LatLong.csv"; // testing

        ReadAndStore readAndStore = new ReadAndStore(passengerDataFile);

        List blocksList = readAndStore.getBlocksList();

        //******************  Testing ends 1 ********************



        /** 2- Map phase(each line): filter each buffer and store the results in map(key, value) object*/
        // 1st implement for one buffer (can be the whole file for the testing phase) with RegExp
        // Use threadpools to allocate each buffer(each block) to a thread.


        //*************** Testing start  ***************
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);
        job.setJobName("non-MapReduce executable prototype");

//        job.setMapperClass(MapPassengersAndAirports.class);
//        job.setReducerClass(ReducerPassangersAndAirports.class);
        configuration.setReducersNumber(3);

        // Set the locations for the input and output files
        job.addInputPath(passengerDataFile);    //args[0] testing
        job.addInputPath(topAirportsFile);       //args[1] testing
        //set output path
        job.addOutputPath("./outputMppRdcr.txt");       //args[2] testing

//        job.waitForCompletion(true);//pass config to job and run job
        // Run the job
        job.submit();

    }
}
