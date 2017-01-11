package com.align;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by john on 1/11/17.
 */
public class Obfuscator {

    private ArrayList<String> m_firstnames;
    private ArrayList<String> m_lastnames;

    public Obfuscator(){
        
    }

    //Accessors and Mutators
    public


    // argument: firstname.
    public static String getNewFirstName( String firstname ){

        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return firstname;
    }

    public static String getNewLastName( String lastname ){
        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return lastname;
    }

    public static Date getNewBirthday ( Date birthday ){
        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return birthday;
    }

    public static String getLowSodium_Hash( String id ){
        String hash = "";

        try{
            hash = CryptoKeeper.lowSodium_Hash(id);
        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return hash;
    }
}
