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

    public ResultSet getMembers(){
        ResultSet rs = null;
        Statement stmt;
        String query;

        try{
            query = "SELECT name_first,name_last FROM align.members;";
            stmt = m_con.createStatement();

            rs = stmt.executeQuery(query);
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return rs;
    }
}
