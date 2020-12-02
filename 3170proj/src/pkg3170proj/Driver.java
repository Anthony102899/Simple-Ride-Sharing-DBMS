/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3170proj;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
/**
 *
 * @author 24111
 */
public class Driver {
    public Driver(Connection con){
        String id = null;
        String start_location = null;
        String destination = null;
        String driver_id;
        String driver_location_x;
        String driver_location_y;
        String max_distance;
        while(1 == 1){
        System.out.println("Driver, what would you like to do?");
        System.out.println("1. Search requests");
        System.out.println("2. Take a request");
        System.out.println("3. Finish a trip");
        System.out.println("4. Go back");
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter [1-4]");
        String do_thing = s.nextLine();
        if ((Integer.parseInt(do_thing) < 1) || (Integer.parseInt(do_thing) > 4))
        {
            System.out.println("Invalid input, please try again.");
            continue;
        }
        //System.out.println("do >>> " + do_thing);
        /*try
        {
            Statement stmt1 = con.createStatement();
            String sql1 = "INSERT INTO request(id, passenger_id, start_location, destination, model, passengers, taken, driving_years) VALUES('2', '43', 'Lok Fu', 'Mei Foo', '', '1', '0', '0')";
            stmt1.executeUpdate(sql1);
        }catch(Exception e){
                System.out.println("error");
        }*/
        if(Integer.parseInt(do_thing) == 1)// SEARCH REQUEST
        {
            System.out.println("Please enter your ID.");
            driver_id = s.nextLine();// DRIVER_ID
            //We should check whther id is valid or not later.
            while(1 == 1)
            {
                System.out.println("Please enter the coordinates of your location.");
                String[] numbers = s.nextLine().split(" ");
                if(numbers.length == 2)
                {
                     driver_location_x = numbers[0];//LOCATION_X
                     driver_location_y = numbers[1];//LOCATION_Y
                     if (Integer.parseInt(driver_location_x) < 0 || Integer.parseInt(driver_location_y) < 0)
                     {
                         System.out.println("Invalid input, please try again.");
                         continue;
                      }
                    break;
                }
                else
                    System.out.println("Invalid input, please try again.");
            }
            System.out.println("Please enter the maximum distance from you to the passenger.");
            max_distance = s.nextLine();
            //System.out.println("ID is >>> " + driver_id);
            //System.out.println("Location x is >>> " + driver_location_x);
            //System.out.println("Location y is >>> " + driver_location_y);
            //System.out.println("maximum distance is >>> " + max_distance);
            try{
            Statement stmt = con.createStatement();
            String sql = "SELECT DISTINCT R.id, P.name, R.passengers, R.start_location, R.destination "
                    + "FROM request R, passenger P, driver D, vehicle V, taxi_stop T "
                    + "WHERE P.id = R.passenger_id "
                    + "AND D.vehicle_id = V.id "
                    + "AND (V.model = R.model OR R.model = '') "
                    + "AND T.name = R.start_location "
                    + "AND R.taken = 0 "
                    + "AND R.passengers < V.seats "
                    + "AND ((abs(T.location_x - " + driver_location_x + ")) + (abs(T.location_y - " + driver_location_y + ")) < " + max_distance + ")"
                    + "AND D.id = " + driver_id;
            //System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("request ID, passenger name, num of passengers, start location, destination");
            while(rs.next())
            {
                System.out.println(rs.getString(1) + ", " + (rs.getString(2)) + ", " + (rs.getString(3)) + ", " + (rs.getString(4)) + ", " + (rs.getString(5)));
            }
            }catch(Exception e){
                System.out.println("No appopriate require or wrong input, please try again");
            }
        }
        
        else if(Integer.parseInt(do_thing) == 2){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
            int flag = 0;
            int counter = 0;
            System.out.println("Please enter your ID.");
            driver_id = s.nextLine();// DRIVER_ID
            System.out.println("Please enter your request ID.");
            String request_id = s.nextLine();
            //System.out.println("ID is >>>" + driver_id);
            //System.out.println("Request ID is >>>" + request_id);
            try{
            Statement stmt = con.createStatement();
            String sql = "SELECT DISTINCT R.id, P.name "
                    + "FROM request R, passenger P, driver D, vehicle V "
                    + "WHERE P.id = R.passenger_id "
                    //+ " AND R.id is not null AND P.name is not null "
                    + "AND R.id = " + request_id
                    + " AND D.vehicle_id = V.id "
                    + "AND (V.model = R.model OR R.model = '') "
                    + "AND R.taken = 0 "
                    + "AND R.passengers < V.seats "
                    + "AND D.id = " + driver_id;
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println("Trip ID, Passenger name, Start");
            Date date = new Date();
            while(rs.next())
            {
                flag = 1;
                try
                {
                    Statement stmtacc = con.createStatement();
                    String sqlacc = "UPDATE request "
                        + "SET taken = '1' "
                        + "WHERE id = " + request_id;
                    stmtacc.executeUpdate(sqlacc);
                }catch (Exception e){
                    System.out.println("No appopriate require or wrong input, please try again");
                }
                try
                {
                    Statement stmt4 = con.createStatement();
                    String sql4 = "SELECT * FROM trip";
                    ResultSet rs4 = stmt4.executeQuery(sql4);
                    while(rs4.next())
                    {
                        counter++;
                    }
                    //System.out.println(counter);
                }catch(Exception e){
                        System.out.println("error");
                }
                counter++;
                try 
                {
                    Statement stmt5 = con.createStatement();
                    String sql5 = "SELECT DISTINCT R.passenger_id, R.start_location, R.destination FROM request R WHERE R.id = " + request_id;
                    ResultSet rs5 = stmt5.executeQuery(sql5);
                    System.out.println(sql5);
                    rs5.next();
                    id = rs5.getString(1);
                    start_location = rs5.getString(2);
                    destination = rs5.getString(3);
                    System.out.println(id);
                    System.out.println(start_location);
                    System.out.println(destination);

                }catch(Exception e){
                    System.out.println(e);
                }

                    /*try
                    {
                        Statement stmt3 = con.createStatement();
                        String sql3 = "INSERT INTO request(id, passenger_id, start_location, destination, model, passengers, taken, driving_years) VALUES('2', '43', 'Lok Fu', 'Mei Foo', '', '1', '0', '0')";
                        ResultSet rs3 = stmt3.executeQuery(sql3);
                    }catch(Exception e){
                        System.out.println("error");
                    }*/
                try
               {
                    Statement stmt2 = con.createStatement();
                    String sql2 = "INSERT INTO trip(id, driver_id, passenger_id, start_location, destination, start_time, end_time, fee) "
                        + "VALUES(" + counter + ", " + Integer.parseInt(driver_id) + ", " + Integer.parseInt(id) + ", '" + start_location + "','" + destination + "','" + df.format(new Date())+ "', '0000-00-00 00:00:00', 0)";
                    System.out.println(sql2);
                    stmt2.executeUpdate(sql2);
                }catch(Exception e){
                System.out.println(e);
                }
            //System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + date.toString());
        }
        if(flag == 0)
            System.out.println("No appopriate require or wrong input, please try again");
        }catch(Exception e){
            System.out.println("No appopriate require or wrong input, please try again");
        }
    }
        
        else if(Integer.parseInt(do_thing) == 3){ 
            System.out.println("Please enter your ID.");
            driver_id = s.nextLine();
            
        }
        
        else if(Integer.parseInt(do_thing) == 4){ 
            break;
        } else{
            System.out.println("Invalid input, please try again.");
        }
        }
    }
}
