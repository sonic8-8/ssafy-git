package swea;

import java.io.*;
import java.util.*;

public class Solution_5644_무선충전 {
    static int T;
    static int M, A;

    static int[] pathA;
    static int[] pathB;
    static int[][] chargerInfos;

    static final int COL = 0;
    static final int ROW = 1;
    static final int RANGE = 2;
    static final int PERFORMANCE = 3;

    static final int[] DIR_ROW = {0, -1, 0, 1, 0};
    static final int[] DIR_COL = {0, 0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            pathA = new int[M];
            pathB = new int[M];
            chargerInfos = new int[A][4];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathA[i] = Integer.parseInt(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathB[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                chargerInfos[i][COL] = Integer.parseInt(st.nextToken());
                chargerInfos[i][ROW] = Integer.parseInt(st.nextToken());
                chargerInfos[i][RANGE] = Integer.parseInt(st.nextToken());
                chargerInfos[i][PERFORMANCE] = Integer.parseInt(st.nextToken());
            }

            int totalCharges = simulate();
            sb.append('#').append(t).append(' ').append(totalCharges).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static int simulate() {
        int total = 0;

        int rowA = 1, colA = 1;
        int rowB = 10, colB = 10;

        // 시작 위치 충전 반영을 위해
        for (int index = 0; index <= M; index++) {
            if (index > 0) {
                rowA += DIR_ROW[pathA[index - 1]];
                colA += DIR_COL[pathA[index - 1]];
                rowB += DIR_ROW[pathB[index - 1]];
                colB += DIR_COL[pathB[index - 1]];
            }

            int maxChargeAmount = 0;
            for (int i = 0; i < A; i++) {
                for (int j = 0; j < A; j++) {
                    int chargeAmount = 0;
                    boolean aIn = isInRange(rowA, colA, chargerInfos[i]);
                    boolean bIn = isInRange(rowB, colB, chargerInfos[j]);

                    if (aIn && bIn) {
                        if (i == j) {
                            chargeAmount += chargerInfos[i][PERFORMANCE];
                        }
                        if (i != j) {
                            chargeAmount += chargerInfos[i][PERFORMANCE] + chargerInfos[j][PERFORMANCE];
                        }
                    }
                    else {
                        if (aIn) {
                            chargeAmount += chargerInfos[i][PERFORMANCE];
                        }
                        if (bIn) {
                            chargeAmount += chargerInfos[j][PERFORMANCE];
                        }
                    }
                    maxChargeAmount = Math.max(maxChargeAmount, chargeAmount);
                }
            }
            total += maxChargeAmount;
        }
        return total;
    }

    private static boolean isInRange(int currentRow, int currentCol, int[] chargerInfo) {
        int chargerCol = chargerInfo[COL];
        int chargerRow = chargerInfo[ROW];
        int range = chargerInfo[RANGE];

        int distance = Math.abs(currentRow - chargerRow) + Math.abs(currentCol - chargerCol);
        return distance <= range;
    }
}