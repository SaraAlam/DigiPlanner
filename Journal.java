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
import javafx.scene.text.Font;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.FileNotFoundException;
import javafx.scene.control.Tooltip;

public class Journal{
    ArrayList<JournalEntry> entries;
    GridPane container = new GridPane();
    TextArea page = new TextArea();
    GridPane book = new GridPane();

    
    
    int curIdx = -1;
    Label pageNum = new Label();
    
    private final TableView<JournalEntry> entryS = new TableView<>();
    public final ObservableList<JournalEntry> entriesT = 
        FXCollections.observableArrayList();
    
    Button deleteEntry = new Button();
    Button editEntry = new Button();
    Boolean editing = false;
    HBox del = new HBox(editEntry, deleteEntry);
    
    Button arrRight = new Button(">");
    Button arrLeft = new Button("<");

    String musicFile = "page.mp3";

    //Media sound = new Media(new File(musicFile).toURI().toString());
    
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
                pageNum.setText("");
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
            }
        });

        entryS.setFixedCellSize(30);
        entryS.prefHeightProperty().bind(Bindings.size(entryS.getItems()).multiply(30).add(30));

        page.setEditable(false);
        page.getStyleClass().add("book-page");
        
        container.setVgap(10);
        container.setPadding(new Insets(15));

        TextArea addField = toAdd();

        arrRight.setVisible(false);
        arrLeft.setVisible(false);

        arrRight.getStyleClass().add("journal-btn");
        arrLeft.getStyleClass().add("journal-btn");

        arrRight.setOnAction(e -> {
            if(curIdx < this.getJournalSize()-1){
                // MediaPlayer mediaPlayer = new MediaPlayer(sound);
                // mediaPlayer.play();
                curIdx += 1;
                pageNum.setText(Integer.toString(curIdx+1) + "/" +Integer.toString(this.getJournalSize()));
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
                editing = false;
                if(curIdx == this.getJournalSize()-1){
                    arrRight.setVisible(false);
                }
                arrLeft.setVisible(true);

                
            }
        });

        arrLeft.setOnAction(e -> {
            if(curIdx > 0){
                // MediaPlayer mediaPlayer = new MediaPlayer(sound);
                // mediaPlayer.play();
                curIdx -= 1;
                pageNum.setText(Integer.toString(curIdx+1) + "/" +Integer.toString(this.getJournalSize()));
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
                editing = false;
                if(curIdx == 0){
                    arrLeft.setVisible(false);
                }
                arrRight.setVisible(true);
            }
        });

        HBox pageNumCont = new HBox(pageNum);
        HBox right = new HBox(arrLeft, arrRight);
        right.setSpacing(10);
        right.setPrefWidth(Integer.MAX_VALUE);
        pageNumCont.setAlignment(Pos.BOTTOM_RIGHT);
        pageNumCont.setPadding(new Insets(0, 10, 0 ,0));
        
        //right.setPrefHeight(Integer.MAX_VALUE);
        right.setAlignment(Pos.BOTTOM_CENTER);
        right.setPadding(new Insets(0, 15, 0, 0));

        Tooltip delete = new Tooltip("Delete this entry");
        Tooltip edit = new Tooltip("Edit this entry");

        deleteEntry.setPrefWidth(30);
        deleteEntry.setId("del-entry");
        deleteEntry.setTooltip(delete);

        editEntry.setPrefWidth(30);
        editEntry.setId("edit-entry");
        editEntry.setTooltip(edit);

        book.add(del, 1, 0);
        del.setVisible(false);

        deleteEntry.setOnAction(e -> {
            DigiPlanner.updateMsg("Entry deleted!");
            int pages = this.getJournalSize();
            if(pages == 1){
                page.setVisible(false);
                entries.remove(this.getJournalSize()-1);
                pageNum.setText("");
                arrRight.setVisible(false);
                arrLeft.setVisible(false);
                del.setVisible(false);
                curIdx = -1;
                return;
            }
            for(int i = curIdx; i < pages-1; i++){
                entries.set(i, entries.get(i+1));
            }
            if(curIdx == pages-1){
                curIdx -= 1;
            }
            entries.remove(this.getJournalSize()-1);
            page.setVisible(false);
            page = entries.get(curIdx).container;
            page.setVisible(true);
            pageNum.setText(Integer.toString(curIdx+1) + "/" +Integer.toString(pages-1));
            if(pages-1 == 1){
                arrRight.setVisible(false);
                arrLeft.setVisible(false);
            }
            editing = false;
        });



        editEntry.setOnAction(e -> {
            DigiPlanner.updateMsg("Edit the text below your book! Press Enter when you are done editing.");
            if(editing){
                page.setVisible(false);
                page = entries.get(curIdx).container;
                page.setVisible(true);
                addField.setText("");
                editing = false;
            }
            else if(!editing){
                JournalEntry[] cur = new JournalEntry[1];
                String curEnt = entries.get(curIdx).getContent();
                JournalEntry entry = new JournalEntry(curEnt);
                page.setVisible(false);
                page = entry.container;
                book.add(page, 1, 0);
                page.setVisible(true);
                del.toFront();
                page.setText("*******EDITING*******\n\n"+page.getText());
                addField.setText(entries.get(curIdx).getContent());
                editing = true;
            }
        });
        
        del.setAlignment(Pos.TOP_RIGHT);
        del.setPadding(new Insets(-10, 10, 0, 0));

        book.add(pageNumCont, 1,1);
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
        row1.setPercentHeight(85);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(8);
        
        book.getRowConstraints().addAll(row1, row2);


        container.add(book, 0, 0);
        container.add(addField, 0,1);
        //container.add(entryS, 0,2);
       
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

    public TextArea toAdd(){
        TextArea cont = new TextArea();
        cont.setWrapText(true);
        cont.setId("add-entry");
        cont.setPromptText("Press Enter after writing a new entry!");
        Font font = Font.loadFont("file:scriptina/SCRIPALT.ttf", 16);
        cont.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER && !cont.getText().isEmpty()){
                DigiPlanner.updateMsg("New entry created!");
                JournalEntry[] cur = new JournalEntry[1];
                String curEnt = cont.getText();
                curEnt = curEnt.replace("\n", "");
                JournalEntry entry = new JournalEntry(curEnt);
                if(editing){
                    entry = new JournalEntry(curEnt, entries.get(curIdx).getEntryTime());
                }
                cont.setText("");
                page.setVisible(false);
                page = entry.container;
                //page.setFont(font);
                book.add(page, 1, 0);
                del.setVisible(true);
                del.toFront();
                arrRight.setVisible(false);
                if(editing){
                    DigiPlanner.updateMsg("Entry updated!");
                    entries.set(curIdx, entry);
                    editing = false;
                }
                else{
                    cur[0] = entry;
                    this.addEntries(cur);
                    if(curIdx == -1){
                        curIdx += 1;
                        
                    }
                    else{
                        curIdx = this.getJournalSize()-1;
                        arrLeft.setVisible(true);
                    }
                    entriesT.add(entry);
                    pageNum.setText(Integer.toString(curIdx+1) + "/" +Integer.toString(this.getJournalSize()));
                    editing = false;
                }
                //container.add(page, 0, 2);
            }
        });
        return cont;
    }


}
