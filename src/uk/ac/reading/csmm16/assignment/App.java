package uk.ac.reading.csmm16.assignment;


import uk.ac.reading.csmm16.assignment.core.Configuration;
import uk.ac.reading.csmm16.assignment.core.ErrorHandler;
import uk.ac.reading.csmm16.assignment.core.Job;

public class App {

    public static void main(String[] args) throws ErrorHandler {

        Configuration conf = new Configuration();
        Job job1 = new Job(conf, "Job-1 for Objective1");

        // Job 1 for Objective1 ----------------------------------------

        job1.setMapperClass(MapPassengersAndAirports.class);
        job1.setReducerClass(ReduceObjective1.class);
        // Using the combiner class
        job1.setCombinerClass(CombineObjective1.class);
        job1.addInputPath("Top30_airports_LatLong.csv");
        job1.addInputPath("AComp_Passenger_data.csv");
        job1.setOutputDirPath("job1");
        job1.submit();

        // Job 2 for Objective2 ----------------------------------------
        /**
         * We need use one Reducer for the next Jobs to insure ordered outputs for Objective 2 & 3.
         * Also the last objective (4th) we need one Reducer to be able to calculate the distance for each passenger.
         */
        conf.setSingleReducer(true);
        Job job2 = new Job(conf, "Job-2 for Objective2");

        job2.setMapperClass(MapPassengersAndAirports.class);
        job2.setReducerClass(ReduceObjective2.class);
        job2.addInputPath("Top30_airports_LatLong.csv");
        job2.addInputPath("AComp_Passenger_data.csv");
        job2.setOutputDirPath("job2");
        job2.submit();


        // Job 3 for Objective3 ----------------------------------------
        Job job3 = new Job(conf, "Job-3 for Objective3");
        job3.setMapperClass(MapPassengersAndAirports.class);
        job3.setReducerClass(ReduceObjective3.class);
        job3.addInputPath("Top30_airports_LatLong.csv");
        job3.addInputPath("AComp_Passenger_data.csv");
        job3.setOutputDirPath("job3");
        job3.submit();

        // Job 4 for Objective4 ----------------------------------------
        Job job4 = new Job(conf, "Job-4 for Objective4");
        job4.setMapperClass(MapPassengersAndAirports.class);
        job4.setReducerClass(ReduceObjective4.class);
        job4.addInputPath("Top30_airports_LatLong.csv");
        job4.addInputPath("AComp_Passenger_data.csv");
        job4.setOutputDirPath("job4");
        job4.submit();

    }
}
