/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pharmacymanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author PC
 */
public class database {

    public static Connection connectDb() {
        String strDbUrl = "jdbc:sqlserver://localhost:1433; databaseName=Java_Project;user=sa;password=sa;"
                + "encrypt=true;trustServerCertificate=true";

        try {

            Connection connect = DriverManager.getConnection(strDbUrl);
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
