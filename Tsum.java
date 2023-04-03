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
import java.util.HashMap;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
/**
* Class supplements the Summary class.
* Creates an object storing the current week's tracking information
* for a specific tracker name
* 
*/
public class Tsum {
    
/**************** NEW GLOBAL VARIABLES ADDED ********************************/
    /**
     * Name of the tracker
     */
    private final  SimpleStringProperty tname;
    /**
     * Colors of the day labels to include in the summary for this tracker
     */
    public HashMap<String, Color> dayColors = new HashMap<String,Color>();
   // public ArrayList<String> 
    /**
     * List of days of the current week
    */
    public ArrayList<String> days =  new ArrayList<String>();
    /**
     * Day 1 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day1;
    /**
     * Day 2 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day2;
    /**
     * Day 3 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day3;
    /**
     * Day 4 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day4;
    /**
     * Day 5 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day5;
    /**
     * Day 6 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day6;
    /**
     * Day 7 of the current week along with its color encoded in a string
     */
    private final  SimpleStringProperty day7;


/************************* ***************************************************/

    public Tsum(String name){
        tname = new SimpleStringProperty(name);
        make_tsumdays();
        if (days.size()>=1){
            day1 = new SimpleStringProperty(days.get(0));
        }else{
            day1 = new SimpleStringProperty("");
        }
        if (days.size()>=2){
         day2 = new SimpleStringProperty(days.get(1));
        }else{
            day2 = new SimpleStringProperty("");
        }

        if (days.size()>=3){
            day3 = new SimpleStringProperty(days.get(2));
        }else{
            day3 = new SimpleStringProperty("");
        }

        if (days.size()>=4){
            day4 = new SimpleStringProperty(days.get(3));
        }else{
            day4 = new SimpleStringProperty("");
        }

        if (days.size()>=5){
            day5 = new SimpleStringProperty(days.get(4));
        }else{
            day5 = new SimpleStringProperty("");
        }

        if (days.size()>=6){
            day6 = new SimpleStringProperty(days.get(5));
        }else{
            day6 = new SimpleStringProperty("");
        }

        if (days.size()>=7){
            day7 = new SimpleStringProperty(days.get(6));
        }else{
            day7 = new SimpleStringProperty("");
        }

    }

    public String getTname() {
        return tname.get();
    }

    public String getDay1() {
        return day1.get();
    }

    public String getDay2() {
        return day2.get();
    }

    public String getDay3() {
        return day3.get();
    }

    public String getDay4() {
        return day4.get();
    }

    public String getDay5() {
        return day5.get();
    }

    public String getDay6() {
        return day6.get();
    }

    public String getDay7() {
        return day7.get();
    }

    /**
     * Method to find values of the days of the current week and their colors,
     * and encode both the day number and the color of the day label
     * on the tracker calendar, within one string
     */
    public void make_tsumdays(){
        if (!days.isEmpty()){ 
            days = new ArrayList<String>();
            dayColors = new HashMap<String,Color>();
        }

        TrackerList tList = MiscTrackerApp.trackerLists.get(tname.get());
        Tracker currTracker = tList.monthly_trackers.get(tList.currMonth);


        int num_days = currTracker.get_num_days();
        int currDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int start = currDay-6;
        if (start <= 0){
            if (tList.currMonth != 0){
                Tracker prevTracker = tList.monthly_trackers.get(tList.currMonth-1);
                int prev_num_days = prevTracker.get_num_days();
                int prev_start = prev_num_days + start;
                System.out.println(prev_start);
                for(int i = prev_start; i <= prev_num_days; i++){
                    Color color = prevTracker.dayColors.get(i);
                    String day = ""+i+color;
                    days.add(day);
                    dayColors.put(day, color);
                }
            }
            start = 1;
        }
        for(int i = start; i <= currDay; i++){
            Color color = currTracker.dayColors.get(i);
            String day = "";
            //dayColors.put((""+i), color);
            if(i<= 9){day = day + "0";}
            day = day+i+color;
            days.add(day);
            dayColors.put((""+i), color);
        }
    }
}
   

