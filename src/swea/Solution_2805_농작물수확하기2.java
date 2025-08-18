package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * for문으로 2차 배열을 탐색할 때
 * center - row가  col의 시작 인덱스와 같다는 것을 이용합니다.
 * 직접 나열해보면 관찰할 수 있음
 */

/**
 * 메모리: 26880 KB
 * 시간: 86 ms
 * 난이도: D3
 */
public class Solution_2805_농작물수확하기2 {
    static int T;
    static int N;

    static int[][] map;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = line.charAt(j) - '0';
                }
            }

            int sum = 0;
            int center = N / 2;

            for (int row = 0; row < N; row++) {
                int gap = Math.abs(center - row);
                for (int col = gap; col < N - gap; col++) {
                    sum += map[row][col];
                }
            }

            sb.append("#").append(t).append(" ").append(sum).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}