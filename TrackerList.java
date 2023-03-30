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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Calendar;
import java.lang.Thread;

public class TrackerList {

    //default 
    public final int DEFAULT = 0;
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August",
                                       "September", "October", "November", "December"};
                    
    
    // tracker specific
    public String name;
    public int year;
    /**
     * Mode is teh type of the tracker, either default or custom
     * 0 for default
     */
    public int mode;
    /**
     * List of tracker objects, one for each month of the current year
     */
    public ArrayList<Tracker> monthly_trackers = new ArrayList<Tracker>();
    /**
     * Index of the currently displayed tracker in the list of monthly_trackers
     */
    public int currMonth = Calendar.getInstance().MONTH;
    /**
     * HBox holding the "Previous" and "Next" buttons to move between monthly trackers
     */
    public HBox nav_button_holder =  new HBox();
    /**
     * "Levels" to describe the behavior/habit being tracked
     * Hardcoded to {"reset", "Good", "Alright", "Bad", "Very Bad"} for default trackers
     */
    public String[] categories = new String[]{"reset", "Good", "Alright", "Bad", "Very Bad"};
    /**
     * Used to get user input for "levels" to describe custom tracker habits/behaviors
     */
    public TextField[] cats = new TextField[5];
    /**
     * Button signaling the user is done enteing custom_tracker information
     */
    Button done_button = null;

    public TrackerList(String name_of_tracker, int year_of_tracker, int typ_of_tracker){
        name = name_of_tracker;
        year = year_of_tracker;
        mode = typ_of_tracker;
        if (mode!=DEFAULT){
            if (!MiscTrackerApp.customTrackerKeys.containsKey(name_of_tracker)){
                get_user_specified_key();
            }
            else{
                // for the case where information of a custom tracker is saved
                String[] saved = MiscTrackerApp.customTrackerKeys.get(name_of_tracker);
                if (saved!=null){
                 categories = saved;
                }
                create_monthly_trackers();
                get_recorded_colors();
            }
        }
        else{
            create_monthly_trackers();
            get_recorded_colors();
        }
    }

    public TrackerList(String name_of_tracker, int year_of_tracker){
        this(name_of_tracker, year_of_tracker, 0);
    }

    /**
     * Method to create a tracker object for each month
     * and store it in the monthly_trackers ArrayList
     * and also create instructions, color labels and the exit_button
     */
    public void create_monthly_trackers(){
        for (String month: months){
            Tracker t = new Tracker(name, month, year, mode, this);
            monthly_trackers.add(t);
            t.exit_button.setOnAction( e -> { 
                MiscTrackerApp.currTrackerList = null;
                MiscTrackerApp.root.getChildren().remove(this.nav_button_holder);
                MiscTrackerApp.info_holder.getChildren().clear();
                Summary.make_sum_view();
                MiscTrackerApp.info_holder.getChildren().add(Summary.sum_box);
                });
        }
        create_nav_buttons();
    }


    /**
     * Method to change each tracker object's day labels to 
     * the color previously saved, if any
     */
    public void get_recorded_colors(){
        if (MiscTrackerApp.tDayCatsMaps.containsKey(name)){
            HashMap<String,HashMap<Integer, String>> dayCatsMaps = MiscTrackerApp.tDayCatsMaps.get(name);
            for(Tracker monthly_tracker: monthly_trackers){
                String m =  monthly_tracker.month;
                monthly_tracker.dayCats = dayCatsMaps.get(m);
                //if (dayCatsMaps.get(m)==null){ System.out.println(name + " has no records for "+m);}
                for(Integer day: dayCatsMaps.get(m).keySet()){
                    String cat = dayCatsMaps.get(m).get(day);
                    if (!dayCatsMaps.get(m).containsKey(day)){System.out.println("No color for day "+day);}
                    Color c = monthly_tracker.color_key.get(cat);
                    monthly_tracker.dayColors.put(day, c);
                    monthly_tracker.remake_info_box();
                }
            }
        }
    }


    /**
     * Method to create the "Previous" and "Next" buttons
     * to switch between months
     */
    public void create_nav_buttons(){
        Button prev = new Button(" < Previous");
        prev.setFont(new Font("Times New Roman", 15));
        prev.setOnAction( e -> {
            if (currMonth > 0){
                currMonth -= 1;
                Tracker curr_tracker = monthly_trackers.get(currMonth);
                MiscTrackerApp.info_holder.getChildren().clear();
                MiscTrackerApp.info_holder.getChildren().add(curr_tracker.info_box);
            }
        });


        Button next = new Button("Next > ");
        next.setFont(new Font("Times New Roman", 15));
        next.setOnAction( e -> {
            if(currMonth < monthly_trackers.size() - 1){
                currMonth += 1;
                Tracker curr_tracker = monthly_trackers.get(currMonth);
                MiscTrackerApp.info_holder.getChildren().clear();
                MiscTrackerApp.info_holder.getChildren().add(curr_tracker.info_box);
            }
        });

        nav_button_holder.getChildren().addAll(prev, next);
    }


    /**
     * Method to create get user's custom key for a custom tracker
     */
    public void get_user_specified_key(){

        // Empty the info_holder to display the VBox
        if (MiscTrackerApp.currTrackerList!=null){
            MiscTrackerApp.root.getChildren().remove(MiscTrackerApp.currTrackerList.nav_button_holder);
            MiscTrackerApp.currTrackerList = null;
           // MiscTrackerApp.root.getChildren().remove(MiscTrackerApp.currTrackerList.nav_button_holder);
        }
        MiscTrackerApp.info_holder.getChildren().clear();

        //create and display VBox to collect new tracker info
        VBox getCats = new VBox();
        Label instruction1 = new Label("Please define the following for your new tracker:");
        Label instruction2 = new Label("These will describe the tracking colors for a day.");
        getCats.getChildren().addAll(instruction1, instruction2);
        for(int i = 0; i < 4; i++){
            TextField l = new TextField(categories[i+1]);
            cats[i] = l;
            getCats.getChildren().add(l);
        }
        done_button = new Button("Done");
        done_button.setOnAction( e-> done_button_handler());
        getCats.getChildren().add(done_button);
        MiscTrackerApp.info_holder.getChildren().add(getCats);
    }


    public void done_button_handler(){
        String[] new_cats = new String[5];
        new_cats[0] = "reset";
        for(int i = 1; i < 5; i++){
            MiscTrackerApp.customTrackerTextField.setText("Enter new tracker name");
            String new_cat = cats[i-1].getText();
            if (new_cat.isEmpty()){ new_cat = categories[i];}
            new_cats[i] = new_cat;
            categories = new_cats;
        }

        create_monthly_trackers();

        MiscTrackerApp.currTrackerList = this;
        MiscTrackerApp.info_holder.getChildren().clear();
        MiscTrackerApp.root.setConstraints(this.nav_button_holder,1,0);
        MiscTrackerApp.root.getChildren().add(this.nav_button_holder);
        MiscTrackerApp.info_holder.getChildren().add(monthly_trackers.get(currMonth).info_box);
    }
    
}
