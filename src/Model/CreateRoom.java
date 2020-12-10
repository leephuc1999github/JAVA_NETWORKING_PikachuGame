/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author Dell M4800
 */
public class CreateRoom {
    private User hoster;
    private Room room;
    private ArrayList<User> users = new ArrayList<>();

    public CreateRoom() {
    }

    public CreateRoom(User hoster, Room room) {
        this.hoster = hoster;
        this.room = room;
        this.users.add(hoster);
    }

    public User getHoster() {
        return hoster;
    }

    public void setHoster(User hoster) {
        this.hoster = hoster;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
    
}
