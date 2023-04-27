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
 



public class ToDoMonth {

    public GridPane toDoMonthGridPane;
    public TableView<ToDoTask> toDoTable;
    public ArrayList<ToDoList> allToDoLists;
    public int currMonth = 0;

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
            aList.listTasks.add(new ToDoTask("Check second index"));
            aList.listTasks.add(new ToDoTask("Third thing I guess"));
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
        toDoTable.setItems(allToDoLists.get(currMonth).listTasks);
        toDoTable.setEditable(true);


        toDoMonthGridPane.add(toDoTable, 0, 0);
    }

    private void addButtonToTable() {
        TableColumn<ToDoTask, Void> clearCol = new TableColumn("Clear");
        // Button btn2 = new Button();
        // Image image = new Image("trashCan.png");
        // ImageView imv = new ImageView(image);

        // btn.setGraphic(imv);
        // btn.getStyleClass().add("trash");
        
     

        Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>> cellFactory = new Callback<TableColumn<ToDoTask, Void>, TableCell<ToDoTask, Void>>() {
            @Override
            public TableCell<ToDoTask, Void> call(final TableColumn<ToDoTask, Void> param) {
                final TableCell<ToDoTask, Void> cell = new TableCell<ToDoTask, Void>() {
                    Button btn = new Button();
                    {
                        btn.getStyleClass().add("trash");
                        btn.setOnAction((ActionEvent event) -> {
                            ToDoTask taskToDelete = getTableView().getItems().get(getIndex());
                            allToDoLists.get(currMonth).listTasks.remove(taskToDelete);
                            toDoTable.setItems(allToDoLists.get(currMonth).listTasks);
                        });
                    }
                    

                    //Creating a graphic (image)
                    // Image img = new Image("trashCan.png");
                    // ImageView view = new ImageView(img);
                    // view.setFitHeight(80);
                    // view.setPreserveRatio(true);
                    // Button button = new Button();
                    // button.setTranslateX(200);
                    // button.setTranslateY(25);
                    // button.setPrefSize(80, 80);
                    // button.setGraphic(view);
                    // Image image = new Image(getClass().getResourceAsStream("trashCan.jpg"));
                    // Image image = new Image("trashCan.jpg");
                    // ImageView imv = new ImageView(image);
                    // btn.setGraphic(imv);
                    // imv.setImage(image);
                    //btn.setGraphic(imv);

                    //btn.setGraphic(new ImageView(new Image(trashCan.png)));
                    
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