import java.util.Date;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.text.DateFormat;  
import java.util.Calendar;  
import java.text.SimpleDateFormat;  

public class JournalEntry {
    private String entryTime;
    private String desc;
    private String content;
    TextArea container = new TextArea();

    public JournalEntry(String entry){
        container.setEditable(false);
        container.setWrapText(true);
        container.getStyleClass().add("book-page");
        content = entry;
        
        int end = Math.min(6, entry.length());
        desc = content.substring(0, end);
        Date timeOfEntry = new Date();
        Calendar 
        DateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");  
        entryTime = dateFormat.format(timeOfEntry);
        container.setText(content);
    }

    public String getContent(){
        return content;
    }

    public String getDesc(){
        return content;
    }

    public String getEntryTime(){
        return entryTime;
    }

    public TextArea getContainer(){
        return container;
    }

}
