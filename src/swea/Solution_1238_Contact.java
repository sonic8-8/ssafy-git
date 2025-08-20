package swea;

import java.io.*;
import java.util.*;

public class Solution_1238_Contact {
    static final int T = 10;
    static int N;
    static int startNode;

    static Map<Integer, Integer> orderNodes;
    static int order;

    static List<Integer>[] graph;
    static boolean[] visited;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            startNode = Integer.parseInt(st.nextToken());

            graph = new ArrayList[100 + 1];
            for (int i = 1; i <= 100; i++) {
                graph[i] = new ArrayList<>();
            }

            for (int i = 0; i < N / 2; i++) {
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                graph[from].add(to);
            }

        visited = new boolean[100 + 1];
        int lastNode = bfs();
        sb.append('#').append(t).append(' ').append(lastNode).append('\n');
    }

        bw.write(sb.toString());

        br.close();
        bw.close();
}

private static int bfs() {
    Queue<Integer> queue = new ArrayDeque<>();
    queue.add(startNode);
    visited[startNode] = true;

    while (!queue.isEmpty()) {
        int current = queue.poll();

        for (int next : graph[current]) {
            queue.add(next);
            orderNodes.put(order++, next);
        }
    }
}