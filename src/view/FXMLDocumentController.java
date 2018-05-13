/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GuestDAO;
import controller.RoomDAO;
import controller.Validation;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import model.Guest;
import model.Room;

/**
 *
 * @author PC
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button btn_sb_home, btn_sb_register_guest, btn_sb_remove_guest, btn_sb_hotel_status,
            btn_registration_submit, btn_remove_submit;
    
    @FXML
    private Pane pane_home, pane_register_guest, pane_remove_guest, pane_hotel_status,
            pane_hotelstatus_first,pane_hotelstatus_second,pane_hotelstatus_third,
            pane_hotelstatus_fourth,pane_hotelstatus_fifth;
    
    @FXML
    private ImageView hotel_status_first, hotel_status_second, hotel_status_third,
            hotel_status_fourth, hotel_status_fifth;
               
    @FXML
    private TextField tf_registration_name, tf_registration_surname, tf_remove_name,
            tf_remove_surname;
    
    @FXML 
    private Text home_text_freerooms;
    
    private ArrayList<ImageView> hotelStatusPaneList;
    private Image ivRoomFree, ivRoomTaken;
    private int freeRoomsNumber,freeRoomNumber;
    
    
    @FXML
    private void handleButtonActionSidebar(ActionEvent event) {
        if(event.getSource() == btn_sb_register_guest){
            pane_register_guest.toFront();
        }else if(event.getSource() == btn_sb_remove_guest){
            pane_remove_guest.toFront();
        }else if(event.getSource() == btn_sb_hotel_status){
            pane_hotel_status.toFront();
            hotelStatusFromSQL();
        }else{
           pane_home.toFront();
           home_text_freerooms.setText(String.valueOf(freeRoomsNumber = hotelStatusFromSQL()));
        }
    }
    
    @FXML
    private void handleButtonActionRegistration(ActionEvent event) {
        //Validate textField data
        String guestName = tf_registration_name.getText().toString();
        String guestSurname = tf_registration_surname.getText().toString();
        textValidationForNameAndSurname(guestName,guestSurname);
        
        freeRoomNumber = returnFreeRoomNumber();
        if(freeRoomNumber>0 && freeRoomNumber<=5){
            Guest guest = new Guest(guestName,guestSurname,freeRoomNumber,true);
            GuestDAO guestDao = new GuestDAO();
            guestDao.addGuest(guest);
        }else{
            showAlert(Alert.AlertType.ERROR,"Failed",String.format("Sorry but there are no free rooms",guest.getName(),guest.getSurname()));
        }               
    }   
    
    //Checks if user is in database and removes it
    @FXML
    private void handleButtonActionRemoval(ActionEvent event) {
        String guestName = tf_remove_name.getText().toString();
        String guestSurname = tf_remove_surname.getText().toString();        
        textValidationForNameAndSurname(guestName,guestSurname);        
        GuestDAO guestDao = new GuestDAO();
        RoomDAO roomDao = new RoomDAO();
        ObservableList<Guest> guestsList= FXCollections.observableArrayList();
        guestDao.getAllGuests(guestsList);
        for(Guest guest: guestsList){            
            if(guest.getName().equals(guestName) && guest.getSurname().equals(guestSurname) && guest.isGuestActive()==true){
                roomDao.changeRoomAvailability(false,guest.getRoomNum());
                guestDao.removeGuestFromRoom(guest);                
                showAlert(Alert.AlertType.CONFIRMATION,"Success",String.format("Guest %s %s successfully removed"));
                break;
            }
        }        
        System.out.println(guestName);
        System.out.println(guestSurname);
    }
    
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        hotelStatusPaneList = new ArrayList<ImageView>();
        hotelStatusPaneList.add(hotel_status_first);
        hotelStatusPaneList.add(hotel_status_second);
        hotelStatusPaneList.add(hotel_status_third);
        hotelStatusPaneList.add(hotel_status_fourth);
        hotelStatusPaneList.add(hotel_status_fifth);
        
        try{
           ivRoomFree = new Image(new FileInputStream("C:\\Users\\PC\\\\Documents\\NetBeansProjects\\NoMagicHotel\\src\\view\\green.png"));
           ivRoomTaken = new Image(new FileInputStream("C:\\Users\\PC\\\\Documents\\NetBeansProjects\\NoMagicHotel\\src\\view\\red.png"));
        }catch(Exception ex){
            ex.printStackTrace();
        }   
        
        home_text_freerooms.setText(String.valueOf(freeRoomsNumber = hotelStatusFromSQL()));
        
    }    
    
    private void showAlert(Alert.AlertType alerType, String title, String message){
	Alert alert = new Alert(alerType);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.show();
    }
    
    //Checks free rooms in hotel
    private int hotelStatusFromSQL(){
        int freeRooms = 5;
        RoomDAO roomDao = new RoomDAO();
        ObservableList<Room> roomsList= FXCollections.observableArrayList();
        roomDao.getAllRooms(roomsList);            
        for(int i = 0;i<roomsList.size();i++){            
            if(roomsList.get(i).isRoomTaken() == true){                
                hotelStatusPaneList.get(i).setImage(ivRoomTaken);
                freeRooms = freeRooms - 1;
            }else{             
               hotelStatusPaneList.get(i).setImage(ivRoomFree);
            }
        }
        return freeRooms;
    }
    
    //Return free room number
    private int returnFreeRoomNumber(){        
        int freeRoomNumber = -1;
        RoomDAO roomDao = new RoomDAO();
        ObservableList<Room> roomsList= FXCollections.observableArrayList();
        roomDao.getAllRooms(roomsList);            
        for(int i = 0;i<roomsList.size();i++){            
            if(roomsList.get(i).isRoomTaken() == false){            
                freeRoomNumber = i+1;
                roomDao.changeRoomAvailability(true,freeRoomNumber);
                break;
            }
        }
        return freeRoomNumber;
    }
    
    private void textValidationForNameAndSurname(String guestName, String guestSurname){
        if(!Validation.isValidCredentials(guestName)){
            showAlert(Alert.AlertType.ERROR,"Error","Name must only include A-Z(a-z) characters");
            return;
        }
        if(!Validation.isValidCredentials(guestSurname)){
            showAlert(Alert.AlertType.ERROR,"Error","Surname must only include A-Z(a-z) characters");
            return;
        }
    }
    
}
