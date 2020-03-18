package uk.ac.reading.csmm16.assignment.core;


import java.io.*;

public class ErrorHandler extends Exception {

    private static File file = new File("error-hist"+ System.currentTimeMillis() + ".log");
    private FileWriter fileWriter;
    private BufferedWriter bw;

     public ErrorHandler(String msg) throws ErrorHandler {
         super(msg);
        errorLogWriter(this);
        if(!Configuration.DEV_MODE) {
            Helper.errorMsg(msg);
            System.exit(1);
        }
        this.printStackTrace();
    }

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
