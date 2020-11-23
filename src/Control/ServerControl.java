/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ServerControl {

    // khởi tạo host server
    private ServerSocket myServer;
    private int serverPort = 8889;
    // kết nối database
    public static Connection con;
    private String dbUrl = "jdbc:mysql://127.0.0.1:3306/btlltm";
    private String nameUser = "root";
    private String passwordUser = "";

    public ServerControl() {
        getDBConnection(dbUrl, nameUser, passwordUser);
        openServer(serverPort);
        while (true) {
            listenning();
        }
    }

    // hàm kết nối cơ sở dữ liệu
    private void getDBConnection(String dbUrl, String nameUser, String passwordUser) {
        System.out.println("connect db ...");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(dbUrl, nameUser, passwordUser);
            if (con != null) {
                System.out.println("connect success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // hàm tạo server host
    private void openServer(int portNumber) {
        try {
            System.out.println("mở server");
            myServer = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "errorPort");
        }
    }

    // hàm lắng nghe sự kiện từ client
    private void listenning() {
        try {
            Socket clientSocket = myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            Object o = ois.readObject();
            if (o instanceof DataPackage) {
                DataPackage dp = (DataPackage) o;
                String value;
                User user;
                switch (dp.getKey()) {
                    case "login":
                        value = dp.getValue();
                        user = new Gson().fromJson(value, User.class);
                        user = checkUser(user);
                        oos.writeObject(new DataPackage("login", new Gson().toJson(user)));
                        break;
                    case "logout":
                        value = dp.getValue();
                        user = new Gson().fromJson(value, User.class);
                        oos.writeObject(new DataPackage("logout", new Gson().toJson(logOut(user))));
                        break;
                    case "updateProfile":
                        value = dp.getValue();
                        user = new Gson().fromJson(value, User.class);
                        oos.writeObject(new DataPackage("updateProfile", new Gson().toJson(updateProfile(user))));
                        break;
                    case "register":
                        value = dp.getValue();
                        user = new Gson().fromJson(value, User.class);
                        oos.writeObject(new DataPackage("register", new Gson().toJson(addUser(user))));
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // hàm xử lý với cơ sở dữ liệu
    public boolean addUser(User user) {
        boolean result = false;
        String sql = "INSERT INTO user(name, username, password, address, sdt, soccer, level, onlines) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getSdt());
            ps.setFloat(6, user.getSoccer());
            ps.setInt(7, user.getLevel());
            ps.setBoolean(8, user.isOnlines());

            ps.executeUpdate();
            ps.close();
            return true;
            //get id of the new inserted client
//            ResultSet generatedKeys = ps.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                user.setId(generatedKeys.getInt(1));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateProfile(User user) {
        boolean result = false;
        String updateUser = "UPDATE user SET name=?, password=?, address=?, sdt=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(updateUser);
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getAddress());
            ps.setString(4, user.getSdt());
            ps.setInt(5, user.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private User checkUser(User user) {
        User result = new User();
        String checkUserQr = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(checkUserQr);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.setId(rs.getInt("id"));
                result.setName(rs.getString("name"));
                result.setUsername(rs.getString("username"));
                result.setPassword(rs.getString("password"));
                result.setAddress(rs.getString("address"));
                result.setSdt(rs.getString("sdt"));
                result.setSoccer(rs.getFloat("soccer"));
                result.setLevel(rs.getInt("level"));
                result.setOnlines(true);
                break;
            }
            // cập nhật trạng thái online cho người dùng
            String updateStatusQr = "UPDATE user SET onlines=true WHERE id= ?";
            try {
                ps = con.prepareStatement(updateStatusQr);
                ps.setInt(1, result.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                result = new User();
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean logOut(User user) {
        boolean result = false;
        String updateStatusQr = "UPDATE user SET onlines=false WHERE id= ?";
        try {
            PreparedStatement ps = con.prepareStatement(updateStatusQr);
            //trạng thái online cho người dùng
            ps = con.prepareStatement(updateStatusQr);
            ps.setInt(1, user.getId());
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
