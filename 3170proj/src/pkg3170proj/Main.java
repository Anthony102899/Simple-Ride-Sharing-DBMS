/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3170proj;
import java.sql.*;
import java.util.*;
/**
 *
 * @author 24111
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
       Connection con = Conn.connect();
       int ReturnValue = 0;
       while(ReturnValue != 5){
           ReturnValue = root.initial_page();
            if(ReturnValue == 1){
                Admin admin = new Admin(con);
            }else if(ReturnValue == 2){
                Passenger pass = new Passenger(con);
            }else if(ReturnValue == 3){
                Driver driver = new Driver(con);
            }else if(ReturnValue == 4){
                Manager manager = new Manager(con);
            }else if(ReturnValue == 5){
                System.out.println("Successfully quit the program.");
            }else{
                System.out.println("[ERROR]:Invalid input!");
            }
        }
    }
    
}
