package com.align;

import java.sql.*;
import java.math.BigDecimal;

/**
 * Created by john on 1/9/17.
 */
public class demoSQL {

    private Connection m_con = null;
    private PreparedStatement m_insertDemoMember_Statement = null;
    private PreparedStatement m_insertDemoProvider_Statement = null;
    private PreparedStatement m_insertDemoClaim_Statement = null;

    public demoSQL(Connection con){
        this.m_con = con;
        this.m_insertDemoMember_Statement = initDemoMemberInsert();
        this.m_insertDemoProvider_Statement = initDemoProviderInsert();
        this.m_insertDemoClaim_Statement = initDemoClaimInsert();
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

    private PreparedStatement initDemoProviderInsert(){

        String SQL;
        SQL = "INSERT INTO align.hash_providers( " +
                "physician_id_hash," +
                "provider_name_first," +
                "provider_name_last," +
                "ordering_physician_dea_hash," +
                "specialty_code," +
                "network_participant ) " +
                "VALUES ( ?,?,?,?,?,? );";

        try {
            return this.m_con.prepareStatement(SQL);
        }
        catch (SQLException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private PreparedStatement initDemoClaimInsert(){
        String SQL;

        SQL = "INSERT INTO align.hash_claims(" +
                "pharmacy_claim_nbr," +
                "member_id_hash," +
                "physician_id_hash," +
                "physician_dea_hash," +
                "pharmacy_id_hash," +
                "prescription_nbr," +
                "refill_code," +
                "ndc," +
                "date_paid," +
                "date_filled," +
                "brand_or_generic," +
                "amount_paid," +
                "ingredient_cost," +
                "dispensing_fee," +
                "copay_amt," +
                "deductible_amount," +
                "disallowed_amount," +
                "awp," +
                "quantity," +
                "day_supply )" +
                "VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? );";
        try{
            return this.m_con.prepareStatement(SQL);
        }
        catch( Exception e ){
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
                    "align.members " +
                "ORDER BY " +
                    "id;";
        return getRecords(query);
    }

    public ResultSet getProviders(){
        String query =
                "SELECT " +
                    "ordering_physician_id," +
                    "provider_name_first," +
                    "provider_name_last," +
                    "ordering_physician_dea," +
                    "specialty_code," +
                    "network_participant " +
                "FROM " +
                    "align.providers;";

        return getRecords(query);
    }
    public ResultSet getClaims(){
        String query =
                "SELECT " +
                        "pharmacy_claim_nbr," +
                        "id_number," +
                        "ordering_physician_id," +
                        "ordering_physician_dea," +
                        "pharmacy_id," +
                        "prescription_nbr," +
                        "refill_code," +
                        "ndc," +
                        "date_paid," +
                        "date_filled," +
                        "brand_or_generic," +
                        "amount_paid," +
                        "ingredient_cost," +
                        "dispensing_fee," +
                        "copay_amt," +
                        "deductible_amount," +
                        "disallowed_amount," +
                        "awp," +
                        "quantity," +
                        "day_supply " +
                "FROM " +
                    "align.claims;";

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
            this.m_insertDemoMember_Statement.setString(1,member_id_hash);
            this.m_insertDemoMember_Statement.setDate(2,birth_date);
            this.m_insertDemoMember_Statement.setString(3,gender_code);
            this.m_insertDemoMember_Statement.setString(4,name_first);
            this.m_insertDemoMember_Statement.setString(5,name_last);
            this.m_insertDemoMember_Statement.setDate(6,membership_date);
            this.m_insertDemoMember_Statement.setString(7,region);
            this.m_insertDemoMember_Statement.setString(8,group_id);
            this.m_insertDemoMember_Statement.setString(9,office);
            this.m_insertDemoMember_Statement.setString(10,new_member);
            this.m_insertDemoMember_Statement.setString(11,lob);

            this.m_insertDemoMember_Statement.executeUpdate();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void postDemoProvider(
            String physician_id_hash,
            String provider_name_first,
            String provider_name_last,
            String ordering_physician_dea_hash,
            String specialty_code,
            Boolean network_participant){

        try{
            this.m_insertDemoProvider_Statement.setString(1,physician_id_hash);
            this.m_insertDemoProvider_Statement.setString(2,provider_name_first);
            this.m_insertDemoProvider_Statement.setString(3,provider_name_last);
            this.m_insertDemoProvider_Statement.setString(4,ordering_physician_dea_hash);
            this.m_insertDemoProvider_Statement.setString(5,specialty_code);
            this.m_insertDemoProvider_Statement.setBoolean(6,network_participant);

            this.m_insertDemoProvider_Statement.executeUpdate();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void postDemoClaim(
            String pharmacy_claim_nbr,
            String member_id_hash,
            String physician_id_hash,
            String physician_dea_hash,
            String pharmacy_id_hash,
            String prescription_nbr,
            String refill_code,
            String ndc,
            Date date_paid,
            Date date_filled,
            String brand_or_generic,
            BigDecimal amount_paid,
            BigDecimal ingredient_cost,
            BigDecimal dispensing_fee,
            BigDecimal copay_amt,
            BigDecimal deductible_amount,
            BigDecimal disallowed_amount,
            BigDecimal awp,
            BigDecimal quantity,
            BigDecimal day_supply ){
        try{
            this.m_insertDemoClaim_Statement.setString(1,pharmacy_claim_nbr);
            this.m_insertDemoClaim_Statement.setString(2,member_id_hash);
            this.m_insertDemoClaim_Statement.setString(3,physician_id_hash);
            this.m_insertDemoClaim_Statement.setString(4,physician_dea_hash);
            this.m_insertDemoClaim_Statement.setString(5,pharmacy_id_hash);
            this.m_insertDemoClaim_Statement.setString(6,prescription_nbr);
            this.m_insertDemoClaim_Statement.setString(7,refill_code);
            this.m_insertDemoClaim_Statement.setString(8,ndc);
            this.m_insertDemoClaim_Statement.setDate(9,date_paid);
            this.m_insertDemoClaim_Statement.setDate(10,date_filled);
            this.m_insertDemoClaim_Statement.setString(11,brand_or_generic);
            this.m_insertDemoClaim_Statement.setBigDecimal(12,amount_paid);
            this.m_insertDemoClaim_Statement.setBigDecimal(13,ingredient_cost);
            this.m_insertDemoClaim_Statement.setBigDecimal(14,dispensing_fee);
            this.m_insertDemoClaim_Statement.setBigDecimal(15,copay_amt);
            this.m_insertDemoClaim_Statement.setBigDecimal(16,deductible_amount);
            this.m_insertDemoClaim_Statement.setBigDecimal(17,disallowed_amount);
            this.m_insertDemoClaim_Statement.setBigDecimal(18,awp);
            this.m_insertDemoClaim_Statement.setBigDecimal(19,quantity);
            this.m_insertDemoClaim_Statement.setBigDecimal(20,day_supply);

            this.m_insertDemoClaim_Statement.executeUpdate();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
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
            se.printStackTrace();
        }
        return committed;
    }
}
