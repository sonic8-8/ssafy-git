package swea;

import java.io.*;
import java.util.*;

public class Solution {
    static int N;
    static int[][] board;
    static int[][][] wormholes; // [번호][0 or 1][row/col]
    static int[] dirRow = {-1, 0, 1, 0}; // 상, 우, 하, 좌
    static int[] dirCol = {0, 1, 0, -1};

    // reflect[블록번호][진입방향] = 새로운 방향
    static int[][] reflect = {
            {}, // 0 dummy
            {2, 3, 1, 0}, // 블록 1
            {1, 3, 0, 2}, // 블록 2
            {3, 2, 0, 1}, // 블록 3
            {2, 0, 3, 1}, // 블록 4
            {2, 3, 0, 1}  // 블록 5 (벽 포함)
    };

    // 0, 1, 2, 3
    // 북, 동, 남, 서

    static final int ROW = 0;
    static final int COL = 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];
            wormholes = new int[11][2][2]; // 6~10 번호, 2개 위치, [row,col]

            for (int num = 6; num <= 10; num++) {
                for (int i = 0; i < 2; i++) {
                    wormholes[num][i][0] = -1; // 초기화
                    wormholes[num][i][1] = -1;
                }
            }

            for (int row = 0; row < N; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < N; col++) {
                    board[row][col] = Integer.parseInt(st.nextToken());
                    if (board[row][col] >= 6) {
                        int num = board[row][col];
                        if (wormholes[num][0][ROW] == -1) {
                            wormholes[num][0][ROW] = row;
                            wormholes[num][0][COL] = col;
                        } else {
                            wormholes[num][1][COL] = row;
                            wormholes[num][1][ROW] = col;
                        }
                    }
                }
            }

            int maxScore = 0;
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (board[row][col] == 0) { // 빈 칸에서만 시작
                        for (int direction = 0; direction < 4; direction++) {
                            int score = simulate(row, col, direction);
                            maxScore = Math.max(maxScore, score);
                        }
                    }
                }
            }

            sb.append('#').append(t).append(' ').append(maxScore).append('\n');
        }
        System.out.print(sb);
    }

    static int simulate(int startRow, int startCol, int direction) {
        int row = startRow;
        int col = startCol;
        int score = 0;

        while (true) {
            row += dirRow[direction];
            col += dirCol[direction];

            // 경계 밖 -> 벽 처리
            if (row < 0 || row >= N || col < 0 || col >= N) {
                direction = reflect[5][direction]; // 블록 5처럼
                score++;
                continue;
            }

            // 종료 조건
            if ((row == startRow && col == startCol) || board[row][col] == -1) {
                return score;
            }

            int cell = board[row][col];

            if (cell == 0) {
                continue;
            }
            if (1 <= cell && cell <= 5) {
                direction = reflect[cell][direction];
                score++;
                continue;
            }
            if (cell >= 6) {
                // 웜홀 이동
                int[] wormhole1 = wormholes[cell][0];
                int[] wormhole2 = wormholes[cell][1];
                if (row == wormhole1[ROW] && col == wormhole1[COL]) {
                    row = wormhole2[ROW];
                    col = wormhole2[COL];
                } else {
                    row = wormhole1[ROW];
                    col = wormhole1[COL];
                }
            }
        }
    }
}