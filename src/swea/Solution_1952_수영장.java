package swea;

import java.io.*;
import java.util.*;

public class Solution_1952_수영장 {
    static int T;
    static int[] passes;
    static int[] monthlyPlans;
    static boolean[] visited;

    static final int DAY = 0;
    static final int MONTH = 1;
    static final int QUARTER = 2;
    static final int YEAR = 3;

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++) {
                passes[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 12; i++) {
                monthlyPlans[i] = Integer.parseInt(st.nextToken());
            }

            visited = new boolean[12];
            dfs(0, 0);

            sb.append("#").append(t).append(" ").append(min).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int month, int costSum) {
        if (12 <= month) {
            min = Math.min(min, costSum);
            return;
        }

        dfs(month + 1, costSum + passes[DAY] * monthlyPlans[month]);

        dfs(month + 1, costSum + passes[MONTH]);

        dfs(month + 3, costSum + passes[QUARTER]);

        dfs(12, costSum + passes[YEAR]);

    }
}