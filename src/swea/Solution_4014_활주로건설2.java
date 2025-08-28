package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 시뮬레이션 구현 후 중복 제거
 * row 1줄, col 1줄을 각각 배열로 만들어서 넘기기
 *
 * 높이 1 길이 x
 * 경사로 길이가 시작 기준으로 X보다 짧으면 설치할 수 없다
 * 경사로 길이가 범위를 벗어나면 설치할 수 없다
 */

/**
 * 메모리: 28032 KB
 * 시간: 98 ms
 * 난이도: 모의 SW 역량테스트 61.32%
 */
public class Solution_4014_활주로건설2 {
    static int T;
    static int N, X;

    static int[][] map;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            X = Integer.parseInt(st.nextToken());

            map = new int[N][N];

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            int airstripCount = 0;

            for (int row = 0; row < N; row++) {
                int[] line = map[row];
                if (checkLine(line)) {
                    airstripCount++;
                }
            }

            for (int col = 0; col < N; col++) {
                int[] line = new int[N];

                for (int row = 0; row < N; row++) {
                    line[row] = map[row][col];
                }
                if (checkLine(line)) {
                    airstripCount++;
                }
            }

            sb.append('#').append(t).append(' ').append(airstripCount).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static boolean checkLine(int[] line) {
        boolean[] installed = new boolean[N];

        for (int i = 0; i < N - 1; i++) {
            int diff = line[i] - line[i + 1];

            if (Math.abs(diff) > 1) {
                return false;
            }

            // 정방향
            if (diff == 1) {
                for (int j = i + 1; j <= i + X; j++) {
                    // 범위 확인 / 길이가 충분히 넓은지 확인 / 이미 설치돼있는지 확인
                    if (N <= j || line[j] != line[i + 1] || installed[j]) {
                        return false;
                    }
                    installed[j] = true;
                }
                continue;
            }

            // 역방향
            if (diff == -1) {
                for (int j = i; j > i - X; j--) {
                    // 범위 확인 / 길이가 충분히 넓은지 확인 / 이미 설치돼있는지 확인
                    if (j < 0 || line[j] != line[i] || installed[j]) {
                        return false;
                    }
                    installed[j] = true;
                }
            }
        }
        return true;
    }
}