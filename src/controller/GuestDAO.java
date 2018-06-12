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
            System.out.printf("Guest %s successfully registered to the hotel",guest.getName());
        }else{
            System.out.println("Sorry. Hotel is currentlly full.");
        }
        
//        String sql = "INSERT INTO `guests`(`name`, `surname`,`roomnum`,`gueststatus`) VALUES (?,?,?,?)";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement addGuestStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            addGuestStatement.setString(1, guest.getName());
//            addGuestStatement.setString(2, guest.getSurname());
//            addGuestStatement.setInt(3, guest.getRoomNum());
//            addGuestStatement.setBoolean(4, guest.isGuestActive());
//            addGuestStatement.execute();
//            addGuestStatement.close();
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Sorry, database is not connected ");
//        }
    }
    
    public void removeGuestFromRoom(Guest guest, ObservableList<Guest> activeGuestsList){
        if(activeGuestsList.contains(guest)){
            activeGuestsList.remove(guest);
            System.out.printf("Guest: %s removed",guest.getName());
        }else{
            System.out.printf("Sorry. Guest %s was not found as active guest",guest.getName());
        }
//        String sql = "UPDATE `guests` SET `roomnum`= ?,`gueststatus`= ? WHERE `name` = ? AND `surname` = ?";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement deleteGuestStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            deleteGuestStatement.setInt(1, guest.getRoomNum());
//            deleteGuestStatement.setBoolean(2, false);
//            deleteGuestStatement.setString(3, guest.getName());
//            deleteGuestStatement.setString(4, guest.getSurname());
//            deleteGuestStatement.executeUpdate();
//	}catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Sorry, database is not connected ");			
//        } 
    }
      
    public void getAllGuests(ObservableList<Guest> guestsList){
          
//        String sql = "SELECT * FROM `guests`";
//        try{
//            Connection myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nomagichotel", "root", "");
//            PreparedStatement getAllGuestStatement = (PreparedStatement) myConnection.prepareStatement(sql);
//            ResultSet resultSet = getAllGuestStatement.executeQuery();
//            while (resultSet.next()) {
//                guestsList.add(new Guest(
//                        resultSet.getInt("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("surname"),
//                        resultSet.getInt("roomnum"),
//                        resultSet.getBoolean("gueststatus")));
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    }              
}
        
    

