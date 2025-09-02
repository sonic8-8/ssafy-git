package level_a;

import java.io.*;
import java.util.*;

public class Solution_1949_등산로조성_복습 {
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

            maxHeight = 0;
            maxLength = Integer.MIN_VALUE;

            board = new int[N][N];
            visited= new boolean[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, board[i][j]);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == maxHeight) {
                        dfs(i, j, 1, false);
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(maxLength).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int row, int col, int length, boolean isCut) {
        visited[row][col] = true;
        maxLength = Math.max(maxLength, length);

        for (int i = 0; i < 4; i++) {
            int nextRow = row + DIR_ROW[i];
            int nextCol = col + DIR_COL[i];

            if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                continue;
            }

            if (visited[nextRow][nextCol]) {
                continue;
            }

            if (board[nextRow][nextCol] < board[row][col]) {
                dfs(nextRow, nextCol, length + 1, isCut);
            }

            if (!isCut && board[nextRow][nextCol] >= board[row][col]) {
                for (int k = 1; k <= K; k++) {
                    if (board[nextRow][nextCol] - k < board[row][col]) {
                        board[nextRow][nextCol] -= k;
                        dfs(nextRow, nextCol, length + 1, true);
                        board[nextRow][nextCol] += k;
                    }
                }
            }
        }

        visited[row][col] = false;
    }
}
