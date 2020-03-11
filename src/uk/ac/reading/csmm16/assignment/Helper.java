package uk.ac.reading.csmm16.assignment;


import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



class Helper {
    private final ArrayList<Context> contexts = new ArrayList<>();//store a list of contexts from the reducers

    private static SimpleDateFormat FORMATER = new SimpleDateFormat("HH:mm:ss");

    static void promptMsg(String s){
        System.out.println(FORMATER.format(new Date()) + " ---> " + s);
    }

    static void errorMsg(String s){
        System.out.println(FORMATER.format(new Date()) + " ---> Error: " + s);
    }


}

