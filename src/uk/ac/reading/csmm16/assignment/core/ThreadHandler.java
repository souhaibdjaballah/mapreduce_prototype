package uk.ac.reading.csmm16.assignment.core;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * ThreadHandler class handles all the Threadpools setups and shutdown for each phase
 * of the MapReduce workflow in the Job class.
 * Unlike the other classes, this class implements the Callable interface instead of the
 * Runnable interface to make it able to throw Exception from each pool.
 * The thrown Exceptions will be caught and logged by the ErrorHandler class.
 */

public abstract class ThreadHandler implements Callable {

    /**
     * A static pool instance of to ensure one Threadpool is created per execution.
     * This is important for keeping the sequencetial order between the MapReduce phases
     * while each phase can run its threads in parallel when allowed.
     */
    private static ExecutorService pool;
    private int threadsNum;

    /**
     * The constructor receives the name of the phase in each Job and
     * the number of threads to create fro each phase.
     *
     * @param phaseName
     * @param threadsNum
     * @throws ErrorHandler
     */
    public ThreadHandler(String phaseName, int threadsNum) throws ErrorHandler {
        this.threadsNum = threadsNum;
        Helper.promptMsg(phaseName + " Phase Started");
        setupThreadPool();
        run();
        waitAndShutdownThreadPool();

        Helper.promptMsg(phaseName + " Phase Finished");
    }

    /**
     * This is implemented in whenever a new instance of this class is instantiated (i.e. in each phase).
     * @throws ErrorHandler
     */
    public abstract void run() throws ErrorHandler;

    @Override
    public Object call() throws ErrorHandler {
        return new ErrorHandler("ExecutionException");
    }

    /**
     * Gets the current thread pool within each of its threads.
     * @return ExecutorService
     */
    public ExecutorService getPool() {
        return pool;
    }

    /**
     * Setup a thread pool with required number of threads for each phase.
     * @return void
     */
    private void setupThreadPool(){
        if( pool == null || pool.isShutdown()){
            if(threadsNum <= Runtime.getRuntime().availableProcessors())
                pool = Executors.newFixedThreadPool(threadsNum);
            else
                pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }


    /**
     * This will make sure that the Threadpool is terminated
     * before it shuts it down when called.
     * @return void
     */
    private void waitAndShutdownThreadPool() {
        try {
            pool.shutdown();
            while (!pool.awaitTermination(24L, TimeUnit.HOURS)) {
                System.out.println("Still waiting for termination.");
            }
        } catch (InterruptedException e) {
            Helper.promptMsg(e.getMessage());
        }
    }
}
