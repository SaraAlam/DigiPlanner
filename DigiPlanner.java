import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
// import javafx.scene.control.TableView;
// import javafx.collections.ObservableList;
// import java.util.ArrayList;
// import javafx.scene.control.ListView;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.TabPane;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
// import javafx.scene.layout.Priority;
// import javafx.scene.layout.RowConstraints;
import javafx.geometry.Insets;
import java.util.Calendar;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.geometry.Pos;
// import javafx.scene.layout.Background;
// import javafx.scene.layout.BackgroundFill;
// import javafx.scene.layout.Border;
// import javafx.scene.layout.BorderStroke;
// import javafx.scene.layout.CornerRadii;
// import javafx.scene.paint.Color;
//import java.util.Date;

import java.util.Locale;
import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;

public class DigiPlanner extends Application{
    private int WIDTH = 800;
    private int HEIGHT = 600;
    public static int year = 2023;
    public int currMonth = Calendar.getInstance().get(Calendar.MONTH);
    
    //create the calendar view
    DatePicker dp = new DatePicker();
    Calendar selectedDate = Calendar.getInstance();
    Date selDate;
    public int year = Calendar.getInstance().get(Calendar.YEAR); // Only works for 2023 for now
    public String currMonth = Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
    public int currDayNum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public String currDayName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());

    // For navigator label
    Label viewing_label = new Label();
    String[] numEnd = {"th", "st", "nd", "rd", "th"};

    public BorderPane rightDisplay = new BorderPane();
    public GridPane root;
    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public HashMap<String,SpreadBundle> monthlyBundles = new HashMap<String,SpreadBundle>();

    public DatePicker dp = new DatePicker();
    
    public void start(Stage stage){
        GridPane root = create_root();
        GridPane left_nav = create_left_nav();

        GridPane.setConstraints(left_nav, 0, 0);
        root.getChildren().add(left_nav);

        dp.setOnAction(e ->{
            Instant instant = Instant.from(dp.getValue().atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            System.out.println(date);
            selectedDate.setTime(date);
            year = selectedDate.get(Calendar.YEAR); // Only works for 2023 for now
            currMonth = selectedDate.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
            currDayNum = selectedDate.get(Calendar.DAY_OF_MONTH);
            currDayName = selectedDate.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        
            int dayDigit = currDayNum % 10;
            if(dayDigit > 4){
                dayDigit = 4;
            }
            viewing_label.setText("Viewing: " + currDayName + " " + currDayNum + numEnd[dayDigit] + ", " +  currMonth + ", " + + year);
        });

        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        scene.getStylesheets().add(getClass().getResource("planner.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("DigiPlanner");
        stage.show(); 
        
    }

    public GridPane create_root(){
        GridPane g = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        g.getColumnConstraints().addAll(col1,col2);

        create_spread_bundles();
        TabPane currTabPane = monthlyBundles.get(currMonth).displayPane;
        BorderPane.setAlignment(currTabPane, Pos.TOP_LEFT);
        BorderPane.setMargin(currTabPane, new Insets(20,20,20,20));
        rightDisplay.setCenter(currTabPane);
        rightDisplay.setId("rightDisplay");
        //rightDisplay.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setConstraints(rightDisplay, 1, 0);
        g.getChildren().add(rightDisplay);
        return g;
    }

    public GridPane create_left_nav(){
        GridPane g = new GridPane();
        g.setPrefHeight(Integer.MAX_VALUE);
        int dayDigit = currDayNum % 10;
        if(dayDigit > 4){
            dayDigit = 4;
        }
        viewing_label.setText("Viewing: " + currDayName + " " + currDayNum + numEnd[dayDigit] + ", " +  currMonth + ", " + + year);
        viewing_label.setFont(new Font("Times New Roman", 20));

        
        g.setPadding(new Insets(5));
        g.setId("navigator");
        g.setVgap(10);

        GridPane.setConstraints(viewing_label, 0,0, 2, 1);
        g.getChildren().add(viewing_label);

        //create the calendar view
        DatePickerSkin test = new DatePickerSkin(dp);
        Node newdp = test.getPopupContent();

        GridPane.setConstraints(newdp, 0,1, 1, 1);
        g.getChildren().add(newdp);

        //imageview of blueberry

        //save button

        //quit button

        //notes button (maybe not now)

        return g;
    }

    public void create_spread_bundles(){
        for(String month:months){
            //make spread bundle objects
            int num_days = get_num_days(month);
            monthlyBundles.put(month,new SpreadBundle(month, num_days));
        }
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
    public int get_num_days(String month){
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


}
