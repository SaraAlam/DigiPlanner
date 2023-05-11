import java.util.Date;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.Calendar;
import java.time.Instant;  
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 


import java.text.DateFormat;  
import java.util.Calendar;  

public class JournalEntry {
    private String entryTime;
    private String content;
    TextArea container = new TextArea();

    public JournalEntry(String entry){
        container.setEditable(false);
        container.setWrapText(true);
        container.getStyleClass().add("book-page");
        content = entry;
        Date timeOfEntry = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");  
        entryTime = dateFormat.format(timeOfEntry);
        
        
        container.setText(entryTime + "\n\n" + content);

    }

    public JournalEntry(String entry, String originalEntry){
        container.setEditable(false);
        container.setWrapText(true);
        container.getStyleClass().add("book-page");
        Date timeOfEntry = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");  
        content = entry;
        entryTime = dateFormat.format(timeOfEntry);
        
        String curEnt = originalEntry + "\n\nEdited on: "; 
        container.setText(curEnt + "\n" + entryTime + "\n\n" + content);
        entryTime = originalEntry;

    }


    public String getContent(){
        return content;
    }
    
    public String getEntryTime(){
        return entryTime;
    }

    public TextArea getContainer(){
        return container;
    }

}
