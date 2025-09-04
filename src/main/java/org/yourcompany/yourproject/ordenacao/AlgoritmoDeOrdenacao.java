package org.yourcompany.yourproject.ordenacao;

/**
 * Interface para algoritmos de ordenação
 */
public interface AlgoritmoDeOrdenacao {
    /**
     * Ordena o array fornecido
     * @param arr Array a ser ordenado
     */
    void sort(int[] arr);
    
    /**
     * Retorna o nome do algoritmo
     * @return Nome do algoritmo
     */
    String getName();
}

