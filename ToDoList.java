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

    public ToDoList(){
        listTasks = FXCollections.observableArrayList();
    }

    // public static void reorder(ObservableList<ToDoTask> toOrganize){
    //     int size = toOrganize.size();
    //     for (int i=0; i<size; i++){
    //         if (toOrganize.get(i).fakeDone){
    //             ToDoTask temp = toOrganize.get(i);
    //             toOrganize.remove(i);
    //             toOrganize.add(temp);
    //         }
    //     }

    // }
}