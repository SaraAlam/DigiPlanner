import java.util.HashMap;

public class TrackerList {
    String month;
    int numDays;
    String[] trackerNames = {"Water", "Workout","Stress","Study","Sleep"};
    HashMap<String,Tracker> trackers = new HashMap<String,Tracker>();
    public Tracker currTracker;

    public TrackerList(String m, int nDays){
        month = m;
        numDays = nDays;
        for(int i = 0; i < trackerNames.length; i++){
            Tracker t = new Tracker(month, numDays);
            trackers.put(trackerNames[i], t);
        }
        currTracker = trackers.get("Water");
    }
}
