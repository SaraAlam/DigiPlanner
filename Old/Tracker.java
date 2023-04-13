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
import java.io.*;

/**
 * Class to create a Tracker object for one kind of tracker, for a particular month
 * of the current year.
 * Contains all the widgets for this months tracker of this type, in one VBox called info_box
 */
public class Tracker {

    // default
    public final int DEFAULT = 0;
    public static Color[] colors = new Color[]{Color.WHITE, Color.PINK, Color.HOTPINK, Color.FUCHSIA, Color.DEEPPINK};

    /**
     * Default meaning of each color
     */
    public String[] categories = new String[]{"reset", "Good", "Alright", "Bad", "Very Bad"};

    // tracker specific variables
    public String name;
    public String month;
    public int year;
    /**
     * 0 for default
     */
    public int typ = 0;
    /**
     * The trackerList object that created this tracker object
     */
    public TrackerList parentList = null;

    // variables for handling color changes
    /**
     * FlowPane containing all the day labels for this month, for this tracker
     */
    public FlowPane calendar;
    /**
     * HashMap storing (day, Color) pairs
     */
    public HashMap<Integer, Color> dayColors = new HashMap<Integer, Color>();
    /**
     * HashMap storing (day, category) pairs, where category is the meaning of the color
     * of the correspondig day label
     */
    public HashMap<Integer, String> dayCats = new HashMap<Integer, String>();
    /**
     * Used to track if the user selected a day label to modify
     */
    public Label currSelectedDay = null;
    /**
     * Used to track the color of the color label the user selected, if any
     */
    public Color currSelectedColor = null;
    /**
     * Used the track if the user selected a color label
     */
    public Label currSelectedColorLabel = null;
    /**
     * Category to color mapping
     */
    public HashMap<String, Color> color_key = new HashMap<String, Color>();

    /**
     * Holds a Label telling the user to pick a color
     */
    private HBox instruction_holder = null;

    /**
     * Holds the color labels
     */
    private HBox color_holder =  null;
    public Button exit_button = new Button("Exit Tracker");
    /**
     * Main container of all the widgets for this tracker object
     */
    public VBox info_box =  null;



    public Tracker(String name_of_tracker, String month_of_tracker, int year_of_tracker, int typ_of_tracker, TrackerList tList){
        name = name_of_tracker;
        month = month_of_tracker;
        year = year_of_tracker;
        typ = typ_of_tracker;
        parentList = tList;

        // get the key
        if (typ==DEFAULT){
            get_default_key();
        }
        else{
            get_user_specified_key();
        }

        create_tracker_view();
    }

    public Tracker(String name_of_tracker, String month_of_tracker, int year_of_tracker, TrackerList tList){ 
        this(name_of_tracker, month_of_tracker, year_of_tracker, 0, tList);
     }

    /**
     * Creates all the widgets for this tracker object and their handlers (if any)
     */
    public void create_tracker_view(){
        make_calendar();
        int num_days = get_num_days();
        for (int i = 1; i <= num_days; i++){
            Label day = new Label(""+i);
            make_day(i, day);
            calendar.getChildren().add(day);
        }
        create_color_holder();
        create_instruction_holder();
        make_info_box();
    }

    /**
     * Method to create a day label for the calendar
     * @param i: Day of the month
     * @param day: Label containing the string form of i
     */
    public void make_day(int i, Label day){
        if (!dayColors.containsKey(i)){ dayColors.put(i,Color.WHITE);}
        if (!dayCats.containsKey(i)){dayCats.put(i, "reset");}
        day.setFont(new Font("Times New Roman",20));
        day.setMinWidth(40);
        day.setMinHeight(40);
        day.setBackground(new Background(new BackgroundFill(dayColors.get(i), new CornerRadii(0), new Insets(0))));
        day.setAlignment(Pos.CENTER);
        day.setOnMousePressed( e -> {
            if (currSelectedDay != null){
                currSelectedDay.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            }
            currSelectedDay = day;
            currSelectedDay.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        });

        day.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        if (currSelectedDay != null){
            if ((Integer.parseInt(currSelectedDay.getText()))==i){
                currSelectedDay = day;
                currSelectedDay.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
            }
        }
    }

    /**
     * Same as the make_day() method
     * Except the day labels created in this method do not have handlers
     * @param i: Day of the month
     * @param day: Day label containing stirng form of i
     */
    public void make_summary_day(int i, Label day){
        if (!dayColors.containsKey(i)){ dayColors.put(i,Color.WHITE);}
        if (!dayCats.containsKey(i)){dayCats.put(i, "reset");}
        day.setFont(new Font("Times New Roman",20));
        day.setMinWidth(40);
        day.setMinHeight(40);
        day.setBackground(new Background(new BackgroundFill(dayColors.get(i), new CornerRadii(0), new Insets(0))));
        day.setAlignment(Pos.CENTER);
        day.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
    }

    /**
     * Method to specify layout of the VBox containing all the widgets
     */
    public void make_info_box(){
        Label tracker_name = new Label(name);
        tracker_name.setFont(new Font("Times New Roman",40));
        Label month_and_year_label = new Label(month+" "+year);
        month_and_year_label.setFont(new Font("Times New Roman",30));
        exit_button.setFont(new Font("Times New Roman", 15));

        info_box = new VBox();
        info_box.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        info_box.setPadding(new Insets(10));
        info_box.setSpacing(10);
        info_box.getChildren().addAll(tracker_name, month_and_year_label, calendar, instruction_holder, color_holder, exit_button);
    }

    public void make_calendar(){
        calendar = new FlowPane();
        calendar.setBackground(new Background(new BackgroundFill(Color.web("#E6E6FA"), new CornerRadii(0), new Insets(0))));
        calendar.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        calendar.setPadding(new Insets(10));
        calendar.setVgap(4);
        calendar.setHgap(4);
        calendar.setAlignment(Pos.CENTER);
    }

    /**
     * Method to check if the current year is leap or not
     * @return boolean
     */
    public boolean is_leap(){
        if (year % 400 == 0) {
            return true;
        } 
        else if (year % 100 == 0) {
            return false;
        } 
        else if (year % 4 == 0) {
            return true;
        } 
        
        return false;
    }

    /**
     * Get the number of days in the month for this tracker
     * @return int
     */
    public int get_num_days(){
        String[] monthsWith31Days = {"January", "March", "May", "July", "August", "October","December"};
        for(String m: monthsWith31Days){
            if (month.equals(m)){
                return 31;
            }
        }
        if (month.equals("February")){
            if (is_leap()){ return 29; }
            return 28;
        }

        return 30;
    }

    /**
     * Create an HBox to display instructions for the user
     */
    public void create_instruction_holder(){
        if (instruction_holder == null){ instruction_holder = new HBox();}
        else{ instruction_holder.getChildren().clear();}

        instruction_holder.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        instruction_holder.setPadding(new Insets(10));
        instruction_holder.setBackground(new Background(new BackgroundFill(Color.web("#E6E6FA"), 
            new CornerRadii(0), new Insets(0))));
        instruction_holder.setMinWidth(color_holder.getWidth());

        Label instruction = new Label("Please select a color: ");
        instruction.setFont(new Font("Times New Roman", 20));
        instruction_holder.getChildren().add(instruction);

    }

    /**
     * Create the color labels, their container and their handlers
     */
    public void create_color_holder(){

        // for debugging
        if (color_holder == null){ color_holder = new HBox();}
        else{ color_holder.getChildren().clear();}

        // layout
        color_holder.setMinHeight(60);
        color_holder.setMinWidth(500);
        color_holder.setAlignment(Pos.CENTER);
        color_holder.setBorder(new Border(new BorderStroke(Color.BLACK,
        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        color_holder.setPadding(new Insets(10));
        color_holder.setSpacing(5);
        color_holder.setBackground(new Background(new BackgroundFill(Color.web("#E6E6FA"), 
            CornerRadii.EMPTY, Insets.EMPTY)));


        // create color_key and labels
        for (int i = 0; i < colors.length; i++){
            String category = categories[i];
            Color c = colors[i];
            color_key.put(category, c);

            Label l = new Label(category);
            l.setFont(new Font("Times New Roman", 20));
            l.setMinWidth(category.length()+50);
            l.setAlignment(Pos.CENTER);
            l.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
            l.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

            l.setOnMousePressed( e -> {
            if (currSelectedColorLabel!=null){
                currSelectedColorLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));}
            currSelectedColorLabel = l;
            String category_chosen = l.getText();
            currSelectedColor = color_key.get(category_chosen);
            l.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

            if (currSelectedDay != null){
                int dayNum = Integer.parseInt(currSelectedDay.getText());
                dayColors.put(dayNum, currSelectedColor);
                dayCats.put(dayNum, currSelectedColorLabel.getText());
                remake_info_box();
            }
            });

            color_holder.getChildren().add(l);
        }
    }

    /**
     * Method to redraw all the widgets after the colors day labels
     */
    public void remake_info_box(){
        MiscTrackerApp.info_holder.getChildren().clear();
        calendar = null;
        instruction_holder = null;
        color_holder = null;


        info_box.getChildren().clear();
        info_box=null;
        create_tracker_view();
       // TestApp.info_holder.getChildren().add(info_box);
        MiscTrackerApp.info_holder.getChildren().add(info_box);
    }

    /**
     * Assigns specific meanings to each color depending on which default tracker this is
     */
    public void get_default_key(){
        if (name.equals("Sleep")){
            categories = new String[]{"reset", "8hrs", "7hrs", "5hrs", "less than 5hrs"};
        }

        else  if (name.equals("Water")){
            categories =new String[]{"reset", "10 glasses", "9 glasses", "8 glasses", "less than 8 glasses"};
        }

        else if (name.equals("Study")){
            categories = new String[]{"reset", "6hrs", "5hrs", "4hrs", "less than 4hrs"};
        }

        else if (name.equals("Stress")){
            categories = new String[]{"reset", "Low", "Rising", "High", "Extreme"};
        }

        else if (name.equals("Mood")){
            categories = new String[]{"reset", "Good", "Moderate", "Sad", "Depressed"};
        }
    }

    /**
     * Assigns the user specified meanings to the colors if this is a custom_tracker
     * The information is obtained by and stored in the TrackerList object that created
     * this tracker object.
     */
    public void get_user_specified_key(){
        categories = parentList.categories;
    }
   
}
