/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GuestDAO;
import controller.Validation;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import model.Guest;

/**
 *
 * @author PC
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button btn_sb_home, btn_sb_register_guest, btn_sb_remove_guest, btn_sb_hotel_status,
            btn_registration_submit, btn_remove_submit;
    
    @FXML
    private Pane pane_home, pane_register_guest, pane_remove_guest, pane_hotel_status;
    
    @FXML
    private TextField tf_registration_name, tf_registration_surname, tf_remove_name,
            tf_remove_surname;
    
    @FXML
    private void handleButtonActionSidebar(ActionEvent event) {
        if(event.getSource() == btn_sb_register_guest){
            pane_register_guest.toFront();
        }else if(event.getSource() == btn_sb_remove_guest){
            pane_remove_guest.toFront();
        }else if(event.getSource() == btn_sb_hotel_status){
            pane_hotel_status.toFront();
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
        // TODO
    }    
    
    private void showAlert(Alert.AlertType alerType, String title, String message){
	Alert alert = new Alert(alerType);
	alert.setTitle(title);
	alert.setHeaderText(null);
	alert.setContentText(message);
	alert.show();
    }
    
}
