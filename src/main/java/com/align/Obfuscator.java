package com.align;

import java.sql.Date;
import java.util.ArrayList;

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

    //Accessors and Mutators
    public void setFirstnames( ArrayList<String> firstnames ){
        m_firstnames = firstnames;
    }

    public void setLastnames( ArrayList<String> lastnames ){
        m_lastnames = lastnames;
    }


    // argument: firstname.
    public String getNewFirstName( String firstname ){

        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return firstname;
    }

    public String getNewLastName( String lastname ){
        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return lastname;
    }

    public Date getNewBirthday ( Date birthday ){
        try{

        }
        catch( Exception e ){
            System.err.println( e.getMessage());
        }
        return birthday;
    }

    public String getLowSodium_Hash( String id ){
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
