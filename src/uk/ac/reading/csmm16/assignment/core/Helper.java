package uk.ac.reading.csmm16.assignment.core;


import java.text.SimpleDateFormat;
import java.util.Date;


class Helper {

    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("HH:MM:SS");


    static void promptMsg(String s){
        System.out.println(FORMATER.format(new Date()) + " ---> INFO: " + s);
    }

    static void errorMsg(String s){
        System.err.println(FORMATER.format(new Date()) + " ---> ERROR: " + s);
    }


}

