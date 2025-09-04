package org.yourcompany.yourproject.desempenho;

/**
 * Classe para armazenar métricas de desempenho de um algoritmo
 */
public class Metricas {
    private final String algorithmName;
    private final long executionTimeNanos;
    private final long memoryUsedBytes;
    
    public Metricas(String algorithmName, long executionTimeNanos, long memoryUsedBytes) {
        if (algorithmName == null || algorithmName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do algoritmo não pode ser nulo ou vazio");
        }
        if (executionTimeNanos < 0) {
            throw new IllegalArgumentException("Tempo de execução não pode ser negativo");
        }
        if (memoryUsedBytes < 0) {
            throw new IllegalArgumentException("Uso de memória não pode ser negativo");
        }
        
        this.algorithmName = algorithmName;
        this.executionTimeNanos = executionTimeNanos;
        this.memoryUsedBytes = memoryUsedBytes;
    }
    
    public String getAlgorithmName() {
        return algorithmName;
    }
    
    public long getExecutionTimeNanos() {
        return executionTimeNanos;
    }
    
    public double getExecutionTimeMillis() {
        return executionTimeNanos / 1_000_000.0;
    }
    
    public long getMemoryUsedBytes() {
        return memoryUsedBytes;
    }
    
    public double getMemoryUsedMB() {
        return memoryUsedBytes / (1024.0 * 1024.0);
    }
    
    @Override
    public String toString() {
        return String.format("Algoritmo: %s%nTempo: %.2f ms%nMemória: %.2f MB", 
                algorithmName, 
                getExecutionTimeMillis(),
                getMemoryUsedMB());
    }
}

