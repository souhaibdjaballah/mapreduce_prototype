package uk.ac.reading.csmm16.assignment.core;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The Helper has general methods that make the development process quicker.
 * It is used across all the core classes -when needed.
 */
class Helper {

    private static final SimpleDateFormat FORMATER = new SimpleDateFormat("HH:MM:SS");


    /**
     * Prints information messages to the user.
     * @param s
     */
    static void promptMsg(String s){
        System.out.println(FORMATER.format(new Date()) + " ---> INFO: " + s);
    }

    /**
     * Prints error messages to the user.
     * @param s
     */
    static void errorMsg(String s){
        System.err.println(FORMATER.format(new Date()) + " ---> ERROR: " + s);
    }


}

