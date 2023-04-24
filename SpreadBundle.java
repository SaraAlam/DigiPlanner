import java.util.Calendar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SpreadBundle {

    public String month = "January";
    public int numDays = 31;
    public int currDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    public ToDoMonth aToDoMonth = new ToDoMonth(month, numDays);// new ToDoDays();
    public TrackerList trackerList;
    public JournalList journalList = new JournalList(numDays);// new JournalList();
    public TabPane displayPane;

    public SpreadBundle(String m, int nDays){
        month = m;
        numDays = nDays;
        trackerList = new TrackerList(month,numDays);
        displayPane = create_tab_pane();
    }
    
    public TabPane create_tab_pane(){
        TabPane tabpane = new TabPane();
        tabpane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        String[] tabNames = {"Home", "Todo", "Trackers", "Journal"};
        for(String tabName: tabNames){
            Tab tab = new Tab();
            //System.out.println(trackerList.currTracker.calendar);
            if (tabName.equals("Trackers")){
                tab.setContent(trackerList.currTracker.calendar_holder);
            } else if (tabName.equals("Todo")){
                //tab.setContent(aToDoList.toDoGridPane);
                tab.setContent(aToDoMonth.toDoMonthGridPane);
            }
            else if (tabName.equals("Journal")){
                tab.setContent(journalList.currJournal.container);
            }
            else{
                tab.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
            }
            tab.setText(tabName);
            tabpane.getTabs().add(tab);
        }
        tabpane.getSelectionModel().select(2);
        tabpane.setPrefSize(200, 400);
        return tabpane;
    }
}
