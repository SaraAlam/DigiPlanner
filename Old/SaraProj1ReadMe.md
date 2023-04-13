# MiscTrackerApp
### Author: Sara Alam,  Course: COSC480E UI,  Instructor: Prof. E. Fourquet

MiscTrackerApp is a simple GUI program for tracking daily habits and behaviors like drinking water, hours of sleep, hours of studying etc. Levels of success for each tracker correspond to colors that can be used to fill in days of a calendar. This format is inspired by bullet journal trackers, which are usually drawn as grids that can be colored in based on a key, like the calendar days of this app.

## Description

MiscTrackerApp can be used to track the default habits/behaviors/states it comes with: Water, Sleep, Study, Stress and Mood. It can also be used to create custom trackers, where each color is defined by the user. Each tracker comes with the full year's calendar for the current year. The current year's tracking information can be saved and reloaded when the program is rerun. In total, this program'code is divided into six .java files.


MiscTrackerApp was built using javafx, with a backend API written in java.
The main App code is in MiscTrackerApp.java. It creates and stores all the javafx components that hold the listviews of tracker names, the titles corresponding to the listviews and the buttons for adding trackers and saving and quitting the program. These must be public because the classes mentioned below need to access them through various handlers.

The following list describes the five additional classes used by MiscTrackerApp.java:


1. The Tracker class creates and stores a single month's calendar for a particular tracker. All javafx components for this specific tracker are stored in this Tracker object, including the instructions, the colors and the "Exit Tracker" button. 

2. The TrackerList class contains an ArrayList of Tracker objects for all 12 months of the year for a particular tracker. The only javafx components created and stored in the TrackerList object are the navigation buttons that allow users to switch between months. The MiscTrackerApp class creates TrackerList objects for each type of tracker. 


3. The summary TableView is created by the Summary class, which uses the Tsum class to create Tsum objects for each type of tracker. 

4. The Tsum object for each tracker gets the tracking information of the current weeks's days from the corresponding TrackerList objects.


5. MiscTrackerFileHandler.java is called for initializing the state of the program usign saved information and also saving the state before quitting. When initializing the state, it uses the temp variabled defined in MiscTrackerApp just above the start function. These must also be public so that MiscTrackerHandler.java can modify them as needed. They are static because the functions modifying them in MiscTrackerFileHandler.java are static.

Note: The code was written with efficiency in mind. The only aspect which could be optimized is the remaking of the whole calendar of a month for a tracker if single day is colored in. This had to be done to make the change persist. The author could not deduce why the change would not persist otherwise (any new insight in this regard would be heavily appreciated). However, this did prompt the use of a HashMap to store the "level" for each day of each month, which later helps store the state of the program on quitting.

## Dependencies

The following is a complete list of java and javafx packages, used by some or all of the classes mentioned above:

#### Java Packages
1. java.util.ArrayList
2. java.io
3. java.nio.file
4. java.util.HashMap
5. java.util.Calendar
6. java.util.Scanner

#### Javafx Packages
4. javafx.application.Application
5. javafx.application.Platform
6. javafx.geometry.Insets
7. javafx.geometry.Pos
8. javafx.scene.Scene
9. javafx.scene.control.Button
10. javafx.scene.control.Label
11. javafx.scene.control.TextArea
12. javafx.scene.control.TextField
13. javafx.scene.layout.Background
14. javafx.scene.layout.BackgroundFill
15. javafx.scene.layout.Border
16. javafx.scene.layout.BorderStroke
17. javafx.scene.layout.BorderStrokeStyle
18. javafx.scene.layout.BorderWidths
19. javafx.scene.layout.CornerRadii
20. javafx.scene.layout.HBox
21. javafx.scene.layout.VBox
22. javafx.scene.control.ComboBox
23. javafx.scene.paint.Color
24. javafx.scene.text.Font
25. javafx.stage.Stage
26. javafx.scene.control.TextInputDialog
27. javafx.scene.layout.GridPane
28. javafx.scene.control.ListView
29. javafx.collections.FXCollections
30. javafx.collections.ObservableList
31. javafx.scene.layout.Pane
32. javafx.geometry.Pos
33. javafx.scene.control.TableColumn
34. javafx.scene.control.TableView
35. javafx.beans.property.SimpleStringProperty
36. javafx.scene.control.cell.PropertyValueFactory
37. javafx.scene.layout.FlowPane
38. javafx.scene.image.Image
39. javafx.scene.image.ImageView
40. import javafx.scene.layout.BorderPane
41. javafx.beans.property.SimpleStringProperty
42. javafx.scene.control.cell.PropertyValueFactory


## How to run

Step 1: Navigate to the directory where all the .java files mentioned in the Description section are located.

Step 2: Compile using the following command, replacing javafx_packages_dir with the path to the directory of the javafx .jar files:

```bash
javac --module-path "javafx_packages_dir" --add-modules javafx.controls *.java
```

Step 3: Run using the following command, again replacing javafx_packages_dir with the appropriate path:

```bash
java --module-path "javafx_packages_dir" --add-modules javafx.controls MiscTrackerApp
```

## Usage

#### 1. Tracking

Click on the name of the specific tracker, pick a day to modify and click on the color of the level that describes that day the best.

Press the exit button to see the changes reflected in the summary table.


#### 2. Viewing the summary

This is the default view when the application is started and when all trackers are exited.

#### 3. Adding Trackers

Enter the name of the new tracker in the provided text field that says "Enter new tracker name". Then click on the "Add Tracker" button. This will ask the user to input descriptions for the different levels associated with the different colors for this specific tracker. Enter this information as desired and click on the "Done" button. This will display the new tracker for the current month and the navigation buttons to see the other months. Enter tracking information as desired and see the changes reflected on the summary table after exiting the tracker.

## Acknowledgements

In addition to the main writer of the code Sara Alam, the following contributors provided significant support for the project:

1. Professor Elodie Fourquet who taught important concepts regarding GUI development
2. TA Gabby Laines who helped further reinforce core concepts
3. All the fellow students in COSC 480E whose active participation during lessons helped clarify ideas.

The following is a complete (to the best of the author's knowledge) list of the online resources used to build this app:

1. All the doc.oracle.com pages for the java and javafx classes mentioned in the Dependencies section above
2. https://www.baeldung.com/java-file-directory-exists
3. https://edencoding.com/tableview-customization-cellfactory/
4. https://stackoverflow.com/questions/39782952/javafx-set-cell-background-color-of-tablecolumn
5. https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
6. https://www.tutorialspoint.com/java/java_files_io.htm

## License

[MIT](https://choosealicense.com/licenses/mit/)