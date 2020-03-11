package uk.ac.reading.csmm16.assignment;


import java.util.*;
import java.util.concurrent.*;

public class Job {
    // Setting the number of threads in the pool
//    private ExecutorService pool = Executors.newFixedThreadPool(Configuration.BLOCK_SIZE);
    // Map <key, value> for each to count the number flights
    public static Map<String, Integer> mapperOutput = new ConcurrentHashMap<>();

    private List<ReadAndStore> readAndStoreList = new ArrayList<>();
    private List<Mapper> mappers = new ArrayList<>();
    private List<Reducer> reducers = new ArrayList<>();
    private Configuration configuration;

    private Map<String, String> contextList = new HashMap<>();

    private static Job job = null;

    private Mapper mapper;
    private Reducer reducer;

    private String jobName;

    private final LinkedList<String> inputPathList = new LinkedList<>();
    private String outputPath;

    private long jobStartTime;


    private Job(Configuration configuration){
        this.configuration = configuration;
    }

    /**
     * Singleton class implementation
     */
    public static Job getInstance(Configuration configuration){
        if(job == null)
            job = new Job(configuration);

        return job;
    }

    public static Job getInstance(Configuration configuration, String jobName){
        if(job == null)
            job = new Job(configuration);

        job.setJobName(jobName);
        return job;
    }


    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void addInputPath(String inputPath){
        this.inputPathList.add(inputPath);
    }
    public void addOutputPath(String outputPath){
        this.outputPath = outputPath;
    }

    void add(String a, String b){
        contextList.put(a, b);
    }

    /**
     * Getter
     * @return Map<Object, Object>
     */
    Map<String, String> getContextList(){return contextList;}


    private void dataStorage(){
        for(String path: inputPathList) { //get input files
            readAndStoreList.add(new ReadAndStore(path));//add a new input reader for each file path
        }
        Helper.promptMsg("Read and Store files Started.");

        new ThreadHandler(){
            @Override
            public void run() {
                for(ReadAndStore readAndStore : readAndStoreList){
                    this.getPool().execute(readAndStore);//run mappers parallel
                }
            }
        };
        Helper.promptMsg("Read and Store files Finished.");
    }

    private void map(){
        for(ReadAndStore readAndStore : readAndStoreList) {
            for (Iterator block = readAndStore.getBlocksList().iterator(); block.hasNext(); ) {
//            for (LinkedList block : readAndStore.getBlocksList()) {
//                mappers.add(new Mapper((LinkedList) block, configuration.getMapperClass()));
                LinkedList<String> _unit1 = (LinkedList<String>)block.next();
                mappers.add(mapper);
            }
        }
        Helper.promptMsg("Map Phase Started \nNumber of Mappers is : [" + mappers.size() + "]");

        new ThreadHandler(){
            @Override
            public void run() {
                for(Mapper mapper: mappers){
                    this.getPool().execute(mapper);
                }
            }
        };
        Helper.promptMsg("Mapping Phase Finished");
    }


    private void reduce(){
        Helper.promptMsg("Reduce Phase Started");

        new ThreadHandler(){
            @Override
            public void run() {
                for(Reducer reducer : reducers){
                    this.getPool().execute(reducer);
                }
            }
        };
        Helper.promptMsg("Reduce Phase Finished");
    }

    private void outputHandler(){
        WriteToFile writeToFile = new WriteToFile();
        writeToFile.setOutputPath(outputPath);
        for(Reducer reducer: reducers){
            writeToFile.addContext(reducer.getContext());
        }
        writeToFile.write();
    }


    public void submit() {
        try {
            jobStartTime  = System.currentTimeMillis();
            dataStorage();
            map();
            reduce();
            // output handler writes final results to a file
            outputHandler();
            Helper.promptMsg("Job " + "{" + jobName + "}" + " output file written in " + outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}