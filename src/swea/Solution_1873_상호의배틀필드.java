package swea;

import java.io.*;
import java.util.*;

public class Solution_1873_상호의배틀필드 {
    static int T;
    static int H, W;

    static char[][] map;

    static int N;
    static char[] inputs;

    static final char FLAT = '.';
    static final char BRICK = '*';
    static final char STEEL = '#';
    static final char WATER = '-';

    static final int ROW = 0;
    static final int COL = 1;
    static final int DIR = 2;

    static final int UP = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final char[] TANK_DIR = {'^', '>', 'v', '<'};

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];

            // 현재 전차의 정보를 담을 배열
            // ROW, COL, DIR
            int[] current = new int[3];

            // 지도를 초기화합니다
            for (int row = 0; row < H; row++) {
                String line = br.readLine();
                for (int col = 0; col < W; col++) {
                    map[row][col] = line.charAt(col);

                    // 지도에서 전차를 찾으면 row, col, dir을 업데이트합니다.
                    for (int dir = 0; dir < 4; dir++) {
                        if (map[row][col] == TANK_DIR[dir]) {
                            current[ROW] = row;
                            current[COL] = col;
                            current[DIR] = dir;
                        }
                    }
                }
            }

            N = Integer.parseInt(br.readLine());
            inputs = new char[N];

            String line = br.readLine();
            for (int i = 0; i < N; i++) {
                inputs[i] = line.charAt(i);
            }

            for (char input : inputs) {
                // 포탄 발사
                if (input == 'S') {
                    shootShell(current[ROW], current[COL], current[DIR]);
                    continue;
                }
                // 전차의 방향 업데이트
                if (input == 'U') {
                    current[DIR] = UP;
                }
                if (input == 'D') {
                    current[DIR] = DOWN;
                }
                if (input == 'R') {
                    current[DIR] = RIGHT;
                }
                if (input == 'L') {
                    current[DIR] = LEFT;
                }

                // 지도에 전차 표시 (업데이트 한 전차 방향 반영)
                map[current[ROW]][current[COL]] = TANK_DIR[current[DIR]];

                // 이동 좌표 계산
                int nextRow = current[ROW] + DIR_ROW[current[DIR]];
                int nextCol = current[COL] + DIR_COL[current[DIR]];

                // 위치 이동
                if (isIn(nextRow, nextCol) && map[nextRow][nextCol] == FLAT) {
                    // 기존 위치를 평지로 만들기
                    map[current[ROW]][current[COL]] = FLAT;
                    // current[ROW], current[COL] 업데이트
                    current[ROW] = nextRow;
                    current[COL] = nextCol;
                    // 이동한 위치에 전차 표시
                    map[current[ROW]][current[COL]] = TANK_DIR[current[DIR]];
                }
            }

            sb.append("#").append(t).append(" ");
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static boolean isIn(int row, int col) {
        return 0 <= row && row < H && 0 <= col && col < W;
    }

    // 아래 메서드의 row, col은 포탄의 row, col 입니다.
    // dir은 전차의 방향을 의미합니다.
    private static void shootShell(int row, int col, int dir) {
        // 포탄을직선 방향으로 계속 이동시킵니다. 벽을 만났는지 확인하기 위해 한 칸씩 이동하며 확인합니다.
        while (true) {
            row += DIR_ROW[dir];
            col += DIR_COL[dir];

            // 지도 밖으로 나갔을 경우 가지치기합니다.
            if (!isIn(row, col)) {
                return;
            }

            // 포탄이 벽돌 벽에 맞았을 경우 벽을 평지로 만들고, 포탄을 멈춥니다
            if (map[row][col] == BRICK) {
                map[row][col] = FLAT;
                return;
            }

            // 포탄이 철벽에 맞았을 경우, 포탄을 멈춥니다.
            if (map[row][col] == STEEL) {
                return;
            }
        }
    }
}