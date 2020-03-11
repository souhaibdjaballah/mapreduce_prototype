package uk.ac.reading.csmm16.assignment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public abstract class ThreadHandler {

    private ExecutorService pool = Executors.newFixedThreadPool(Configuration.NUMBER_OF_THREADS);

    public ThreadHandler() {
        setupThreadPool();
        run();
        shutdownThreadPool();
    }

    public abstract void run();

    public ExecutorService getPool() {
        return pool;
    }

    private void setupThreadPool(){
        if(pool.isShutdown()){
            if(Configuration.NUMBER_OF_THREADS <= Runtime.getRuntime().availableProcessors())
                pool = Executors.newFixedThreadPool(Configuration.NUMBER_OF_THREADS);
            else
                pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
    }


    private void shutdownThreadPool() {
        pool.shutdown();
        try {
            pool.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            Helper.promptMsg(e.getMessage());
        }
    }
}
