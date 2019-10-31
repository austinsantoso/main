package seedu.moneygowhere.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * Tab containing the spending graph.
 */
public class GraphPanel extends UiPart<Region> {
    private static final String FXML = "PlaceholderPanel.fxml";

    @FXML
    private StackPane panePlaceholder;

    public GraphPanel(LinkedHashMap<String, Double> graphData, String commandResult) {
        super(FXML);
        loadData(graphData, commandResult);
    }

    /**
     * Constructs the spending graph with the data.
     */
    private void loadData(LinkedHashMap<String, Double> graphData, String commandResult) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount spent ($)");
        LineChart<String, Number> spendingChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Spending");
        for (Map.Entry<String, Double> i : graphData.entrySet()) {
            XYChart.Data<String, Number> dataToAdd = new XYChart.Data<>(i.getKey().toString(),
                    Math.round(i.getValue()));
            series.getData().add(dataToAdd);
        }
        spendingChart.getData().add(series);

        spendingChart.setTitle(commandResult);
        spendingChart.setLegendSide(Side.RIGHT);

        for (XYChart.Series<String, Number> s : spendingChart.getData()) {
            for (XYChart.Data<String, Number> d : s.getData()) {
                Tooltip.install(d.getNode(), new Tooltip(
                        "Date: \t" + d.getXValue() + "\n"
                                + "Spending: \t$" + d.getYValue() + ".00"));

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }
        panePlaceholder.getChildren().add(spendingChart);
    }

}
