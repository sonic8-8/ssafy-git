package level_a;

import java.io.*;
import java.util.*;

public class Solution_5643_키순서_플로이드워셜_복습 {
    static int T;
    static int N, M;

    static int[][] edges;

    static final int INF = 100_000;

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

            for (int intermediate = 1; intermediate <= N; intermediate++) {
                for (int start = 1; start <= N; start++) {
                    for (int end = 1; end <= N; end++) {
                        if (edges[start][end] > edges[start][intermediate] + edges[intermediate][end]) {
                            edges[start][end] = edges[start][intermediate] + edges[intermediate][end];
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
                    if (edges[start][end] != INF || edges[end][start] != INF) {
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