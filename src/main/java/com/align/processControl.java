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
            this.m_obfuscate = new Obfuscator();

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
        ResultSet rs;
        ArrayList<String> firstnames = null;
        ArrayList<String> lastnames = null;
        String firstName;
        String lastName;
        Obfuscator obfuscate;

        try {
            rs = m_dbconnect.getFirstNames();
            rs = m_dbconnect.getLastNames();

            m_obfuscate.setFirstnames(firstnames);
            m_obfuscate.setLastnames(lastnames);

            rs = m_dbconnect.getMembers();
            while (rs.next())
            {

                m_dbconnect.postDemoMember(
                    rs.getString("id_member"),
                    rs.getDate("birth_date"),
                    rs.getString("gender_code"),
                    rs.getString("name_first"),
                    rs.getString("name_last"),
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
}
