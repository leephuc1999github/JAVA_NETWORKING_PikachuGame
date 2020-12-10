package Model;

import java.io.Serializable;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell M4800
 */
public class Room implements Serializable{
    private int id;
    private int name;
    private Date timeInit;
    private String description;

    public Room(int id, int name, Date timeInit, String description ) {
        this.id = id;
        this.name = name;
        this.timeInit = timeInit;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Date getTimeInit() {
        return timeInit;
    }

    public void setTimeInit(Date timeInit) {
        this.timeInit = timeInit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public Room() {
    }

    public Room(int name, Date timeInit, String description) {
        this.name = name;
        this.timeInit = timeInit;
        this.description = description;
    }

   
    
}
