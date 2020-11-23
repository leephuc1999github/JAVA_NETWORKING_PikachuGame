/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

/**
 *
 * @author DELL
 */
public class User implements Serializable{
    private int id;
    private String name;
    private String username;
    private String password;
    private String address;
    private String sdt;
    private float soccer;
    private int level;
    private boolean onlines;
    

    public User() {
        super();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    
    public User(String name, String username, String password, String address, String sdt, float soccer, int level, boolean onlines) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.sdt = sdt;
        this.soccer = soccer;
        this.level = level;
        this.onlines = onlines;
    }

    public User(int id, String name, String username, String password, String address, String sdt, float soccer, int level, boolean onlines) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.address = address;
        this.sdt = sdt;
        this.soccer = soccer;
        this.level = level;
        this.onlines = onlines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public float getSoccer() {
        return soccer;
    }

    public void setSoccer(float soccer) {
        this.soccer = soccer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isOnlines() {
        return onlines;
    }

    public void setOnlines(boolean onlines) {
        this.onlines = onlines;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", address=" + address + ", sdt=" + sdt + ", soccer=" + soccer + ", level=" + level + ", onlines=" + onlines + '}';
    }
    
    
}
