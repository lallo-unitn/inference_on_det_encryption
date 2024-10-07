package nl.ut.eemcs.cloudsec;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartBuilder {
    private CategoryChart chart;

    public static CategoryChart buildSecChart(HashMap<String, String> lookupLastNames, List<Map.Entry<String, Integer>> list, boolean plaintext) {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(1680).height(2000).title("Secure Lastname Frequency Distribution").xAxisTitle("Lastname").yAxisTitle("Frequency").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setXAxisLabelRotation(90);
        chart.getStyler().setPlotGridLinesVisible(false);

        // invert lookupLastNames
        HashMap<String, String> lookupLastNamesInverted = new HashMap<>();
        for (Map.Entry<String, String> entry : lookupLastNames.entrySet()) {
            lookupLastNamesInverted.put(entry.getValue(), entry.getKey());
        }

        //System.out.println("Wang: " + lookupLastNamesInverted.get("Wang"));
        //System.out.println("4f09d41d4e6975a14551b684e7ee7739c059bddfd813a8a05a74f88690791445: " + lookupLastNames.get("4f09d41d4e6975a14551b684e7ee7739c059bddfd813a8a05a74f88690791445"));

        // Extract sorted keys and values into separate lists
        List<String> sortedKeys = new ArrayList<>();
        List<Integer> sortedValues = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : list) {
            if (plaintext) {
                sortedKeys.add(entry.getKey());
            } else {
                sortedKeys.add(lookupLastNamesInverted.get(entry.getKey()));
            }
            sortedValues.add(entry.getValue());
        }

        // Add data to the chart from list and order it by value
        chart.addSeries("Frequency", sortedKeys, sortedValues);
        return chart;
    }

    public static CategoryChart buildChart(HashMap<String, String> lookupLastNames, HashMap<String, Integer> lastnameFrequencies, boolean plaintext) {
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder().width(1680).height(2000).title("Lastname Frequency Distribution").xAxisTitle("Lastname").yAxisTitle("Frequency").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setXAxisLabelRotation(90);
        chart.getStyler().setPlotGridLinesVisible(false);
        ArrayList<String> keySet = new ArrayList<>(lastnameFrequencies.keySet());
        ArrayList<String> tmp = new ArrayList<>();
        //iterate keyset
        for (String key : keySet) {
            if (plaintext) {
                tmp.add(lookupLastNames.get(key));
            } else {
                tmp.add(key);
            }
        }

        // Add data
        chart.addSeries(
                "Frequency",
                tmp,
                new ArrayList<>(lastnameFrequencies.values())
        );
        return chart;
    }

    public static void printChartToFile(CategoryChart chart, String filePath) {
        try {
            BitmapEncoder.saveBitmap(chart, filePath, BitmapEncoder.BitmapFormat.PNG);
            //System.out.println("Chart saved as PDF: Lastname_Frequency_Distribution.pdf");
        } catch (IOException e) {
            System.out.println("Error saving chart as PDF: " + e.getMessage());
        }
    }
}
