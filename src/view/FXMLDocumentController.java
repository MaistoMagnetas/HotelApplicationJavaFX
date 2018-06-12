/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GuestDAO;
import controller.RoomDAO;
import controller.Validation;
import static java.awt.SystemColor.window;
import java.io.FileInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import model.Guest;
import model.Room;

/**
 *
 * @author PC
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btn_sb_register_guest, btn_sb_remove_guest, btn_sb_hotel_status;

    @FXML
    private Pane pane_home, pane_register_guest, pane_remove_guest, pane_hotel_status,
            sidebar_pane_color_home, sidebar_pane_color_status,
            sidebar_pane_color_delguest, sidebar_pane_color_regguest;

    @FXML
    private ImageView hotel_status_first, hotel_status_second, hotel_status_third,
            hotel_status_fourth, hotel_status_fifth;

    @FXML
    private TextField tf_registration_name, tf_registration_surname, tf_remove_name,
            tf_remove_surname;

    @FXML
    private PieChart home_piechart;

    @FXML
    private Text home_text_freerooms, home_text_guesthistory, home_activeguests, text_sb_date,
            text_homestatus_firstroom, text_homestatus_secondroom, text_homestatus_thirdroom,
            text_homestatus_fourthroom, text_homestatus_fifthroom;

    @FXML
    private ScrollPane home_scrollpane;

    @FXML
    private ChoiceBox home_cb_roomnum;
    
    
    private ArrayList<ImageView> hotelStatusPaneList;
    private Image ivRoomFree, ivRoomTaken;
    private int freeRoomsNumber, freeRoomNumber;
    private Guest guest;
    private Room room;
    private GuestDAO guestDao;
    private RoomDAO roomDao;
    private ObservableList<Room> roomsList = FXCollections.observableArrayList();
    private ObservableList<Guest> guestsList = FXCollections.observableArrayList();
    private ObservableList<Guest> guestsHistoryList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //At start prepare Home Pane with data.
        roomDao = new RoomDAO();
        roomsList = roomDao.createHotelRooms(5);
        setRoomStatusImagesAndGatherArray(); //creates image = new image for room status and gathers array of rooms
        home_text_freerooms.setText(String.valueOf(freeRoomsNumber = hotelRoomStatus())); //Gets free room numbers
        initAndSetPieChartData(5 - freeRoomsNumber, freeRoomsNumber); //Load piechart
        home_activeguests.setText(getActiveGuests()); //sets active guest number
        highlightSidebar(1); //Highlight home on app start.
        setDateToText(); //Sets date on the bottom pane
        initChoiceBoxItems(); //Sets spinner from 1-5 for room history
        initScrollPaneParameters(); //Sets scrollpane to panable and scrolable
        initHotelStatusText();//SetsHOtelStatus Text to rooms are free
        
        
    }

    /**
     * ***General methods****
     */
    
    
    
    //Spinner-choicebox items
    private void initChoiceBoxItems() {
        home_cb_roomnum.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5));
        home_cb_roomnum.setValue(1);
    }
    
    //Alert on button register/delete buttons
    private void showAlert(Alert.AlertType alerType, String title, String message) {
        Alert alert = new Alert(alerType);
        alert.setTitle(title);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    //Validation for textfields-surname, name
    private boolean textValidationForNameAndSurname(String guestName, String guestSurname) {
        boolean status = true;
        if (!Validation.isValidCredentials(guestName)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Name must only include A-Z(a-z) characters and cant be blank");
            status = false;
        }else if (!Validation.isValidCredentials(guestSurname)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Surname must only include A-Z(a-z) characters ant cant be blank");
            status = false;
        }
        return status;
    }

    //Sveciu istorijos pane home tab'e.
    private void initScrollPaneParameters() {
        home_scrollpane.setContent(home_text_guesthistory);
        home_scrollpane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        home_scrollpane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        home_scrollpane.setPannable(true);
    }
    
    private void initHotelStatusText(){        
        text_homestatus_firstroom.setText("Room is free");                       
        text_homestatus_secondroom.setText("Room is free");
        text_homestatus_thirdroom.setText("Room is free");
        text_homestatus_fourthroom.setText("Room is free");
        text_homestatus_fifthroom.setText("Room is free");                           
    }
       
    private void setRoomStatusImagesAndGatherArray() {//Hotel status activity. Open/CLosed door images
        hotelStatusPaneList = new ArrayList<ImageView>();
        hotelStatusPaneList.add(hotel_status_first);
        hotelStatusPaneList.add(hotel_status_second);
        hotelStatusPaneList.add(hotel_status_third);
        hotelStatusPaneList.add(hotel_status_fourth);
        hotelStatusPaneList.add(hotel_status_fifth);
        try {
            ivRoomFree = new Image(new FileInputStream(
                    "C:\\Users\\PC\\\\Documents\\NetBeansProjects\\NoMagicHotel\\src\\view\\open_door.png"));
            ivRoomTaken = new Image(new FileInputStream(
                    "C:\\Users\\PC\\\\Documents\\NetBeansProjects\\NoMagicHotel\\src\\view\\closed_door.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    
    private void initAndSetPieChartData(int roomsTaken, int freeRooms) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Occupied hotel rooms", roomsTaken),
                new PieChart.Data("Free hotel rooms", freeRooms));
        home_piechart.setData(pieChartData);
    }
    
    //Sets current date to text at footer
    private void setDateToText() { 
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        text_sb_date.setText(formatter.format(date));
    }

    //On sidebar item click changes color. 
    private void highlightSidebar(int pos) { 
        switch (pos) {
            case 2:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 3:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            case 4:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
            default:
                sidebar_pane_color_regguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_delguest.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_status.setBackground(new Background(new BackgroundFill(Color.SLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                sidebar_pane_color_home.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));
                break;
        }
    }


    @FXML
    private void handleButtonActionHomeRoomHistory(ActionEvent event) {
        int selectedRoomNum = (int) home_cb_roomnum.getValue();
        String roomNumHistory = "";
        for (Guest guest : guestsHistoryList) {
            if (selectedRoomNum == guest.getRoomNum()) {
                if(guest.isGuestActive() == true){
                    roomNumHistory += guest.getName() + " " + guest.getSurname() + " - Currently active "+ "\n";
                    break;
                }
                roomNumHistory += guest.getName() + " " + guest.getSurname() + "\n";
            }
        }
        if (roomNumHistory.length() == 0) {
            home_text_guesthistory.setText("No guest have lived in this room yet.");
        } else {
            home_text_guesthistory.setText(roomNumHistory);
        }
    }

    @FXML
    private void handleButtonActionSidebar(ActionEvent event) {
        if (event.getSource() == btn_sb_register_guest) {
            highlightSidebar(2);
            pane_register_guest.toFront();
        } else if (event.getSource() == btn_sb_remove_guest) {
            highlightSidebar(3);
            pane_remove_guest.toFront();
        } else if (event.getSource() == btn_sb_hotel_status) {
            highlightSidebar(4);
            pane_hotel_status.toFront();
            hotelRoomStatus();
            setOccupiedRoomGuestText();            
        } else {
            highlightSidebar(1);
            pane_home.toFront();
            home_text_freerooms.setText(String.valueOf(freeRoomsNumber = hotelRoomStatus()));
            initAndSetPieChartData(5 - freeRoomsNumber, freeRoomsNumber);
            home_activeguests.setText(getActiveGuests());
        }
    }

    @FXML
    private void handleButtonActionRegistration(ActionEvent event) {
        String guestName = tf_registration_name.getText().toString();
        String guestSurname = tf_registration_surname.getText().toString();
        if(textValidationForNameAndSurname(guestName, guestSurname)) {
            freeRoomNumber = returnFreeRoomNumber();
            if (freeRoomNumber > 0 && freeRoomNumber <= 5) { //If there are free rooms in hotel
                guest = new Guest(guestName, guestSurname, freeRoomNumber, true);
                guestDao = new GuestDAO();
                guestDao.addGuest(guest,guestsList); //Register guest
                guestDao.addGuestHistory(guest, guestsHistoryList); //Add guest to guest history
                showAlert(Alert.AlertType.CONFIRMATION,
                        "Success",String.format("Guest %s %s successfully registered at room %d",guest.getName(),guest.getSurname(),freeRoomNumber));                              
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Sorry but there are no free rooms");
            }
        }
    }
    
   

    @FXML
    private void handleButtonActionRemoval(ActionEvent event) {
        String guestName = tf_remove_name.getText().toString();
        String guestSurname = tf_remove_surname.getText().toString();
        if (textValidationForNameAndSurname(guestName, guestSurname)) {
            roomDao = new RoomDAO();
            for (Guest guest : guestsList) {
                if (guest.getName().equals(guestName) && guest.getSurname().equals(guestSurname) && guest.isGuestActive() == true) {
                    roomDao.changeRoomAvailability(false, guest.getRoomNum()-1,roomsList); //roomNum -1 for index and size match. (CODE - cahnged from SQL to LISTS).
                    guestDao.removeGuestFromRoom(guest,guestsList);
                    guest.setGuestStatus(false);
                    showAlert(Alert.AlertType.CONFIRMATION, "Success",
                            String.format("Guest %s %s successfully removed", guest.getName(), guest.getSurname()));
                    break;
                }
            }
        }
    }

    /**
     * ***SQL methods****
     */
    //Checks free room in hotel
    private int hotelRoomStatus() {
        int freeRooms = 5;
//        ObservableList<Room> roomsList = initRoomDao();
        for (int i = 0; i < roomsList.size(); i++) {
            if (roomsList.get(i).isRoomTaken() == true) {
                hotelStatusPaneList.get(i).setImage(ivRoomTaken);
                freeRooms = freeRooms - 1;
            } else {
                hotelStatusPaneList.get(i).setImage(ivRoomFree);                
            }
        }
        return freeRooms;
    }

    //Counts free rooms
    private int returnFreeRoomNumber() {
        int freeRoomNumber = -1;
        //ObservableList<Room> roomsList = roomDao.getHotelRooms();
        for (int i = 0; i < roomsList.size(); i++) {
            if (roomsList.get(i).isRoomTaken() == false) {
                freeRoomNumber = i + 1;
                roomsList.get(i).setStatus(true);
                //roomDao.changeRoomAvailability(true, freeRoomNumber, roomsList);
                break;
            }
        }
        return freeRoomNumber;
    }

    private String getRoomHistoryByNumber(int number) {
        String history = "";
        for (Guest guest : guestsHistoryList) {
            if (guest.getRoomNum() == number) {
                history += guest.getName() + " " + guest.getSurname() + "\n";
            }
        }
        return history;
    }

    private String getActiveGuests() {
        String activeGuests = "";
        for (Guest guest : guestsList) {
            if (guest.isGuestActive() == true) {
                activeGuests += guest.getName() + " " + guest.getSurname() + "\n";
            }
        }
        if (activeGuests.length() == 0) {
            activeGuests += "No active guests right now";
        }
        return activeGuests;
    }

    private void setOccupiedRoomGuestText() {
            for (Room room : roomsList) {
                if (room.isRoomTaken() == false) {
                    switch (room.getId()) {
                        case 1:
                            text_homestatus_firstroom.setText("Room is free");
                            break;
                        case 2:
                            text_homestatus_secondroom.setText("Room is free");
                            break;
                        case 3:
                            text_homestatus_thirdroom.setText("Room is free");
                            break;
                        case 4:
                            text_homestatus_fourthroom.setText("Room is free");
                            break;
                        case 5:
                            text_homestatus_fifthroom.setText("Room is free");
                            break;
                    }
                }else{
                     switch (room.getId()) {
                        case 1:
                            text_homestatus_firstroom.setText("Room is taken");
                            break;
                        case 2:
                            text_homestatus_secondroom.setText("Room is taken");
                            break;
                        case 3:
                            text_homestatus_thirdroom.setText("Room is taken");
                            break;
                        case 4:
                            text_homestatus_fourthroom.setText("Room is taken");
                            break;
                        case 5:
                            text_homestatus_fifthroom.setText("Room is taken");
                            break;                
                }
            }

        }
    }
}
