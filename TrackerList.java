import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;
import javafx.scene.control.ComboBox;
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import  javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

public class TrackerList {
    String month;
    int numDays;
    public int first_day;
    String[] trackerNames = {"Water", "Workout","Stress","Study","Sleep"};
    HashMap<String,Tracker> trackers = new HashMap<String,Tracker>();
    public Tracker currTracker;
    public String[] colors = {"#F8F8FF","#CCCCFF","#C4C3D0", "#92A1CF", "#8C92AC",
    "#0000FF", "#2A52BE","#002FA7","#003399", "#00009C"};

    public ComboBox<String> trackerMenu;
    public HBox color_holder;
    public Label currSelectedColorLabel;
    public Color currSelectedColor;
    public GridPane trackerSpreadPane;

    public TrackerList(String m, int nDays){
        month = m;
        numDays = nDays;
        first_day = get_month_first_day();
        for(int i = 0; i < trackerNames.length; i++){
            Tracker t = new Tracker(trackerNames[i], month, numDays, first_day);
            trackers.put(trackerNames[i], t);
        }
        currTracker = trackers.get("Water");
        trackerSpreadPane = new GridPane();
        trackerSpreadPane.setVgap(10);

        set_up_view();
    }

    public void set_up_view(){
        create_header();
        create_drop_down();
        GridPane.setConstraints(currTracker.calendar_holder, 0, 3, 7, 8);
        trackerSpreadPane.getChildren().add(currTracker.calendar_holder);
        create_color_holder();
    }
    public void create_header(){
        Label year_and_month_label = new Label(""+DigiPlanner.year+",  "+month);
        Label spread_title = new Label("Trackers");
        GridPane.setConstraints(year_and_month_label, 0,0);
        GridPane.setConstraints(spread_title, 0, 1);
        trackerSpreadPane.getChildren().addAll(year_and_month_label, spread_title);
    }

    public void create_drop_down(){
        ObservableList<String> trackerNamesObs = FXCollections.observableArrayList(trackerNames);
        trackerMenu = new ComboBox<String>();
        trackerMenu.setItems(trackerNamesObs);
        trackerMenu.setValue(currTracker.name);
        trackerMenu.valueProperty().addListener(
            new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue observable, String oldValue, String newValue) {
                    trackerSpreadPane.getChildren().remove(currTracker.calendar_holder);
                    currTracker = trackers.get(newValue);
                    set_up_view();
                }
        });
        GridPane.setConstraints(trackerMenu, 0, 2);
        trackerSpreadPane.getChildren().addAll(trackerMenu);
    }
    public int get_month_first_day(){
        // month_num is 0 for January
        int month_num = get_month_num();
        Calendar cal = Calendar.getInstance();
        cal.set(DigiPlanner.year, month_num, 1);
        int first_day = cal.get(Calendar.DAY_OF_WEEK);
        //System.out.println(first_day);
        return first_day;
    }

    public int get_month_num(){
        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for(int i = 0; i < 12; i++){
            if (month.equals(months[i])){
                return i;
            }
        }
    return 0;
    }

    public void create_color_holder(){
        // create slider
        Slider slider = new Slider(0, 1, 0.5);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(0.25f);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(0.125f);
        slider.setSnapToTicks(true);

        //slider listener and handler
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                int i = 1;
               while (i <= colors.length){
                    if (newValue.doubleValue() <= (0.1*i)){
                        currSelectedColor = Color.web(colors[i-1]);
                        i += colors.length;
                        currSelectedColorLabel.setBackground(new Background(new BackgroundFill(currSelectedColor, new CornerRadii(0), new Insets(0))));
                    }
                    i++;
               }
            }
         });
         
        // current selected color label
        currSelectedColorLabel = new Label("Current Color");
        currSelectedColorLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));

        color_holder = new HBox();
        color_holder.getChildren().addAll(slider, currSelectedColorLabel);
        GridPane.setConstraints(color_holder, 0, 12, 7, 3);
        trackerSpreadPane.getChildren().add(color_holder);
    }
}
