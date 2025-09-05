package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * DFS를 이용해 문제를 해결합니다.
 * 매개변수로 공사 여부와 현재까지 지나온 거리를 넣어 관리합니다.
 */

/**
 * 메모리: 27,680 KB
 * 시간: 94 ms
 * 난이도: 모의 SW 역량테스트 50.05%
 */
public class Solution_1949_등산로조성_과제 {
    static int T;
    static int N, K;

    static int[][] board;
    static boolean[][] visited;

    static int maxHeight;
    static int maxLength;

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            board = new int[N][N];
            visited = new boolean[N][N];

            maxHeight = 0;
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());

                    maxHeight = Math.max(maxHeight, board[i][j]); // 가장 높은 지형 찾기
                }
            }

            maxLength = Integer.MIN_VALUE;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == maxHeight) { // 가장 높은 지형일 경우
                        dfs(i, j, false, 1);
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(maxLength).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int row, int col, boolean isCut, int length) {
        maxLength = Math.max(maxLength, length); // 경로 이동마다 최장거리를 갱신합니다.
        visited[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int nextRow = row + DIR_ROW[i];
            int nextCol = col + DIR_COL[i];

            if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                continue;
            }

            if (visited[nextRow][nextCol]) {
                continue;
            }

            if (board[row][col] > board[nextRow][nextCol]) {
                dfs(nextRow, nextCol, isCut, length + 1);
            }

            // 공사를 할 수 있는 경우
            if (board[row][col] <= board[nextRow][nextCol] && !isCut) {
                for (int k = 1; k <= K; k++) {
                    // 공사 후 이동이 가능하다면
                    if (board[row][col] > board[nextRow][nextCol] - k) {
                        board[nextRow][nextCol] -= k;
                        dfs(nextRow, nextCol, true, length + 1);
                        board[nextRow][nextCol] += k;
                    }
                }
            }
        }

        visited[row][col] = false;
    }
}