package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 플로이드-워셜 알고리즘을 통해
 * 모든 노드 사이의 관계를 파악합니다.
 *
 * 중간 노드를 거쳐 이동할 수 있다면 연결된 것이므로
 * 각 노드 앞뒤에 몇 개의 노드가 존재하는지 파악합니다.
 * 앞뒤 노드의 수의 합이 N - 1개면 해당 노드의 정확한 순서를 알 수 있습니다.
 */

/**
 * 메모리: 98,652 KB
 * 시간: 1702 ms
 * 난이도: D4
 */
public class Solution_5643_키순서_플로이드워셜 {
    static int T;
    static int N, M;

    static int[][] graph;
    static final int INF = 100_000;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            graph = new int[N + 1][N + 1];

            // 플로이드-워셜 구현을 위해 INF로 초기화합니다.
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (i == j) {
                        continue;
                    }
                    graph[i][j] = INF;
                }
            }

            for (int i = 0; i < M; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                graph[from][to] = 1;
            }

            // 플로이드-워셜, 중간 노드를 거쳐서 도달할 수 있으면 값을 갱신
            for (int intermediate = 1; intermediate <= N; intermediate++) {
                for (int start = 1; start <= N; start++) {
                    for (int end = 1; end <= N; end++) {
                        if (graph[start][end] > graph[start][intermediate] + graph[intermediate][end]) {
                            graph[start][end] = graph[start][intermediate] + graph[intermediate][end];
                        }
                    }
                }
            }

            int knowableNode = 0;

            for (int start = 1; start <= N; start++) {
                int nodeCount = 0;
                for (int end = 1; end <= N; end++) {
                    if (start == end) {
                        continue;
                    }
                    // 뒤쪽 노드, 앞쪽 노드 개수 세기
                    if (graph[start][end] != INF || graph[end][start] != INF) {
                        nodeCount++;
                    }
                }
                if (nodeCount == N - 1) {
                    knowableNode++;
                }
            }

            sb.append('#').append(t).append(' ').append(knowableNode).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}
