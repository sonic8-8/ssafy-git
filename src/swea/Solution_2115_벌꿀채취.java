package swea;

import java.io.*;
import java.util.*;

public class Solution_2115_벌꿀채취 {
    static int T;
    static int N, M, C;

    static int[][] board;
    static int[][] dp;

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

            // 두 명의 일꾼이 벌꿀을 채취한다
            // 벌통의 합이 C보다 크면 벌통 개수를 줄여야한다

            board = new int[N][N];
            dp = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    // M개의 벌통을 여기서 먼저 계산?
                    // M개의 벌통 더하고 C를 초과할 경우 가장 작은 숫자 빼보기?
                }
            }

            // DP로 푸는건가?
            // 슬라이딩 윈도우로 계산하기

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {

                }
            }

            sb.append("#").append(t).append(" ").append().append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}
