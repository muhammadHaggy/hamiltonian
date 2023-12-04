import java.io.*;
import java.util.Scanner;

public class HamiltonianPathBacktracking {

    public static void main(String[] args) {
        String inputFilePath = "generated_graph_20.txt";
        int[][] graph = readGraphFromFile(inputFilePath);

        long startTime = System.currentTimeMillis();
        long startMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        if (hamPath(graph)) {
            System.out.println("Hamiltonian Path found.");
        } else {
            System.out.println("Solution does not exist for Hamiltonian Path.");
        }

        long endMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endTime = System.currentTimeMillis();

        System.out.println("Current memory usage: " + (endMemory - startMemory) + " bytes");
        System.out.println("Running Time: " + (endTime - startTime) + " milliseconds");
    }

    private static int[][] readGraphFromFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            int numberOfRows = 0;
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                numberOfRows++;
            }
            scanner.close();

            int[][] graph = new int[numberOfRows][numberOfRows];
            scanner = new Scanner(new File(filePath));

            int row = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().trim().split("\\s+");
                for (int i = 0; i < line.length; i++) {
                    graph[row][i] = Integer.parseInt(line[i]);
                }
                row++;
            }
            scanner.close();
            return graph;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean isSafe(int[][] graph, int v, int pos, int[] path) {
        if (graph[path[pos - 1]][v] == 0) {
            return false;
        }

        for (int i = 0; i < pos; i++) {
            if (path[i] == v) {
                return false;
            }
        }

        return true;
    }

    private static boolean hamPathUtil(int[][] graph, int[] path, int pos, int V) {
        if (pos == V) {
            return true;
        }

        for (int v = 0; v < V; v++) {
            if (isSafe(graph, v, pos, path)) {
                path[pos] = v;
                if (hamPathUtil(graph, path, pos + 1, V)) {
                    return true;
                }
                path[pos] = -1;
            }
        }

        return false;
    }

    public static boolean hamPath(int[][] graph) {
        int V = graph.length;
        for (int startVertex = 0; startVertex < V; startVertex++) {
            int[] path = new int[V];
            for (int i = 0; i < V; i++) {
                path[i] = -1;
            }
            path[0] = startVertex;

            if (hamPathUtil(graph, path, 1, V)) {
                printSolution(path);
                return true;
            }
        }

        return false;
    }

    private static void printSolution(int[] path) {
        System.out.println("Solution Exists: Following is one Hamiltonian Path");
        for (int vertex : path) {
            if (vertex != -1) {
                System.out.print(vertex + " ");
            }
        }
        System.out.println();
    }
}
