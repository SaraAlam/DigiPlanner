import java.util.Date;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class JournalEntry {
    final Date entryTime;
    String desc;
    String content;
    TextArea container = new TextArea();

    public JournalEntry(String entry){
        container.setEditable(false);
        container.getStyleClass().add("book-page");
        content = entry;
        int end = Math.min(6, entry.length());
        desc = content.substring(0, end);
        entryTime = new Date();
        container.setText(content);
    }

    public String getContent(){
        return content;
    }

    public Date getTime(){
        return entryTime;
    }

    public TextArea getContainer(){
        return container;
    }

}
