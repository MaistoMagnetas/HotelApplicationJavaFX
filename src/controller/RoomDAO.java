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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Guest;
import model.Room;

/**
 *
 * @author PC
 */
public class RoomDAO {
    
    private Room room;
    private ObservableList<Room> roomsList = FXCollections.observableArrayList();
    
    //Init 5(integer as variable) rooms at app start
    public ObservableList<Room> createHotelRooms(int roomNumber){
        for(int i = 1;i<=5;i++){
            room = new Room(i,false); //at start all rooms are free
            roomsList.add(room);
        }
        return roomsList;
    }
    
    public ObservableList<Room> getHotelRooms(){
        return roomsList;
    } 
    
    public void changeRoomAvailability(boolean availability,int integer, ObservableList<Room> roomsList){
        roomsList.get(integer).setStatus(availability);  
    }
}
