package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 크루스칼 알고리즘 + 랭크 최적화
 * 1. 리스트로 간선 정보 입력받기
 * 2. 가중치 기준 오름차순 정렬
 * 3. 각 노드를 하나의 원소로 갖는 서로소 집합을 만듭니다 (parent 배열 이용)
 * 4. 반복문으로 간선 정보를 하나씩 확인하며, 각 노드의 루트 노드(find()로 확인)가 같은지 확인합니다. (같은 집합인지 확인하는 것)
 *    - 같은 집합이면 사이클이 발생하므로 건너뜁니다
 *    - 다른 집합이면 union()으로 합쳐줍니다.
 */

/**
 * 메모리: 122,220 KB
 * 시간: 1,409 ms
 * 난이도: D4
 */
public class Solution_3124_최소스패닝트리_크루스칼 {
    static int T;
    static int V, E;
    static List<int[]> edges;

    static int[] parent;
    static int[] rank;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            edges = new ArrayList<>();

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int startNode = Integer.parseInt(st.nextToken());
                int endNode = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edges.add(new int[]{startNode, endNode, weight});
            }

            // 가중치 기준 오름차순
            edges.sort(Comparator.comparingInt(e -> e[2]));

            parent = new int[V + 1];
            rank = new int[V + 1];
            for (int i = 1; i <= V; i++) {
                parent[i] = i;
                rank[i] = 0;
            }

            long totalWeight = 0;
            int edgeCount = 0;

            for (int[] edge : edges) {
                int nodeA = edge[0];
                int nodeB = edge[1];
                int weight = edge[2];

                if (find(nodeA) != find(nodeB)) {
                    union(nodeA, nodeB);
                    edgeCount++;
                    totalWeight += weight;

                    if (edgeCount == V - 1) {
                        break;
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(totalWeight).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    // 루트 노드 찾기
    private static int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]);
        }
        return parent[node];
    }

    // 두 집합 합치기 (루트 노드)
    private static void union(int nodeA, int nodeB) {
        int rootA = find(nodeA);
        int rootB = find(nodeB);

        if (rootA == rootB) {
            return;
        }

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
            return;
        }
        if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
            return;
        }
        if (rank[rootA] == rank[rootB]) {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }
}