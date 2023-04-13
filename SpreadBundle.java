import java.util.Calendar;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class SpreadBundle {

    public String month = "January";
    public int num_days = 31;
    public int currDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    //public ToDoDays tododays =// new ToDoDays();
    //public TrackerList trackerList =// new TrackerList();
    //public JournalList journalList =// new JournalList();
    public TabPane displayPane;

    public SpreadBundle(String m, int n_d){
        month = m;
        num_days = n_d;
        displayPane = create_tab_pane();
        
    }
    
    public TabPane create_tab_pane(){
        TabPane tabpane = new TabPane();
        String[] tabNames = {"Home", "Todo", "Trackers", "Journal"};
        for(String tabName: tabNames){
            Tab tab = new Tab();
            tab.setText(tabName);
            tab.setContent(new Rectangle(200,200, Color.LIGHTSTEELBLUE));
            tabpane.getTabs().add(tab);
        }

        return tabpane;
    }
}
