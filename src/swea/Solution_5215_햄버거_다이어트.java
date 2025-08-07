package swea;

import java.io.*;
import java.util.*;

public class Solution_5215_햄버거_다이어트 {
    static int T;
    static int N, L;
    static int[][] foods;

    static final int SCORE = 0;
    static final int KCAL = 1;

    static int max;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            foods = new int[N][2];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());

                foods[i][SCORE] = Integer.parseInt(st.nextToken());
                foods[i][KCAL] = Integer.parseInt(st.nextToken());
            }

            max = 0;
            backtrack(0, 0, 0);

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void backtrack(int start, int kcalSum, int scoreSum) {
        if (kcalSum > L) {
            return;
        }

        max = Math.max(max, scoreSum);

        for (int i = start; i < N; i++) {
            int nextKcalSum = kcalSum + foods[i][KCAL];
            int nextScoreSum = scoreSum + foods[i][SCORE];

            backtrack(i + 1, nextKcalSum, nextScoreSum);
        }
    }
}