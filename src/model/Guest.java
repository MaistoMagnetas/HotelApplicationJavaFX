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
public class Guest {
    
   private int id;
   private String name;
   private String surname;
   private int roomNum;
   private boolean guestStatus;
   
   public Guest(){
       
   }
   
   public Guest(String name, String surname){
       this.name = name;
       this.surname = surname;
   }
   
   public Guest(String name, String surname, int roomNum, boolean guestStatus){
       this.name = name;
       this.surname = surname;
       this.roomNum = roomNum;
       this.guestStatus = guestStatus;
   }
   
   public Guest(int id, String name, String surname, int roomNum, boolean guestStatus){
       this.id = id;
       this.name = name;
       this.surname = surname;
       this.roomNum = roomNum;
       this.guestStatus = guestStatus;       
   }
   
   public int getId(){
       return id;
   }
   
   public String getName(){
       return name;
   }
   
   public void setName(String name){
       this.name = name;
   }
   
   public String getSurname(){
       return surname;
   }
   
   public void setSurname(String surname){
       this.surname = surname;
   }
   
   public int getRoomNum(){
       return roomNum;
   }
   
   public void setRoomNum(int roomNum){
       this.roomNum = roomNum;
   }
   
   public boolean isGuestActive(){
       return guestStatus;
   }
   
   public void setGuestStatus(boolean guestStatus){
       this.guestStatus = guestStatus;
   }
   
   
   
   
}
