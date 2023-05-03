import java.io.*;
import javafx.collections.ObservableList;
import java.util.ArrayList;


class ToDoFileHandler {
    
    public static void saveToDos(ArrayList<ToDoList> allToDoLists, String filename, int numDays) {
        System.out.println("Are we here?");
        try {
            FileOutputStream toDoFile = new FileOutputStream(filename);
            DataOutputStream toDoWriter = new DataOutputStream(toDoFile);
            toDoWriter.writeInt(numDays);
            for (int i=0; i < numDays; i++) { //needs the amount of tasks it needs to add on line 0 and then 1 to end+1 lines are filled by tasks
                for (int j=0; j < allToDoLists.get(i).listTasks.size(); j++){
                    toDoWriter.writeUTF(allToDoLists.get(i).listTasks.get(j).getTaskDetails());
                }
                toDoWriter.writeUTF("\n======\n");
            }
            toDoWriter.flush();
            toDoWriter.close();
        }
        catch(IOException ioe) {
            System.out.println("Error writing file");
        }
        
    }  
    
    // public static void readTasks(ObservableList<Task> tasks, String filename) {}
        
    //     try {
    //         FileInputStream tasksFile = new FileInputStream(filename);
    //         DataInputStream tasksReader = new DataInputStream(tasksFile);

    //         String thisTask;
    //         int amtTasks = tasksReader.readInt();
    //         for (int j = 0; j<amtTasks; j++) {
    //             thisTask = tasksReader.readUTF();
    //             tasks.add(new Task(thisTask));
    //         }
    //         tasksReader.close();
    //     }
        
    //     catch(IOException ioe) {
    //         System.out.println("No records found");
    //     }
        
    // }
}
