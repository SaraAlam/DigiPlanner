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

import java.util.ArrayList;

public class Journal {
    ArrayList<JournalEntry> entries;
    
    public Journal(){
        entries = new ArrayList<JournalEntry>();
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

    public static TextField test(){
        TextField cont = new TextField();
        cont.setPromptText("Add a new entry!");
        Journal temp = new Journal();
        cont.setOnAction(e -> {
            if(!cont.getText().isEmpty()){
                JournalEntry[] cur = new JournalEntry[1];
                JournalEntry entry = new JournalEntry(cont.getText());
                cur[0] = entry;
                temp.addEntries(cur);
                cont.setText("");
                int k = temp.getJournalSize();
                for(int i = 0; i<k; i++){
                    System.out.println(temp.getEntry(i).getTime());
                }
                
            }
        });
        return cont;
    }


}
