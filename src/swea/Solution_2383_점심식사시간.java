package swea;

import java.io.*;
import java.util.*;

public class Solution_2383_점심식사시간 {
    static int T;
    static int N;
    static int[][] board;

    static List<int[]> stairs;
    static List<int[]> people;
    static int[] select;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] > 1) {
                        stairs.add(new int[]{i, j, board[i][j]});
                        continue;
                    }
                    if (board[i][j] == 1) {
                        people.add(new int[]{i, j});
                    }
                }
            }

            select = new int[people.size()];

            // 계단 입구까지 이동
            // 계단 내려가는 시간
            // 최대 3명까지 올라가기 가능
            subset(0);

            sb.append("#").append(t).append(" ").append().append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void subset(int index) {
        if (index == people.size()) {
            // 시뮬레이션
            simulate();
            return;
        }

        // 1번 계단 선택
        select[index] = 0;
        subset(index + 1);


        // 2번 계단 선택
        select[index] = 1;
        subset(index + 1);
    }

    private static void simulate() {
        // 계단 내려가는 시간 + 거리 = 종료시간
        // 종료시간으로 3명 제약 해결하기
    }
}