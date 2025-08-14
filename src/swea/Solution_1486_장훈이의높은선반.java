package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 부분 집합과 백트래킹을 이용해서
 * 쌓을 수 있는 탑의 모든 경우를 살펴봅니다.
 *
 * 백트래킹으로 불필요한 탐색의 줄입니다.
 */

/**
 * 메모리: 24448 KB
 * 시간: 179 ms
 * 난이도: D4
 */
public class Solution_1486_장훈이의높은선반 {
    static int T;
    static int N, B;
    static int[] heights;

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            B = Integer.parseInt(st.nextToken());

            heights = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                heights[i] = Integer.parseInt(st.nextToken());
            }

            min = Integer.MAX_VALUE;
            subset(0, 0);

            int diff = min - B;

            sb.append("#").append(t).append(" ").append(diff).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void subset(int index, int heightSum) {
        if (heightSum >= min) {
            return;
        }

        if (B <= heightSum) {
            min = Math.min(min, heightSum);
            return;
        }

        if (index == N) {
            return;
        }

        // 포함 O
        subset(index + 1, heightSum + heights[index]);

        // 포함 X
        subset(index + 1, heightSum);
    }
}
