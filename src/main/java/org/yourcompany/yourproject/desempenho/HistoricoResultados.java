package org.yourcompany.yourproject.desempenho;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricoResultados {
    private static final Map<String, List<Metricas>> historicoCompleto = new HashMap<>();
    
    public static void adicionarResultados(Map<String, Metricas> resultados) {
        for (Map.Entry<String, Metricas> entry : resultados.entrySet()) {
            historicoCompleto.computeIfAbsent(entry.getKey(), k -> new ArrayList<>())
                           .add(entry.getValue());
        }
    }
    
    public static Map<String, List<Metricas>> getHistoricoCompleto() {
        return Collections.unmodifiableMap(historicoCompleto);
    }
    
    public static Map<String, Metricas> getMediasGerais() {
        Map<String, Metricas> medias = new HashMap<>();
        
        for (Map.Entry<String, List<Metricas>> entry : historicoCompleto.entrySet()) {
            String algorithmName = entry.getKey();
            List<Metricas> metricas = entry.getValue();
            
            long avgTime = (long) metricas.stream()
                .mapToLong(Metricas::getExecutionTimeNanos)
                .average()
                .orElse(0.0);
            
            long avgMemory = (long) metricas.stream()
                .mapToLong(Metricas::getMemoryUsedBytes)
                .average()
                .orElse(0.0);
                
            medias.put(algorithmName, new Metricas(algorithmName, avgTime, avgMemory));
        }
        
        return medias;
    }
}
