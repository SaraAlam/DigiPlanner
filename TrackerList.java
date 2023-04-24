import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;

public class TrackerList {
    String month;
    int numDays;
    public int first_day;
    String[] trackerNames = {"Water", "Workout","Stress","Study","Sleep"};
    HashMap<String,Tracker> trackers = new HashMap<String,Tracker>();
    public Tracker currTracker;

    public TrackerList(String m, int nDays){
        month = m;
        numDays = nDays;
        first_day = get_month_first_day();
        for(int i = 0; i < trackerNames.length; i++){
            Tracker t = new Tracker(month, numDays, first_day);
            trackers.put(trackerNames[i], t);
        }
        currTracker = trackers.get("Water");
    }

    public int get_month_first_day(){
        // month_num is 0 for January
        int month_num = get_month_num();
        Calendar cal = Calendar.getInstance();
        cal.set(DigiPlanner.year, month_num, 1);
        int first_day = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(first_day);
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
}
