package swea;

import java.io.*;

/**
 * 아이디어:
 * 1. 농장은 항상 정사각형 마름모 모양 형태이다.
 * -> 항상 같은 방식으로 수익 계산하기
 *
 * 2. 위에서 아래로 살펴봤을때,
 * Column의 시작 인덱스를 나열해보면
 * [1 X 1] 0 : center
 * [2 X 2] 1 0 1 : center center-1 center
 * [3 X 3] 2 1 0 1 2 : center center-1 center-2 center-1 center
 * [4 X 4] 3 2 1 0 1 2 3 : center center-1 center-2 center-3 center-2 center-1 center
 * -> for문으로 0부터 N-1 까지 row를 돌면서 Math.abs(center - row)를 계산해주면
 *    Column의 시작인덱스와 같다.
 *
 * 위 2가지를 활용해서 넓이를 구할 수 있겠다.
 */
public class Solution_2805_농작물수확하기 {
    static int T;
    static int N;
    static int[][] farm;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            farm = new int[N][N];

            for (int row = 0; row < N; row++) {
                String line = br.readLine();
                for (int col = 0; col < N; col++) {
                    farm[row][col] = line.charAt(col) - '0';
                }
            }

            int profit = 0;
            int center = N / 2; // 0-based기 때문에 중앙 인덱스는 N / 2이다.
            for (int row = 0; row < N; row++) {
                int distanceFromCenter = Math.abs(center - row);

                int startCol = distanceFromCenter;
                int endCol = N - startCol;

                for (int col = startCol; col < endCol; col++) {
                    profit += farm[row][col];
                }
            }

            sb.append("#").append(t).append(" ").append(profit);
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}

