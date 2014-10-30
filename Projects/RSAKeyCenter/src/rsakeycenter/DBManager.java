package rsakeycenter;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Adel
 */
public class DBManager {
    private String DBName;
    private Connection connection;
    private Statement stmt;
    
    public DBManager(String DBName){
        this.DBName = DBName;
        try{
            initialize();
        }catch(Exception ex){
            System.err.println(ex.getMessage());
        }
    }
    
    public synchronized ResultSet executeQuery(String sql) throws SQLException{
        ResultSet r = stmt.executeQuery(sql);
        return r;
    }
    
    public synchronized void executeUpdate(String sql) throws SQLException{
        stmt.executeUpdate(sql);
    }
    
    public synchronized void execute(String sql) throws SQLException{
        stmt.execute(sql);
    }
    
    public synchronized boolean doesExist(String table) throws Exception{
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='"+table+"'");
        rs.next();
        return !rs.getString(1).equalsIgnoreCase("0");
    }
    
    private synchronized void initialize() throws Exception{
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + DBName);
        stmt = connection.createStatement();
    }
    
    
}
