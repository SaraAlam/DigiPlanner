import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.*;
import javafx.beans.binding.Bindings;

import java.util.ArrayList;
import java.util.Arrays;

public class Journal{
    ArrayList<JournalEntry> entries;
    GridPane container = new GridPane();
    JournalEntry curEntry;
    TextArea page = new TextArea();
    GridPane book = new GridPane();
    int curIdx = -1;
    Label pageNum = new Label();
    private final TableView<JournalEntry> entryS = new TableView<>();
    private final ObservableList<JournalEntry> entriesT = 
        FXCollections.observableArrayList();
    ScrollPane bigCont = new ScrollPane(container);
    
    public Journal(){

        entries = new ArrayList<JournalEntry>();

        entryS.setItems(entriesT);

        TableColumn timeCol = new TableColumn<>("Time of Entry");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("entryTime"));
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //initCol.setPrefWidth(50);
        timeCol.setSortable(false);

        TableColumn descCol = new TableColumn<>("Text");
        descCol.setCellValueFactory(new PropertyValueFactory<>("content"));
        descCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //initCol.setPrefWidth(50);
        descCol.setSortable(false);

        timeCol.prefWidthProperty().bind(entryS.widthProperty().multiply(0.34));
        descCol.prefWidthProperty().bind(entryS.widthProperty().multiply(0.61));

        timeCol.setResizable(false);
        descCol.setResizable(false);

        entryS.getColumns().addAll(timeCol,descCol);
        entryS.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        entryS.setOnMouseClicked(e -> {
            int cur = entryS.getSelectionModel().getSelectedIndex();
            if(cur != -1){
                curIdx = cur;
                pageNum.setText(Integer.toString(curIdx+1));
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
            }
        });

        entryS.setFixedCellSize(30);
        entryS.prefHeightProperty().bind(Bindings.size(entryS.getItems()).multiply(30).add(30));



        page.setEditable(false);
        page.getStyleClass().add("book-page");
        book.add(page, 1,0);

        container.setVgap(10);
        container.setPadding(new Insets(15));

        TextArea addField = toAdd();
    

        Button arrRight = new Button(">");
        Button arrLeft = new Button("<");

        arrRight.setOnAction(e -> {
            if(curIdx < this.getJournalSize()-1){
                curIdx += 1;
                pageNum.setText(Integer.toString(curIdx+1));
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
                
            }
        });

        arrLeft.setOnAction(e -> {
            if(curIdx > 0){
                curIdx -= 1;
                pageNum.setText(Integer.toString(curIdx+1));
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
            }
        });

        HBox right = new HBox(arrLeft, pageNum, arrRight);
        right.setSpacing(20);
        
        //right.setPrefHeight(Integer.MAX_VALUE);
        right.setAlignment(Pos.BOTTOM_CENTER);
        right.setPadding(new Insets(0, 15, 0, 0));

        book.add(right, 1,1);

        book.setAlignment(Pos.CENTER);
        book.setPadding(new Insets(8, 20, 8, 0));
        book.setPrefWidth(Integer.MAX_VALUE);
        book.prefHeightProperty().bind(book.widthProperty());

        book.setId("journal-book");
        
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(20);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(80);
        book.getColumnConstraints().addAll(col1,col2);
       // book.setPrefSize(Integer.MAX_VALUE,Integer.MAX_VALUE);


        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(75);
        row1.setVgrow(Priority.ALWAYS);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(20);
        //container.getRowConstraints().addAll(row1, row2);
        book.getRowConstraints().addAll(row1, row2);


        container.add(book, 0, 0);
        container.add(addField, 0,1);
        container.add(entryS, 0,2);
       
        container.setId("journal-background");

        //bigCont.setFitToHeight(true);
        bigCont.setFitToWidth(true);
        bigCont.setContent(container);
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

    public TextArea toAdd(){
        TextArea cont = new TextArea();
        cont.setWrapText(true);
        cont.setId("add-entry");
        cont.setPromptText("Add a new entry!");
        cont.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER && !cont.getText().isEmpty()){
                JournalEntry[] cur = new JournalEntry[1];
                JournalEntry entry = new JournalEntry(cont.getText());
                cur[0] = entry;
                this.addEntries(cur);
                cont.setText("");
                page.setVisible(false);
                page = entry.container;
                book.add(page, 1, 0);
                entriesT.add(this.getJournalSize()-1, entry);
                if(curIdx == -1){
                    curIdx += 1;
                }
                else{
                    curIdx = this.getJournalSize()-1;
                }
                pageNum.setText(Integer.toString(curIdx+1));
                //container.add(page, 0, 2);
            }
        });
        return cont;
    }


}
