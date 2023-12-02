import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class HamiltonianComparison {

    // Method to read the dataset and convert it to an adjacency matrix
    static int[][] readDataset(String filename, int size) {
        int[][] graph = new int[size][size];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    int v1 = Integer.parseInt(parts[0]);
                    int v2 = Integer.parseInt(parts[1]);
                    graph[v1][v2] = 1;
                    graph[v2][v1] = 1; // For undirected graph
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    // Benchmark method
    static void benchmark(String datasetName, int[][] graph) {
        // Measuring for Hamiltonian Cycle Backtracking
        long startTime = System.currentTimeMillis();
        long startMemory = getCurrentMemoryUsage();
//        HamiltonianPathBacktracking.hamPath(graph);
        HamiltonianPathBacktracking.checkUsingDfs(graph, graph.length);
        long endMemory = getCurrentMemoryUsage();
        long endTime = System.currentTimeMillis();
        System.out.println("Hamiltonian Path Backtracking (" + datasetName + "): Time = " + (endTime - startTime) + "ms, Memory = " + (endMemory - startMemory) + " bytes");

        // Measuring for Hamiltonian Path DP
        startTime = System.currentTimeMillis();
        startMemory = getCurrentMemoryUsage();
        HamiltonianPathDP.Hamiltonian_path(graph, graph.length);
        endMemory = getCurrentMemoryUsage();
        endTime = System.currentTimeMillis();
        System.out.println("Hamiltonian Path DP (" + datasetName + "): Time = " + (endTime - startTime) + "ms, Memory = " + (endMemory - startMemory) + " bytes");
    }

    // Helper method to get current memory usage
    static long getCurrentMemoryUsage() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    // Main method
    public static void main(String[] args) {
        int[][] smallGraph = readDataset("datasets/small_graph_edgelist.txt", 16);
        int[][] mediumGraph = readDataset("datasets/medium_graph_edgelist.txt", 18);
        int[][] largeGraph = readDataset("datasets/large_graph_edgelist.txt", 20);

        // Benchmark for each dataset
        benchmark("Small", smallGraph);
        benchmark("Medium", mediumGraph);
        benchmark("Large", largeGraph);
    }
}
