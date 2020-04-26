package com.lironprojects.costmanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLiteDB {
    private String dbName;
    private Connection c;
    private Statement stmt = null;

    SQLiteDB(String dbName){
        this.dbName = dbName;
        c = getConnection(dbName); //create DB
        closeDB(c);  // close connection
        createProfileTable();
        createTransactionTable();
    }

    private Connection getConnection(String dbName){
        c = null;
        try {
            Class.forName(("org.sqlite.JDBC"));
            c = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".sqlite");
            System.out.println("Connected to DB");
        } catch (SQLException | ClassNotFoundException e){
            System.out.println("Error - Failed to connect\n" + e.getMessage());
        }
        return c;
    }

    private void createProfileTable(){
        String sql = "CREATE TABLE IF NOT EXISTS profile (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";
        createTable(sql);
    }

    private void createTransactionTable(){
        String sql = "CREATE TABLE IF NOT EXISTS profile (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";
        createTable(sql);
    }

    private void createTable(String sql) {
        c = getConnection(this.dbName);
        try {
            stmt = c.createStatement();
            stmt.execute(sql);
        } catch (SQLException e){
            System.out.println("Error\n" + e.getMessage());
        }finally {
            closeDB(c);
        }
    }

    private void closeDB(Connection c){
        try {
            if (c != null) c.close();
        } catch (SQLException e) {
            System.out.println("Error close connection to db.\n" + e.getMessage());
        }
    }

    public void getData(String table, int id){
        c = getConnection(this.dbName);
        String query = "SELECT * FROM " + table + " WHERE id=" + id;
        try{
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error\n" + e.getMessage());
        }
        closeDB(c);
    }
}
