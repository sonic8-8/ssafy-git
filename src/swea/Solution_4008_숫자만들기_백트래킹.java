package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 백트래킹을 이용해 연산자로 만들 수 있는 모든 경우의 수를 살핍니다.
 */

/**
 * 메모리: 27272 KB
 * 시간: 93 ms
 * 난이도: 모의 SW 역량테스트 정답률 66.24%
 */
public class Solution_4008_숫자만들기_백트래킹 {
    static int T;
    static int N;
    static int[] operatorCounts;
    static int[] numbers;

    static int min;
    static int max;
    static int difference;

    static final int PLUS = 0;
    static final int MINUS = 1;
    static final int PRODUCT = 2;
    static final int DIVIDE = 3;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;

            numbers = new int[N];
            operatorCounts = new int[4];

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++) {
                operatorCounts[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            backtrack(1, numbers[0]);
            difference = max - min;

            sb.append("#").append(t).append(" ").append(difference).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void backtrack(int index, int total) {
        if (index == N) {
            min = Math.min(min, total);
            max = Math.max(max, total);
            return;
        }

        for (int i = 0; i < 4; i++) {
            if (operatorCounts[i] > 0) {
                operatorCounts[i]--;

                if (i == PLUS) {
                    backtrack(index + 1, total + numbers[index]);
                }
                if (i == MINUS) {
                    backtrack(index + 1, total - numbers[index]);
                }
                if (i == PRODUCT) {
                    backtrack(index + 1, total * numbers[index]);
                }
                if (i == DIVIDE) {
                    backtrack(index + 1, total / numbers[index]);
                }

                operatorCounts[i]++;
            }
        }
    }
}