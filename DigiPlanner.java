import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import java.util.ArrayList;
import javax.swing.text.TableView;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Locale;

import java.util.Date;
import java.time.Instant;
import java.time.ZoneId;

import java.io.*;
import java.nio.file.*;



public class DigiPlanner extends Application{
    private int WIDTH = 800;
    private int HEIGHT = 700;
    public static int year = Calendar.getInstance().get(Calendar.YEAR);;
    public static int currMonth = Calendar.getInstance().get(Calendar.MONTH);

    public BorderPane rightDisplay = new BorderPane();
    public GridPane left_nav;
    public GridPane root;
    public static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    public static HashMap<String,SpreadBundle> monthlyBundles = new HashMap<String,SpreadBundle>();
    
    //create the calendar view
    DatePicker dp = new DatePicker();
    Calendar selectedDate = Calendar.getInstance();
    Date selDate;
    public String currMonthStr = months[currMonth]; //Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
    public static int currDayNum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public String currDayName = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());

    // For navigator label
    Label viewing_label = new Label();
    String[] numEnd = {"th", "st", "nd", "rd", "th"};
    
    public static TextArea msgCont = new TextArea();

    public void start(Stage stage) throws Exception{
        GridPane root = create_root();
        left_nav = create_left_nav();

        GridPane.setConstraints(left_nav, 0, 0);
        root.getChildren().add(left_nav);

        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        scene.getStylesheets().add(getClass().getResource("planner.css").toExternalForm());
        switch(currMonth){
            case 11:
            case 0:
            case 1:
                scene.getStylesheets().add(getClass().getResource("snow.css").toExternalForm());
                break;
            case 2:
            case 3:
            case 4:
                scene.getStylesheets().add(getClass().getResource("pink.css").toExternalForm());
                break;
            case 5:
            case 6:
            case 7:
                System.out.println("Green");
                break;
            case 8:
            case 9:
            case 10:
                System.out.println("Burnt");
                break;
        }

        dp.setOnAction(e ->{
            Instant instant = Instant.from(dp.getValue().atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            System.out.println(date);
            selectedDate.setTime(date);
            year = selectedDate.get(Calendar.YEAR); // Only works for 2023 for now
            currMonth = selectedDate.get(Calendar.MONTH);
            currMonthStr = selectedDate.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
            currDayNum = selectedDate.get(Calendar.DAY_OF_MONTH);
            currDayName = selectedDate.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
            
            SpreadBundle currBundle = monthlyBundles.get(currMonthStr);
            currBundle.currDay = currDayNum;
            currBundle.journalList.currJournal = currBundle.journalList.journals.get(currDayNum);
            currBundle.tabs.get("Journal").setContent(currBundle.journalList.currJournal.container);
            ToDoMonth currToDoMonth = currBundle.aToDoMonth;
            currToDoMonth.currDay = currDayNum - 1;
            currToDoMonth.toDoTable.setItems(currToDoMonth.allToDoLists.get(currToDoMonth.currDay).listTasks);
            TabPane currTabPane = currBundle.displayPane;
            BorderPane.setAlignment(currTabPane, Pos.TOP_LEFT);
            BorderPane.setMargin(currTabPane, new Insets(20,20,20,20));
            rightDisplay.setCenter(currTabPane);
        
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("planner.css").toExternalForm());
            switch(currMonth){
                case 11:
                case 0:
                case 1:
                    scene.getStylesheets().add(getClass().getResource("snow.css").toExternalForm());
                    break;
                case 2:
                case 3:
                case 4:
                    scene.getStylesheets().add(getClass().getResource("pink.css").toExternalForm());
                    break;
                case 5:
                case 6:
                case 7:
                    scene.getStylesheets().add(getClass().getResource("green.css").toExternalForm());
                    break;
                case 8:
                case 9:
                case 10:
                    scene.getStylesheets().add(getClass().getResource("burnt.css").toExternalForm());
                    break;
            }

            int dayDigit = currDayNum % 10;
            if(dayDigit > 4){
                dayDigit = 4;
            }
            viewing_label.setText(currDayName + " " + currDayNum + numEnd[dayDigit] + "," + "\n" +   currMonthStr + ", " + + year);
        });

        reload();

        stage.setMinWidth(WIDTH);
        stage.setMinHeight(HEIGHT);

        stage.setScene(scene);
        stage.setTitle("DigiPlanner");
        stage.show(); 
        
        /*stage.setScene(scene);
        stage.setTitle("DigiPlanner");
        stage.show(); */

        
    }


    public GridPane create_root() throws Exception{
        GridPane g = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(70);
        g.getColumnConstraints().addAll(col1,col2);

        create_spread_bundles();
        TabPane currTabPane = monthlyBundles.get(currMonthStr).displayPane;
        BorderPane.setAlignment(currTabPane, Pos.TOP_LEFT);
        BorderPane.setMargin(currTabPane, new Insets(20,20,20,20));
        rightDisplay.setCenter(currTabPane);
        rightDisplay.setId("rightDisplay");
        //rightDisplay.setBackground(new Background(new BackgroundFill(Color.PINK, CornerRadii.EMPTY, Insets.EMPTY)));
        GridPane.setConstraints(rightDisplay, 1, 0);
        g.getChildren().add(rightDisplay);
        return g;
    }

    public GridPane create_left_nav() throws Exception{
        GridPane g = new GridPane();
        g.setPrefHeight(Integer.MAX_VALUE);
        int dayDigit = currDayNum % 10;
        if(dayDigit > 4){
            dayDigit = 4;
        }
        viewing_label.getStyleClass().add("labels");
        viewing_label.setText(currDayName + " " + currDayNum + numEnd[dayDigit] + ","+ "\n"  +  currMonthStr + ", " + + year);
        viewing_label.getStyleClass().add("labels");

        
        g.setPadding(new Insets(5));
        g.setId("navigator");
        g.setVgap(10);

        GridPane.setConstraints(viewing_label, 0,0, 2, 1);
        g.getChildren().add(viewing_label);

        Button toDay = new Button("Take me to Today!");
        //save button
        Button save_button = new Button("Save");
        save_button.setId("save");
        save_button.setOnAction( e -> {try{
            save_button.setDisable(true);
            save_info(save_button);}
        catch(Exception exc){
            System.out.println(exc);
        }});
        HBox topCalendar = new HBox(toDay, save_button);
        topCalendar.setSpacing(45);
        //create the calendar view
        DatePickerSkin test = new DatePickerSkin(dp);
        Node newdp = test.getPopupContent();
        VBox calendar = new VBox(topCalendar, newdp);
        calendar.setPadding(new Insets(0, 0, -18 ,0));
        
        toDay.setId("today");
        toDay.setOnAction(evt -> toDayPressed());

        GridPane.setConstraints(calendar, 0, 2, 2, 1);
        g.getChildren().add(calendar);


        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(90);
        g.getColumnConstraints().addAll(col1);

        VBox leaf = new VBox();
        leaf.setId("leaf");
        leaf.setPrefSize(200, 180);

        VBox msg = new VBox(msgCont);

        
        msg.setId("speech-bub");
        msg.setPrefSize(200, 150);

        msgCont.setId("speech");
        msgCont.setEditable(false);
        msgCont.setText("Welcome!");
        msgCont.setPrefSize(75, 125);
        msgCont.setWrapText(true);

        msg.setPadding(new Insets(-4, 15, 0, 20));
        msg.setAlignment(Pos.CENTER);

        VBox mascot = new VBox(msg, leaf);
        
        mascot.setAlignment(Pos.BOTTOM_CENTER);
        mascot.setPrefSize(200, 375);

        
        mascot.setVisible(true);

        GridPane.setConstraints(mascot, 0, 3, 2, 1);
        g.getChildren().add(mascot);

        // GridPane.setConstraints(save_button, 0, 4);
        // g.getChildren().add(save_button);

        return g;
    }

    public void create_spread_bundles() throws Exception{
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
    public static boolean is_leap(){
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
    public static int get_num_days(String month){
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

    public static void updateMsg(String m){
        msgCont.setText(m);
    }

    /*
     * Reorders the todo spread tab so that completed tasks appear at the bottom
     * This method is called every time a change occurs inside of the todo spread tab
     */
    public static void updateToDos(){
        int selectedDay = monthlyBundles.get(months[currMonth]).aToDoMonth.currDay;
        ObservableList<ToDoTask> toOrganize = monthlyBundles.get(months[currMonth]).aToDoMonth.allToDoLists.get(selectedDay).listTasks;
        int size = toOrganize.size();
        int changeAt = 0;
        for (int i=0; i<size; i++){
            if (toOrganize.get(i).getTheDoneVal()){
                for(int j = size-1; j>i; j--){
                    if(!toOrganize.get(j).getTheDoneVal()){
                        ToDoTask temp = toOrganize.get(i);
                        toOrganize.set(i, toOrganize.get(j));
                        toOrganize.set(j, temp);
                    }
                }
            }
        }
        ObservableList<ToDoTask> lTasks = monthlyBundles.get(months[currMonth]).aToDoMonth.allToDoLists.get(selectedDay).listTasks;
        lTasks = toOrganize;
        monthlyBundles.get(months[currMonth]).aToDoMonth.toDoTable.setItems(lTasks);
    }

    /*
     * Updates the graph in the home page
     * This method is called every time a change occurs inside of the todo spread tab
     */
    public static void getCompletionRates(){
        int selectedDay = monthlyBundles.get(months[currMonth]).aToDoMonth.currDay;
        int numDays = monthlyBundles.get(months[currMonth]).numDays;
        ArrayList<ToDoTask> toView = monthlyBundles.get(months[currMonth]).aToDoMonth.allToDoLists.get(selectedDay).toWrite;
        if (toView.size() == 0){
            return;
        }
        double numer = 0;
        double denom = 0;
        for (int i = 0; i<toView.size(); i++){
            if (toView.get(i).getTheDoneVal()){
                numer += 1;
            }
            denom ++;
        }
        monthlyBundles.get(months[currMonth]).aToDoMonth.rates[selectedDay] = numer/denom;
        ToDoMonth aToDoMonth = monthlyBundles.get(months[currMonth]).aToDoMonth;
        
        for (int j = 0; j < numDays; j++){
            aToDoMonth.dailyTaskCompletionRate.put(j,aToDoMonth.rates[j]);
        }
        LineChart<Number,Number> lineChart = monthlyBundles.get(months[currMonth]).monthlyHome.lineChart;
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        for (int i = 0; i < numDays; i ++){
            if (aToDoMonth.dailyTaskCompletionRate.containsKey(i)){
                Number rate = aToDoMonth.dailyTaskCompletionRate.get(i);
                series.getData().add(new XYChart.Data<Number, Number>(i,rate));
            }
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

    /*
     * Brings the user to today in the navigator
     */
    public void toDayPressed(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int currMonth = Calendar.getInstance().get(Calendar.MONTH);
        int currDayNum = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Calendar calendar = Calendar.getInstance();
        LocalDate localDate = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
        dp.setValue(localDate);
    }

    public void save_info(Button save_button) throws Exception{
        TabPane currTabPane = monthlyBundles.get(currMonthStr).displayPane;
        BorderPane.clearConstraints(currTabPane);
        rightDisplay.getChildren().clear();


        Label saving_label = new Label("Saving...");
        rightDisplay.setCenter(saving_label);
        

        String dirname = ""+Paths.get(System.getProperty("user.dir"),"PlannerInfo");
        Path d = Paths.get(dirname);
        if (Files.notExists(d)){File f = new File(dirname); f.mkdirs(); dirname = f.getPath();}
        dirname = ""+Paths.get(dirname,""+year);
        if (Files.notExists(d)){File f = new File(dirname); f.mkdirs(); dirname = f.getPath();}
        d = Paths.get(dirname);
        File f = new File(dirname);
        if (Files.notExists(d)){ f.mkdirs();}

        //call function in file_handler
        String monthDirname = "";
        for(String m: months){
            monthDirname = dirname + "/"+ m;
            d = Paths.get(monthDirname);
            File monthF = new File(monthDirname);
            if (Files.notExists(d)){ monthF.mkdirs();}
            SpreadBundle mBundle = monthlyBundles.get(m);
            DPFileHandler.saveRecords(m, mBundle, monthDirname);
        }

        BorderPane.clearConstraints(saving_label);
        rightDisplay.getChildren().clear();
        rightDisplay.setCenter(currTabPane);

        save_button.setDisable(false);

        System.out.println("Save");
    }

    public void reload(){
        String dirname = ""+Paths.get(System.getProperty("user.dir"),"PlannerInfo",""+year);
        String monthDirname;
        Path d;
        for(String m: months){
            monthDirname = ""+Paths.get(dirname,m);
            d = Paths.get(monthDirname);
            if (Files.exists(d)){
                SpreadBundle mBundle = monthlyBundles.get(m);
                DPFileHandler.readRecords(m, mBundle, monthDirname);
            }
        }
    }
}
