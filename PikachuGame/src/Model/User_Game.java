/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


/**
 *
 * @author Dell M4800
 */
public class User_Game {
    private User hoster;
    private User guest;
    private Room room;
    private int pointHost;
    private int pointGuest;
    

    public User_Game() {
    }

    public User_Game(User hoster, User guest, Room room, int pointHost, int pointGuest) {
        this.hoster = hoster;
        this.guest = guest;
        this.room = room;
        this.pointHost = pointHost;
        this.pointGuest = pointGuest;
    }
    
    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public int getPointHost() {
        return pointHost;
    }

    public void setPointHost(int pointHost) {
        this.pointHost = pointHost;
    }

    public int getPointGuest() {
        return pointGuest;
    }

    public void setPointGuest(int pointGuest) {
        this.pointGuest = pointGuest;
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
}
