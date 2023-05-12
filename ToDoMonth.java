import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.HashMap;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;



public class ToDoMonth {

    public GridPane toDoMonthGridPane;
    public TableView<ToDoTask> toDoTable;
    public ArrayList<ToDoList> allToDoLists;
    public int currMonth = 0;
    public int numDays;
    public int currDay = 0;
    public SpreadBundle parentBundle;
    public double[] rates;

    public HashMap<Integer, Double> dailyTaskCompletionRate = new HashMap<Integer, Double>();

    public ToDoMonth(String name, int nDays, SpreadBundle pBundle){
        numDays = nDays;
        rates = new double[numDays];
        for (int i=0; i<numDays; i++){
            rates[i] = 0.0;
        }
        for (int j = 0; j < numDays; j++){
            dailyTaskCompletionRate.put(j,rates[j]);
        }
        parentBundle = pBundle;
        currDay = pBundle.currDay - 1; // -1 because this currDay is the index of the arraylist
        toDoMonthGridPane = new GridPane();
        // toDoMonthGridPane.setPrefWidth(400);
        toDoMonthGridPane.setPadding(new Insets(10));
        // toDoMonthGridPane.setMinWidth(150);
        // input new task
        TextField input = new TextField();
        input.setPrefWidth(Integer.MAX_VALUE);
        input.setPromptText("Press Enter after writing a new entry!");
        // input.setPadding(new Insets(10));
        toDoMonthGridPane.setMargin(input, new Insets(0,0,10,0));
        toDoMonthGridPane.add(input, 0, 0);


        toDoTable = new TableView<ToDoTask>();
        toDoTable.setId("to-do-table");
        toDoTable.setMinWidth(400);

        allToDoLists = new ArrayList<ToDoList>();
        /*

        initFromFile(allToDoLists, ); //initialize all days in a month into allToDoLists

         */

        // Create a list for each day
        for (int i=0; i<numDays; i++){
            ToDoList aList = new ToDoList();
            allToDoLists.add(aList);
        }

        // Create and add all columns
        TableColumn<ToDoTask,Boolean> completeCol = new TableColumn<ToDoTask,Boolean>("Complete");
        completeCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, Boolean>("done"));
        completeCol.setCellFactory(e -> new CheckBoxTableCell<>());
        // completeCol.setPrefWidth(80);
        completeCol.setResizable(false);

        TableColumn<ToDoTask,String> detailsCol = new TableColumn<ToDoTask,String>("To Do");
        detailsCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("taskDetails"));
        // detailsCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));
        detailsCol.setResizable(false);



        toDoTable.getColumns().addAll(completeCol, detailsCol);//, clearCol);
        completeCol.prefWidthProperty().bind(toDoTable.widthProperty().multiply(0.18));
        detailsCol.prefWidthProperty().bind(toDoTable.widthProperty().multiply(0.704));
        addButtonToTable();
        toDoTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        toDoTable.setItems(allToDoLists.get(currDay).listTasks);
        toDoTable.setEditable(true);

        toDoMonthGridPane.add(toDoTable, 0, 1);

        // Button saveAndQuitButton = new Button("Save and Quit");
        // saveAndQuitButton.setMinWidth(100);
        // toDoMonthGridPane.setMargin(saveAndQuitButton, new Insets(0, 10, 10, 0));
        // toDoMonthGridPane.add(saveAndQuitButton, 0, 2);
        // saveAndQuitButton.setOnAction(evt -> saveAndQuitHandler(allToDoLists, numDays));


        // Handlers
        input.setOnAction(evt -> {
            enterKeypressed(KeyCode.ENTER, allToDoLists.get(currDay), input);
            DigiPlanner.updateMsg("New task created!");
        });

        

        //toDoMonthGridPane.add(toDoTable, 0, 0);

        // hardCodeCompletionRates();
    }

    private void addButtonToTable() {
        TableColumn<ToDoTask, Void> clearCol = new TableColumn("Clear");

        Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>> cellFactory = new Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>>() {
            @Override
            public TableCell<ToDoTask, Void> call(final TableColumn<ToDoTask, Void> param) {
                final TableCell<ToDoTask, Void> cell = new TableCell<ToDoTask, Void>() {                    
                    Button btn = new Button("     ");
                    // btn.setWidth(50);
                    {
                        btn.getStyleClass().add("trash");
                        Tooltip trash = new Tooltip("Delete this entry");
                        btn.setTooltip(trash);
                        btn.setOnAction((ActionEvent event) -> {
                            DigiPlanner.updateMsg("Task deleted!");
                            ToDoTask taskToDelete = getTableView().getItems().get(getIndex());
                            allToDoLists.get(currDay).listTasks.remove(taskToDelete);
                            allToDoLists.get(currDay).toWrite.remove(taskToDelete);
                            toDoTable.setItems(allToDoLists.get(currDay).listTasks);
                            toDoTable.getSelectionModel().clearSelection();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        clearCol.setCellFactory(cellFactory);
        // clearCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));
        // clearCol.setPrefWidth(50 );
        clearCol.prefWidthProperty().bind(toDoTable.widthProperty().multiply(0.106));
        clearCol.setResizable(false);
        toDoTable.getColumns().add(clearCol);

    }

    public void enterKeypressed(KeyCode keyCode, ToDoList thisList, TextField input){
        if (keyCode == KeyCode.ENTER ){
            String add = input.getText();
            ToDoTask newToDoTask = new ToDoTask(add);
            thisList.listTasks.add(newToDoTask);
            thisList.toWrite.add(newToDoTask);
            input.setText("");
            toDoTable.getSelectionModel().clearSelection();
        }
    }

    // public void updateTable(ObservableList<ToDoTask> toOrganize){
    //     allToDoLists.get(currDay).listTasks = toOrganize;
    // }
    // private void saveAndQuitHandler(ArrayList<ToDoList> allToDoLists, int numDays) {
    //     ToDoFileHandler.saveToDos(allToDoLists, "ToDoMonth.txt", numDays);
    //     Platform.exit();
    // }

}