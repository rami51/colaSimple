import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Franco on 28/07/2016.
 */
    import java.awt.BasicStroke;
    import java.awt.Color;
    import java.awt.Dimension;
    import java.io.File;
    import java.io.IOException;
    import org.jfree.chart.ChartFactory;
    import org.jfree.chart.ChartPanel;
    import org.jfree.chart.ChartUtilities;
    import org.jfree.chart.JFreeChart;
    import org.jfree.chart.plot.PlotOrientation;
    import org.jfree.chart.plot.XYPlot;
    import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
    import org.jfree.data.xy.XYDataset;
    import org.jfree.data.xy.XYSeries;
    import org.jfree.data.xy.XYSeriesCollection;
    import org.jfree.ui.ApplicationFrame;
    import org.jfree.ui.RefineryUtilities;

    public class ChartLinea extends ApplicationFrame {
        JFreeChart grafica;

        private XYDataset createDataset(double[] datosX, double[] datosY) {
            XYSeries serie = new XYSeries("");

            for(int data = 0; data <= datosX.length - 1; ++data) {
                serie.add(datosX[data], datosY[data]);
            }

            XYSeriesCollection var5 = new XYSeriesCollection();
            var5.addSeries(serie);
            return var5;
        }

        public ChartLinea(String titulo, String x, String y, double[] datosX, double[] datosY, String nombreArchivo) {
            super("Freecharteando");
            JFreeChart xylineChart = ChartFactory.createXYLineChart(titulo, x, y, this.createDataset(datosX, datosY), PlotOrientation.VERTICAL, true, true, false);
            ChartPanel chartPanel = new ChartPanel(xylineChart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            XYPlot plot = xylineChart.getXYPlot();
            XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
            renderer.setSeriesPaint(0, Color.RED);
            renderer.setSeriesPaint(1, Color.GREEN);
            renderer.setSeriesPaint(2, Color.YELLOW);
            renderer.setSeriesStroke(0, new BasicStroke(1.0F));
            renderer.setSeriesShapesFilled(0,false);
            renderer.setSeriesStroke(1, new BasicStroke(1.0F));
            renderer.setSeriesStroke(2, new BasicStroke(1.0F));
            plot.setRenderer(renderer);
            this.setContentPane(chartPanel);

            try {
                short ex = 800;
                short height = 600;
                File XYChart = new File(nombreArchivo + ".jpeg");
                ChartUtilities.saveChartAsJPEG(XYChart, xylineChart, ex, height);
            } catch (IOException var14) {
                var14.printStackTrace();
            }

        }

        public static void main(String[] args) {
            double[] datosX = new double[]{1.0D, 2.0D, 3.0D, 4.0D, 5.0D};
            double[] datosY = new double[]{1.0D, 2.0D, 3.0D, 4.0D, 5.0D};
            ChartLinea chart = new ChartLinea("Titulo", "X", "Y", datosX, datosY, "Chart");
            chart.pack();
            RefineryUtilities.centerFrameOnScreen(chart);
            chart.setVisible(true);
        }
    }


