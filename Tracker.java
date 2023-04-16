import java.util.Calendar;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
//import javafx.scene.control.TableView;
//import javafx.collections.ObservableList;

public class Tracker {    
    String month;
    int numDays;
    HashMap<Integer, Label> dayLabels = new HashMap<Integer, Label>();
    int selectedDay = 0;
    FlowPane calendar = new FlowPane();

    public Tracker(String m, int nDays){
        month = m;
        numDays = nDays;
        create_calendar();
    }

    public void create_calendar(){
        for (int i=0; i<numDays; i++){
            Label dayLabel = new Label(""+(i+1));
            dayLabel.setOnMouseClicked( e -> {
                if (selectedDay!=0){
                    Label currSelectedDay = dayLabels.get(selectedDay);
                    currSelectedDay.setStyle("-fx-border-color: white;");
                }
                selectedDay = Integer.parseInt(dayLabel.getText());
                dayLabel.setStyle("-fx-border-color: black;");
            });
            calendar.getChildren().add(dayLabel);
        }
    }


}

// Mood tracker slider
    /*Slider slider = new Slider(0, 1, 0.5);
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(0.25f);
    slider.setMinorTickCount(1);
    slider.setBlockIncrement(0.125f);
    slider.setSnapToTicks(true);*/