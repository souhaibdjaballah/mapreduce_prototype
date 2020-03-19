package uk.ac.reading.csmm16.assignment.core;


import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Job {

    // List of all the all the stored blocks simulating the distributed node storage
    private LinkedList mapperInputBlocksList = new LinkedList();
//    private LinkedList reducerInputBlocksList = new LinkedList();
    // List of All Mappers objects; each Mapper has a list of keys Values
    private List<Mapper> mappersList = new LinkedList<>();
    // List of All Cominers
    private List<Combiner> combinersList = new LinkedList<>();
    // List of All Reducers
    private List<Reducer> reducersList = new LinkedList<>();
    private Configuration configuration;

    private Class mapperClass;
    private Class reducerClass;
    private Class combinerClass;
    private Class partitionerClass;

    private String jobName;

    private final LinkedList<String> inputPathList = new LinkedList<>();
    private String outputDirPath;


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



    public void submit() throws ErrorHandler {
        try {
            Helper.promptMsg("Job: " + jobName + " started.");
            dataStorage();
            map();
            shuffleAndSort();
            if(partitionerClass != null)
                partition();
            reduce();
            writeToFile();
        } catch (Exception e) {
            throw new ErrorHandler("Job:" + this.jobName + " - workflow disrupted!");
        }

        Helper.promptMsg("Job: " + jobName + " finished. \n-----------------------------------------------------");
    }

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

    private void combine() throws ErrorHandler {
        new ThreadHandler("Combine", mapperInputBlocksList.size()){
            @Override
            public void run() throws ErrorHandler {
                for (Object singleFileBlocksList : mapperInputBlocksList) {
                    List blockslist = (List) singleFileBlocksList;
                    for (Object obj : blockslist) {
                        LinkedList<String> block = (LinkedList<String>) obj;
                        try {
                            Combiner combiner = (Combiner) combinerClass.getDeclaredConstructor(LinkedList.class).newInstance(block);
                            combinersList.add(combiner);
                            this.getPool().execute(combiner);
                        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                        }
                    }
                }
            }
        };
    }

    private void shuffleAndSort() throws ErrorHandler {
        new ThreadHandler("Shuffle", mapperInputBlocksList.size()) {
            @Override
            public void run() {
                this.getPool().execute(new ShuffleAndSort(mappersList));
            }
        };
    }

    private void partition() throws ErrorHandler {
        new ThreadHandler("Partition", mapperInputBlocksList.size()) {
            @Override
            public void run() {
                this.getPool().execute(new Partitioner(ShuffleAndSort.getSortedList()));
            }
        };
    }

    private void reduce() throws ErrorHandler {
        new ThreadHandler("Reduce", 1) { //testing
            @Override
            public void run() throws ErrorHandler {

                if(partitionerClass != null) {
                    List<Map> list = Partitioner.getPartitionedBlocksList();
                    for (Map singlePartitionedBlockList : list) {
                        try {
                            Reducer reducer = (Reducer) reducerClass.getDeclaredConstructor(Map.class).newInstance(singlePartitionedBlockList);
                            reducersList.add(reducer);
                            this.getPool().execute(reducer);
                        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            throw new ErrorHandler("Job:" + jobName + " - Instantiation Error!");
                        }

                    }
                }else {
                    try {
                        Reducer reducer = (Reducer) reducerClass.getDeclaredConstructor(Map.class).newInstance(ShuffleAndSort.getSortedList());
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