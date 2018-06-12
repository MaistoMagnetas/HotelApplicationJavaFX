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
    
//    //Could be used for later expansion
//    public void addRoom(Room room){               
//        String sql = "INSERT INTO `rooms`(`id`, `status`) VALUES (?,?)";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement addRoomStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            addRoomStatement.setInt(1, room.getId());
//            addRoomStatement.setBoolean(2, room.isRoomTaken());
//            addRoomStatement.execute();
//            addRoomStatement.close();
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Sorry, database is not connected ");
//        }
//    }
    
//    //If room is unavailable - needs fixing etc..
//    public void removeRoom(Room room){
//        String sql = "DELETE FROM `rooms` WHERE id = ?";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement deleteRoomStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            deleteRoomStatement.setInt(1, room.getId());            
//            deleteRoomStatement.executeUpdate();
//	}catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Sorry, database is not connected ");			
//        } 
//    }
    
     public void getAllRooms(ObservableList<Room> roomsList){
//        String sql = "SELECT * FROM `rooms`";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement getAllRoomsStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            ResultSet resultSet = getAllRoomsStatement.executeQuery();
//            while (resultSet.next()) {
//                roomsList.add(new Room(
//                        resultSet.getInt("id"),
//                        resultSet.getBoolean("status")));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }
     
    public void changeRoomAvailability(boolean availability,int integer, ObservableList<Room> roomsList){
        roomsList.get(integer).setStatus(availability);            
//          String sql = "UPDATE `rooms` SET `status`= ? WHERE `id` = ?";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement setRoomAsTakenStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            setRoomAsTakenStatement.setBoolean(1, availability);
//            setRoomAsTakenStatement.setInt(2, integer);              
//            setRoomAsTakenStatement.executeUpdate();
//	}catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Sorry, database is not connected ");			
//        }     
    }
    
   
    
    
}
