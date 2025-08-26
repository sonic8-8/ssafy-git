package swea;

import java.io.*;
import java.util.*;

public class Solution_1247_최적경로 {
    static int T;
    static int N;

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            StringTokenizer st = new StringTokenizer(br.readLine());

            int companyRow = Integer.parseInt(st.nextToken());
            int companyCol = Integer.parseInt(st.nextToken());

            int homeRow = Integer.parseInt(st.nextToken());
            int homeCol = Integer.parseInt(st.nextToken());

            int[][] customers = new int[N][2];
            for (int i = 0; i < N; i++) {
                customers[i][0] = Integer.parseInt(st.nextToken());
                customers[i][1] = Integer.parseInt(st.nextToken());
            }

            // 회사에서 출발해 고객 좌표를 모두 방문하고 집에 돌아가는 경로 중
            // 이동거리가 가장 짧은 경로를 찾기
            // 최단 경로 찾는 문제인데 가중값이 존재함
            // -> 크루스칼로 풀면 되나?

            min = Integer.MAX_VALUE;
            subset(0, 0);

            sb.append('#').append(t).append(' ').append(min).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void subset(int index, int prevNodeIndex, int totalLength) {
        if (index == N) {
            min = Math.min(min, totalLength);
            return;
        }

        for (int i = 0; i < N; i++) {

            int length = Math.abs()
            subset(index + 1, i,totalLength + );
        }
    }
}