/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author DUONGNV
 */
public class Game implements Serializable {
    private int id;
    private int score;
    private Date timeStart;

    public Game(int id, int score, Date timeStart) {
        this.id = id;
        this.score = score;
        this.timeStart = timeStart;
    }

    public Game(int score, Date timeStart) {
        this.score = score;
        this.timeStart = timeStart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }
    
}
