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
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
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
            pane_hotelstatus_fourth,pane_hotelstatus_fifth,sidebar_pane_color_home,
            sidebar_pane_color_status,sidebar_pane_color_delguest,sidebar_pane_color_regguest;
    
    @FXML
    private ImageView hotel_status_first, hotel_status_second, hotel_status_third,
            hotel_status_fourth, hotel_status_fifth;
               
    @FXML
    private TextField tf_registration_name, tf_registration_surname, tf_remove_name,
            tf_remove_surname;
    
    @FXML
    private PieChart home_piechart;
    
    @FXML 
    private Text home_text_freerooms,home_text_guesthistory,home_activeguests;
        
    private ArrayList<ImageView> hotelStatusPaneList;
    private Image ivRoomFree, ivRoomTaken;
    private int freeRoomsNumber,freeRoomNumber;
    private Room room;
    private Guest guest;
    private GuestDAO guestDao;
    private RoomDAO roomDao;
   
    
    @FXML
    private void handleButtonActionSidebar(ActionEvent event) {
        if(event.getSource() == btn_sb_register_guest){
            highlightSidebar(2);
            pane_register_guest.toFront();        
        }else if(event.getSource() == btn_sb_remove_guest){
            highlightSidebar(3);
            pane_remove_guest.toFront();            
        }else if(event.getSource() == btn_sb_hotel_status){
            highlightSidebar(4);
            pane_hotel_status.toFront();           
            hotelStatusFromSQL();
        }else{
           highlightSidebar(1);
           pane_home.toFront();
           home_text_freerooms.setText(String.valueOf(freeRoomsNumber = hotelStatusFromSQL()));
           initAndSetPieChartData(5-freeRoomsNumber,freeRoomsNumber);
           home_activeguests.setText(getActiveGuests());      
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
            guest = new Guest(guestName,guestSurname,freeRoomNumber,true);
            guestDao = new GuestDAO();
            guestDao.addGuest(guest);
            showAlert(Alert.AlertType.CONFIRMATION,"Success",String.format("Your room number is %s",freeRoomNumber));
        }else{
            showAlert(Alert.AlertType.ERROR,"Failed",String.format("Sorry but there are no free rooms"));
        }               
    }   
    
    //Checks if user is in database and removes it
    @FXML
    private void handleButtonActionRemoval(ActionEvent event) {
        String guestName = tf_remove_name.getText().toString();
        String guestSurname = tf_remove_surname.getText().toString();        
        textValidationForNameAndSurname(guestName,guestSurname);        
        guestDao = new GuestDAO();
        roomDao = new RoomDAO();
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
        home_text_guesthistory.setText(getRoomHistoryByNumber(3));
        initAndSetPieChartData(5-freeRoomsNumber,freeRoomsNumber);
        home_activeguests.setText(getActiveGuests());
        highlightSidebar(1);
    }    
   
            

     private void highlightSidebar(int pos){       
        switch(pos){
            case 2:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 3:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 4:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            default:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY,CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.AQUA,CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
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
        roomDao = new RoomDAO();
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
        roomDao = new RoomDAO();
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
    
    private String getRoomHistoryByNumber(int number){
        String history = "";
        ObservableList<Guest> guestHistory= FXCollections.observableArrayList();
        guestDao = new GuestDAO();
        guestDao.getGuestHistoryByRoomNumber(guestHistory, number);
        for(int i =0;i<guestHistory.size();i++){
            history += guestHistory.get(i).getName()+" "+guestHistory.get(i).getSurname()+"\n";
        }
        return history;
    }
    
    private void initAndSetPieChartData(int roomsTaken, int freeRooms){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
            new PieChart.Data("Occupied hotel rooms", roomsTaken), 
            new PieChart.Data("Free hotel rooms", freeRooms));             
        home_piechart.setData(pieChartData);
        home_piechart.setLegendVisible(true);
        home_piechart.setLegendSide(Side.LEFT);
    }
    
    private String getActiveGuests(){
        String activeGuests = "";
        guestDao = new GuestDAO();
        ObservableList<Guest> guestsList= FXCollections.observableArrayList();
        guestDao.getAllGuests(guestsList);
        for(Guest guest: guestsList){            
            if(guest.isGuestActive()==true){
               activeGuests += guest.getName()+" "+guest.getSurname()+"\n";
            }
        }
        return activeGuests;
    }
    
   
    
    
}
