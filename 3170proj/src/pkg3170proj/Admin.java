package pkg3170proj;
import java.sql.*;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author LYU An
 */
public class Admin {
    static String[] CreateQuery = {"create table driver(id int primary key, name varchar(30) not null, vehicle_id varchar(6) not null, driving_years int);",
                                   "create table vehicle(id char(6) primary key, model varchar(30) not null, seats int);",
                                   "create table passenger(id int primary key, name varchar(30) not null);",
                                   "create table request(id int primary key, passenger_id int, start_location varchar(20) not null, destination varchar(20) not null, model varchar(30) not null, passengers int, taken int, driving_years int);",
                                   "create table trip(id int primary key, driver_id int, passenger_id int, start_time datetime, end_time datetime, start_location varchar(20) not null, destination varchar(20) not null, fee int);",
                                   "create table taxi_stop(name varchar(20) primary key, location_x int, location_y int);"};
    static String DeleteQuery = "DROP TABLE driver, vehicle, passenger, request, trip, taxi_stop;";
    public Admin(Connection con, Scanner scn) throws SQLException{
        int temp = 0;
        while(temp != 5){
            temp = Admin_UI(scn);
            switch(temp){
                case 1:
                    try{
                        create_Table(con);
                    }catch(Exception e){
                        System.out.println("[ERROR]: Table already exists!");
                    }
                    break;
                case 2:
                    try{
                        delete_Table(con);
                    }catch(Exception e){
                        System.out.println("[Error]: No table is found!");
                    }
                    break;
                case 3:
                    try{
                        load_Data(con, scn);
                    }catch(Exception e){
                        System.out.println("[ERROR]: Can't find the directory or the csv file is missed!");
                    }
                    break;
                case 4:
                    try{
                        check_Data(con);
                    }catch(Exception e){
                        
                    }
                    break;
                case 5:
                    break;// return to front interface.
                default:
                    System.out.println("[ERROR]: Invalid input");
                    break;//as error catcher
            }
        }
    }
    public static int Admin_UI(Scanner scanner){
        System.out.println("Administrator, what would you like to do?");
        System.out.println("1. Create tables");
        System.out.println("2. Delete tables");
        System.out.println("3. Load data");
        System.out.println("4. Check data");
        System.out.println("5. Go back");
        System.out.println("Please enter [1-5]");
        int n = 0;
        try{
            n = Integer.parseInt(scanner.nextLine());
        }catch(Exception e){
            n = 0;
            System.out.println(e);
        }
        return n;
    }
    public static void create_Table(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        for(int i = 0; i < 6; i++){
        stmt.executeUpdate(CreateQuery[i]);
        }
        System.out.println("Done! Tables are created!");
    }
    public static void delete_Table(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        stmt.executeUpdate(DeleteQuery);
        System.out.println("Done! Tables are deleted!");
    }
    public static void load_Data(Connection con, Scanner scn) throws SQLException, IOException{
        Statement stmt = con.createStatement();
        String temp = null;
        String[] instances = null;
        System.out.println("Please enter the folder path");
        String path = scn.nextLine();
        //System.out.println(path);
        File drivers = new File(path + "\\drivers.csv");
        File passengers = new File(path + "\\passengers.csv");
        File taxi_stops = new File(path + "\\taxi_stops.csv");
        File trips = new File(path + "\\trips.csv");
        File vehicles = new File(path + "\\vehicles.csv");
        String InsertQuery1 = "INSERT INTO driver(id,name,vehicle_id,driving_years) VALUES('%d','%s','%s','%d')";
        String InsertQuery2 = "INSERT INTO passenger(id,name) VALUES('%d','%s')";
        String InsertQuery3 = "INSERT INTO vehicle(id,model,seats) VALUES('%s','%s','%d')";
        String InsertQuery4 = "INSERT INTO trip(id,driver_id,passenger_id,start_time,end_time,start_location,destination,fee) VALUES('%d','%d','%d','%s','%s','%s','%s','%d')";
        String InsertQuery5 = "INSERT INTO taxi_stop(name, location_x, location_y) VALUES('%s','%d','%d')";
        try {// Load drivers.csv and insert all instances to db.
            Scanner scn1 = new Scanner(drivers);
            while(scn1.hasNextLine()){
                temp = scn1.nextLine();
                //System.out.println(temp);
                instances = temp.split(",");
                InsertQuery1 = "INSERT INTO driver(id,name,vehicle_id,driving_years) VALUES('%d','%s','%s','%d')";
                InsertQuery1 = String.format(InsertQuery1,Integer.parseInt(instances[0]),instances[1],instances[2],Integer.parseInt(instances[3]));
                //System.out.println(InsertQuery1);
                stmt.executeUpdate(InsertQuery1);
                instances = null;
                temp = null;
            }
            System.out.println("Done! Load drivers.csv finished!");
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]: drivers.csv not found.");
        }
        try {// Load passengers.csv and insert all instances to db.
            Scanner scn2 = new Scanner(passengers);
            while(scn2.hasNextLine()){
                temp = scn2.nextLine();
                //System.out.println(temp);
                instances = temp.split(",");
                InsertQuery2 = "INSERT INTO passenger(id,name) VALUES('%d','%s')";
                InsertQuery2 = String.format(InsertQuery2,Integer.parseInt(instances[0]),instances[1]);
                //System.out.println(InsertQuery2);
                stmt.executeUpdate(InsertQuery2);
                instances = null;
                temp = null;
            }
            System.out.println("Done! Load passengers.csv finished!");
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]: passengers.csv not found.");
        }
        try {// Load passengers.csv and insert all instances to db.
            Scanner scn3 = new Scanner(vehicles);
            while(scn3.hasNextLine()){
                temp = scn3.nextLine();
                //System.out.println(temp);
                instances = temp.split(",");
                InsertQuery3 = "INSERT INTO vehicle(id,model,seats) VALUES('%s','%s','%d')";
                InsertQuery3 = String.format(InsertQuery3,instances[0],instances[1], Integer.parseInt(instances[2]));
                //System.out.println(InsertQuery3);
                stmt.executeUpdate(InsertQuery3);
                instances = null;
                temp = null;
            }
            System.out.println("Done! Load vehicles.csv finished!");
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]: vehicles.csv not found.");
        }
        try {// Load trips.csv and insert all instances to db.
            Scanner scn4 = new Scanner(trips);
            while(scn4.hasNextLine()){
                temp = scn4.nextLine();
                //System.out.println(temp);
                instances = temp.split(",");
                InsertQuery4 = "INSERT INTO trip(id,driver_id,passenger_id,start_time,end_time,start_location,destination,fee) VALUES('%d','%d','%d','%s','%s','%s','%s','%d')";
                InsertQuery4 = String.format(InsertQuery4,Integer.parseInt(instances[0]),Integer.parseInt(instances[1]),Integer.parseInt(instances[2]), instances[3], instances[4], instances[5],instances[6],Integer.parseInt(instances[7]));
                //System.out.println(InsertQuery4);
                stmt.executeUpdate(InsertQuery4);
                instances = null;
                temp = null;
            }
            System.out.println("Done! Load trips.csv finished!");
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]: trips.csv not found.");
        }
        try {// Load taxi_stops.csv and insert all instances to db.
            Scanner scn5 = new Scanner(taxi_stops);
            while(scn5.hasNextLine()){
                temp = scn5.nextLine();
                //System.out.println(temp);
                instances = temp.split(",");
                InsertQuery5 = "INSERT INTO taxi_stop(name, location_x, location_y) VALUES('%s','%d','%d')";
                InsertQuery5 = String.format(InsertQuery5,instances[0],Integer.parseInt(instances[1]), Integer.parseInt(instances[2]));
                //System.out.println(InsertQuery5);
                stmt.executeUpdate(InsertQuery5);
                instances = null;
                temp = null;
            }
            System.out.println("Done! Load taxi_stops.csv finished!");
        } catch (FileNotFoundException ex) {
            System.out.println("[ERROR]: taxi_stops.csv not found.");
        }
    }
    
    public static void check_Data(Connection con) throws SQLException{
        int counter;
        Statement stmt = con.createStatement();
        String temp = null;
        
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM vehicle;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Numbers of records in each table");
            System.out.println("Vehicle: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table vehicle doesn't exist!");
        }
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM passenger;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Passenger: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table passenger doesn't exist!");
        }
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM driver;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Driver: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table driver doesn't exist!");
        }
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM trip;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Trip: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table trip doesn't exist!");
        }
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM request;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Request: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table Request doesn't exist!");
        }
        try{
            counter = 0;
            ResultSet rs = stmt.executeQuery("SELECT * FROM taxi_stop;");
            while(rs.next()){
                counter++;
            }
            System.out.println("Taxi_Stop: " + counter);
        }catch(Exception e){
            System.out.println("[ERROR] table taxi_stop doesn't exist!");
        }
    }
}
