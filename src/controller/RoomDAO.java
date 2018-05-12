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
import model.Room;

/**
 *
 * @author PC
 */
public class RoomDAO {
    
    //Could be used for later expansion
    public void addRoom(Room room){
        String sql = "INSERT INTO `rooms`(`id`, `status`, `history`) VALUES (?,?,?)";
        try{
            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
            PreparedStatement addRoomStatement = (PreparedStatement) myConnection.prepareStatement(sql);
            addRoomStatement.setInt(1, room.getId());
            addRoomStatement.setBoolean(2, room.isRoomTaken());
            addRoomStatement.setString(3,room.getHistory());
            addRoomStatement.execute();
            addRoomStatement.close();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Sorry, database is not connected ");
        }
    }
    
    //If room is unavailable - needs fixing etc..
    public void removeRoom(Room room){
        String sql = "DELETE FROM `rooms` WHERE id = ?";
        try{
            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
            PreparedStatement deleteRoomStatement = (PreparedStatement) myConnection.prepareStatement(sql);
            deleteRoomStatement.setInt(1, room.getId());            
            deleteRoomStatement.executeUpdate();
	}catch(Exception e){
            e.printStackTrace();
            System.out.println("Sorry, database is not connected ");			
        } 
    }
    
     public void getAllRooms(ObservableList<Room> roomsList){
        String sql = "SELECT * FROM `rooms`";
        try{
            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
            PreparedStatement getAllRoomsStatement = (PreparedStatement) myConnection.prepareStatement(sql);
            ResultSet resultSet = getAllRoomsStatement.executeQuery();
            while (resultSet.next()) {
                roomsList.add(new Room(
                        resultSet.getInt("id"),
                        resultSet.getBoolean("status"),
                        resultSet.getString("history")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
