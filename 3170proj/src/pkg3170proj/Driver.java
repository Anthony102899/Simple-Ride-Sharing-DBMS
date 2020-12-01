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
public class Driver {
    public Driver(Connection con){
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
        String sql1 = "INSERT INTO request(id, passenger_id, start_location, destination, model, passengers, taken, driving_years) VALUES('1', '43', 'Lok Fu', 'Mei Foo', 'Mazda 6', '1', '0', '0')";
        stmt1.executeUpdate(sql1);
        }catch(Exception e){
                System.out.println("fuck");
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
                    + "AND R."
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
            System.out.println("Please enter your ID.");
            driver_id = s.nextLine();// DRIVER_ID
            //We should check whther id is valid or not later.
            System.out.println("Please enter your request ID.");
            String request_id = s.nextLine();
            //We should check whther request id is valid or not later.
            System.out.println("ID is >>>" + driver_id);
            System.out.println("Request ID is >>>" + request_id);
        }
        
        else if(Integer.parseInt(do_thing) == 3){ 
            System.out.println("Please enter your ID.");
            driver_id = s.nextLine();// DRIVER_ID
            //We should check whther id is valid or not later, and whether the ID have taken a request.
        }
        
        else if(Integer.parseInt(do_thing) == 4){ 
            break;
        } else{
            System.out.println("Invalid input, please try again.");
        }
        }
    }
}
