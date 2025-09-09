package org.analise.projeto.interface_grafica;

import java.util.Map;

import org.analise.projeto.desempenho.Metricas;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;

/**
 * Classe responsável por exibir e gerar tabelas de resultados
 */
public class VisualizadorTabelaResultados {
    
    /**
     * Exibe os resultados no console
     */
    public void displayResultsConsole(Map<String, Metricas> averageMetrics) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           RESULTADOS DOS TESTES DE DESEMPENHO");
        System.out.println("=".repeat(60));
        
        System.out.println("\nTEMPOS MÉDIOS DE EXECUÇÃO:");
        System.out.println("-".repeat(40));
        for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
            String algorithmName = entry.getKey();
            double timeMs = entry.getValue().getExecutionTimeMillis();
            System.out.printf("%-15s: %8.2f ms\n", algorithmName, timeMs);
        }
        
        System.out.println("\nUSO MÉDIO DE MEMÓRIA:");
        System.out.println("-".repeat(40));
        for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
            String algorithmName = entry.getKey();
            long memoryBytes = entry.getValue().getMemoryUsedBytes();
            System.out.printf("%-15s: %8d bytes\n", algorithmName, memoryBytes);
        }
        
        System.out.println("=".repeat(60));
    }
    
    /**
     * Gera relatório PDF com os resultados
     */
    public void generatePDFReport(Map<String, Metricas> averageMetrics, String fileName) {
        try {
            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título principal
            document.add(new Paragraph("RELATÓRIO DE DESEMPENHO DOS ALGORITMOS DE ORDENAÇÃO")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(16)
                    .setBold());
            
            document.add(new Paragraph(" ")); // Espaço
            
            // Tabela de Tempos de Execução
            document.add(new Paragraph("Tempos Médios de Execução")
                    .setFontSize(14)
                    .setBold());
            
            Table timeTable = new Table(2);
            timeTable.addCell(new Cell().add(new Paragraph("Algoritmo").setBold()));
            timeTable.addCell(new Cell().add(new Paragraph("Tempo (ms)").setBold()));
            
            for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
                timeTable.addCell(new Cell().add(new Paragraph(entry.getKey())));
                timeTable.addCell(new Cell().add(new Paragraph(
                    String.format("%.2f", entry.getValue().getExecutionTimeMillis()))));
            }
            document.add(timeTable);
            
            document.add(new Paragraph(" ")); // Espaço
            
            // Tabela de Uso de Memória
            document.add(new Paragraph("Uso Médio de Memória")
                    .setFontSize(14)
                    .setBold());
            
            Table memoryTable = new Table(2);
            memoryTable.addCell(new Cell().add(new Paragraph("Algoritmo").setBold()));
            memoryTable.addCell(new Cell().add(new Paragraph("Memória (bytes)").setBold()));
            
            for (Map.Entry<String, Metricas> entry : averageMetrics.entrySet()) {
                memoryTable.addCell(new Cell().add(new Paragraph(entry.getKey())));
                memoryTable.addCell(new Cell().add(new Paragraph(
                    String.valueOf(entry.getValue().getMemoryUsedBytes()))));
            }
            document.add(memoryTable);
            
            document.close();
            System.out.println("Relatório PDF gerado com sucesso: " + fileName);
            
        } catch (Exception e) {
            System.err.println("Erro ao gerar relatório PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

