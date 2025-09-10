package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 플로이드-워셜 알고리즘을 이용합니다.
 *
 * 플로이드-워셜은 모든 노드 쌍간의 최단 거리를 구할 수 있습니다.
 * 거리를 구할 수 있다는 것은 곧 연결되어있다는 것이기 때문에
 * 각 노드쌍의 연결 여부를 확인할 수 있습니다. (edges[시작][끝]의 값이 INF가 아니면 연결된 것)
 *
 * 문제에선 특정 노드의 순서를 알 수 있는지 묻고 있는데,
 * 앞뒤로 연결된 노드가 N-1개 있다는 것을 알면 순서를 정확히 알 수 있습니다.
 * (예를 들어, 앞쪽 2명 뒤쪽 3명이면 내 순서는 3번째)
 *
 * 처음에 입력을 단방향 그래프로 받는데
 * 앞뒤 여부 상관없이 i와 j가 연결되었는지만 알면 되기 때문에
 * edges[i][j] != INF || edges[j][i] != INF 로 연결이 되어있는지 여부만 확인하면 됩니다.
 */

/**
 * 메모리: 101,104 KB
 * 시간: 2,020 ms
 * 난이도: D4
 */
public class Solution_5643_키순서 {
    static int T;
    static int N, M;

    static int[][] edges;

    static int INF = 100_000;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            edges = new int[N + 1][N + 1];

            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (i != j) {
                        edges[i][j] = INF;
                    }
                }
            }

            for (int i = 0; i < M; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                edges[from][to] = 1;
            }

            for (int inter = 1; inter <= N; inter++) {
                for (int start = 1; start <= N; start++) {
                    for (int end = 1; end <= N; end++) {
                        if (edges[start][end] > edges[start][inter] + edges[inter][end]) {
                            edges[start][end] = edges[start][inter] + edges[inter][end];
                        }
                    }
                }
            }

            int knowableNode = 0;
            for (int i = 1; i <= N; i++) {
                int nodeCount = 0;
                for (int j = 1; j <= N; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (edges[i][j] != INF || edges[j][i] != INF) {
                        nodeCount++;
                    }
                }
                if (nodeCount == N - 1) {
                    knowableNode++;
                }
            }

            sb.append("#").append(t).append(" ").append(knowableNode).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}