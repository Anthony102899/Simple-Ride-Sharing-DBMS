
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;
import java.util.*;

/**
 *
 * @author HUANG Zihao
 */

public class Passenger {

    public Passenger(Connection con, Scanner scn)throws SQLException{
        
         int temp = 0;
        while(temp != 3){
            temp = Passenger_UI(scn);
            switch(temp){
                case 1:
                    try{
                        request_a_trip(con, scn);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                case 2:
                    try{
                        check_trip_records(con, scn);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    break;
                case 3:
                    break;// return to front interface.
                default:
                    System.out.println("[ERROR]: Invalid input");
                    break;//as error catcher
            }
        }
    }
    
    public static int Passenger_UI(Scanner scn){
        System.out.println("Passenger,what would you like to do?");
        System.out.println("1. Request a ride");
        System.out.println("2. Check trip records");
        System.out.println("3. Go back");
        System.out.println("Please enter [1-3].");
        int m = 0;
        try{
            m = Integer.parseInt(scn.nextLine());
        }catch(NoSuchElementException ex){
            m = 3;
        }catch(Exception e){
            m = 0;
        }
        return m;
    }
    
     public static void request_a_trip(Connection con, Scanner scanner) throws SQLException{
        
        int ID=0;
        int flag = 0;
        int num_of_passengers=0;
        String start_location=null;
        String destination=null;
        String model=null;
        int min_driving_years=0;
        String temp_min_driving_years=null;
        String CheckDest = "";
        System.out.println("Please enter your ID.");
        Statement stmt_tmp = con.createStatement();
         ID = Integer.parseInt(scanner.nextLine());
         
        // System.out.println("test"+ID);
        
        System.out.println("Please enter the number of passengers.");
         num_of_passengers=Integer.parseInt(scanner.nextLine());
         while(num_of_passengers > 8 || num_of_passengers < 1){
             System.out.println("[ERROR] The number of passenger must between 1-8.");
             System.out.println("Please enter the number of passengers.");
             num_of_passengers=Integer.parseInt(scanner.nextLine());
         }
         //System.out.println("test"+num_of_passengers);
         while(flag == 0){
            System.out.println("Please enter the start location."); 
            start_location=scanner.nextLine();
           // System.out.println(start_location);
            CheckDest = "SELECT * FROM taxi_stop t WHERE t.name = " + "'" + start_location + "';";
             try{
                ResultSet rs_sl = stmt_tmp.executeQuery(CheckDest);
                if(rs_sl.next() == false){
                    flag = 0;
                    System.out.println("[ERROR] No such taxi stop.");
                }else{
                    flag = 1;
                }
            }catch(Exception e){
                System.out.println("[ERROR]");
                flag = 0;
            }
         }
         
         //System.out.println("test"+start_location);
         
        System.out.println("Please enter the destination.");
         destination=scanner.nextLine();
         System.out.println(destination);
         while(destination.equals(start_location)){
             System.out.println("[ERROR] The destination must be different from start location!");
             System.out.println("Please enter the destination.");
             destination=scanner.nextLine();
            // System.out.println(destination);
         }
         //System.out.println("test"+destination);
         
        System.out.println("Please enter the model.(Press enter to skip)"); 
         model=scanner.nextLine();
         
         //System.out.println("test"+model);
         
        System.out.println("Please enter the minimum driving years of the driver.(Press enter to skip)");
        temp_min_driving_years=scanner.nextLine();
        if("".equals(temp_min_driving_years))
            min_driving_years=0;
        else
            min_driving_years=Integer.parseInt(temp_min_driving_years);
         
        // System.out.println("test"+min_driving_years);
         
         //System.out.println("test"+num_of_passengers+model+min_driving_years);
         
         try{
            if(con == null) System.out.println("not connected");           
            int num = 0;       
         
         Statement stmt = con.createStatement();
         String str="SELECT COUNT(*)"//distinct ?
                 + " FROM driver D, vehicle V"
                 + " WHERE V.seats>="+ num_of_passengers
                 + " AND V.model LIKE '%"+model+"%'"
                 + " AND V.id=D.vehicle_id"
                 + " AND D.driving_years>="+ min_driving_years;
         
       // System.out.println("test"+str);
       
          ResultSet rs=stmt.executeQuery(str);
          
          while(rs.next()){
                num = rs.getInt(1);
                 //System.out.println("test"+num);
            }
          
          System.out.println("Your request is placed. "+num+" drivers are able to take the request."); 
          
          int max_id=0;
           try{
                     Statement stmt2=con.createStatement();
                     String str2="SELECT MAX(R.id) FROM  request R";
                     ResultSet rs2=stmt2.executeQuery(str2);
                     rs2.next();
                     max_id=rs2.getInt(1);
                     
                 }catch(Exception e){
                     System.out.println(e);
                 }
           max_id++;
           
          try{
                     Statement stmt1=con.createStatement();
                     String str1="INSERT INTO request"
                             + " VALUES("+max_id+ ","+ID +",'"+start_location+"','"+destination+"','"+model+"',"+num_of_passengers+",0,"+min_driving_years+")";
                     //System.out.println("test"+str1);
                     stmt1.executeUpdate(str1);
                     //System.out.println("test_____insert success");
                    
                 }catch(Exception e){
                     System.out.println(e);
                 }
         }catch(Exception e){
             System.out.println("Sorry, no matching vehicle, please adjust the criteria");
         }
        
    }
     
    public static void check_trip_records(Connection con, Scanner scanner) throws SQLException{
       
        int ID=0;
       String start_date=null;
       String end_date=null;
       String destination=null;

       
        System.out.println("Please enter your ID.");
        ID=Integer.parseInt(scanner.nextLine());
        
       System.out.println("Please enter the start date.");
        start_date=scanner.nextLine();
        
       System.out.println("Please enter the end date.");
        end_date=scanner.nextLine();
        
       System.out.println("Please enter the destination.");
       destination=scanner.nextLine();
       
        try{
            if(con == null) System.out.println("not connected");           
         
         Statement stmt = con.createStatement();
         String str="SELECT T.id,D.name,D.vehicle_id,V.model,T.start_time,T.end_time,T.fee,T.start_location,T.destination"
                 + " FROM trip T,driver D,vehicle V"
                 + " WHERE T.passenger_id="+ ID
                 + " AND T.destination='"+destination+"'"
                 + " AND T.start_time>='"+start_date+"'"
                 + " AND T.end_time<='"+end_date+"'"
                 + " AND T.driver_id=D.id"
                 + " AND D.vehicle_id=V.id";
          //System.out.println("test"+str);
          
          ResultSet rs=stmt.executeQuery(str);
          
          System.out.println("Trip_id, Driver Name, Vehicle ID, Vehicle Model, Start, End, Fee, Start Location, Destination");
          
          while(rs.next()){
               System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","
                       +rs.getTimestamp(5)+","+rs.getTimestamp(6)+","+rs.getInt(7)+","+rs.getString(8)+","+rs.getString(9));
            }

         }catch(Exception e){
             System.out.println("Sorry, check failed");
         }
    }
    
}