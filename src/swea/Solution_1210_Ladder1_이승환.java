package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 아래에서 위로 탐색합니다.
 * 도착 지점에서 역으로 시작점을 탐색해 시간을 단축합니다.
 */

/**
 * 메모리: 37472 KB
 * 시간: 137 ms
 * 난이도: D4
 */
public class Solution_1210_Ladder1_이승환 {
    static final int T = 10;
    static int[][] board;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        for (int t = 1; t <= T; t++) {
            br.readLine();
            board = new int[100][100];

            for (int i = 0; i < 100; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 100; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int row = 99;
            int col = 0;

            for (int i = 0; i < 100; i++) {
                if (board[row][i] == 2) {
                    col = i;
                }
            }


            while (row > 0) {
                // left
                if (0 < col && board[row][col - 1] == 1) {
                    while (0 < col && board[row][col - 1] == 1) {
                        col--;
                    }
                    row--;
                    continue;
                }

                // right
                if (col < 99 && board[row][col + 1] == 1) {
                    while (col < 99 && board[row][col + 1] == 1) {
                        col++;
                    }
                    row--;
                    continue;
                }

                // up
                row--;
            }

            sb.append("#").append(t).append(" ").append(col).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}