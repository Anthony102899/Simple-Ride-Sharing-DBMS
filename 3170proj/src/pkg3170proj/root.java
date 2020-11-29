/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3170proj;

import java.util.*;
import java.sql.*;
/**
 *
 * @author 24111
 */
public class root {
    public static int initial_page(){
        System.out.println("Welcome! Who are you?");
        System.out.println("1. An administrator");
        System.out.println("2. A passenger");
        System.out.println("3. A driver");
        System.out.println("4. A manager");
        System.out.println("5. Quit the programme");
        System.out.println("Please enter [1-5]");
        Scanner s = new Scanner(System.in);
        String root = s.nextLine();
        return Integer.parseInt(root);
    }
}
