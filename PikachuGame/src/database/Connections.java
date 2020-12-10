package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class Connections {
    
    public Connection con;

    public Connections() {
        if(con == null ){
            String dbUrl = "jdbc:mysql://localhost:3306/btlltm";
            String nameUser = "root";
            String passwordUser = "";
            try {
                Class.forName("com.mysql.jdbc.Driver");
                try {
                    con = DriverManager.getConnection(dbUrl, nameUser, passwordUser);
                } catch (SQLException ex) {
                    Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Connections.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
