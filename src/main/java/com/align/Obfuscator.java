package com.align;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Created by john on 1/11/17.
 */
public class Obfuscator {

    private ArrayList<String> m_firstnames;
    private ArrayList<String> m_lastnames;

    public Obfuscator(ArrayList<String> firstnames, ArrayList<String> lastnames){
        m_firstnames = firstnames;
        m_lastnames = lastnames;
    }

    // argument: firstname.  The "real" firstname is passed in order to prevent
    // this method from returning it as the new, random first name.
    public String getNewFirstName( String firstname ){

        return getNewName(m_firstnames,firstname);
    }

    public String getNewLastName( String lastname ){

        return getNewName(m_lastnames,lastname);
    }

    private String getNewName(ArrayList<String> list,String name){
        String newName = "";
        int min;
        int max;
        int indexName;
        int indexNewName;

        min = 1;
        max = list.size();

        if( max > 0 ){
            try{

                indexName = list.indexOf(name);
                indexNewName = getRandomWithExclude(min,max,indexName);
                newName = list.get(indexNewName);
            }
            catch( Exception e ){
                System.err.println( e.getMessage());
                e.printStackTrace();
            }
        }
        return newName;
    }

    public Date getNewBirthday ( Date birthday ){

        Date newBirthday = new Date(0);
        try{

            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday);
            int year = cal.get(Calendar.YEAR);
            int bday = cal.get(Calendar.DAY_OF_YEAR);

            GregorianCalendar gc = new GregorianCalendar();
            gc.set(gc.YEAR, year);

            int newBday = getRandomWithExclude(gc.getActualMinimum(gc.DAY_OF_YEAR),gc.getActualMaximum(gc.DAY_OF_YEAR),bday);
            gc.set(gc.DAY_OF_YEAR, newBday);

            newBirthday.setTime(gc.getTimeInMillis());
            return newBirthday;

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
            e.printStackTrace();
        }
        return newBirthday;
    }

    private int getRandomWithExclude(int min, int max, int exclude) {
        Random rnd = new Random();
        int random = min + rnd.nextInt(max - min);

        if( random == exclude ){
            random++;
        }
        return random;
    }

    public String getLowSodium_Hash( String id ){
        String hash = "";

        try{
            hash = CryptoKeeper.lowSodium_Hash(id);
        }
        catch( Exception e ){
            System.err.println( e.getMessage());
            e.printStackTrace();
        }
        return hash;
    }
}
