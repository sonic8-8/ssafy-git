package level_a;

import java.io.*;
import java.util.*;

public class Solution_1249_보급로 {
    static int T;
    static int N;

    static int[][] board;
    static int[][] times;

    static int[] DIR_ROW = {-1, 0, 1, 0};
    static int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            board = new int[N][N];
            times = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                     board[i][j] = line.charAt(j) - '0';
                     times[i][j] = Integer.MAX_VALUE;
                }
            }

            dijkstra();

            sb.append("#").append(t).append(" ").append(times[N - 1][N - 1]).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dijkstra() {
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.add(new int[]{0, 0, 0});
        times[0][0] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int cost = current[2];

            // 이미 한 번 계산한 times가 이번 계산 결과보다 작다면 건너뛰기
            if (times[row][col] < cost) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = row + DIR_ROW[i];
                int nextCol = col + DIR_COL[i];

                if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                    continue;
                }

                int nextCost = board[row][col] + cost;

                if (nextCost < times[nextRow][nextCol]) {
                    times[nextRow][nextCol] = nextCost;
                    pq.add(new int[]{nextRow, nextCol, nextCost});
                }
            }

        }
    }
}
