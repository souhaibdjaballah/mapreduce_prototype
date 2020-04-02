package uk.ac.reading.csmm16.assignment.core;


import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * The Job class is the brain of the non-MapReduce prototype.
 *
 */
public class Job {

    // List of all the all the stored blocks simulating the distributed node storage
    private LinkedList mapperInputBlocksList = new LinkedList();
    // List of all Mappers objects; each Mapper has a list of keys Values
    private List<Mapper> mappersList = new LinkedList<>();
    // List of all Cominers
    private List<Combiner> combinersList = new LinkedList<>();
    // List of all Partitioners
    private List<Partitioner> partitionersList = new LinkedList<>();
    // List of all Reducers
    private List<Reducer> reducersList = new LinkedList<>();
    private Configuration configuration;

    // These are the custom class set for each task (objective).
    private Class mapperClass;
    private Class reducerClass;
    private Class combinerClass;

    private String jobName;

    private final LinkedList<String> inputPathList = new LinkedList<>();
    private String outputDirPath;


    /**
     * Constructor receives Configuration instance to set in the App class (main program)
     * @param configuration
     */
    public Job(Configuration configuration){
        this.configuration = configuration;
    }

    public Job(Configuration configuration, String jobName) {
        this.configuration = configuration;
        this.jobName = jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setMapperClass(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    public void setReducerClass(Class reducerClass) {
        this.reducerClass = reducerClass;
    }

    public void setCombinerClass(Class combinerClass) {
        this.combinerClass = combinerClass;
    }

    public void addInputPath(String inputPath){
        this.inputPathList.add(inputPath);
    }
    public void setOutputDirPath(String outputDirPath){
        this.outputDirPath = outputDirPath;
    }


    /**
     * This runs the job to manage all the phases of the MapReduce workflow
     * @throws ErrorHandler
     */
    public void submit() throws ErrorHandler {
        try {
            Helper.promptMsg("Job: " + jobName + " started.");
            dataStorage();
            map();
            combine(); // Optional phase. This executes only when cominerClass is set.
            partition();
            shuffle();
            reduce();
            writeToFile();
        } catch (Exception e) {
            throw new ErrorHandler("Job:" + this.jobName + " - workflow disrupted!");
        }

        Helper.promptMsg("Job: " + jobName + " finished. \n-----------------------------------------------------");
    }

    /**
     * This handles reading multiple inputs and store them in the form of blocks (chunks)
     * @throws ErrorHandler
     */
    private void dataStorage() throws ErrorHandler {

        Helper.promptMsg("Read and Store files Started.");
        //get input files path list
        for(String path: inputPathList) {
            mapperInputBlocksList.add(new ReadAndStore(path).getBlocksList());
        }
        Helper.promptMsg("Read and Store files Finished.");
    }


    /**
     * Write final output to a text file
     */
    private void writeToFile() throws ErrorHandler {
        WriteToFile writeToFile = new WriteToFile(reducersList);
        writeToFile.setOutputDirPath(outputDirPath);
        writeToFile.setFileExtention(configuration.getOutputFileExtension());
        writeToFile.write();
    }

    /**
     * This handles the map phase by passing a block of lines to each mapper dynamically during the run-time.
     * @throws ErrorHandler
     */
    private void map() throws ErrorHandler {
        if(!mapperInputBlocksList.isEmpty()) {
            new ThreadHandler("Map", mapperInputBlocksList.size()){
                @Override
                public void run() throws ErrorHandler {
                    for (Object singleFileBlocksList : mapperInputBlocksList) {
                        List blocksList = (List) singleFileBlocksList;
                        for (Object obj : blocksList) {
                            LinkedList<String> block = (LinkedList<String>) obj;
                            try {
                                // assign each mapper a block(chunk) of data to process in parallel
                                Mapper mapper = (Mapper) mapperClass.getDeclaredConstructor(LinkedList.class).newInstance(block);
                                mappersList.add(mapper);
                                this.getPool().execute(mapper);
                            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                                throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                            }
                        }
                    }
                }
            };
            Helper.promptMsg("Number of Mappers is : [" + mappersList.size() + "]");
        }else {
            throw new ErrorHandler("Job:" + jobName + " - No data available!");
        }
    }

    /**
     * Optional phase.
     * This handles the combine phase by passing the list of mappers output dynamically during the run-time.
     * @throws ErrorHandler
     */
    private void combine() throws ErrorHandler {
        if(combinerClass != null) {
            new ThreadHandler("Combine", mappersList.size()){
                @Override
                public void run() throws ErrorHandler {
                    for (Mapper mapper : mappersList) {
                        try {
                            Combiner combiner = (Combiner) combinerClass.getDeclaredConstructor(List.class).newInstance(mapper.mapperOutputList);
                            combinersList.add(combiner);
                            this.getPool().execute(combiner);
                        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                        }
                    }
                }
            };
        }
    }

    /**
     * This handles the partitioning  phase.
     * @throws ErrorHandler
     */
    private void partition() throws ErrorHandler {
        new ThreadHandler("Partition", 1) {
            @Override
            public void run() {
                if (combinerClass != null) {
                    for(Combiner m : combinersList) {
                        Partitioner partitioner = new Partitioner(m);
                        partitionersList.add(partitioner);
                        this.getPool().execute(partitioner);
                    }
                }else{
                    for(Mapper m : mappersList) {
                        Partitioner partitioner = new Partitioner(m);
                        partitionersList.add(partitioner);
                        this.getPool().execute(partitioner);
                    }
                }
            }
        };

    }

    /**
     * This handles the shuffle phase.
     * @throws ErrorHandler
     */
    private void shuffle() throws ErrorHandler {
        new ThreadHandler("Shuffle", partitionersList.size()) {
            @Override
            public void run() {
                this.getPool().execute(new Shuffle(partitionersList, configuration));
            }
        };

    }


    /**
     * This handles the reduce phase by single partition is the partitioner is set,
     * else it will receive a list directly from the shuffle and sort phase dynamically during the run-time.
     * @throws ErrorHandler
     */
    private void reduce() throws ErrorHandler {
        new ThreadHandler("Reduce", partitionersList.size()) {
            @Override
            public void run() throws ErrorHandler {

                if(!configuration.isSingleReducer() && configuration.getNumberOfReducers() <= partitionersList.size()) {
                    for (Map mergedList : Shuffle.getMergedList()) {
                        Set set = mergedList.entrySet();
                        Iterator partitionBlock = set.iterator();
                        try {
                            Reducer reducer = (Reducer) reducerClass.getDeclaredConstructor(Iterator.class).newInstance(partitionBlock);
                            reducersList.add(reducer);
                            this.getPool().execute(reducer);
                        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                        }

                    }

                }else {
                    //Converting the Map object to a Set object so that we can traverse it
                    Set set = Shuffle.getSingleList().entrySet();
                    Iterator iterator = set.iterator();
                    try {
                        Reducer reducer = (Reducer) reducerClass.getDeclaredConstructor(Iterator.class).newInstance(iterator);
                        reducersList.add(reducer);
                        this.getPool().execute(reducer);
                    } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                    }
                }
            }
        };
    }
}