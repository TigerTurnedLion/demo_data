package com.align;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by john on 1/8/17.
 */
public class processControl {

    private static processControl m_instance = null;
    private demoSQL m_dbconnect = null;
    private Obfuscator m_obfuscate = null;

    private processControl() {

        try {
            this.m_dbconnect = new demoSQL(initConnection());
            this.m_obfuscate = new Obfuscator(getFirstNameList(),getLastNameList());

            System.out.println("processControl construction complete!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static processControl getInstance() {
        if (m_instance == null) {
            m_instance = new processControl();
        }
        return m_instance;
    }

    public void run(){

        //Process data
        obscureMembers();
        obscureProviders();
        obscureClaims();

        //Now commit all upserts to the database.
        m_dbconnect.commitWork();

        //Export results.
    }

    private Connection initConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection pgConnect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres");
            pgConnect.setAutoCommit(false);

            return pgConnect;
        }
        catch(ClassNotFoundException | SQLException e){
            throw e;
        }
    }

    private ArrayList<String> getFirstNameList(){
        ArrayList<String> firstnames = new ArrayList<>();
        ResultSet rs;

        try{
            rs = m_dbconnect.getFirstNames();
            while(rs.next()) {
                firstnames.add(rs.getString("firstname"));
            }
            rs.close();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return firstnames;
    }

    private ArrayList<String> getLastNameList(){
        ArrayList<String> lastnames = new ArrayList<>();
        ResultSet rs;

        try{
            rs = m_dbconnect.getLastNames();
            while(rs.next()) {
                lastnames.add(rs.getString("lastname"));
            }
            rs.close();
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return lastnames;
    }

    private void obscureMembers(){
        ResultSet rs;
        String firstName;
        String lastName;
        String id_hash;
        Date newBday;

        try {
            rs = m_dbconnect.getMembers();
            m_dbconnect.truncateTable("align.hash_members");
            while (rs.next())
            {

                firstName = m_obfuscate.getNewFirstName(rs.getString("name_first"));
                lastName = m_obfuscate.getNewLastName(rs.getString("name_last"));
                id_hash = m_obfuscate.getLowSodium_Hash(rs.getString("id_member"));
                newBday = m_obfuscate.getNewBirthday(rs.getDate("birth_date"));

                m_dbconnect.postDemoMember(
                        id_hash,
                        newBday,
                        rs.getString("gender_code"),
                        firstName,
                        lastName,
                        rs.getDate("membership_date"),
                        rs.getString("region"),
                        rs.getString("group_id"),
                        rs.getString("office"),
                        rs.getString("new_member"),
                        rs.getString("lob")
                );
            }
            rs.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    private void obscureProviders(){

    }
    private void obscureClaims(){

    }
}
