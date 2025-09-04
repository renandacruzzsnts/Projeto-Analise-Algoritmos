package org.yourcompany.yourproject.ordenacao;

/**
 * Implementação do algoritmo Insertion Sort
 */
public class OrdenacaoPorInsercao implements AlgoritmoDeOrdenacao {
    
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    
    @Override
    public String getName() {
        return "Insertion Sort";
    }
}

