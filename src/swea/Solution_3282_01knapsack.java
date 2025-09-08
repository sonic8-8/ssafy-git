package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * - DP
 * 점화식에서 값을 뒤에서부터 갱신해
 * 아이템을 한 번만 담도록 합니다.
 */

/**
 * 메모리: 27,872 KB
 * 시간: 108 ms
 * 난이도: D3
 */
public class Solution_3282_01knapsack {
    static int T;
    static int N, K;

    static int[] dp;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            dp = new int[K + 1];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                int v = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());

                for (int weight = K; weight >= v; weight--) {
                    dp[weight] = Math.max(dp[weight], dp[weight - v] + c);
                }
            }

            sb.append("#").append(t).append(" ").append(dp[K]).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}