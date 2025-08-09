package etc;

import java.io.*;
import java.util.*;

public class Question2032 {
    static int N;
    static int[][] map;
    static int[] dirRow = {-1, 0, 1, 0};
    static int[] dirCol = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        map = new int[N + 2][N + 2];

        for (int i = 1; i <= N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                int sum = 0;

                sum += map[i][j];
                for (int k = 0; k < 4; k++) {
                    sum += map[i + dirRow[k]][j + dirCol[k]];
                }

                max = Math.max(max, sum);
            }
        }

        bw.write(String.valueOf(max));

        br.close();
        bw.close();
    }
}
