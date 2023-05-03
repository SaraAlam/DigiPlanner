import java.util.HashMap;
import javafx.scene.layout.GridPane;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;


public class MonthlyHome {
    public SpreadBundle parentBundle;
    public String month;
    public int numDays;
    public GridPane disp = new GridPane();
    
    public MonthlyHome(String m, int nDays, SpreadBundle pBundle) throws Exception{
        parentBundle = pBundle;
        month = m;
        numDays = nDays;
        create_task_completion_plot();
        create_title_img();
    }

    public void create_task_completion_plot(){
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Days");
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle(month + " task completion rates");
        //defining a series
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("Rates");
        //populating the series with data
        HashMap<Integer, Double> rates_dict = parentBundle.aToDoMonth.dailyTaskCompletionRate;
        for (int i = 0; i < numDays; i ++){
            rates_dict = parentBundle.aToDoMonth.dailyTaskCompletionRate;
            if (rates_dict.containsKey(i)){
                Number rate = rates_dict.get(i);
                series.getData().add(new XYChart.Data<Number, Number>(i,rate));
            }
        }
        
        lineChart.getData().add(series);
        GridPane.setConstraints(lineChart, 1,1);
        disp.getChildren().add(lineChart);
    }

    public void create_title_img() throws Exception{
        FileInputStream input = new FileInputStream("monthTitles/"+ month + "Title.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        GridPane.setConstraints(imageView, 1,2);
        disp.getChildren().add(imageView);
    }
}
