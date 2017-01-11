package com.align;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

/**
 * Created by john on 1/8/17.
 */
public class processControl {

    private static processControl m_instance = null;
    private demoSQL m_dbconnect = null;

    private processControl() {

        try {
            this.m_dbconnect = new demoSQL(initConnection());
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
        try {

            CryptoKeeper cryptKeeper = new CryptoKeeper();

//            String hash = cryptKeeper.hashMaker("123456789");
//            System.out.println("Feel the hash potatoes " + hash);

            String hash = cryptKeeper.lowSodium_Hash("123456789");
            System.out.println("do the Hash.. the monster hash: " + hash);

            rs = m_dbconnect.getMembers();
            //rs.first();
            while (rs.next())
            {
//                System.out.print("Column 1 returned ");
//                System.out.println(rs.getString(1));
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
