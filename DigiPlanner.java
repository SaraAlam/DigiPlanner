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
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.TabPane;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.geometry.Insets;

public class DigiPlanner extends Application{
    private int WIDTH = 800;
    private int HEIGHT = 600;
    
    public void start(Stage stage){
    
        GridPane root = create_root();
        GridPane left_nav = create_left_nav();
        BorderPane container = new BorderPane(); // It will be a function, just to visualize rn

        container.setPadding(new Insets(5));
        container.setId("container");

        GridPane.setConstraints(left_nav, 0, 0);
        root.getChildren().add(left_nav);

        GridPane.setConstraints(container, 1, 0);
        root.getChildren().add(container);

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
        return g;
    }

    public GridPane create_left_nav(){
        GridPane g = new GridPane();
        g.setPrefHeight(Integer.MAX_VALUE);
        Label viewing_label = new Label("Viewing: ");
        viewing_label.setFont(new Font("Times New Roman", 20));

        
        g.setPadding(new Insets(5));
        g.setId("navigator");
        g.setVgap(10);

        GridPane.setConstraints(viewing_label, 0,0);
        g.getChildren().add(viewing_label);

        //create the calendar view
        DatePicker dp = new DatePicker();
        GridPane.setConstraints(dp, 0,1);
        g.getChildren().add(dp);
        //imageview of blueberry

        //save button

        //quit button

        //notes button (maybe not now)

        return g;
    }

    /*public TabPane create_right_tabpane(){
        TabPane tabpane = new TabPane();

    }*/
}
