import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.Arrays;

public class Journal {
    ArrayList<JournalEntry> entries;
    GridPane container = new GridPane();
    JournalEntry curEntry;
    TextArea page = new TextArea();
    
    public Journal(){
        entries = new ArrayList<JournalEntry>();

        page.setEditable(false);
        page.getStyleClass().add("book-page");

        TextField addField = toAdd();
        container.add(page, 0, 2);
        container.add(addField, 0,3);
        container.setId("journal-background");
    }

    public void addEntries(JournalEntry[] entriesToAdd){
        for(int i = 0; i < entriesToAdd.length; i++){
            entries.add(entriesToAdd[i]);
        }
    }

    public JournalEntry getEntry(int i){
        return entries.get(i);
    }


    public int getJournalSize(){
        return entries.size();
    }

    public TextField toAdd(){
        TextField cont = new TextField();
        cont.setPromptText("Add a new entry!");
        cont.setOnAction(e -> {
            if(!cont.getText().isEmpty()){
                JournalEntry[] cur = new JournalEntry[1];
                JournalEntry entry = new JournalEntry(cont.getText());
                cur[0] = entry;
                this.addEntries(cur);  
                cont.setText("");
                page = entry.container;
                container.add(page, 0, 2);
            }
        });
        return cont;
    }


}
