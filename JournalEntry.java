import java.util.Date;
import javafx.beans.property.SimpleBooleanProperty;

public class JournalEntry {
    final Date entryTime;
    String desc;
    String content;

    public JournalEntry(String entry){
        content = entry;
        int end = Math.min(6, entry.length());
        desc = content.substring(0, end);
        entryTime = new Date();
    }

    public String getContent(){
        return content;
    }

    public Date getTime(){
        return entryTime;
    }

}
