import java.io.*;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.text.TableView;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.control.Label;


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
            saveToDoInfo(month, mBundle, dirname);
            saveJournalInfo(month, mBundle, dirname);
        }
        catch(Exception ioe) {
            System.out.println(ioe);
        }
        
    }

    public static void readRecords(String month, SpreadBundle mBundle, String dirname) {
        
        try {
            readTrackerInfo(month, mBundle, dirname);
            readToDoInfo(month, mBundle, dirname);
            readJournalInfo(month, mBundle, dirname);
        }
        catch(Exception ioe) {
            System.out.println(ioe);
        }
        
    }

    public static void saveTrackerInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        HashMap<String,Tracker> tMap = mBundle.trackerList.trackers;
        String[] trackerNames = {"Water", "Workout","Mood","Study","Sleep"};
        String fpath = ""+Paths.get(dirname,"Trackers.txt");

        File oldFile = new File(fpath);
        if (Files.exists(Paths.get(fpath))){oldFile.delete();}
        FileWriter trackerFile = new FileWriter(fpath);
        for (int k = 0; k < trackerNames.length ; k++){
            HashMap<Integer, Integer> dayColorIndices = tMap.get(trackerNames[k]).dayColorIndices;
            int nDays = DigiPlanner.get_num_days(month);
            for (int i = 0; i < nDays; i++){
                trackerFile.write(""+dayColorIndices.get(i+1));
                if (i != nDays-1){
                    trackerFile.write(" , ");
                }
            }
            trackerFile.write("\n\n");
        }
        trackerFile.close();
    }

    public static void saveToDoInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        ArrayList<ToDoList> allToDoLists = mBundle.aToDoMonth.allToDoLists;
        int nDays = DigiPlanner.get_num_days(month);
        String fpath = ""+Paths.get(dirname,"Todos.txt");
        File oldFile = new File(fpath);
        if (Files.exists(Paths.get(fpath))){oldFile.delete();}
        FileWriter toDoFile = new FileWriter(fpath);
        for (int k = 0; k < nDays ; k++){
            toDoFile.write("Day: "+(k+1)+"\n\n");
            ArrayList<ToDoTask> toWrite = allToDoLists.get(k).toWrite;
            if (toWrite!=null){
                int nTasks = toWrite.size();
                for (int i = 0; i < nTasks; i++){
                    ToDoTask t = toWrite.get(i);
                    String tDetails = t.getTaskDetails();
                    Boolean tDone = t.getTheDoneVal();
                    int intDone = 0;
                    if (tDone==true){ intDone = 1;}
                    toDoFile.write(""+tDetails+" , "+ intDone + "\n");
                    if (i == nTasks-1){
                        toDoFile.write("end\n\n");
                    }
                }
            }
        }
        toDoFile.close();
    }

    public static void saveJournalInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        HashMap<Integer, Journal> journals = mBundle.journalList.journals;
        int nDays = DigiPlanner.get_num_days(month);
        String fpath = ""+Paths.get(dirname,"Journal.txt");
        File oldFile = new File(fpath);
        if (Files.exists(Paths.get(fpath))){oldFile.delete();}
        FileWriter journalFile = new FileWriter(fpath);
        for (int k = 0; k < nDays ; k++){
            journalFile.write("Day: "+(k+1)+"\n\n");
            Journal j = journals.get((k+1));
            ArrayList<JournalEntry> entries = j.entries;
            int nEntries = entries.size();
            for (int i = 0; i < nEntries; i++){
                JournalEntry e = entries.get(i);
                String eDetails = e.getContent();
                String eTime = e.getEntryTime();
                journalFile.write(eTime+ " \n"+eDetails+"\n\n");
                if (i == nEntries-1){
                    journalFile.write(" \n\n");
                }
            }
        }
        journalFile.close();
    }

    public static void readTrackerInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        HashMap<String,Tracker> tMap = mBundle.trackerList.trackers;
        String[] trackerNames = {"Water", "Workout","Mood","Study","Sleep"};
        String fpath = ""+Paths.get(dirname,"Trackers.txt");
        File f = new File(fpath);
        if (Files.exists(Paths.get(fpath))){
            Scanner reader = new Scanner(f);
            int trackerIdx = -1;
            String trackerName;
            while (reader.hasNextLine()){
                String s = reader.nextLine();
                if(!s.isEmpty()){
                    trackerIdx += 1;
                    trackerName = trackerNames[trackerIdx];
                    String[] tokens = s.split(" , ",0);
                    int colorIdx = 0;
                    for (int i=0; i < tokens.length; i++){
                        String colorIdxStr = tokens[i];
                        colorIdx = Integer.parseInt(colorIdxStr);
                        if (colorIdx != 11){
                            Tracker t = tMap.get(trackerName);
                            t.dayColorIndices.put(i+1, colorIdx);
                            Color c = Color.web(t.parentList.colors[colorIdx]);
                            t.dayLabelColors.put(i+1, c);
                            Label dayLabel = t.dayLabels.get(i+1);
                            dayLabel.setBackground(new Background(new BackgroundFill(c, new CornerRadii(0), new Insets(0))));
                            if (colorIdx >= (0.5*t.parentList.colors.length)){
                                dayLabel.setTextFill(Color.WHITE);
                            }else{
                                dayLabel.setTextFill(Color.BLACK);
                            }
                        }
                    }
                }

            }
            reader.close();
        }
    }

    public static void readToDoInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        ArrayList<ToDoList> allToDoLists = mBundle.aToDoMonth.allToDoLists;
        int nDays = DigiPlanner.get_num_days(month);

        String fpath = ""+Paths.get(dirname,"Todos.txt");
        File f = new File(fpath);
        if (Files.exists(Paths.get(fpath))){
            Scanner reader = new Scanner(f);
            int nDay = -1;
            while (reader.hasNextLine()){
                String s = reader.nextLine();
                if(!s.isEmpty()){
                    String[] tokens = s.split(" , ",0);
                    if (tokens[0].contains("Day: ")){
                        nDay++;
                    }
                    else if (tokens.length == 2){
                        String taskDets = tokens[0];
                        int done = Integer.parseInt(tokens[1]);
                        ToDoTask t = new ToDoTask(taskDets);
                        if (done==0){t.setDone(false);}
                        else{t.setDone(true);}
                        allToDoLists.get(nDay).listTasks.add(t);
                        allToDoLists.get(nDay).toWrite.add(t);
                    }
                }
            }
            reader.close();
        }
    }

    public static void readJournalInfo(String month, SpreadBundle mBundle, String dirname) throws Exception{
        JournalList jList = mBundle.journalList;
        String fpath = ""+Paths.get(dirname,"Journal.txt");
        File f = new File(fpath);
        if (Files.exists(Paths.get(fpath))){
            Scanner reader = new Scanner(f);
            int nDay = -1;
            while (reader.hasNextLine()){
                String s = reader.nextLine();
                if(!s.isEmpty()){
                    String[] tokens = s.split(" , ",0);
                    if (tokens[0].contains("Day: ")){
                        nDay++;
                    }
                    else{
                        String entryTime = s;
                        String entry = reader.nextLine();
                        JournalEntry je = new JournalEntry(entry);
                        je.setEntryTime(entryTime);
                        jList.journals.get(nDay).entries.add(je);
                        Journal j = jList.journals.get(nDay);
                        ArrayList<JournalEntry> entries = j.entries;
                        System.out.println(entries.get(entries.size()-1).getContent());
                        if (j.get)
                    }
                }
            }
            reader.close();
        }
    }

}
