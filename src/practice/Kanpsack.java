package practice;

import java.io.*;
import java.util.*;

public class Kanpsack {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = 4;
        int w = 7;

        int[] weights = {0, 6, 4, 3, 5};
        int[] values = {0, 13, 8, 6, 12};

        int[][] dp = new int[n + 1][w + 1];

        for (int i = 1; i <= n; i++) {
            for (int weight = 1; weight <= w; weight++) {
                if (weights[i] > weight) {
                    dp[i][weight] = dp[i - 1][weight];
                } else {
                    dp[i][weight] = Math.max(values[i] + dp[i - 1][weight - weights[i]], dp[i - 1][weight]);
                }
            }
        }

        bw.write(String.valueOf(dp[n][w]));

        br.close();
        bw.close();
    }
}