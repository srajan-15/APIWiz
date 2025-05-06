import java.util.*;
import java.io.*;

public class APIWiz {

    static Map<Integer, String> vertexMap = new HashMap<>();
    static Map<Integer, List<Integer>> adjList = new HashMap<>();
    static Map<Integer, Integer> inDegree = new HashMap<>();
    static Set<Integer> visited = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Read number of nodes
        int nodeCount = Integer.parseInt(scanner.nextLine().trim());

        // Read node definitions
        for (int i = 0; i < nodeCount; i++) {
            String[] parts = scanner.nextLine().split(":");
            int id = Integer.parseInt(parts[0].trim());
            String label = parts[1].trim();

            vertexMap.put(id, label);
            adjList.put(id, new ArrayList<>());
            inDegree.put(id, 0);
        }

        // Read number of edges
        int edgeCount = Integer.parseInt(scanner.nextLine().trim());

        // Read edge definitions
        for (int i = 0; i < edgeCount; i++) {
            String[] parts = scanner.nextLine().split(":");
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());

            adjList.get(from).add(to);
            inDegree.put(to, inDegree.get(to) + 1);
        }

        // Start traversal from nodes with in-degree 0
        for (int id : vertexMap.keySet()) {
            if (inDegree.get(id) == 0) {
                traverse(id);
            }
        }

        System.out.println( visited.size());
        scanner.close();
    }

    static void traverse(int nodeId) {
        if (!visited.add(nodeId)) return;

        System.out.println(vertexMap.get(nodeId));

        List<Thread> threads = new ArrayList<>();

        for (int child : adjList.get(nodeId)) {
            boolean ready = false;
            synchronized (inDegree) {
                inDegree.put(child, inDegree.get(child) - 1);
                if (inDegree.get(child) == 0) {
                    ready = true;
                }
            }

            if (ready) {
                Thread t = new Thread(() -> traverse(child));
                threads.add(t);
                t.start();
            }
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
