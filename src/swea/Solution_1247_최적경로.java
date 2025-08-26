package swea;

import java.io.*;
import java.util.StringTokenizer;

/**
 * 아이디어:
 * DFS 순열 + 백트래킹
 *
 * 회사(0) -> 고객(1~N) -> 집(N+1)
 * -> 전체 노드 수가 정해져있다
 * -> 순열로 접근하기
 *
 * 이동거리가 짧은 경로를 찾아야 함
 * -> 가지치기를 통해 min보다 totalDist가 크면 return 합니다
 */

/**
 * 메모리: 26,808 KB
 * 시간: 242 ms
 * 난이도: D5
 */
public class Solution_1247_최적경로 {
    static int T;
    static int N;

    static int[][] positions;
    static int[] customerOrder;
    static boolean[] visited;

    static final int ROW = 0;
    static final int COL = 1;

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            positions = new int[N + 2][2]; // 회사와 집, 고객을 모두 담을 좌표 배열
            customerOrder = new int[N]; // 고객 순서를 담을 배열
            visited = new boolean[N + 1]; // 고객 방문 여부

            StringTokenizer st = new StringTokenizer(br.readLine());

            // 회사 좌표
            positions[0][ROW] = Integer.parseInt(st.nextToken());
            positions[0][COL] = Integer.parseInt(st.nextToken());

            // 집 좌표
            positions[N + 1][ROW] = Integer.parseInt(st.nextToken());
            positions[N + 1][COL] = Integer.parseInt(st.nextToken());

            // 고객 좌표
            for (int i = 1; i <= N; i++) {
                positions[i][0] = Integer.parseInt(st.nextToken());
                positions[i][1] = Integer.parseInt(st.nextToken());
            }

            min = Integer.MAX_VALUE;
            permutation(0, 0);

            sb.append('#').append(t).append(' ').append(min).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void permutation(int depth, int totalDist) {
        // 현재 계산된 거리가 이전에 계산했던 최소 거리보다 커지면 가지치기
        if (totalDist > min) {
            return;
        }

        if (depth == N) {
            int home = N + 1;
            int lastCustomer = customerOrder[N - 1];
            int lastDist = Math.abs(positions[home][ROW] - positions[lastCustomer][ROW]) + Math.abs(positions[home][COL] - positions[lastCustomer][COL]);
            min = Math.min(min, totalDist + lastDist);
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                customerOrder[depth] = i;

                int current = customerOrder[depth];
                int previous = (depth == 0) ? 0 : customerOrder[depth - 1];

                int nextDist = Math.abs(positions[current][ROW] - positions[previous][ROW]) + Math.abs(positions[current][COL] - positions[previous][COL]);
                permutation(depth + 1, totalDist + nextDist);
                visited[i] = false;
            }
        }
    }
}