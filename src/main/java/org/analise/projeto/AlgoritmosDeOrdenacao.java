package org.analise.projeto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.analise.projeto.desempenho.ExecutorDeBenchmark;
import org.analise.projeto.desempenho.HistoricoResultados;
import org.analise.projeto.desempenho.Metricas;
import org.analise.projeto.interface_grafica.VisualizadorGraficoDesempenho;
import org.analise.projeto.interface_grafica.VisualizadorTabelaResultados;
import org.analise.projeto.ordenacao.AlgoritmoDeOrdenacao;
import org.analise.projeto.ordenacao.OrdenacaoBolha;
import org.analise.projeto.ordenacao.OrdenacaoPorInsercao;
import org.analise.projeto.ordenacao.OrdenacaoPorMesclagem;
import org.analise.projeto.ordenacao.OrdenacaoPorPilha;
import org.analise.projeto.ordenacao.OrdenacaoPorSelecao;
import org.analise.projeto.ordenacao.OrdenacaoRapida;
import org.analise.projeto.utilidades.GeradorDeDados;

/**
 * Classe principal para execução dos testes de algoritmos de ordenação
 */
public class AlgoritmosDeOrdenacao {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String continuarAnalise;

        do {
            executarAnalise(scanner);

            System.out.print("\nDeseja realizar uma nova análise com novos dados? (S/N): ");
            continuarAnalise = scanner.nextLine().trim().toUpperCase();

            if (continuarAnalise.equals("S")) {
                System.out.println("\n" + "=".repeat(60));
                System.out.println("    NOVA ANALISE DE ALGORITMOS DE ORDENACAO");
                System.out.println("=".repeat(60));
            }

        } while (continuarAnalise.equals("S"));

        System.out.println("\nPrograma encerrado. Obrigado por utilizar!");
        scanner.close();
    }

    /**
     * Método responsável por executar a análise de desempenho dos algoritmos de ordenação
     */
    private static void executarAnalise(Scanner scanner) {
        System.out.println("=".repeat(60));
        System.out.println("    SISTEMA DE ANALISE DE ALGORITMOS DE ORDENACAO");
        System.out.println("=".repeat(60));

        // Entrada de dados
        System.out.print("Digite a quantidade de números a serem ordenados: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        System.out.println("\nEscolha o tipo de números para gerar:");
        System.out.println("1 - Crescente com repetição");
        System.out.println("2 - Decrescente com repetição");
        System.out.println("3 - Aleatório com repetição");
        System.out.println("4 - Crescente sem repetição");
        System.out.println("5 - Decrescente sem repetição");
        System.out.println("6 - Aleatório sem repetição");
        System.out.print("Opção: ");
        int tipoGeracao = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        // Seleção de algoritmos
        List<AlgoritmoDeOrdenacao> algorithms = new ArrayList<>();
        boolean adicionarMaisAlgoritmos;

        do {
            System.out.println("\nEscolha o algoritmo de ordenação:");
            System.out.println("1. Bubble Sort");
            System.out.println("2. Insertion Sort");
            System.out.println("3. Selection Sort");
            System.out.println("4. Merge Sort");
            System.out.println("5. Quick Sort");
            System.out.println("6. Heap Sort");
            System.out.println("7. Executar todos os algoritmos");
            System.out.print("Opção: ");
            int algorithmChoice = scanner.nextInt();
            scanner.nextLine(); // Consumir a nova linha

            if (algorithmChoice == 7) {
                // Todos os algoritmos
                algorithms.add(new OrdenacaoBolha());
                algorithms.add(new OrdenacaoPorInsercao());
                algorithms.add(new OrdenacaoPorSelecao());
                algorithms.add(new OrdenacaoPorMesclagem());
                algorithms.add(new OrdenacaoRapida());
                algorithms.add(new OrdenacaoPorPilha());
            } else {
                AlgoritmoDeOrdenacao selectedAlgorithm = getAlgorithmByChoice(algorithmChoice);
                if (selectedAlgorithm != null) {
                    algorithms.add(selectedAlgorithm);
                } else {
                    System.err.println("Opção de algoritmo inválida!");
                }
            }

            System.out.print("\nDeseja adicionar outro algoritmo à análise atual? (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            adicionarMaisAlgoritmos = resposta.equals("S");

        } while (adicionarMaisAlgoritmos);

        // Nome do arquivo de saída
        System.out.print("\nDigite o nome do arquivo de saída: ");
        String outputFile = scanner.nextLine();

        // Gerar dados de teste
        System.out.println("\nGerando dados de teste (" + GeradorDeDados.getDataTypeDescription(tipoGeracao) + ")...");
        int[] testData = GeradorDeDados.generateData(n, tipoGeracao);

        // Executar benchmarks
        System.out.println("\nExecutando testes de desempenho...");
        ExecutorDeBenchmark benchmarkRunner = new ExecutorDeBenchmark();
        Map<String, List<Metricas>> allResults = benchmarkRunner.runBenchmarks(algorithms, testData);
        Map<String, Metricas> averageMetrics = benchmarkRunner.calculateAverageMetrics(allResults);

        // Salvar dados ordenados
        for (AlgoritmoDeOrdenacao algorithm : algorithms) {
            int[] dataCopy = testData.clone();
            algorithm.sort(dataCopy);
            String filename = outputFile + "_" + algorithm.getName().toLowerCase().replace(" ", "_") + ".txt";
            GeradorDeDados.writeNumbersToFile(filename, dataCopy);
        }

        // Adicionar métricas ao histórico
        HistoricoResultados.adicionarResultados(averageMetrics);

        // Exibir resultados
        VisualizadorTabelaResultados tableView = new VisualizadorTabelaResultados();
        tableView.displayResultsConsole(averageMetrics);

        String resposta;
        do {
            System.out.print("\nDeseja visualizar os gráficos comparativos? (S/N): ");
            resposta = scanner.nextLine().trim().toUpperCase();
        } while (!resposta.equals("S") && !resposta.equals("N"));

        VisualizadorGraficoDesempenho chartView = new VisualizadorGraficoDesempenho();
        if (resposta.equals("S")) {
            chartView.displayCharts(averageMetrics);
        }

        do {
            System.out.print("\nDeseja gerar o relatório PDF com os gráficos? (S/N): ");
            resposta = scanner.nextLine().trim().toUpperCase();
        } while (!resposta.equals("S") && !resposta.equals("N"));

        if (resposta.equals("S")) {
            String pdfFileName = "relatorio_ordenacao_" +
                    System.currentTimeMillis() + "_" +
                    n + "_elementos.pdf";
            tableView.generatePDFReport(averageMetrics, pdfFileName);
            System.out.println("Relatório PDF gerado: " + pdfFileName);
        }

        System.out.println("\nAnálise concluída! Arquivos gerados:");
        System.out.println("- Dados ordenados: " + outputFile + "_[algoritmo].txt");
        if (resposta.equals("S")) {
            System.out.println("- Relatório PDF: relatorio_ordenacao_*_elementos.pdf");
        }
    }

    /**
     * Retorna o algoritmo baseado na escolha do usuário
     */
    private static AlgoritmoDeOrdenacao getAlgorithmByChoice(int choice) {
        switch (choice) {
            case 1: return new OrdenacaoBolha();
            case 2: return new OrdenacaoPorInsercao();
            case 3: return new OrdenacaoPorSelecao();
            case 4: return new OrdenacaoPorMesclagem();
            case 5: return new OrdenacaoRapida();
            case 6: return new OrdenacaoPorPilha();
            default: return null;
        }
    }
}
