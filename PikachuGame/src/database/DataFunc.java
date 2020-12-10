package database;

import Model.User_Game;
import Model.Game;
import Model.Room;
import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
public class DataFunc extends Connections {

    public DataFunc() {
        super();
    }
    public ArrayList<String> getHistory(User user){
        ArrayList<String> result = new ArrayList<>();
        try {
            String query = "SELECT u.name,ug.score,ug.gameId from user_game AS ug \n" +
                "INNER JOIN user AS u ON u.id = ug.userId \n" +
                "INNER JOIN game as g ON g.id = ug.gameId\n" +
                "WHERE gameId IN (SELECT gameId FROM user_game WHERE userId = "+user.getId()+") ORDER BY g.timeStart DESC";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                String str = rs.getString("name")+ "-" + rs.getInt("score") + "-" + rs.getInt("gameId");
                result.add(str);
            }
        } catch (Exception e) {
        }
        return result;
    }
    public ArrayList<User> getRank(){
        ArrayList<User> result = new ArrayList<>();
        try {
            String query = "select name,address,soccer from user order by soccer desc";
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                User element = new User(rs.getString("name"),"","",rs.getString("address"),"",rs.getFloat("soccer"),1,true);
                result.add(element);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public Game createGame(Game game, Room room) {
        String addGame = "INSERT INTO game(roomId, timeStart, point, note) VALUES (?,?,?,?)";
        try {
            
            PreparedStatement ps = con.prepareStatement(addGame,  Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, room.getId());
            ps.setTimestamp(2, new java.sql.Timestamp(game.getTimeStart().getTime()));
            ps.setInt(3, 1);
            ps.setString(4, "");
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                game.setId(generatedKeys.getInt(1));
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return game;
    }

    public void addUserToRoom(User_Game user_Game, Game game) {
        String addUser = "INSERT INTO user_game (roomid,userid,hoster,score,gameid) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(addUser);
            ps.setInt(1, user_Game.getRoom().getId());
         
            ps.setInt(2, user_Game.getGuest().getId());
            ps.setBoolean(3, false);
            ps.setInt(4, 0);
            ps.setInt(5, game.getId());           
            ps.execute();
            ps.setInt(1, user_Game.getRoom().getId());
            ps.setInt(2, user_Game.getHoster().getId());
            ps.setBoolean(3, true);
            ps.setInt(4, 0);
            ps.setInt(5, game.getId());           
            ps.execute();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Room getRoom(int roomid) {
        Room room = new Room();
        try {
            String query = "SELECT * FROM room WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, roomid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                room.setId(roomid);
                room.setName(rs.getInt("name"));
                room.setTimeInit(rs.getTimestamp("timeInit"));
                room.setDescription("");
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return room;
    }

    public void updateScore(int gameid, User user, int score) {
        String updateUser = "UPDATE user_game SET score=? WHERE gameid=? AND userid=?";
        try {
            PreparedStatement ps = con.prepareStatement(updateUser);
            ps.setInt(1, score);
            ps.setInt(2, gameid);
            ps.setInt(3, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    public User checkUser(User user) {
        User result = new User();
        String checkUserQr = "SELECT * FROM user WHERE username = ? AND password = ? AND onlines = false";
        //
        try {
            PreparedStatement ps = con.prepareStatement(checkUserQr);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            boolean check = false;
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
                check = true;
                break;
            }
            // cập nhật trạng thái online cho người dùng
            if (check) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean logOut(User user) {
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

    public Room createRoom(Room room) {
        String sql = "INSERT INTO room( timeInit, name, description) VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setTimestamp(1, new java.sql.Timestamp(room.getTimeInit().getTime()));
            ps.setInt(2, room.getName());
            ps.setString(3, room.getDescription());
            ps.executeUpdate();
//            //get id of the new inserted client
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setId(generatedKeys.getInt(1));
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }

    public ArrayList<User> getAllUserOnline(User user) {
        ArrayList<User> result = new ArrayList<>();
        String query = "SELECT * FROM user WHERE onlines=true AND id != ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setAddress(rs.getString("address"));
                u.setSdt(rs.getString("sdt"));
                u.setSoccer(rs.getFloat("soccer"));
                u.setLevel(rs.getInt("level"));
                u.setOnlines(true);

                result.add(u);
            }
            ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public HashMap<Integer, Integer> getUser_Game(int idGame){
        HashMap<Integer, Integer> result = new HashMap<>();
        try {

            String query = "select score,userId from user_game where gameId=" + idGame;
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next()) {    
               result.put(rs.getInt("userId"), rs.getInt("score"));
            }
            
           
        } catch (SQLException ex) {
            Logger.getLogger(DataFunc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public void updatePointUser(User user,float point){
        String updatePointQr = "UPDATE user SET soccer=soccer+? WHERE id= ?";
        try {
            PreparedStatement ps = con.prepareStatement(updatePointQr);
            //trạng thái online cho người dùng
            ps = con.prepareStatement(updatePointQr);
            ps.setFloat(1, point);
            ps.setInt(2, user.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
