import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class Chart {

    public Chart(){
    }


    public void drawForAll(double[] x, double[] y, double[] eulerX, double[] eulerY,
                           double[] rungeX, double[] rungeY, double[] milneX, double[] milneY){

        XYSeries series1 = new XYSeries("Точное значение");
        int n = x.length;
        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        XYSeries series2 = new XYSeries("Метод Эйлера");
        n = eulerX.length;
        for(int i = 0; i < n; i++){
            series2.add(eulerX[i], eulerY[i]);
        }
        XYSeries series3 = new XYSeries("Метод Рунге-Кутта");
        n = rungeX.length;
        for(int i = 0; i < n; i++){
            series3.add(rungeX[i], rungeY[i]);
        }
        XYSeries series4 = new XYSeries("Метод Милна");
        n = milneX.length;
        for(int i = 0; i < n; i++){
            series4.add(milneX[i], milneY[i]);
        }

        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        xyDataset1.addSeries(series1);
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
        xyDataset2.addSeries(series2);
        xyDataset2.addSeries(series3);
        xyDataset2.addSeries(series4);
        show(xyDataset1, xyDataset2);

    }

    public void drawGraphics(double[] x, double[] y, double[] polX, double[] polValue, String name){
        int n = x.length;
        XYSeries series1 = new XYSeries("Точное значение");
        XYSeries series2 = new XYSeries(name);
        for(int i = 0; i < n; i++){
            series1.add(x[i], y[i]);
        }
        n = polX.length;
        for(int i = 0; i < n; i++){
            series2.add(polX[i], polValue[i]);
        }
        XYSeriesCollection xyDataset1 = new XYSeriesCollection();
        xyDataset1.addSeries(series1);
        XYSeriesCollection xyDataset2 = new XYSeriesCollection();
        xyDataset2.addSeries(series2);

        show(xyDataset1, xyDataset2);
    }

    private void show(XYDataset line1, XYDataset line2){
//        XYItemRenderer rendererLine = new StandardXYItemRenderer();
//        XYItemRenderer rendererLine2 = new StandardXYItemRenderer();
        XYItemRenderer rendererLine = new XYShapeRenderer();
        XYItemRenderer rendererLine2 = new XYLineAndShapeRenderer();
        rendererLine2.setSeriesPaint(0, Color.BLUE);
        rendererLine2.setSeriesPaint(1, Color.GREEN);
        rendererLine2.setSeriesPaint(2, Color.ORANGE);
        NumberAxis XAxis = new NumberAxis("x");
        NumberAxis YAxis = new NumberAxis("y");

        XYPlot plot = new XYPlot(line1, XAxis, YAxis, rendererLine);
        plot.setRenderer(1, rendererLine2);
        plot.setDataset(1, line2);


        JFreeChart chart = new JFreeChart(plot);

        JFrame frame = new JFrame("График");
        frame.getContentPane().add(new ChartPanel(chart));

        frame.setSize(800, 800);
        frame.show();
    }
}
