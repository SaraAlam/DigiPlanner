
import java.io.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Scanner;



/**
* Class for file i/o code of DigiPlanner
* Stores information in a specific format
* defined in the saveRecords method
*/
class DPFileHandler {
    
     /**
     * Method to save a trackerList object's information in a text file, in the provided directory
     * @param tList: TrackerList object to store the information from
     * @param dirname: Name of the directory to create and store the text file in
     * @return void
     */
    public static void saveRecords(String month, SpreadBundle mBundle, String dirname) {
        
        try {
            saveTrackerInfo(month, mBundle, dirname);
        }
        catch(Exception ioe) {
            System.out.println("Error writing file");
        }
        
    }

    public static void saveTrackerInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        HashMap<String,Tracker> tMap = mBundle.trackerList.trackers;
        String[] trackerNames = {"Water", "Workout","Mood","Study","Sleep"};
        String fpath = dirname+"/"+month+"Trackers.txt";

        File oldFile = new File(fpath);
        if (Files.exists(Paths.get(fpath))){oldFile.delete();}
        FileWriter trackerFile = new FileWriter(fpath);
        for (int k = 0; k < trackerNames.length ; k++){
            HashMap<Integer, Integer> dayColorIndices = tMap.get(trackerNames[k]).dayColorIndices;
            int nDays = DigiPlanner.get_num_days(month);
            for (int i = 0; i < nDays; i++){
                trackerFile.write(""+dayColorIndices.get(i+1));
                if (i == nDays-1){
                    trackerFile.write(" .");
                }
                else{
                    trackerFile.write(" , ");
                }
            }
            trackerFile.write("\n\n");
        }
        trackerFile.close();
    }

    public static void saveTodoInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        HashMap<String,Tracker> tMap = mBundle.trackerList.trackers;
        String[] trackerNames = {"Water", "Workout","Mood","Study","Sleep"};
        String fpath = dirname+"/"+month+"Trackers.txt";

        File oldFile = new File(fpath);
        if (Files.exists(Paths.get(fpath))){oldFile.delete();}
        FileWriter trackerFile = new FileWriter(fpath);
        for (int k = 0; k < trackerNames.length ; k++){
            HashMap<Integer, Integer> dayColorIndices = tMap.get(trackerNames[k]).dayColorIndices;
            int nDays = DigiPlanner.get_num_days(month);
            for (int i = 0; i < nDays; i++){
                trackerFile.write(""+dayColorIndices.get(i+1));
                if (i == nDays-1){
                    trackerFile.write(" .");
                }
                else{
                    trackerFile.write(" , ");
                }
            }
            trackerFile.write("\n\n");
        }
        trackerFile.close();
    }

    /**
     * Method to read a trackerList object's information from a text file
     * Returns a HashMap of HashMaps, where the keys are month names and
     * the values are HashMaps storing (day, category) pairs
     * @param f: File to read from
     * @return HashMap<String,HashMap<Integer,String>>
     */
    
/*    public static HashMap<String,HashMap<Integer,String>> readRecords(File f) {
        try {
            String tname="";
            int mode= 0;
            String[] custom_tracker_key = null;
            HashMap<String,HashMap<Integer, String>> dayCatsMap = new HashMap<String,HashMap<Integer, String>>();
            Scanner reader = new Scanner(f);
            String month="";
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                if (!data.isEmpty()){
                    String[] tokens = data.split("[,]", 0);
                    int num_tokens = tokens.length;
                    if(num_tokens==3){
                        tname = tokens[0];
                        mode = Integer.parseInt(tokens[2]);
                    }
                    else if(num_tokens==1){month = tokens[0];}
                    else if(num_tokens==5){
                        custom_tracker_key = new String[5];
                        custom_tracker_key[0]="reset";
                        for(int i=1; i < 5; i++){
                            custom_tracker_key[i] = tokens[i].substring(1,tokens[i].length());
                        }
                    }
                    else if (num_tokens>5){
                        HashMap<Integer, String> dayCats = get_dayCats(tokens);
                        dayCatsMap.put(month, dayCats);
                    }
                    
                }
        }
        MiscTrackerApp.temp_tracker_name = tname;
        MiscTrackerApp.temp_typ_of_tracker = mode;
        MiscTrackerApp.temp_key = custom_tracker_key;

        reader.close();
        return dayCatsMap;
        }

        
        catch(IOException ioe) {
            System.out.println("No records found");
        }
      return null;  
    }*/

    /**
     * Helper method for reading saved tracker information
     * Creates a HashMap of (day,category) pairs for a particular month
     * @param tokens: String array storing each (day,category) pair
     * @return HashMap<Integer,String>
     */

  /*  private static HashMap<Integer,String> get_dayCats(String[] tokens){
        HashMap<Integer,String> dayCats = new HashMap<Integer,String>();
        String token = "";
        for(int i = 0; i < tokens.length; i++){
            token = tokens[i];
            String day_and_cat="";
            if (i==0){day_and_cat = token.substring(2, token.length()-3);}
            else if (i==tokens.length-1){day_and_cat = token.substring(3, token.length()-4);}
            else{day_and_cat = token.substring(3, token.length()-3);}
            //if(i==tokens.length-1){System.out.println(day_and_cat.substring(0,day_and_cat.length()-1));}
            //System.out.println(day_and_cat);
            String[] day_and_cat_arr = day_and_cat.split("-");
            if (day_and_cat_arr.length>1){
                int day = Integer.parseInt(day_and_cat_arr[0]);
                String cat = day_and_cat_arr[1];
                dayCats.put(day, cat);
            }
        }

        return dayCats;

    }*/
}
