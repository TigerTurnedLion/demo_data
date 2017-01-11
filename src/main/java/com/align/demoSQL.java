package com.align;

import java.sql.*;

/**
 * Created by john on 1/9/17.
 */
public class demoSQL {

    private Connection m_con = null;

    public demoSQL(Connection con){
        m_con = con;
    }

    public ResultSet getMembers() {

        String query = "SELECT name_first,name_last FROM align.members;";
        return getRecords(query);
    }

    public ResultSet getFirstNames(){
        String query = "SELECT firstname FROM align.firstnames;";
        return getRecords(query);
    }

    public ResultSet getLastNames(){
        String query = "SELECT lastname FROM align.lastnames;";
        return getRecords(query);
    }

    public void postDemoMember(){

    }

    private ResultSet getRecords(String query){
        ResultSet rs = null;
        Statement stmt;

        try {
            stmt = m_con.createStatement();
            rs = stmt.executeQuery(query);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return rs;
    }
}
