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
public class Admin {
    static String CreateQuery = "create table driver(id int(1), name varchar(30), vehicle_id varchar(6), driving_years int(1));";
    static String DeleteQuery = "DROP TABLE driver;";
    public Admin(Connection con) throws SQLException{
        int temp = 0;
        while(temp != 5){
            temp = Admin_UI();
            switch(temp){
                case 1:
                    create_Table(con);
                    break;
                case 2:
                    delete_Table(con);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default:
                    System.out.println("[ERROR]: Invalid input");
                    break;//as error catcher
            }
        }
    }
    public static int Admin_UI(){
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create tables");
        System.out.println("2. Delete tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");
        System.out.println("Please enter [1-5]");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static void create_Table(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate(CreateQuery);
    }
    public static void delete_Table(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate(DeleteQuery);
    }
}
