package swea;

import java.io.*;
import java.util.*;

public class Solution_1873_상호의배틀필드2 {
    static int T;
    static int H, W;

    static char[][] map;

    static int N;
    static char[] inputs;

    static final char FLAT = '.';
    static final char BRICK = '*';
    static final char STEEL = '#';
    static final char WATER = '-';

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

            // 현재 전차의 row, col, dir
            int row = -1;
            int col = -1;
            int dir = -1;

            // 지도를 초기화합니다
            for (int r = 0; r < H; r++) {
                String line = br.readLine();
                for (int c = 0; c < W; c++) {
                    map[r][c] = line.charAt(c);

                    // 지도에서 전차를 찾으면 row, col, dir을 업데이트합니다.
                    for (int d = 0; d < 4; d++) {
                        if (map[r][c] == TANK_DIR[d]) {
                            row = r;
                            col = c;
                            dir = d;
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
                    shootShell(row, col, dir);
                    continue;
                }
                // 전차의 방향 업데이트
                if (input == 'U') {
                    dir = UP;
                }
                if (input == 'D') {
                    dir = DOWN;
                }
                if (input == 'R') {
                    dir = RIGHT;
                }
                if (input == 'L') {
                    dir = LEFT;
                }

                // 지도에 전차 표시 (업데이트 한 전차 방향 반영)
                map[row][col] = TANK_DIR[dir];

                // 이동 좌표 계산
                int nextRow = row + DIR_ROW[dir];
                int nextCol = col + DIR_COL[dir];

                // 위치 이동
                if (isIn(nextRow, nextCol) && map[nextRow][nextCol] == FLAT) {
                    // 기존 위치를 평지로 만들기
                    map[row][col] = FLAT;
                    // row, col 업데이트
                    row = nextRow;
                    col = nextCol;
                    // 이동한 위치에 전차 표시
                    map[row][col] = TANK_DIR[dir];
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
