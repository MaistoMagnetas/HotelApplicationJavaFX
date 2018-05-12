/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author PC
 */
public class Room {
    
    private int id;
    private boolean status;
    private String guestsHistory;
    
    public Room(){
        
    }
    
    public Room(int id,boolean status, String guestsHostory){
        this.id = id;
        this.status = status;
        this.guestsHistory = guestsHistory;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public boolean isRoomTaken(){
        return status;
    }
    
    public void setStatus(boolean status){
        this.status = status;
    }
    
    public String getHistory(){
        return guestsHistory;
    }
    
    public void setHistory(String guestsHistory){
        this.guestsHistory = guestsHistory;
    }
}
