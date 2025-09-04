package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 슬라이딩 윈도우 + 부분 집합 + DP
 */

/**
 * 메모리: 27,264 KB
 * 시간: 97 ms
 * 난이도: 모의 SW 역량테스트
 */
public class Solution_2115_벌꿀채취 {
    static int T;
    static int N, M, C;

    static int[][] honeyBoxes;
    static int[][] dp;

    static int maxProfit;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 배열
            M = Integer.parseInt(st.nextToken()); // 벌통 개수
            C = Integer.parseInt(st.nextToken()); // 꿀의 최대양

            honeyBoxes = new int[N][N];
            dp = new int[N][N - M + 1];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    honeyBoxes[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 각 행마다 M개의 연속 구간 최대 이익을 계산합니다.
            for (int row = 0; row < N; row++) {
                for (int col = 0; col <= N - M; col++) {
                    // 현재 구간의 배열을 추출합니다. (M개의 연속 구간)
                    int[] subArr = new int[M];
                    for (int i = 0; i < M; i++) {
                        subArr[i] = honeyBoxes[row][col + i];
                    }

                    // 부분 집합으로 현재 구간에서 얻을 수 있는 조합 중 최대값을 찾습니다.
                    maxProfit = Integer.MIN_VALUE;
                    subset(0, subArr, 0, 0);
                    dp[row][col] = maxProfit;
                }
            }

            // 두 일꾼이 선택할 수 있는 모든 조합 중 최댓값을 찾습니다.
            // N이 M의 2배 이상이라면 같은 행에서 두 일꾼이 모두 벌통을 선택할 수 있습니다.
            int max = Integer.MIN_VALUE;
            for (int r1 = 0; r1 < N; r1++) {
                for (int c1 = 0; c1 <= N - M; c1++) {
                    for (int r2 = r1; r2 < N; r2++) {
                        int cStart = (r1 == r2) ? c1 + M : 0;
                        for (int c2 = cStart; c2 <= N - M; c2++) {
                            max = Math.max(max, dp[r1][c1] + dp[r2][c2]);
                        }
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    // 최댓값을 편하게 추적하기 위해 현재 탐색중인 구간을 잘라서 매개변수로 넣어줬습니다.
    // sum: 벌통 숫자 합, profit: 벌통 숫자 제곱끼리 합
    private static void subset(int index, int[] arr, int sum, int profit) {
        if (index == arr.length) {
            maxProfit = Math.max(maxProfit, profit);
            return;
        }

        // 현재 인덱스 포함
        if (sum + arr[index] <= C) {
            subset(index + 1, arr, sum + arr[index], profit + (arr[index] * arr[index]));
        }

        // 현재 인덱스 포함 X
        subset(index + 1, arr, sum, profit);
    }
}