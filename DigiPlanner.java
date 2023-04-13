import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;

public class DigiPlanner extends Application{
    
    public void start(Stage stage){

        Label default_trackers_title = new Label(" Default Trackers: ");
        default_trackers_title.setFont(new Font("Times New Roman", 20)); // create title
        default_trackers_title.setAlignment(Pos.CENTER);

        Label custom_trackers_title = new Label(" Custom Trackers: ");
        custom_trackers_title.setFont(new Font("Times New Roman", 20)); // create title
        custom_trackers_title.setAlignment(Pos.CENTER);


        BorderPane save_and_quit_button_pane = create_save_and_quit_button();
        

    
       

        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
      
        // call private methods for button event handlers ---> DONE IN HELPER METHODS BELOW
        // you will need one for each button added: call and complete all the ones provided
        stage.setResizable(false); // TO AVOID EXTRA CODE FOR RESIZING GRACEFULLY
        stage.setScene(scene);
        stage.setTitle("MiscTrackers");
        stage.show(); 
        
    }

    public GridPane create_root(){
        GridPane 
    }
}
