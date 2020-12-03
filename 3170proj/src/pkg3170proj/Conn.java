package pkg3170proj;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
/**
 *
 * @author 24111
 */
public class Conn {
    public static Connection connect(){
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/group53";
        String dbUsername = "Group53";
        String dbPassword = "3170group53";
    
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
            //System.out.println("connected!");
        }catch (ClassNotFoundException e){
            System.out.println("[ERROR]: Java MySQL DB Driver not found!!");
            System.exit(0);
        }catch (SQLException e){
            System.out.println(e);
        }
        return con;
    }
}
