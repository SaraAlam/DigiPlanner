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


public class ToDoMonth {

    public GridPane toDoMonthGridPane;
    public TableView<ToDoTask> toDoTable;
    public ArrayList<ToDoList> allToDoLists;

    public ToDoMonth(String name, int numDays){
        toDoMonthGridPane = new GridPane();
        toDoMonthGridPane.setPadding(new Insets(10));

        toDoTable = new TableView<ToDoTask>();
        allToDoLists = new ArrayList<ToDoList>();
        //ObservableList<ToDoTask> allTasks = FXCollections.observableArrayList();
        //replace with file init later
        for (int i=0; i<numDays; i++){
            ToDoList aList = new ToDoList();
            aList.listTasks.add(new ToDoTask("Finish To Do List " + i));
            allToDoLists.add(aList);
        }
        //ToDoTask example1 = new ToDoTask("Finish To Do List");
        //allTasks.add(example1);

        TableColumn<ToDoTask,Boolean> completeCol = new TableColumn<ToDoTask,Boolean>("Complete");
        completeCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, Boolean>("done"));
        completeCol.setCellFactory(e -> new CheckBoxTableCell<>());
        completeCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        TableColumn<ToDoTask,String> detailsCol = new TableColumn<ToDoTask,String>("To Do");
        detailsCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("taskDetails"));
        detailsCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        // TableColumn<ToDoTask,Boolean> clearCol = new TableColumn<ToDoTask,Boolean>("Clear");
        // clearCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, Boolean>("clear"));
        // clearCol.setCellFactory(e -> new CheckBoxTableCell<>());
        // clearCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        toDoTable.getColumns().addAll(completeCol, detailsCol);//, clearCol);
        addButtonToTable(); 
        toDoTable.setItems(allToDoLists.get(0).listTasks);
        toDoTable.setEditable(true);


        toDoMonthGridPane.add(toDoTable, 0, 0);
    }

    private void addButtonToTable() {
        TableColumn<ToDoTask, Void> clearCol = new TableColumn("Clear");

        Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>> cellFactory = new Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>>() {
            @Override
            public TableCell<ToDoTask, Void> call(final TableColumn<ToDoTask, Void> param) {
                final TableCell<ToDoTask, Void> cell = new TableCell<ToDoTask, Void>() {

                    private final Button btn = new Button("Trash");
                    /* 
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Data data = getTableView().getItems().get(getIndex());
                            System.out.println("selectedData: " + data);
                        });
                    }
                    */ 
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

        toDoTable.getColumns().add(clearCol);

    }
}