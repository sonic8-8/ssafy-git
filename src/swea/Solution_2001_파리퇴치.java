package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * N x N 배열을 M x M 배열로 순회한다.
 * -> 풀이는 2단계로 나눈다.
 * -> 1. M x M 배열의 첫 번째 인덱스로 순회하기
 * -> 2. 첫 번째 인덱스를 매개변수로 받아 M x M 배열의 파리 숫자 세기
 * -> 3. 2번으로 구한 파리 숫자로 Math.max()로 최대 파리 숫자를 계산한다.
 */
public class Solution_2001_파리퇴치 {
    static int T;
    static int N, M;
    static int[][] board;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            board = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 1. (i, j)가 배열의 첫 번째 인덱스다.
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < N - M + 1; i++) {
                for (int j = 0; j < N - M + 1; j++) {
                    // 2. calculateFlyCount(첫 번째 인덱스)로 파리 숫자를 센다.
                    int flyCount = calculateFlyCount(i, j);

                    // 3. 최대 파리 숫자를 계산한다.
                    max = Math.max(max, flyCount);
                }
            }

            sb.append("#").append(t).append(" ").append(max).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
    private static int calculateFlyCount(int row, int col) {
        int count = 0;

        for (int i = row; i < row + M; i++) {
            for (int j = col; j < col + M; j++) {
                count += board[i][j];
            }
        }

        return count;
    }
}
