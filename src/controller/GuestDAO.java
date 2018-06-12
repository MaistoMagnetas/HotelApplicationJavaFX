/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import model.Guest;

/**
 *
 * @author PC
 */
public class GuestDAO {
    
    public void addGuest(Guest guest,ObservableList<Guest> activeGuestsList){
        if(activeGuestsList.size() < 5){
            activeGuestsList.add(guest);
            //System.out.printf("Guest %s successfully registered to the hotel",guest.getName());
        }else{
            //System.out.println("Sorry. Hotel is currentlly full.");
        }       
    }
    
    public void addGuestHistory(Guest guest, ObservableList<Guest> historyGuestList){
        historyGuestList.add(guest);
    }
    
    public void removeGuestFromRoom(Guest guest, ObservableList<Guest> activeGuestsList){
        if(activeGuestsList.contains(guest)){
            activeGuestsList.remove(guest);
            //System.out.printf("Guest: %s removed",guest.getName());
        }else{
            //System.out.printf("Sorry. Guest %s was not found as active guest",guest.getName());
        }      
    }              
}
        
    

