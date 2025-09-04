package org.yourcompany.yourproject.interface_grafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.yourcompany.yourproject.desempenho.Metricas;

/**
 * Classe responsável por criar e exibir gráficos de desempenho
 */
public class VisualizadorGraficoDesempenho {
    private static final Color[] CORES = {
        Color.RED, Color.BLUE, Color.GREEN, 
        Color.ORANGE, Color.MAGENTA, Color.CYAN
    };

    /**
     * Cria gráfico de tempo de execução
     */
    public JFreeChart createTimeChart(Map<String, Metricas> averageMetrics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
            String algorithmName = entry.getKey();
            double timeMs = entry.getValue().getExecutionTimeMillis();
            dataset.addValue(timeMs, "Tempo de Execução", algorithmName);
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Tempo Médio de Execução dos Algoritmos",
                "Algoritmos de Ordenação",
                "Tempo (milissegundos)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Personalizar o gráfico
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(0, true);
        plot.setRenderer(renderer);
        
        // Configurar eixos
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            org.jfree.chart.axis.CategoryLabelPositions.UP_45);
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        
        return chart;
    }
    
    /**
     * Cria gráfico de uso de memória
     */
    public JFreeChart createMemoryChart(Map<String, Metricas> averageMetrics) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
            String algorithmName = entry.getKey();
            long memoryBytes = entry.getValue().getMemoryUsedBytes();
            dataset.addValue(memoryBytes, "Uso de Memória", algorithmName);
        }
        
        JFreeChart chart = ChartFactory.createLineChart(
                "Uso Médio de Memória dos Algoritmos",
                "Algoritmos de Ordenação",
                "Memória (bytes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        
        // Personalizar o gráfico
        CategoryPlot plot = chart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesLinesVisible(0, true);
        plot.setRenderer(renderer);
        
        // Configurar eixos
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
            org.jfree.chart.axis.CategoryLabelPositions.UP_45);
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        
        return chart;
    }
    
    /**
     * Exibe os gráficos em uma janela
     */
    public void displayCharts(Map<String, Metricas> averageMetrics) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Análise de Desempenho dos Algoritmos de Ordenação");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(new GridLayout(2, 1));
            
            // Gráfico de tempo
            JFreeChart timeChart = createTimeChart(averageMetrics);
            ChartPanel timeChartPanel = new ChartPanel(timeChart);
            timeChartPanel.setPreferredSize(new Dimension(800, 400));
            
            // Gráfico de memória
            JFreeChart memoryChart = createMemoryChart(averageMetrics);
            ChartPanel memoryChartPanel = new ChartPanel(memoryChart);
            memoryChartPanel.setPreferredSize(new Dimension(800, 400));
            
            frame.add(timeChartPanel);
            frame.add(memoryChartPanel);
            
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            System.out.println("Gráficos de desempenho exibidos em janela interativa.");
        });
    }
}

