package level_a;

import java.io.*;
import java.util.*;

public class Main_17471_게리맨더링 {
    static int N;

    static int[] population;
    static List<Integer>[] graph;
    static boolean[] visited;

    static List<Integer> groupA;
    static List<Integer> groupB;

    static int min;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        population = new int[N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            population[i] = Integer.parseInt(st.nextToken());
        }

        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int count = Integer.parseInt(st.nextToken());

            for (int j = 0; j < count; j++) {
                int node = Integer.parseInt(st.nextToken());
                graph[i].add(node);
            }
        }

        groupA = new ArrayList<>();
        groupB = new ArrayList<>();

        min = Integer.MAX_VALUE;
        subset(1);

        if (min == Integer.MAX_VALUE) {
            min = -1;
        }
        bw.write(String.valueOf(min));

        br.close();
        bw.close();
    }

    private static void subset(int index) {
        if (index == N + 1) {
            if (!groupA.isEmpty() && !groupB.isEmpty()) {
                if (isConnected(groupA) && isConnected(groupB)) {
                    int diff = Math.abs(calculateSum(groupA) - calculateSum(groupB));
                    min = Math.min(min, diff);
                }
            }
            return;
        }

        groupA.add(index);
        subset(index + 1);
        groupA.remove(groupA.size() - 1);

        groupB.add(index);
        subset(index + 1);
        groupB.remove(groupB.size() - 1);
    }

    private static int calculateSum(List<Integer> group) {
        int sum = 0;
        for (int node : group) {
            sum += population[node];
        }
        return sum;
    }

    private static boolean isConnected(List<Integer> group) {
        visited = new boolean[N + 1];

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(group.get(0));
        int count = 1;
        visited[group.get(0)] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int next : graph[current]) {
                if (!visited[next] && group.contains(next)) {
                    visited[next] = true;
                    queue.add(next);
                    count++;
                }
            }
        }
        return count == group.size();
    }
}