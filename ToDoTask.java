import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ToDoTask{
    //private final StringProperty taskDetails = new SimpleStringProperty();
    private final SimpleBooleanProperty done = new SimpleBooleanProperty();
    private String taskDetails;
    
    public ToDoTask(String details){
        taskDetails = details;
    }
    
    // get tasks details
    public String getTaskDetails(){
        return taskDetails;
    }
    
    // done binding
    public SimpleBooleanProperty doneProperty(){
        DigiPlanner.printDate();
        return this.done;
    }

    public java.lang.Boolean getDone() {
        return this.doneProperty().get();
    }

    public void setDone(final java.lang.Boolean done) {
        this.doneProperty().set(done);
    }


}