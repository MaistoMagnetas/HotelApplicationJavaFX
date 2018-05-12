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
    
    private ArrayList<ImageView> hotelStatusPaneList;
    private Image ivRoomFree, ivRoomTaken;
    
    
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
        }
    }
    
    @FXML
    private void handleButtonActionRegistration(ActionEvent event) {
        //Validate textField data
        String guestName = tf_registration_name.getText().toString();
        String guestSurname = tf_registration_surname.getText().toString();
        if(!Validation.isValidCredentials(guestName)){
            showAlert(Alert.AlertType.ERROR,"Error","Name must only include A-Z(a-z) characters");
            return;
        }
        if(!Validation.isValidCredentials(guestSurname)){
            showAlert(Alert.AlertType.ERROR,"Error","Surname must only include A-Z(a-z) characters");
            return;
        }
        //If data validated -> register new user
        //TO-DO check if there is room in hotel
        Guest guest = new Guest(guestName,guestSurname);
        GuestDAO guestDao = new GuestDAO();
        guestDao.addGuest(guest);
        
        System.out.println(guestName);
        System.out.println(guestSurname);
    }
    
    
    //Checks if user is in database and removes it
    @FXML
    private void handleButtonActionRemoval(ActionEvent event) {
        String guestName = tf_remove_name.getText().toString();
        String guestSurname = tf_remove_surname.getText().toString();        
        GuestDAO guestDao = new GuestDAO();
        ObservableList<Guest> guestsList= FXCollections.observableArrayList();
        guestDao.getAllGuests(guestsList);
        for(Guest guest: guestsList){            
            if(guest.getName().equals(guestName) && guest.getSurname().equals(guestSurname)){
                guestDao.removeGuest(guest);
                showAlert(Alert.AlertType.CONFIRMATION,"Success",String.format("Guest %s %s successfully removed",guest.getName(),guest.getSurname()));
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
        
    }    
    
    private void showAlert(Alert.AlertType alerType, String title, String message){
	Alert alert = new Alert(alerType);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.show();
    }
    
    private void hotelStatusFromSQL(){       
        RoomDAO roomDao = new RoomDAO();
        ObservableList<Room> roomsList= FXCollections.observableArrayList();
        roomDao.getAllRooms(roomsList);            
        for(int i = 0;i<roomsList.size();i++){            
            if(roomsList.get(i).isRoomTaken() == true){                
                hotelStatusPaneList.get(i).setImage(ivRoomTaken);               
               //hotelStatusPaneList.get(i).setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
            }else{
               //hotelStatusPaneList.get(i).setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY))); 
               hotelStatusPaneList.get(i).setImage(ivRoomFree);
            }
        }
    }
    
}
