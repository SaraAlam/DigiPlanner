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
        DigiPlanner.getCompletionRates();
        // DigiPlanner.
        if(this.getTheDoneVal()){
            DigiPlanner.updateMsg("Good job! Use the checkboxes to keep track of your completed tasks.");
        }
        return this.done;
    }

    public java.lang.Boolean getTheDoneVal(){
        return this.done.get();
    }

    public java.lang.Boolean getDone() {
        return this.doneProperty().get();
    }

    public void setDone(final java.lang.Boolean done) {
        this.doneProperty().set(done);
    }


}