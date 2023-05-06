package monthTitles;
import java.io.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Scanner;

import Tracker;
import TrackerList;

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
    public static void saveRecords(TrackerList tList, String dirname) {
        
        try {
            String fpath = dirname+"/"+tList.name+"Tracker.txt";

            File oldFile = new File(fpath);
            if (Files.exists(Paths.get(fpath))){oldFile.delete();}
            FileWriter trackerFile = new FileWriter(dirname+"/"+tList.name+"Tracker.txt");
            //DataOutputStream trackerWriter = new DataOutputStream(trackerFile);
            trackerFile.write(tList.name+","+tList.year+","+tList.mode+"\n\n");
            if (tList.mode!=tList.DEFAULT){
                String categories_str = "User Categories: ";
                String[] categories = tList.categories;
                for (int k = 0; k < categories.length ; k++){
                    categories_str += categories[k];
                    if(k!=categories.length-1){
                        categories_str += ", ";
                    }
                    else{
                        categories_str += "\n\n";
                    }
                }
                trackerFile.write(categories_str);
            }
            
            ArrayList<Tracker> monthly_trackers = tList.monthly_trackers;
            for (int i=0; i < monthly_trackers.size(); i++) {
                Tracker t = monthly_trackers.get(i);
                trackerFile.write(t.month+"\n");
                HashMap<Integer, String> dayCats = t.dayCats;
                int num_days = t.get_num_days();
                for(int j = 1; j <= num_days; j++){
                    String start = "( ";
                    if (j < 10){start = start + "0";}
                    trackerFile.write(start+j+"-"+dayCats.get(j)+" )");
                    if (j!=num_days){
                        trackerFile.write(" , ");
                    }
                    else{
                        trackerFile.write(". ");
                    }
                }
                trackerFile.write("\n\n");
            }
            trackerFile.close();
        }
        catch(IOException ioe) {
            System.out.println("Error writing file");
        }
        
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
    }

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
