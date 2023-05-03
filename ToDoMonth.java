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


public class ToDoMonth {

    public GridPane toDoMonthGridPane;
    public TableView<ToDoTask> toDoTable;
    public ArrayList<ToDoList> allToDoLists;
    public int currMonth = 0;
    public int numDays;
    public int currDay = 0;

    public HashMap<Integer, Double> dailyTaskCompletionRate = new HashMap<Integer, Double>();

    public ToDoMonth(String name, int nDays){
        numDays = nDays;
        toDoMonthGridPane = new GridPane();
        // toDoMonthGridPane.setPrefWidth(400);
        toDoMonthGridPane.setPadding(new Insets(10));
        // toDoMonthGridPane.setMinWidth(150);
        // input new task
        TextField input = new TextField();
        // input.setPadding(new Insets(10));
        toDoMonthGridPane.setMargin(input, new Insets(0,0,10,0));
        toDoMonthGridPane.add(input, 0, 0);


        toDoTable = new TableView<ToDoTask>();
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
        completeCol.setPrefWidth(80);
        completeCol.setResizable(false);

        TableColumn<ToDoTask,String> detailsCol = new TableColumn<ToDoTask,String>("To Do");
        detailsCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("taskDetails"));
        detailsCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        toDoTable.getColumns().addAll(completeCol, detailsCol);//, clearCol);
        addButtonToTable();
        toDoTable.setItems(allToDoLists.get(currDay).listTasks);
        toDoTable.setEditable(true);

        toDoMonthGridPane.add(toDoTable, 0, 1);

        Button saveAndQuitButton = new Button("Save and Quit");
        saveAndQuitButton.setMinWidth(100);
        toDoMonthGridPane.setMargin(saveAndQuitButton, new Insets(0, 10, 10, 0));
        toDoMonthGridPane.add(saveAndQuitButton, 0, 2);
        saveAndQuitButton.setOnAction(evt -> saveAndQuitHandler(allToDoLists, numDays));


        // Handlers
        input.setOnAction(evt -> enterKeypressed(KeyCode.ENTER, allToDoLists.get(currDay), input));

        

        toDoMonthGridPane.add(toDoTable, 0, 0);

        hardCodeCompletionRates();
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
                        btn.setOnAction((ActionEvent event) -> {
                            ToDoTask taskToDelete = getTableView().getItems().get(getIndex());
                            allToDoLists.get(currDay).listTasks.remove(taskToDelete);
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
        clearCol.setPrefWidth(50 );
        clearCol.setResizable(false);
        toDoTable.getColumns().add(clearCol);

    }

    public void hardCodeCompletionRates(){
        double[] random_rates = {0.75, 0.7, 0.8, 0.6, 0.5, 1.0, 0.25, 0.3, 0.2, 0.1, 0.15, 0.3};
        int j = 0;
        for (int i = 0; i < numDays; i++){
            if (i%3==0){
                dailyTaskCompletionRate.put(i,random_rates[j]);
                j++;
            }

        }
    }

    public void enterKeypressed(KeyCode keyCode, ToDoList thisList, TextField input){
        if (keyCode == KeyCode.ENTER ){
            String add = input.getText();
            thisList.listTasks.add(new ToDoTask(add));
            input.setText("");
            toDoTable.getSelectionModel().clearSelection();
        }
    }

    private void saveAndQuitHandler(ArrayList<ToDoList> allToDoLists, int numDays) {
        ToDoFileHandler.saveToDos(allToDoLists, "ToDoMonth.txt", numDays);
        Platform.exit();
    }

}