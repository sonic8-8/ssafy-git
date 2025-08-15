package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * DFS 기반 백트래킹으로 12개월 동안의 모든 이용권 선택 경우를 확인합니다.
 * 이용권은 4가지 (1일권, 1달권, 3달권, 1년권)이며
 * 각 달에 어떤 이용권을 선택할지 재귀 호출을 통해 구현합니다.
 * month가 12를 넘어가면 최소 요금을 업데이트합니다.
 *
 * 현재까지 합한 비용이 최소 비용보다 커지면 가지치기로 탐색을 중단합니다.
 */

/**
 * 메모리: 27520 KB
 * 시간: 94 ms
 * 난이도: 모의 SW 역량테스트 정답률 69.37%
 */
public class Solution_1952_수영장 {
    static int T;
    static int[] passFees;
    static int[] monthlyPlans;

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
            passFees = new int[4];
            monthlyPlans = new int[12];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++) {
                passFees[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 12; i++) {
                monthlyPlans[i] = Integer.parseInt(st.nextToken());
            }

            min = Integer.MAX_VALUE;
            dfs(0, 0);

            sb.append("#").append(t).append(" ").append(min).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int month, int costSum) {
        if (min <= costSum) {
            return;
        }

        if (12 <= month) {
            min = Math.min(min, costSum);
            return;
        }

        dfs(month + 1, costSum + passFees[DAY] * monthlyPlans[month]);

        dfs(month + 1, costSum + passFees[MONTH]);

        dfs(month + 3, costSum + passFees[QUARTER]);

        dfs(12, costSum + passFees[YEAR]);
    }
}