package org.yourcompany.yourproject.ordenacao;

/**
 * Implementação do algoritmo Heap Sort
 */
public class OrdenacaoPorPilha implements AlgoritmoDeOrdenacao {
    
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        
        // Construir heap (reorganizar array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        
        // Extrair um elemento do heap por vez
        for (int i = n - 1; i > 0; i--) {
            // Mover a raiz atual para o final
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            
            // Chamar heapify na heap reduzida
            heapify(arr, i, 0);
        }
    }
    
    private void heapify(int[] arr, int n, int i) {
        int largest = i; // Inicializar largest como raiz
        int l = 2 * i + 1; // esquerda = 2*i + 1
        int r = 2 * i + 2; // direita = 2*i + 2
        
        // Se o filho esquerdo é maior que a raiz
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        
        // Se o filho direito é maior que o largest até agora
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        
        // Se largest não é raiz
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            
            // Recursivamente heapify a subárvore afetada
            heapify(arr, n, largest);
        }
    }
    
    @Override
    public String getName() {
        return "Heap Sort";
    }
}

