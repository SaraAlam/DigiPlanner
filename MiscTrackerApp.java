import java.text.NumberFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import java.lang.Math;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import java.util.HashMap;
import java.util.Calendar;
import java.io.*;
import java.nio.file.*;

/**
* 
* GUI for MiscTrackApp application
*      @author Sara Alam
*      @version 1
* 
* List of resources you used:
* see readme
* 
*/
public class MiscTrackerApp extends Application {

    
    // WIDTH and HEIGHT of GUI stored as constants 
    private final int WIDTH = 725;
    private final int HEIGHT = 550;

    
/**************** DEFAULT GLOBAL VARIABLES ********************************/
    public static  String[] default_tracker_names = {"Water","Sleep", "Study", "Stress", "Mood"};
    private Button saveAndQuitButton  = new Button("Save and Quit");
    private Button createCustomTrackerButton  = new Button("Add Tracker");

/**************** GLOBAL VARIABLES STORING TRACKER INFO ********************************/
    public static ArrayList<String> custom_tracker_names = new ArrayList<String>();
    public static ArrayList<String> allTrackerNames = new ArrayList<String>();
    public static HashMap<String, TrackerList> trackerLists = new HashMap<String, TrackerList>();

/**************** GLOBAL VARIABLES TO DISPLAY INFORMATION AND TRACK WHAT IS BEING DISPLAYED ********************************/
    /**
     * Keeps track of the TrackerList currently being displayed, if any.
     * It is null when the summary is being displayed.
     */
    public static TrackerList currTrackerList =  null;
    /**
     * Used to display the summary and the tracker objects' widgets
     * Also used to get extra user information for custom trackers
     */
    public static VBox info_holder =  new VBox();
    /**
     * Used the display the titles and the listviews of the default and custom trackers
     * Also holds the Textfield for new tracker names, the "add tracker" button and the "save and quit" button
     */
    public static VBox tracker_holder =  new VBox();
    public static GridPane root = new GridPane();
    public static ListView custom_trackers = new ListView();
    public static ListView default_trackers = new ListView();
    /**
     * This is global unlike the observable list for the default trackers,
     * because it changes when new custom trackers are added
     */
    public static ObservableList<String> custom_trackers_obs_lst =FXCollections.observableArrayList (custom_tracker_names);
    /**
     * This allows the user to enter a new tracker's name
     */
    public static TextField customTrackerTextField = new TextField("Enter new tracker name");

/************************************************ Vairables for file reading **********************************/
    /**
    * Used to temporarily store tracker names as they are read from saved files
    */
    public static String temp_tracker_name = "";
    /**
    * Used to temporarily store tracker types (i.e default or custom) as they are read from saved files
    */
    public static int temp_typ_of_tracker = 0;
    /**
    * Used to temporarily store tracker keys (for custom trackers) as they are read from saved files
    */
    public static String[] temp_key = null;
     /**
    * Used to store all custom tracker keys recorded in saved files
    */
    public static HashMap<String, String[]> customTrackerKeys = new HashMap<String, String[]>();
     /**
    * Used to store all day categories for all trackers in saved files.
    * Helps color day labels based on the category.
    */
    public static HashMap<String, HashMap<String, HashMap<Integer, String>>> tDayCatsMaps = 
        new HashMap<String, HashMap<String, HashMap<Integer, String>>>();

/*************** START METHOD **********************************************/
    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) throws InterruptedException{

        initFromFile();

        Label default_trackers_title = new Label(" Default Trackers: ");
        default_trackers_title.setFont(new Font("Times New Roman", 20)); // create title
        default_trackers_title.setAlignment(Pos.CENTER);

        Label custom_trackers_title = new Label(" Custom Trackers: ");
        custom_trackers_title.setFont(new Font("Times New Roman", 20)); // create title
        custom_trackers_title.setAlignment(Pos.CENTER);


        BorderPane save_and_quit_button_pane = create_save_and_quit_button();
        BorderPane create_custom_tracker_button_pane = create_custom_tracker_button();


        tracker_holder.setPrefHeight(480);
        tracker_holder.setAlignment(Pos.TOP_LEFT);
        tracker_holder.setBackground(new Background(new BackgroundFill(Color.web("#E6E6FA"), 
            CornerRadii.EMPTY, Insets.EMPTY)));
        tracker_holder.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        tracker_holder.setSpacing(20);
        if (default_trackers.getItems().isEmpty()){
            // in case no file was saved 
            create_default_trackers();
            create_custom_trackers();
        }

        tracker_holder.getChildren().addAll(default_trackers_title, default_trackers, custom_trackers_title, custom_trackers);
        tracker_holder.getChildren().addAll(customTrackerTextField, create_custom_tracker_button_pane, save_and_quit_button_pane);

        info_holder = new VBox(10);
        Summary.make_sum_view();
        info_holder.getChildren().add(Summary.sum_box); // display summary view of all existing trackers

    
       create_root();

        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
      
        // call private methods for button event handlers ---> DONE IN HELPER METHODS BELOW
        // you will need one for each button added: call and complete all the ones provided
        stage.setResizable(false); // TO AVOID EXTRA CODE FOR RESIZING GRACEFULLY
        stage.setScene(scene);
        stage.setTitle("MiscTrackers");
        stage.show(); 
        
    }


/*************************************** METHODS TO CREATE INITIAL LAYOUT OF THE GUI ****************************************/

    /*
    * Method to create the root
    * @return void
    */
    private void create_root(){
       root.setVgap(10);
       root.setHgap(10);
       root.setPadding(new Insets(5, 15, 5, 15)); 
       root.setBackground(new Background(new BackgroundFill(Color.web("#ADD8E6"), new CornerRadii(0), new Insets(0))));
       root.setConstraints(tracker_holder, 0, 0,1,2);
       root.setConstraints(info_holder, 1,1);
       root.getChildren().addAll(tracker_holder, info_holder);

       tracker_holder.setMinHeight(root.getHeight()*0.8);
       tracker_holder.setMinWidth(root.getWidth()*0.8);
   }

   /*
    * Method to create the add custom tracker button in a BorderPane
    * @return an BorderPane
    */
    private BorderPane create_custom_tracker_button(){
        createCustomTrackerButton.setOnAction(e -> {
            String new_tracker_name = customTrackerTextField.getText();
            if (!new_tracker_name.isEmpty()){
                custom_tracker_names.add(new_tracker_name); 
                allTrackerNames.add(new_tracker_name);
                int year = Calendar.getInstance().get(Calendar.YEAR);
                TrackerList trackerList = new TrackerList(new_tracker_name, year, 1);
                trackerLists.put(new_tracker_name, trackerList);
                custom_trackers_obs_lst =FXCollections.observableArrayList (custom_tracker_names);
                custom_trackers.setItems(custom_trackers_obs_lst);
                custom_trackers.setOnMouseClicked( e2 -> {
                    int idx = custom_trackers.getSelectionModel().getSelectedIndex();
                    String name_of_t = custom_tracker_names.get(idx);
                    TrackerList tList = trackerLists.get(name_of_t);
        
                    if (currTrackerList != null){ root.getChildren().remove(currTrackerList.nav_button_holder);}
                    currTrackerList = tList;
        
                    root.setConstraints(tList.nav_button_holder, 1, 0);
                    root.getChildren().add(tList.nav_button_holder);
                    info_holder.getChildren().clear(); 
                    info_holder.getChildren().add(tList.monthly_trackers.get(tList.currMonth).info_box);
                });
            }
        });
        BorderPane createCustomTrackerButtonPane = new BorderPane();
        createCustomTrackerButtonPane.setPrefSize(100,50);
        createCustomTrackerButtonPane.setCenter(createCustomTrackerButton);
        
        return createCustomTrackerButtonPane;
    }


    /*
    * Method to create the save and quit button in a BorderPane
    * @return an BorderPane
    */
    private BorderPane create_save_and_quit_button(){
        saveAndQuitButton.setOnAction(e -> saveAndQuitHandler());
        BorderPane saveAndQuitButtonPane = new BorderPane();
        saveAndQuitButtonPane.setPrefSize(100,50);
        saveAndQuitButtonPane.setCenter(saveAndQuitButton);
        
        return saveAndQuitButtonPane;
    }

    /*
    * Method to create listview of default trackers
    * @return void
    */
    private void create_default_trackers() throws InterruptedException{

        default_trackers.setPrefSize(tracker_holder.getWidth()-50,125);
        default_trackers.setBackground(new Background(new BackgroundFill(Color.PINK, new CornerRadii(0), Insets.EMPTY)));
        ObservableList<String> default_trackers_obs_lst =FXCollections.observableArrayList (default_tracker_names);

        default_trackers.setItems(default_trackers_obs_lst);

        default_trackers.setOnMouseClicked( e-> {
            int idx = default_trackers.getSelectionModel().getSelectedIndex();
            String name_of_t = default_tracker_names[idx];
            TrackerList tList = trackerLists.get(name_of_t);

            if (currTrackerList != null){ root.getChildren().remove(currTrackerList.nav_button_holder);}
            currTrackerList = tList;

            root.setConstraints(tList.nav_button_holder, 1, 0);
            root.getChildren().add(tList.nav_button_holder);
            info_holder.getChildren().clear(); 
            info_holder.getChildren().add(tList.monthly_trackers.get(tList.currMonth).info_box);
        });


        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=0; i < default_tracker_names.length; i++){
            String name_of_tracker = default_tracker_names[i];
            allTrackerNames.add(name_of_tracker);
            TrackerList trackerList = new TrackerList(name_of_tracker, year);
            trackerLists.put(name_of_tracker, trackerList);
        }

    }


    /*
    * Method to create listview of custom trackers
    * @return an void
    */
    private void create_custom_trackers(){
        custom_trackers_obs_lst = FXCollections.observableArrayList (custom_tracker_names);
        custom_trackers.setPrefSize(tracker_holder.getWidth()-50,125);
        custom_trackers.setItems(custom_trackers_obs_lst);

        custom_trackers.setOnMouseClicked( e-> {
            int idx = custom_trackers.getSelectionModel().getSelectedIndex();
            String name_of_t = custom_tracker_names.get(idx);
            TrackerList tList = trackerLists.get(name_of_t);

            if (currTrackerList != null){ root.getChildren().remove(currTrackerList.nav_button_holder);}
            currTrackerList = tList;

            root.setConstraints(tList.nav_button_holder, 1, 0);
            root.getChildren().add(tList.nav_button_holder);
            info_holder.getChildren().clear(); 
            info_holder.getChildren().add(tList.monthly_trackers.get(tList.currMonth).info_box);
        });


        int year = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=0; i < custom_tracker_names.size(); i++){
            String name_of_tracker = custom_tracker_names.get(i);
            allTrackerNames.add(name_of_tracker);
            TrackerList trackerList = new TrackerList(name_of_tracker, year, 1);
            trackerLists.put(name_of_tracker, trackerList);
        }
    }



/********************************* METHODS FOR FILE I/O *****************************/
    /**
     * Method to save and quit the program
     */
    public void saveAndQuitHandler(){
        String dirname = System. getProperty("user. dir") + "TrackerInfo";
        Path d = Paths.get(dirname);
        if (Files.notExists(d)){File f = new File("TrackerInfo"); f.mkdirs(); dirname = f.getPath();}
        dirname = dirname + "/"+ Calendar.getInstance().get(Calendar.YEAR);
        d = Paths.get(dirname);
        File f = new File(dirname);
        if (Files.notExists(d)){ f.mkdirs();}
        for(String tListName: allTrackerNames){
            TrackerList tList = trackerLists.get(tListName);
            MiscTrackerFileHandler.saveRecords(tList, dirname);
        }
        Platform.exit();
    }

     /**
     * Method to reload tracker information from a file saved in the same directory, if any
     */
    public void initFromFile(){
        String dirname = "TrackerInfo"+"/"+ Calendar.getInstance().get(Calendar.YEAR);
        Path d = Paths.get(dirname);
        if (Files.exists(d)){
            File folder = new File(dirname);
            File[] trackerFiles = folder.listFiles();
            for (File f: trackerFiles){
                HashMap<String,HashMap<Integer,String>> dayCatsMap = MiscTrackerFileHandler.readRecords(f);
                tDayCatsMaps.put(temp_tracker_name, dayCatsMap);
                if (temp_typ_of_tracker!=0){ 
                    custom_tracker_names.add(temp_tracker_name);
                    customTrackerKeys.put(temp_tracker_name, temp_key);
                }
            }
        }
    }

/*************************************** MAIN ********************************************************/
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

