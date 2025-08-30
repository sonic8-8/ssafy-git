package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 프림 알고리즘
 */

/**
 * 메모리: 221,332 KB
 * 시간: 2,743 ms
 * 난이도: D4
 */
public class Solution_3124_최소스패닝트리_프림 {
    static int T;
    static int V, E;

    static List<int[]>[] edges;
    static boolean[] visited;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            edges = new ArrayList[V + 1];
            for (int i = 1; i <= V; i++) {
                edges[i] = new ArrayList<>();
            }

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int startNode = Integer.parseInt(st.nextToken());
                int endNode = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edges[startNode].add(new int[]{endNode, weight});
                edges[endNode].add(new int[]{startNode, weight});
            }

            visited = new boolean[V + 1];

            // 임의의 노드 하나를 시작 노드로 넣는다
            PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e[1]));
            pq.add(new int[]{1, 0}); // 노드, 가중치

            long totalWeight = 0;
            int edgeCount = 0;

            while (!pq.isEmpty() && edgeCount < V) {
                int[] current = pq.poll();
                int currentNode = current[0];
                int currentWeight = current[1];

                // 사이클 형성을 방지하기 위해 방문 여부를 확인합니다
                if (visited[currentNode]) {
                    continue;
                }
                // 방문할 수 있는 노드라면 MST에 추가합니다
                visited[currentNode] = true;
                totalWeight += currentWeight;
                edgeCount++;

                // 연결된 간선 중 가중치가 가장 작은 것을 뽑습니다
                for (int[] edge : edges[currentNode]) {
                    int nextNode = edge[0];
                    int nextWeight = edge[1];

                    // !visited[nextNode]는 성능 최적화를 위한 조건입니다
                    // 조건문 없이 pq.add()를 해도 정답에는 영향이 없지만
                    // 미리 방문 여부를 체크하면 우선순위 큐 크기가 커지는 것을 막고
                    // 불필요한 while 루프 연산도 줄일 수 있습니다
                    if (!visited[nextNode]) {
                        pq.add(new int[]{nextNode, nextWeight});
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(totalWeight).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}
