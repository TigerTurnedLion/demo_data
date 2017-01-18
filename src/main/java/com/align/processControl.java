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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return lastnames;
    }

    private void obscureMembers(){
        ResultSet rs;
        String firstName;
        String lastName;
        String hash_id;
        Date newBday;

        try {
            rs = m_dbconnect.getMembers();
            m_dbconnect.truncateTable("align.hash_members");
            while (rs.next())
            {

                firstName = (rs.getString("name_first") == null)? null :m_obfuscate.getNewFirstName(rs.getString("name_first"));
                lastName = (rs.getString("name_last") == null)? null :m_obfuscate.getNewLastName(rs.getString("name_last"));
                hash_id = (rs.getString("id_member") == null)? null :m_obfuscate.getLowSodium_Hash(rs.getString("id_member"));
                newBday = (rs.getDate("birth_date") == null)? null :m_obfuscate.getNewBirthday(rs.getDate("birth_date"));

                m_dbconnect.postDemoMember(
                        hash_id,
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
            e.printStackTrace();
        }
    }
    private void obscureProviders(){
        ResultSet rs;
        String firstName;
        String lastName;
        String provider_id_hash;
        String provider_dea_hash;

        try {
            rs = m_dbconnect.getProviders();
            m_dbconnect.truncateTable("align.hash_providers");
            while (rs.next())
            {

                firstName = (rs.getString("provider_name_first") == null)? null :m_obfuscate.getNewFirstName(rs.getString("provider_name_first"));
                lastName = (rs.getString("provider_name_last") == null)? null :m_obfuscate.getNewLastName(rs.getString("provider_name_last"));
                provider_id_hash = (rs.getString("ordering_physician_id") == null)? null :m_obfuscate.getLowSodium_Hash(rs.getString("ordering_physician_id"));
                provider_dea_hash = (rs.getString("ordering_physician_dea") == null)? null :m_obfuscate.getLowSodium_Hash(rs.getString("ordering_physician_dea"));

                m_dbconnect.postDemoProvider(
                        provider_id_hash,
                        firstName,
                        lastName,
                        provider_dea_hash,
                        rs.getString("specialty_code"),
                        rs.getBoolean("network_participant")
                );
            }
            rs.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
    private void obscureClaims(){
        ResultSet rs;
        String member_id_hash;
        String physician_id_hash;
        String physician_dea_hash;
        String pharmacy_id_hash;

        try {
            rs = m_dbconnect.getClaims();
            m_dbconnect.truncateTable("align.hash_claims");
            while (rs.next())
            {
                member_id_hash = (rs.getString("id_number") == null)? null : m_obfuscate.getLowSodium_Hash(rs.getString("id_number"));
                physician_id_hash = (rs.getString("ordering_physician_id") == null)? null : m_obfuscate.getLowSodium_Hash(rs.getString("ordering_physician_id"));
                physician_dea_hash = (rs.getString("ordering_physician_dea") == null)? null :m_obfuscate.getLowSodium_Hash(rs.getString("ordering_physician_dea"));
                pharmacy_id_hash = (rs.getString("pharmacy_id") == null)? null :m_obfuscate.getLowSodium_Hash(rs.getString("pharmacy_id"));

                m_dbconnect.postDemoClaim(
                        rs.getString("pharmacy_claim_nbr"),
                        member_id_hash,
                        physician_id_hash,
                        physician_dea_hash,
                        pharmacy_id_hash,
                        rs.getString("prescription_nbr"),
                        rs.getString("refill_code"),
                        rs.getString("ndc"),
                        rs.getDate("date_paid"),
                        rs.getDate("date_filled"),
                        rs.getString("brand_or_generic"),
                        rs.getBigDecimal("amount_paid"),
                        rs.getBigDecimal("ingredient_cost"),
                        rs.getBigDecimal("dispensing_fee"),
                        rs.getBigDecimal("copay_amt"),
                        rs.getBigDecimal("deductible_amount"),
                        rs.getBigDecimal("disallowed_amount"),
                        rs.getBigDecimal("awp"),
                        rs.getBigDecimal("quantity"),
                        rs.getBigDecimal("day_supply")
                );
            }
            rs.close();
        }catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
