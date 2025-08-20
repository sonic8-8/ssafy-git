package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * BFS로 그래프를 탐색합니다.
 * 핵심은 레벨 별로 노드를 처리하는 것입니다.
 * 이렇게 하면 각 레벨에서 최대 번호를 갱신할 수 있고
 * 마지막 레벨에서도 최대 번호를 갱신 할 수 있습니다. (= 마지막에 연락받는 사람 중 번호가 가장 큰 사람)
 */

/**
 * 메모리: 25472 KB
 * 시간: 79 ms
 * 난이도: D4
 */
public class Solution_1238_Contact {
    static final int T = 10;
    static int N;
    static int startNode;

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

            st = new StringTokenizer(br.readLine());
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
        int lastMax = startNode;

        while (!queue.isEmpty()) {
            int size = queue.size();
            int levelMax = 0;

            // 이번 레벨에 있는 노드 수만큼만 poll()하여 처리
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                levelMax = Math.max(levelMax, current);

                // 새로 큐에 추가된 노드는 다음 레벨에서 처리됩니다
                for (int next : graph[current]) {
                    if (!visited[next]) {
                        visited[next] = true;
                        queue.add(next);
                    }
                }
            }
            lastMax = levelMax;
        }
        return lastMax;
    }
}