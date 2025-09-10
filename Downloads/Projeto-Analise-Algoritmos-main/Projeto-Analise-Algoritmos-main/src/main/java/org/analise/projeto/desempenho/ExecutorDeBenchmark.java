package org.analise.projeto.desempenho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.analise.projeto.ordenacao.AlgoritmoDeOrdenacao;

/**
 * Classe responsável por executar benchmarks dos algoritmos de ordenação
 */
public class ExecutorDeBenchmark {
    private final int numRuns;
    
    public ExecutorDeBenchmark(int numRuns) {
        this.numRuns = numRuns > 0 ? numRuns : 3;
    }
    
    public ExecutorDeBenchmark() {
        this(3);
    }
    
    /**
     * Executa benchmark de um algoritmo específico
     */
    public List<Metricas> runBenchmark(AlgoritmoDeOrdenacao algorithm, int[] data) {
        if (algorithm == null) {
            throw new IllegalArgumentException("O algoritmo não pode ser nulo");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Os dados não podem ser nulos ou vazios");
        }
        
        List<Metricas> results = new ArrayList<>();
        
        for (int run = 0; run < numRuns; run++) {
            int[] dataCopy = data.clone();
            
            // Forçar garbage collection antes da medição
            System.gc();
            
            // Medição de memória antes
            long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            
            // Medição de tempo
            long startTime = System.nanoTime();
            algorithm.sort(dataCopy);
            long endTime = System.nanoTime();
            
            // Medição de memória depois
            long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            
            long executionTime = endTime - startTime;
            long memoryUsed = Math.max(0, afterMemory - beforeMemory); // Evitar valores negativos
            
            results.add(new Metricas(algorithm.getName(), executionTime, memoryUsed));
        }
        
        return results;
    }
    
    /**
     * Executa benchmark de múltiplos algoritmos
     */
    public Map<String, List<Metricas>> runBenchmarks(List<AlgoritmoDeOrdenacao> algorithms, int[] data) {
        if (algorithms == null || algorithms.isEmpty()) {
            throw new IllegalArgumentException("A lista de algoritmos não pode ser nula ou vazia");
        }
        if (data == null || data.length == 0) {
            throw new IllegalArgumentException("Os dados não podem ser nulos ou vazios");
        }
        
        Map<String, List<Metricas>> allResults = new HashMap<>();
        
       for (AlgoritmoDeOrdenacao algorithm : algorithms) {
            System.out.println("Executando benchmark para " + algorithm.getName() + "...");
            List<Metricas> results = runBenchmark(algorithm, data);
            allResults.put(algorithm.getName(), results);
        }
        
        return allResults;
    }
    
    /**
     * Calcula métricas médias a partir de múltiplas execuções
     */
    public Map<String, Metricas> calculateAverageMetrics(Map<String, List<Metricas>> allResults) {
        Map<String, Metricas> averages = new HashMap<>();
        
        for (Map.Entry<String, List<Metricas>> entry : allResults.entrySet()) {
            String algorithmName = entry.getKey();
            List<Metricas> metrics = entry.getValue();
            
            long totalTime = 0;
            long totalMemory = 0;
            
            for (Metricas metric : metrics) {
                totalTime += metric.getExecutionTimeNanos();
                totalMemory += metric.getMemoryUsedBytes();
            }
            
            long avgTime = totalTime / metrics.size();
            long avgMemory = totalMemory / metrics.size();
            
            averages.put(algorithmName, new Metricas(algorithmName, avgTime, avgMemory));
        }
        
        return averages;
    }
}

