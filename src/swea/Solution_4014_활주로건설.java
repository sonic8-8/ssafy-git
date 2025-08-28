package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 건설 가능성 확인
 * 가로 방향 or 세로 방향
 * <p>
 * 높이가 다른 경우 경사로 설치 필요
 * 높이 1 길이 x
 * <p>
 * 경사로 길이가 지형보다 짧으면 설치할 수 없다
 * 경사로가 지형밖으로 벗어나면 설치할 수 없다
 * <p>
 * 활주로를 건설하는 방법 찾기
 * 한 행이나 한 열로 세야함
 */

/**
 * 메모리:
 * 시간:
 * 난이도:
 */
public class Solution_4014_활주로건설 {
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

            int airstripCount = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 1. 경사로 설치가 필요한 좌표 확인

            // 2 - 1. 경사로를 설치했을 때 범위를 벗어나는지 체크
            // 2 - 2. 경사로를 설치했을 때 길이가 지형 안에 들어오는지 체크

            // row 확인 시 하나의 row에 경사로(ramp)를 모두 설치했다면 활주로(airstrip) 개수 1개 추가
            // col 확인 시 하나의 col에 경사로(ramp)를 모두 설치했다면 활주로(airstrip) 개수 1개 추가

            // 모두 설치했는지 어떻게 확인할 것인가?
            // 한 번 순회할 때 경사로를 설치했는지 확인하면서 동시에

            // *놓친 부분
            // 1. 높이 차이가 1인지 체크
            // 2. 2 1 1 2 같은 경우에 중복으로 경사로를 설치할 수 있음

            // row 확인
            for (int row = 0; row < N; row++) {
                boolean isAirstrip = true;
                boolean[] installed = new boolean[N];

                // ->
                for (int col = 1; col < N; col++) {

                    if (map[row][col - 1] > map[row][col]) {
                        // 높이 차이가 1인지 체크 (가지치기)
                        if (map[row][col - 1] - map[row][col] != 1) {
                            isAirstrip = false;
                            break;
                        }

                        // 범위를 벗어나는지 체크 (가지치기)
                        if (col + X - 1 >= N) {
                            isAirstrip = false;
                            break;
                        }

                        // 길이가 지형 안에 들어오는지 체크 (가지치기)
                        int currentHeight = map[row][col];
                        for (int i = 0; i < X; i++) {
                            if (map[row][col + i] != currentHeight) {
                                isAirstrip = false;
                                break;
                            }
                        }

                        // 경사로 설치하기
                        for (int i = 0; i < X; i++) {
                            // 이미 설치돼있을 경우 가지치기
                            if (installed[col + i]) {
                                isAirstrip = false;
                                break;
                            }
                            installed[col + i] = true;
                        }
                    }
                }

                // <-
                for (int col = N - 2; col >= 0; col--) {
                    if (map[row][col] < map[row][col + 1]) {
                        // 높이 차이가 1인지 체크 (가지치기)
                        if (map[row][col + 1] - map[row][col] != 1) {
                            isAirstrip = false;
                            break;
                        }

                        // 범위를 벗어나는지 체크 (가지치기)
                        if (col - X + 1 < 0) {
                            isAirstrip = false;
                            break;
                        }

                        // 길이가 지형 안에 들어오는지 체크 (가지치기)
                        int currentHeight = map[row][col];
                        for (int i = 0; i < X; i++) {
                            if (map[row][col - i] != currentHeight) {
                                isAirstrip = false;
                                break;
                            }
                        }

                        // 경사로 설치하기
                        for (int i = 0; i < X; i++) {
                            // 이미 설치돼있을 경우 가지치기
                            if (installed[col - i]) {
                                isAirstrip = false;
                                break;
                            }
                            installed[col - i] = true;
                        }
                    }
                }

                if (isAirstrip) {
                    airstripCount++;
                }
            }

            // col 확인
            for (int col = 0; col < N; col++) {
                boolean isAirstrip = true;
                boolean[] installed = new boolean[N];

                // 아래
                for (int row = 1; row < N; row++) {
                    if (map[row - 1][col] > map[row][col]) {
                        // 높이 차이가 1인지 체크 (가지치기)
                        if (map[row - 1][col] - map[row][col] != 1) {
                            isAirstrip = false;
                            break;
                        }

                        // 범위를 벗어나는지 체크 (가지치기)
                        if (row + X - 1 >= N) {
                            isAirstrip = false;
                            break;
                        }

                        // 길이가 지형 안에 들어오는지 체크 (가지치기)
                        int currentHeight = map[row][col];
                        for (int i = 0; i < X; i++) {
                            if (map[row + i][col] != currentHeight) {
                                isAirstrip = false;
                                break;
                            }
                        }

                        // 경사로 설치하기
                        for (int i = 0; i < X; i++) {
                            // 이미 설치돼있을 경우 가지치기
                            if (installed[row + i]) {
                                isAirstrip = false;
                                break;
                            }
                            installed[row + i] = true;
                        }
                    }
                }

                // 위
                for (int row = N - 2; row >= 0; row--) {
                    if (map[row][col] < map[row + 1][col]) {
                        // 높이 차이가 1인지 체크 (가지치기)
                        if (map[row + 1][col] - map[row][col] != 1) {
                            isAirstrip = false;
                            break;
                        }

                        // 범위를 벗어나는지 체크 (가지치기)
                        if (row - X + 1 < 0) {
                            isAirstrip = false;
                            break;
                        }

                        // 길이가 지형 안에 들어오는지 체크 (가지치기)
                        int currentHeight = map[row][col];
                        for (int i = 0; i < X; i++) {
                            if (map[row - i][col] != currentHeight) {
                                isAirstrip = false;
                                break;
                            }
                        }

                        // 경사로 설치하기
                        for (int i = 0; i < X; i++) {
                            // 이미 설치돼있을 경우 가지치기
                            if (installed[row - i]) {
                                isAirstrip = false;
                                break;
                            }
                            installed[row - i] = true;
                        }
                    }
                }

                if (isAirstrip) {
                    airstripCount++;
                }
            }

            sb.append('#').append(t).append(' ').append(airstripCount).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}