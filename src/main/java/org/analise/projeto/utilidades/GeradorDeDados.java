package org.analise.projeto.utilidades;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Classe utilitária para geração de dados de teste
 */
public class GeradorDeDados {
    
    /**
     * Gera array de números baseado no tipo especificado
     */
    public static int[] generateData(int quantity, int type) {
        Random rand = new Random();
        Set<Integer> generatedNumbersSet = new HashSet<>();
        List<Integer> numbers = new ArrayList<>();
        
        switch (type) {
            case 1: // Crescente com repetição
                for (int i = 0; i < quantity; i++) {
                    numbers.add(rand.nextInt(quantity / 10 + 1));
                }
                Collections.sort(numbers);
                break;
                
            case 2: // Decrescente com repetição
                for (int i = 0; i < quantity; i++) {
                    numbers.add(rand.nextInt(quantity / 10 + 1));
                }
                numbers.sort(Collections.reverseOrder());
                break;
                
            case 3: // Aleatório com repetição
                for (int i = 0; i < quantity; i++) {
                    numbers.add(rand.nextInt(quantity * 2) + 1);
                }
                break;
                
            case 4: // Crescente sem repetição
                for (int i = 1; i <= quantity; i++) {
                    numbers.add(i);
                }
                break;
                
            case 5: // Decrescente sem repetição
                for (int i = quantity; i >= 1; i--) {
                    numbers.add(i);
                }
                break;
                
            case 6: // Aleatório sem repetição
                while (numbers.size() < quantity) {
                    int num = rand.nextInt(quantity * 2) + 1;
                    if (!generatedNumbersSet.contains(num)) {
                        generatedNumbersSet.add(num);
                        numbers.add(num);
                    }
                }
                Collections.shuffle(numbers);
                break;
                
            default:
                System.out.println("Tipo de geração inválido. Gerando números aleatórios com repetição.");
                for (int i = 0; i < quantity; i++) {
                    numbers.add(rand.nextInt(quantity * 2) + 1);
                }
                break;
        }
        
        return numbers.stream().mapToInt(i -> i).toArray();
    }
    
    /**
     * Gera arquivo de entrada com os dados
     */
    public static void generateInputFile(String filename, int quantity, int type) {
        int[] data = generateData(quantity, type);
        
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int number : data) {
                writer.println(number);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao criar o arquivo de entrada: " + e.getMessage());
        }
    }
    
    /**
     * Lê números de um arquivo
     */
    public static int[] readNumbersFromFile(String filename, int n) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            int[] numbers = new int[n];
            for (int i = 0; i < n && fileScanner.hasNextInt(); i++) {
                numbers[i] = fileScanner.nextInt();
            }
            fileScanner.close();
            return numbers;
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Escreve números ordenados em um arquivo
     */
    public static void writeNumbersToFile(String filename, int[] numbers) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (int number : numbers) {
                writer.println(number);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }
    
    /**
     * Retorna descrição do tipo de dados
     */
    public static String getDataTypeDescription(int type) {
        switch (type) {
            case 1: return "Crescente com repetição";
            case 2: return "Decrescente com repetição";
            case 3: return "Aleatório com repetição";
            case 4: return "Crescente sem repetição";
            case 5: return "Decrescente sem repetição";
            case 6: return "Aleatório sem repetição";
            default: return "Tipo inválido";
        }
    }
}

