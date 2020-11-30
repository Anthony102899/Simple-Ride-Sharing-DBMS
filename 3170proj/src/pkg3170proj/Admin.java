package pkg3170proj;
import java.sql.*;
import java.util.*;
import java.lang.*;
/**
 *
 * @author 24111
 */
public class Admin {
    static String[] CreateQuery = {"create table driver(id int, name varchar(30), vehicle_id varchar(6), driving_years int);",
                                   "create table vehicle(id char(6), model varchar(30), seats int);",
                                   "create table passenger(id int, name varchar(30));",
                                   "create table request(id int, passenger_id int, start_location varchar(20), destination varchar(20), model varchar(30), passengers int, taken int, driving_years int);",
                                   "create table trip(id int, driver_id int, start_location varchar(20), destination varchar(20), start_time datetime, end_time datetime, fee int);",
                                   "create table taxi_stop(name varchar(20), location_x int, location_y int);"};
    static String DeleteQuery = "DROP TABLE driver, vehicle, passenger, request, trip, taxi_stop;";
    public Admin(Connection con) throws SQLException{
        int temp = 0;
        while(temp != 5){
            temp = Admin_UI();
            switch(temp){
                case 1:
                    try{
                        create_Table(con);
                    }catch(Exception e){
                        System.out.println("Table already exists!");
                    }
                    break;
                case 2:
                    try{
                        delete_Table(con);
                    }catch(Exception e){
                        System.out.println("No table is found!");
                    }
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
        for(int i = 0; i < 6; i++){
        stmt.executeUpdate(CreateQuery[i]);
        }
    }
    public static void delete_Table(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate(DeleteQuery);
    }
}
