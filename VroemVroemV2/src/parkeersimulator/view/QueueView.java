package parkeersimulator.view;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.ChartTheme;
import parkeersimulator.model.Simulator;
import java.awt.*;
import java.util.Arrays;


public class QueueView extends AbstractView {

    private CategoryChart queueView;

    public QueueView(Simulator simulator) {

        super(simulator);

        // Creating layout and chart
        setLayout(new BorderLayout());
        queueView = new CategoryChartBuilder().width(512).height(400).theme(ChartTheme.GGPlot2).title("Queue").build();
        XChartPanel<CategoryChart> chartPane = new XChartPanel<>(queueView);
        add(chartPane);
        getChart();
    }

    private void getChart() {

        // Styling the chart
        queueView.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        queueView.getStyler().setLegendBackgroundColor(new Color(255, 255, 255, 0));
        queueView.getStyler().setLegendBorderColor(new Color(255, 255, 255, 0));


        queueView.getStyler().setLegendVisible(false);
        //queueView.getStyler().setDefaultSeriesRenderStyle();


        // Adding first data
        queueView.addSeries("Cars in line", Arrays.asList("AdHoc", "Pass", "Payment", "Exit"), Arrays.asList(0, 0, 0, 0));
    }

    @Override
    public void updateView() {

        // Updating data and refreshing chart
        queueView.updateCategorySeries("Cars in line", Arrays.asList("AdHoc", "Pass", "Payment", "Exit"), Arrays.asList(
                simulator.entranceCarQueue.carsInQueue(),
                simulator.entrancePassQueue.carsInQueue(),
                simulator.paymentCarQueue.carsInQueue(),
                simulator.exitCarQueue.carsInQueue()),
                null);
        repaint();
    }
}