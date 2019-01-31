package parkeersimulator.view;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries.PieSeriesRenderStyle;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.Styler.ChartTheme;
import parkeersimulator.model.Simulator;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PieView extends AbstractView {

    private PieChart pieChart2;

    public PieView(Simulator simulator) {

        super(simulator);

        // Creating layout and chart
        setLayout(new BorderLayout());
        pieChart2 = new PieChartBuilder().width(500).height(400).theme(ChartTheme.GGPlot2).title("Cars Parked").build();
        XChartPanel<PieChart> chartPane = new XChartPanel<>(pieChart2);
        add(chartPane);
        getChart();
    }

    private void getChart() {

        // Colors
        Color[] sliceColors = new Color[]{Color.RED, Color.BLUE, Color.ORANGE};

        // Styling the chart
        pieChart2.getStyler().setLegendVisible(true);
        pieChart2.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        pieChart2.getStyler().setLegendBackgroundColor(new Color(255, 255, 255, 0));
        pieChart2.getStyler().setLegendBorderColor(new Color(255, 255, 255, 0));
        pieChart2.getStyler().setDefaultSeriesRenderStyle(PieSeriesRenderStyle.Donut);
        pieChart2.getStyler().setHasAnnotations(true);
        pieChart2.getStyler().setAnnotationType(PieStyler.AnnotationType.Value);
        pieChart2.getStyler().setAnnotationDistance(.82);
        pieChart2.getStyler().setSumVisible(true);
        pieChart2.getStyler().setSumFontSize(30);
        pieChart2.getStyler().setDecimalPattern("#0");
        pieChart2.getStyler().setPlotContentSize(.8);
        pieChart2.getStyler().setChartFontColor(Color.BLACK);
        pieChart2.getStyler().setSeriesColors(sliceColors);

        // Adding first data
        Map<String, Integer> pieData = getData();
        for (Entry<String, Integer> entry : pieData.entrySet()) {
            pieChart2.addSeries(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void updateView() {

        // Updating data and refreshing chart
        Map<String, Integer> pieData = getData();
        for (Entry<String, Integer> entry : pieData.entrySet()) {
            pieChart2.updatePieSeries(entry.getKey(), entry.getValue());
        }

        repaint();
    }

    private Map<String, Integer> getData() {

        // Adding data to HashMap
        Map<String, Integer> pieData = new HashMap<>();
        pieData.put("AdHocCar", simulator.getParkeerGarage().getTotalAdHocCarCarsParked());
        pieData.put("Parkingpass", simulator.getParkeerGarage().getTotalParkingPassCarsParked());
        pieData.put("Reserved", simulator.getParkeerGarage().getTotalReservationCarsParked());


        /*pieData.put("A Regular customer", simulator.getParkeerGarage().getTotalAdHocCarCarsParked());
        pieData.put("BParking subscription", simulator.getParkeerGarage().getTotalParkingPassCarsParked());
        pieData.put("CReservations", simulator.getParkeerGarage().getTotalReservationCarsParked());*/

        return pieData;
    }
}