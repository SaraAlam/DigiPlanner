import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;

public class ToDoList {

    public GridPane toDoGridPane;
    public TableView<ToDoTask> toDoTable;

    public ToDoList(){
        toDoGridPane = new GridPane();
        toDoGridPane.setPadding(new Insets(10));

        toDoTable = new TableView<ToDoTask>();

        ObservableList<ToDoTask> allTasks = FXCollections.observableArrayList();
        //replace with file init later
        ToDoTask example1 = new ToDoTask("Finish To Do List");
        allTasks.add(example1);

        TableColumn<ToDoTask,String> completeCol = new TableColumn<ToDoTask,String>("Complete");
        completeCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("done"));
        completeCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        TableColumn<ToDoTask,String> detailsCol = new TableColumn<ToDoTask,String>("To Do");
        detailsCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("taskDetails"));
        detailsCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        TableColumn<ToDoTask,String> clearCol = new TableColumn<ToDoTask,String>("Clear");
        clearCol.setCellValueFactory(new PropertyValueFactory<ToDoTask, String>("clear"));
        clearCol.prefWidthProperty().bind(toDoTable.widthProperty().divide(3));

        toDoTable.getColumns().addAll(completeCol, detailsCol, clearCol);
        toDoTable.setItems(allTasks);

        toDoGridPane.add(toDoTable, 0, 0);
    }
}