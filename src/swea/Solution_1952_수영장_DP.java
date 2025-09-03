package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * month를 1월부터 12월까지 증가시키며 dp[i]에 최소 누적 비용을 저장합니다.
 * 1. 1일권 * 이용일 수 vs 1달권 → 이전 달까지 비용(dp[i-1])에 더해 계산
 * 2. 3개월권 → 3개월 전까지 비용(dp[i-3]) + 3개월권 비용과 비교
 * 3. 1년권 → 모든 계산이 끝난 후, dp[12]와 1년권 비용 중 작은 값을 선택
 */

/**
 * 메모리: 25,600 KB
 * 시간: 75 ms
 * 난이도: 모의 SW 역량테스트 69.46%
 */
public class Solution_1952_수영장_DP {
    static int T;

    static int[] passFees;
    static int[] monthlyCounts;
    static int[] dp;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            passFees = new int[4];
            monthlyCounts = new int[13];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++) {
                passFees[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= 12; i++) {
                monthlyCounts[i] = Integer.parseInt(st.nextToken());
            }

            dp = new int[13];
            for (int i = 1; i <= 12; i++) {
                dp[i] = dp[i - 1] + Math.min(monthlyCounts[i] * passFees[0], passFees[1]);

                if (i >= 3) {
                    dp[i] = Math.min(dp[i], dp[i - 3] + passFees[2]);
                }

                if (i % 12 == 0) {
                    dp[12] = Math.min(dp[12], passFees[3]);
                }
            }

            sb.append("#").append(t).append(" ").append(dp[12]).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}