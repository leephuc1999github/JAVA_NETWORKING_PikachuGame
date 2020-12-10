/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Model.User_Game;
import Model.DataPackage;
import Model.*;
import codepicachu.Algorithm;
import com.google.gson.Gson;
import database.DataFunc;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ClientHandler extends Thread {

    private Socket socket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    User user;
    User server = new User("server", "server", "server", "server", "server", 0, 0, true);
//    ArrayList<ResultGame> lstResultGame = new ArrayList<>();
    HashMap<Integer, ArrayList<ResultGame>> lstResultGame = new HashMap<>();
    int count = 0;
    //public static Game game1;
    //public static Game game2;

    Boolean execute = true;

    ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.user = new User();
            execute = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void ReceiveMessage(DataPackage dataPackage) throws IOException {
        DataFunc dataFunc = new DataFunc();
        String value = dataPackage.getValue();
        User paramater;
        switch (dataPackage.getKey()) {
            case "requestGetHistory":
                ArrayList<String> history = dataFunc.getHistory(user);
                SendMessage(new DataPackage(server, "respondHistory", new Gson().toJson(history)));
                break;
            case "requestGetRank":
                ArrayList<User> rank = dataFunc.getRank();
                SendMessage(new DataPackage(server, "respondRank", new Gson().toJson(rank)));
                break;
            case "requestLogin":
                User infoLogin = new Gson().fromJson(value, User.class);
                this.user = dataFunc.checkUser(infoLogin);
                if (Main.lstClient.containsKey(this.socket)) {
                    Main.lstClient.put(this.socket, this);
                }
                SendMessage(new DataPackage(server, "respondLogin", new Gson().toJson(this.user)));
                break;
            case "requestLogout":
                User infoLogout = new Gson().fromJson(value, User.class);
                SendMessage(new DataPackage(server, "respondLogout", new Gson().toJson(dataFunc.logOut(infoLogout))));
                if (Main.lstClient.containsKey(this.socket)) {
                    Main.lstClient.remove(this.socket);
                }
                break;
            case "requestUpdateInfo":
                User infoUserUpdate = new Gson().fromJson(value, User.class);
                SendMessage(new DataPackage(server, "respondUpdateInfo", new Gson().toJson(dataFunc.updateProfile(infoUserUpdate))));
                break;
            case "requestRegisterUser":
                User infoRegister = new Gson().fromJson(value, User.class);
                SendMessage(new DataPackage(server, "respondRegisterUser", new DataFunc().addUser(infoRegister) + ""));
                break;
            case "requestCreateRoom":
                //User_Game cr = new Gson().fromJson(value, User_Game.class);
                //cr.setRoom(dataFunc.createRoom(cr.getRoom()));
                Room newroom = dataFunc.createRoom(new Gson().fromJson(dataPackage.getValue(), Room.class));
                SendMessage(new DataPackage(server, "respondCreateRoom", new Gson().toJson(newroom)));
                break;
            case "requestGetUserOnline":
                User infoUserNeedGetAllUser = new Gson().fromJson(value, User.class);
                ArrayList<User> result = dataFunc.getAllUserOnline(infoUserNeedGetAllUser);
                String infoListUser = new Gson().toJson(result);
                SendMessage(new DataPackage(server, "respondGetUserOnline", infoListUser));
                break;
            case "requestInvitePlay":
                User bost = user;
                Pattern pattern = Pattern.compile("-");
                String[] contain = pattern.split(value);
                int guestid = Integer.parseInt(contain[0]);
                int rooid = Integer.parseInt(contain[1]);
                Room room = dataFunc.getRoom(rooid);
                User_Game user_Game = new User_Game(bost, null, room, 0, 0);
                for (ClientHandler ch : Main.lstClient.values()) {
                    if (ch.user.getId() == guestid) {
                        ch.SendMessage(new DataPackage(server, "respondInviteToGuest", new Gson().toJson(user_Game)));
                        break;
                    }
                }
                break;
            case "requestJoinRoom":
                User_Game roomOfBost = new Gson().fromJson(value, User_Game.class);
                for (Map.Entry e : Main.lstClient.entrySet()) {
                    ClientHandler ch = (ClientHandler) e.getValue();
                    if (ch.user.getId() == roomOfBost.getHoster().getId()) {
                        ch.SendMessage(new DataPackage(server, "respondToBostReloadRoom", new Gson().toJson(roomOfBost)));
                        break;
                    }
                }
                break;
            case "requestLeaveRoom":
                User_Game inforRoom = new Gson().fromJson(dataPackage.getValue(), User_Game.class);
                User sender = dataPackage.getSender();
                if (sender.getId() == inforRoom.getHoster().getId()) {
                    inforRoom.setHoster(inforRoom.getGuest());
                }
                inforRoom.setGuest(null);
                SendMessage(new DataPackage(server, "respondGuestLeaveRoomSuccess", "true"));
                for (Map.Entry e : Main.lstClient.entrySet()) {
                    ClientHandler ch = (ClientHandler) e.getValue();
                    if (ch.user.getId() == inforRoom.getHoster().getId()) {
                        ch.SendMessage(new DataPackage(server, "respondBostReloadWhenGuestLeaveRoom", new Gson().toJson(inforRoom)));
                        break;
                    }
                }
                break;
            case "requestLeaveGame":
                break;
            case "requestReady":
                User bost1 = new Gson().fromJson(dataPackage.getValue(), User.class);
                for (Map.Entry e : Main.lstClient.entrySet()) {
                    ClientHandler ch = (ClientHandler) e.getValue();
                    if (ch.user.getId() == bost1.getId()) {
                        ch.SendMessage(new DataPackage(server, "respondReady", "true"));
                        break;
                    }
                }
                break;
            case "requestCreateGame":
                User_Game cr = new Gson().fromJson(value, User_Game.class);
                Algorithm renderMatrix = new Algorithm(12, 12);
                Game game = new Game(1, new java.util.Date());
                game = dataFunc.createGame(game, cr.getRoom());
                dataFunc.addUserToRoom(cr, game);
                for (Map.Entry e : Main.lstClient.entrySet()) {
                    ClientHandler ch = (ClientHandler) e.getValue();
                    if (ch.user.getId() == cr.getGuest().getId() || ch.user.getId() == cr.getHoster().getId()) {
                        ch.SendMessage(new DataPackage(server, "respondInforGame", new Gson().toJson(game)));
                        ch.SendMessage(new DataPackage(server, "respondGame", new Gson().toJson(renderMatrix)));
                    }
                }
                break;
            case "SendScore":
                sender = dataPackage.getSender();
                String gameScore = dataPackage.getValue();
                pattern = Pattern.compile("-");
                contain = pattern.split(gameScore);
                int gameid = Integer.parseInt(contain[0]);
                int score = Integer.parseInt(contain[1]);
                dataFunc.updateScore(gameid, user, score);
                System.out.println("gui ket qua" + sender.getName());
                break;
            case "requestGetResult":
            
                try {
                    new Thread().sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            
                sender = dataPackage.getSender();
                gameid = Integer.parseInt(value);
                HashMap<Integer, Integer> u = new HashMap<>();
                do {
                    u = dataFunc.getUser_Game(gameid);
                    if(u.size()==2){
                        int scoreSender = 0;
                        int scoreAgain = 0;
                        for(Map.Entry e : u.entrySet()){
                            if((int)e.getKey()==sender.getId()){
                                scoreSender = (int) e.getValue();
                            }else{
                                scoreAgain = (int) e.getValue();
                            }
                        }
                        float point = 0;
                        if(scoreSender > scoreAgain){
                            SendMessage(new DataPackage(server, "respondResultGame", "Win"));
                            System.out.println("tra ve ket qua" + sender.getName() + "win");
                            point = 1;      
                        }else if(scoreSender < scoreAgain){
                            SendMessage(new DataPackage(server, "respondResultGame", "Lose"));
                            System.out.println("tra ve ket qua" + sender.getName() + "lose");
                         }else{      
                            SendMessage(new DataPackage(server, "respondResultGame", "Draw Battble"));
                            System.out.println("tra ve ket qua" + sender.getName() + "hoa");
                            point = (float) 0.5;
                        }
                        dataFunc.updatePointUser(user, point);
                        break;
                    }
                } while (u.size()!= 2);
                break;


        }
    }

    public void SendMessage(DataPackage dataPackage) throws IOException {
        outputStream.reset();
        outputStream.writeObject(dataPackage);
    }

    @Override
    public void run() {
        if (!execute) {
            this.socket = null;
            execute = true;
        }
        while (execute) {
            try {
                Object o = inputStream.readObject();
                if (o != null) {
                    ReceiveMessage((DataPackage) o);
                }
            } catch (IOException e) {

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

class ResultGame {

    User user;
    int gameid;
    int score;

    public ResultGame(User user, int gameid, int score) {
        this.user = user;
        this.gameid = gameid;
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
