import java.util.Calendar;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Tracker {  
    String name;  
    String month;
    int numDays;
    int first_weekly_day_of_the_month = 0;
    HashMap<Integer, Label> dayLabels = new HashMap<Integer, Label>();
    int selectedDay = 0;
    GridPane calendar = new GridPane();
    public VBox calendar_holder = new VBox(300);

    public Tracker(String n, String m, int nDays, int first_day){
        name = n;
        month = m;
        numDays = nDays;
        first_weekly_day_of_the_month = first_day;
        create_calendar();
    }

    public void create_calendar(){
        calendar_holder.setId("holder");
        int row = 0;
        int col = 0;
        for (int i=0; i<numDays; i++){
            Label dayLabel = new Label(""+(i+1));
            make_day((i+1), dayLabel);
            dayLabel.setOnMouseClicked( e -> {
                if (selectedDay!=0){
                    Label currSelectedDay = dayLabels.get(selectedDay);
                    //currSelectedDay.setStyle("-fx-border-color: white;");
                }
                selectedDay = Integer.parseInt(dayLabel.getText());
                dayLabel.setStyle("-fx-border-color: black;");
            });
            GridPane.setConstraints(dayLabel, col, row);
            calendar.getChildren().add(dayLabel);
            col++;
            if (col>=7){
                row++;
                col=0;
            }

        }
        calendar_holder.getChildren().add(calendar);
    }

    /**
     * Method to create a day label for the calendar
     * @param i: Day of the month
     * @param day: Label containing the string form of i
     */
    public void make_day(int i, Label day){
        day.setFont(new Font("Times New Roman",20));
        day.setMinWidth(40);
        day.setMinHeight(40);
        day.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0))));
        day.setAlignment(Pos.CENTER);
        day.setOnMousePressed( e -> {
            Label currSelectedDayLabel;
            if (selectedDay != 0){
                currSelectedDayLabel = dayLabels.get(selectedDay);
                currSelectedDayLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            }
            selectedDay = i;
            currSelectedDayLabel = dayLabels.get(selectedDay);
            currSelectedDayLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        });

        day.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));

        
        if (selectedDay != 0){
            if (selectedDay==i){
                Label currSelectedDayLabel = day;
                currSelectedDayLabel.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
            }
        }
    }

}
