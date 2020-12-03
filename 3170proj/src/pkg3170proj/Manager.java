package pkg3170proj;
import java.sql.*;
import java.util.*;
/**
 *
 * @author LYU An
 */
public class Manager {
    public Manager(Connection con, Scanner scn){
        int temp = 0;
        while(temp != 2){
            temp = Manager_UI(scn);
            switch(temp){
                case 1:
                    try{
                        findTrips(con, scn);
                    }catch(Exception e){
                        
                    }
                    break;
                case 2:
                    break;
                default:
                    System.out.println("[ERROR]: invalid input!");
            }
        }
    }
    
    public static int Manager_UI(Scanner scn){
        System.out.println("Manager, what would you like to do?");
        System.out.println("1. Find trips");
        System.out.println("2. Go back");
        System.out.println("Please enter [1-2]");
        int m;
        try{
            m = Integer.parseInt(scn.nextLine());
        }catch(Exception e){
            m = 0;
        }
        return m;
    }
    
    public static void findTrips(Connection con, Scanner scn) throws SQLException{
        int dis_min = -1;
        int dis_max = -1;
        int flag = 0;
        while(dis_min < 0){
            dis_min = getMin(scn); 
            if(dis_min < 0){
                System.out.println("[ERROR]: The input should be an integer no smaller than 0.");
            }
        }
        while(dis_max < 0 || dis_max <= dis_min){
            dis_max = getMax(scn);
            if(dis_max <= 0 || dis_max <= dis_min){
                System.out.println("[ERROR]: The input should be a positive integer and larger than the minimum.");
            }
        }
        //System.out.println(dis_min + ", " + dis_max);
        Statement stmt = con.createStatement();
        String SelectQuery = "SELECT t.id, d.name, p.name, s1.name, s2.name, ROUND(TIMESTAMPDIFF(SECOND,t.start_time,t.end_time)/60) AS duration FROM trip t, passenger p, driver d, taxi_stop s1, taxi_stop s2 "
                                     + "WHERE t.start_location = s1.name "
                                     + "AND t.destination = s2.name "
                                     + "AND t.driver_id = d.id "
                                     + "AND t.passenger_id = p.id "
                                     + "AND (abs(s1.location_x - s2.location_x) + abs(s1.location_y - s2.location_y)) BETWEEN " + dis_min + " AND " + dis_max +";"
                                    ;
        try{
            ResultSet rs = stmt.executeQuery(SelectQuery);
            System.out.println("trip id, driver name, passenger name, start location, destination, duration");
            while(rs.next()){
                System.out.println(rs.getString(1) + ", " + rs.getString(2) + ", " + rs.getString(3) + ", " + rs.getString(4) + "," + rs.getString(5) + ", " + rs.getString(6));
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static int getMin(Scanner scn){
        int min;
        System.out.println("Please enter the minimum traveling distance.");
        try{
            min = Integer.parseInt(scn.nextLine());
        }catch(Exception e){
            min = -1;
        }
        return min;
    }
    public static int getMax(Scanner scn){
        int max;
        System.out.println("Please enter the maximum traveling distance.");
        try{
            max = Integer.parseInt(scn.nextLine());
        }catch(Exception e){
            max = -1;
        }
        return max;
    }
}
