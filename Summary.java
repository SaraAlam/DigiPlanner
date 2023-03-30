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
import javafx.scene.control.TableCell;

/**
* Class to create a summary tableview for MiscTrackerApp
*/
public class Summary {
    
/**************** NEW GLOBAL VARIABLES ADDED ********************************/
    /**
    * Stores the title and the tableview of the summary
    */
    public static VBox sum_box = null;
    /**
    * Table holding the summary
    */
    public static TableView<Tsum> table = null;
    /**
    * List of Tsum objects, one for each tracker
    */
    public static ArrayList<Tsum> tsums = null;
    /**
    * Stores observable form of the list of Tsum objects
    */
    public static ObservableList<Tsum> obs_tsums = null;

/************************* ***************************************************/
    /*
    * Method to create tableview of each trackers' current week days
    * @return an void
    */
    public static void make_sum_view(){

        if (sum_box != null){ sum_box.getChildren().clear();}
        else{ sum_box = new VBox();}

        tsums = new ArrayList<Tsum>();
        for(String tListName: MiscTrackerApp.allTrackerNames){
            TrackerList tList = MiscTrackerApp.trackerLists.get(tListName);
            Tracker currTracker = tList.monthly_trackers.get(tList.currMonth);
            Tsum tsum = new Tsum(tListName);
            tsums.add(tsum);
        }

        obs_tsums = FXCollections.observableArrayList(tsums);

        table = new TableView<Tsum>();
        table.setMaxWidth(360);
        table.setMaxHeight(tsums.size()*45);
 

        TableColumn tnameCol = new TableColumn("Tracker");
        tnameCol.setCellValueFactory(
              new PropertyValueFactory<Tsum, String>("tname"));
        tnameCol.setEditable(false);
        tnameCol.setCellFactory(column -> {
            return new TableCell<Tsum, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item!=null){
                        setText(item);
                        setFont(new Font("Times New Roman", 20));}}};});

        TableColumn tsumboxCol = new TableColumn("Days");
        tsumboxCol.setEditable(false);

        TableColumn day1col = new TableColumn("");
        day1col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day1"));
        addColor(day1col);

        TableColumn day2col = new TableColumn("");
        day2col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day2"));
        addColor(day2col);


        TableColumn day3col = new TableColumn("");
        day3col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day3"));
        addColor(day3col);

        TableColumn day4col = new TableColumn("");
        day4col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day4"));
        addColor(day4col);
    
        
        TableColumn day5col = new TableColumn("");
        day5col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day5"));
        addColor(day5col);

        TableColumn day6col = new TableColumn("");
        day6col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day6"));
        addColor(day6col);

        TableColumn day7col = new TableColumn("");
        day7col.setCellValueFactory(
                new PropertyValueFactory<Tsum, String>("day7"));
        addColor(day7col);

        tsumboxCol.getColumns().addAll(day1col, day2col, day3col, day4col, day5col, day6col, day7col);

        table.setItems(obs_tsums);
        table.getColumns().addAll(tnameCol, tsumboxCol);
        table.setMouseTransparent(true);

        Label header = new Label("This Week's Tracker Summary");
        header.setFont(new Font("Times New Roman", 25));

        sum_box.getChildren().addAll(header, table);

    }

    /**
     * Method to edit cell of a Table Column
     * @param col: TableColumn to edit
     */
    public static void addColor(TableColumn col){
        col.setCellFactory(column -> {
            return new TableCell<Tsum, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item!=null){
                        setText(item.substring(0,2));
                        setFont(new Font("Times New Roman", 20));
                        Color color = Color.web(item.substring(2,item.length()));
                        String colorString = "rgb(" + color.getRed() * 255 + "," + color.getGreen() * 255 + "," + color.getBlue() * 255 + ");";
                        setStyle("-fx-background-color: "+colorString);
                    }
                }
            };
        });
    }
}
   

