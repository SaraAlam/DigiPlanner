import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import java.util.ArrayList;

public class ToDoList {

    ObservableList<ToDoTask> listTasks;
    ArrayList<ToDoTask> toWrite;

    public ToDoList(){
        listTasks = FXCollections.observableArrayList();
    }

    public void reorder(){
        for (int i=0; i<this.listTasks.size(); i++){
            
        }
    }
}