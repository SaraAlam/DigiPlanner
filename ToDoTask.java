import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ToDoTask{
    //private final StringProperty taskDetails = new SimpleStringProperty();
    private final SimpleBooleanProperty done;
    private String taskDetails;
    private final SimpleBooleanProperty clear;
    
    public ToDoTask(String details){
        done = new SimpleBooleanProperty();
        taskDetails = details;
        clear = new SimpleBooleanProperty();
    }
    
    // get tasks details
    public String getTaskDetails(){
        return taskDetails;
    }
    
    // done binding
    public SimpleBooleanProperty doneProperty(){
        return this.done;
    }

    public java.lang.Boolean getDone() {
        return this.doneProperty().get();
    }

    public void setDone(final java.lang.Boolean done) {
        this.doneProperty().set(done);
    }

    //clear binding
    public SimpleBooleanProperty clearProperty(){
        return this.clear;
    }

    public java.lang.Boolean getClear() {
        return this.clearProperty().get();
    }

    public void setClear(final java.lang.Boolean clear) {
        this.clearProperty().set(clear);
    }
}