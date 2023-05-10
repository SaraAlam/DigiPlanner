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
import javafx.scene.layout.Region;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;

public class TrackerList {
    String month;
    int numDays;
    SpreadBundle parentBundle;
    public int first_day;
    String[] trackerNames = {"Water", "Workout","Mood","Study","Sleep"};
    HashMap<String,Tracker> trackers = new HashMap<String,Tracker>();
    public Tracker currTracker;
    public String[] colors = {"#CCCCFF","#C4C3D0", "#8C92AC", "#92A1CF",
     "#2A52BE", "#0000FF","#002FA7","#003399", "#00009C", "#120A8F"};

    public ComboBox<String> trackerMenu;
    public HBox color_holder;
    public Label currSelectedColorLabel;
    public Color currSelectedColor;
    public int currSelectedColorIdx;
    public GridPane trackerSpreadPane;
    VBox icon = new VBox();

    public TrackerList(String m, int nDays, SpreadBundle pBundle) throws Exception{
        month = m;
        numDays = nDays;
        parentBundle = pBundle;
        first_day = get_month_first_day();
        for(int i = 0; i < trackerNames.length; i++){
            Tracker t = new Tracker(trackerNames[i], month, numDays, first_day, this);
            trackers.put(trackerNames[i], t);
        }
        currTracker = trackers.get("Water");
        trackerSpreadPane = new GridPane();
        trackerSpreadPane.setVgap(10);

        set_up_view();
    }

    public void set_up_view()throws Exception{
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
        //trackerSpreadPane.getChildren().addAll(year_and_month_label, spread_title);
    }

    public void create_drop_down() throws Exception{
        ObservableList<String> trackerNamesObs = FXCollections.observableArrayList(trackerNames);
        trackerMenu = new ComboBox<String>();
        trackerMenu.setPrefSize(200, 40);
        trackerMenu.setStyle("-fx-font-size: 18");
        trackerMenu.setId("menu");
        trackerMenu.setItems(trackerNamesObs);
        trackerMenu.setValue(currTracker.name);
        trackerMenu.valueProperty().addListener(
            new ChangeListener<String>(){
                @Override
                public void changed(ObservableValue observable, String oldValue, String newValue) {
                    trackerSpreadPane.getChildren().clear();
                    currTracker = trackers.get(newValue);
                    try{
                        set_up_view();
                    }catch(Exception e){
                        System.out.println("Could not find a file");
                    }
                }
        });

        icon.setId("icon");
        icon.setStyle("-fx-background-image:url('" + trackerMenu.getValue() + ".png')");
        icon.setPrefSize(40, 40);
        HBox trackerInfo = new HBox(trackerMenu, icon);
        trackerInfo.setSpacing(10);
        GridPane.setConstraints(trackerInfo, 0, 2);
        trackerSpreadPane.getChildren().addAll(trackerInfo);
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

    public void create_color_holder() throws Exception{
        // create slider
        Slider slider = new Slider(0, 10, 1);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        //slider.setMajorTickUnit(0.25f);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        //slider.setBlockIncrement(0.125f);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);
        slider.setShowTickLabels(false);
        slider.resize(60, 15);
        slider.setPrefWidth(350);

        //slider listener and handler
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue <?extends Number>observable, Number oldValue, Number newValue){
                int i = 1;
               while (i <= colors.length){
                    if (newValue.doubleValue() <= (i)){
                        currSelectedColor = Color.web(colors[i-1]);
                        currSelectedColorIdx = i-1;
                        i += colors.length;
                        currSelectedColorLabel.setBackground(new Background(new BackgroundFill(currSelectedColor, new CornerRadii(0), new Insets(0))));
                    }
                    i++;
               }
            }
         });

        currSelectedColor = Color.web(colors[1]);

        slider.setPrefWidth(400);
         
        // current selected color label
        currSelectedColorLabel = new Label("        ");
        Label colorLabelDesc = new Label("Current color: ");
        currSelectedColorLabel.setBackground(new Background(new BackgroundFill(currSelectedColor, new CornerRadii(0), new Insets(0))));
        currSelectedColorLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        color_holder = new HBox();
        color_holder.getChildren().addAll(slider, colorLabelDesc, currSelectedColorLabel);

        HBox color_holder_descriptor = new HBox();
        FileInputStream input = new FileInputStream("Happy_transp.png");
        Image image = new Image(input);
        ImageView imageView1 = new ImageView(image);
        input = new FileInputStream("Sad_transp.png");
        image = new Image(input);
        ImageView imageView2 = new ImageView(image);
        imageView1.setFitHeight(40);
        imageView1.setFitWidth(50);
        imageView2.setFitHeight(40);
        imageView2.setFitWidth(50); 
        color_holder_descriptor.setSpacing(250);
        color_holder_descriptor.getChildren().addAll(imageView2, imageView1);

        VBox color_info_container = new VBox();
        color_info_container.getChildren().addAll(color_holder, color_holder_descriptor);
        GridPane.setConstraints(color_info_container, 0, 12, 7, 3);
        trackerSpreadPane.getChildren().add(color_info_container);
    }
}
