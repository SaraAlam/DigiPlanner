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

import java.util.HashMap;


public class JournalList {
    HashMap<Integer, Journal> journals = new HashMap<Integer, Journal>();
    int numDays;
    Journal currJournal;
    SpreadBundle parentBundle;

    public JournalList(int nDays, SpreadBundle pBundle){
        numDays = nDays;
        for(int i = 1; i < nDays+1; i++){
            Journal j = new Journal();
            journals.put(i, j);
        }
        currJournal = new Journal();
        parentBundle = pBundle;
    }
}
