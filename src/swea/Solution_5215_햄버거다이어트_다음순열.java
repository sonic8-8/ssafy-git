package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * next permutation을 이용한다.
 *
 * 재료를 선택할지 말지를 0과 1로 나타냅니다
 * -> int[] selected 사용
 * -> 재료 선택 여부를 담습니다.
 * -> 예를 들어, 00011이라면 인덱스 3번과 4번의 재료를 사용한다는 의미
 * -> boolean 배열의 의미를 갖고 있지만,
 * -> nextPermutation을 편하게 구현하기 위해 int 배열로 만들었습니다.
 *
 * nextPermutation() 메서드로 재료 선택 개수에 따른 모든 경우를 살핍니다.
 * -> selected가 00011 이라면 재료를 2개 선택하는 모든 경우를 살필 수 있습니다.
 */

/**
 * 메모리: 29552 KB
 * 시간: 909 ms
 * 난이도: D3
 */
public class Solution_5215_햄버거다이어트_다음순열 {
    static int T;
    static int N, L;
    static int[][] foods;

    static final int SCORE = 0;
    static final int KCAL = 1;

    static int maxScore;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            maxScore = 0;

            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            foods = new int[N][2];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                foods[i][SCORE] = Integer.parseInt(st.nextToken());
                foods[i][KCAL] = Integer.parseInt(st.nextToken());
            }

            int[] selected = new int[N];

            for (int ingredient = 1; ingredient <= N; ingredient++) {
                for (int index = 0; index < N; index++) {
                    selected[index] = 0;
                }

                for (int index = 0; index < ingredient; index++) {
                    selected[N - 1 - index] = 1;
                }

                updateMaxScore(selected);
                while (nextPermutation(selected)) {
                    updateMaxScore(selected);
                }
            }

            sb.append("#").append(t).append(" ").append(maxScore).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static boolean nextPermutation(int[] selected) {
        int breakPoint = (N - 1) - 1;
        while (0 <= breakPoint && selected[breakPoint] >= selected[breakPoint + 1]) {
            breakPoint--;
        }
        if (breakPoint < 0) {
            return false;
        }

        int swapIndex = N - 1;
        while (breakPoint < swapIndex && selected[breakPoint] >= selected[swapIndex]) {
            swapIndex--;
        }

        swap(selected, breakPoint, swapIndex);
        reverse(selected, breakPoint + 1, N - 1);
        return true;
    }

    private static void swap(int[] selected, int a, int b) {
        int temp = selected[a];
        selected[a] = selected[b];
        selected[b] = temp;
    }

    private static void reverse(int[] selected, int start, int end) {
        while (start < end) {
            swap(selected, start++, end--);
        }
    }

    private static void updateMaxScore(int[] selected) {
        int scoreSum = 0;
        int kcalSum = 0;

        for (int i = 0; i < N; i++) {
            if (selected[i] == 1) {
                scoreSum += foods[i][SCORE];
                kcalSum += foods[i][KCAL];
            }
        }

        if (kcalSum <= L) {
            maxScore = Math.max(maxScore, scoreSum);
        }
    }
}

