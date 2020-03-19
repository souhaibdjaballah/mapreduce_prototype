package uk.ac.reading.csmm16.assignment.core;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


/**
 * This class handles all the errors raised from each Job instance.
 * It can be used throughout the customised classes and the main class too.
 */
public class ErrorHandler extends Exception {

    // a static File object to ensure each run-time has a unique log file.
    private static File file = new File("error-hist"+ System.currentTimeMillis() + ".log");
    private FileWriter fileWriter;
    private BufferedWriter bw;

    /**
     * Create and instance and pass a message the parent class Exception.
     * Log the stack track content to a '.log' file.
     *
     * @param msg
     * @throws ErrorHandler
     */
     public ErrorHandler(String msg) throws ErrorHandler {
         super(msg);
        errorLogWriter(this);
        if(!Configuration.DEV_MODE) {
            Helper.errorMsg(msg);
            System.exit(1);
        }
        this.printStackTrace();
    }

    /**
     * Receives an instance of type Exception, put the content of the stack trace in
     * a PrintWriter instance, create a new log file if there is no log file yet, and
     * append the log content to the '.log' file.
     * @param e
     * @throws ErrorHandler
     */
    public void errorLogWriter(Exception e) throws ErrorHandler {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            fileWriter = new FileWriter(file, true);
            bw = new BufferedWriter(fileWriter);
            bw.write(sw.toString());
            bw.close();
        } catch (IOException ex) {
            throw this;
        }
    }
}
