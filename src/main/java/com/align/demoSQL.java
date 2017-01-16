package com.align;

import java.sql.*;

/**
 * Created by john on 1/9/17.
 */
public class demoSQL {

    private Connection m_con = null;
    private PreparedStatement insertDemoMember_Statement = null;

    public demoSQL(Connection con){
        this.m_con = con;
        this.insertDemoMember_Statement = initDemoMemberInsert();
    }

    private PreparedStatement initDemoMemberInsert(){

        String SQL;
        SQL = "INSERT INTO align.hash_members( " +
                "member_id_hash," +
                "birth_date," +
                "gender_code," +
                "name_first," +
                "name_last," +
                "membership_date," +
                "region," +
                "group_id," +
                "office," +
                "new_member," +
                "lob ) " +
                "VALUES ( ?,?,?,?,?,?,?,?,?,?,? );";

        try {
            return this.m_con.prepareStatement(SQL);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getMembers() {

        String query =
                "SELECT " +
                    "id_member," +
                    "birth_date," +
                    "gender_code," +
                    "name_first," +
                    "name_last," +
                    "membership_date," +
                    "region," +
                    "group_id," +
                    "office," +
                    "new_member," +
                    "lob " +
                "FROM " +
                    "align.members;";
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

    public void postDemoMember(
            String member_id_hash,
            Date birth_date,
            String gender_code,
            String name_first,
            String name_last,
            Date membership_date,
            String region,
            String group_id,
            String office,
            String new_member,
            String lob){
        try {
            this.insertDemoMember_Statement.setString(1,member_id_hash);
            this.insertDemoMember_Statement.setDate(2,birth_date);
            this.insertDemoMember_Statement.setString(3,gender_code);
            this.insertDemoMember_Statement.setString(4,name_first);
            this.insertDemoMember_Statement.setString(5,name_last);
            this.insertDemoMember_Statement.setDate(6,membership_date);
            this.insertDemoMember_Statement.setString(7,region);
            this.insertDemoMember_Statement.setString(8,group_id);
            this.insertDemoMember_Statement.setString(9,office);
            this.insertDemoMember_Statement.setString(10,new_member);
            this.insertDemoMember_Statement.setString(11,lob);

            this.insertDemoMember_Statement.executeUpdate();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
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

    public void truncateTable(String table){
        try {
            Statement stmt = m_con.createStatement();
            stmt.execute( "TRUNCATE " + table );
        }
        catch(SQLException se){
            System.err.println(se.getMessage());
        }

    }
    public boolean commitWork(){
        boolean committed = false;
        try {
            this.m_con.commit();
            committed = true;
        }
        catch(SQLException se){
            System.err.println(se.getMessage());
        }
        return committed;
    }
}
