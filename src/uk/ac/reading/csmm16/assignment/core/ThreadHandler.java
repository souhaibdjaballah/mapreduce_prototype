package uk.ac.reading.csmm16.assignment.core;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ThreadHandler implements Callable {

    private static ExecutorService pool;
    private int threadsNum;
    private Object obj;

    public ThreadHandler(String phaseName, int threadsNum) throws ErrorHandler {
        this.threadsNum = threadsNum;
        Helper.promptMsg(phaseName + " Phase Started");
        setupThreadPool();
        run();
        waitAndShutdownThreadPool();

        Helper.promptMsg(phaseName + " Phase Finished");
    }

    public abstract void run() throws ErrorHandler;

    @Override
    public Object call() throws ErrorHandler {
        return new ErrorHandler("ExecutionException");
    }

    public ExecutorService getPool() {
        return pool;
    }

    private void setupThreadPool(){
        if( pool == null || pool.isShutdown()){
            if(Configuration.NUMBER_OF_THREADS <= Runtime.getRuntime().availableProcessors())
                pool = Executors.newFixedThreadPool(threadsNum);
            else
                pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }


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
