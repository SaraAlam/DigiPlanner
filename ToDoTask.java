import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ToDoTask{
    //private final StringProperty taskDetails = new SimpleStringProperty();
    private final SimpleBooleanProperty done = new SimpleBooleanProperty();
    private String taskDetails;
    public boolean fakeDone = false;
    
    public ToDoTask(String details){
        taskDetails = details;
    }
    
    // get tasks details
    public String getTaskDetails(){
        return taskDetails;
    }
    
    // done binding
    public SimpleBooleanProperty doneProperty(){
        DigiPlanner.updateToDos();
        return this.done;
    }

    public java.lang.Boolean getDone() {
        return this.doneProperty().get();
    }

    public boolean getPrimDone(){
        if (getDone()){
            return true;
        }
        return false;
    }

    public static void setDone(final java.lang.Boolean done) {
        this.doneProperty().set(done);
        fakeDone = getDone();
    }


}