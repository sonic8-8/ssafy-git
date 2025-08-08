package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 백트래킹으로 부분집합을 구현합니다.
 *
 * -> 칼로리 합이 L을 넘으면 가지치기로 종료합니다.
 *
 * -> 재귀 호출 한 번당 "현재 자료를 선택할지 말지" 결정합니다.
 * -> 즉, 1개의 재귀 함수에서 2가지 경우를 모두 호출한다는 것입니다.
 * -> 1. 현재 재료 포함하는 경우
 * -> 2. 현재 재료 미포함하는 경우
 * */

/**
 * 메모리: 26880 KB
 * 시간: 137 ms
 * 난이도: D3
 */
public class Solution_5215_햄버거다이어트_부분집합 {
    static int T;
    static int N, L;
    static int[][] foods;
    static int max;

    static final int SCORE = 0;
    static final int KCAL = 1;

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
            subset(0, 0, 0);

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void subset(int index, int scoreSum, int kcalSum) {
        if (kcalSum > L) {
            return;
        }

        if (index == N) {
            max = Math.max(max, scoreSum);
            return;
        }

        // 현재 재료 포함
        subset(index + 1, scoreSum + foods[index][SCORE], kcalSum + foods[index][KCAL]);

        // 현재 재료 미포함
        subset(index + 1, scoreSum, kcalSum);
    }
}