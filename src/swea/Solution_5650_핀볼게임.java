package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 방향을 변수로 만들고, 방향에 맞게 한 칸씩 이동합니다.
 * 블록을 만났을 경우엔 방향을 업데이트 해줍니다.
 */

/**
 * 메모리: 49924 KB
 * 시간: 697 ms
 * 난이도: 모의 SW 역량 테스트 (정답률 32.51%)
 */
public class Solution_5650_핀볼게임 {
    static int N;
    static int[][] board;
    static int[][][] wormholes; // [번호][0 or 1][row/col]

    static final int[] DIR_ROW = {-1, 0, 1, 0}; // UP, RIGHT, DOWN, LEFT
    static final int[] DIR_COL = {0, 1, 0, -1};

    static final int DIRECTION_COUNT = 4;
    static final int UP = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;

    // 블록을 만났을 때, 진입한 방향에 따른 꺾이는 방향을 계산합니다.
    // REFLECT[블록번호][진입방향] = 꺾인 방향
    static final int[][] REFLECT = {
            {}, // 1-based-index를 위해 넣었습니다.
            {DOWN, LEFT, RIGHT, UP}, // 블록 1
            {RIGHT, LEFT, UP, DOWN}, // 블록 2
            {LEFT, DOWN, UP, RIGHT}, // 블록 3
            {DOWN, UP, LEFT, RIGHT}, // 블록 4
            {DOWN, LEFT, UP, RIGHT}  // 블록 5 (벽도 블록 5로 동작합니다)
    };

    static final int WORMHOLE_MIN = 6;
    static final int WORMHOLE_MAX = 10;
    static final int ENDPOINT_COUNT = 2;
    static final int ENDPOINT_0 = 0;
    static final int ENDPOINT_1 = 1;

    static final int BLOCK_MIN = 1;
    static final int BLOCK_MAX = 5;
    static final int WALL_BLOCK = 5;

    static final int UNASSIGNED = -1;
    static final int BLACK_HOLE = -1;

    static final int EMPTY_CELL = 0;

    static final int ROW = 0;
    static final int COL = 1;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine().trim());
            board = new int[N][N];
            // 6 ~ 10 웜홀 번호 / 2개 위치 (엔드 포인트) / row, col
            wormholes = new int[WORMHOLE_MAX + 1][ENDPOINT_COUNT][2];

            // 웜홀을 초기화합니다.
            for (int index = WORMHOLE_MIN; index <= WORMHOLE_MAX; index++) {
                for (int endpoint = 0; endpoint < 2; endpoint++) {
                    wormholes[index][endpoint][ROW] = UNASSIGNED;
                    wormholes[index][endpoint][COL] = UNASSIGNED;
                }
            }

            // 입력을 받아 게임판을 초기화합니다.
            for (int row = 0; row < N; row++) {
                StringTokenizer st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < N; col++) {
                    board[row][col] = Integer.parseInt(st.nextToken());
                    if (board[row][col] >= WORMHOLE_MIN) {
                        int index = board[row][col];
                        if (wormholes[index][ENDPOINT_0][ROW] == UNASSIGNED) {
                            wormholes[index][ENDPOINT_0][ROW] = row;
                            wormholes[index][ENDPOINT_0][COL] = col;
                            continue;
                        }
                        if (wormholes[index][ENDPOINT_0][ROW] != UNASSIGNED) {
                            wormholes[index][ENDPOINT_1][ROW] = row;
                            wormholes[index][ENDPOINT_1][COL] = col;
                        }
                    }
                }
            }

            // 게임판의 모든 셀을 순회하며, 빈 공간을 찾습니다.
            // 빈 공간은 시작점이며,
            // 시작점에서 모든 방향(4개)으로 핀볼을 출발시켜보고 각 점수를 계산합니다.
            // Math.max()로 최대 점수를 계산합니다.
            int maxScore = 0;
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (board[row][col] == EMPTY_CELL) {
                        for (int direction = 0; direction < DIRECTION_COUNT; direction++) {
                            int score = simulate(row, col, direction);
                            maxScore = Math.max(maxScore, score);
                        }
                    }
                }
            }

            sb.append("#").append(t).append(" ").append(maxScore).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    static int simulate(int startRow, int startCol, int direction) {
        int row = startRow;
        int col = startCol;
        int score = 0;

        while (true) {
            // 매개변수로 들어온 방향으로 한 칸씩 이동시켜봅니다.
            row += DIR_ROW[direction];
            col += DIR_COL[direction];

            // 경계 밖으로 넘어갔는지 체크합니다. (경계 밖은 벽으로 취급함)
            if (row < 0 || row >= N || col < 0 || col >= N) {
                direction = REFLECT[WALL_BLOCK][direction]; // 벽은 블록 5처럼 동작합니다.
                score++;
                continue;
            }

            // 종료 조건인지 체크합니다. (다시 처음 위치로 돌아가거나, 블랙홀에 빠질 경우)
            if ((row == startRow && col == startCol) || board[row][col] == BLACK_HOLE) {
                return score;
            }

            // 현재 위치에 해당하는 셀을 가져옵니다.
            int cell = board[row][col];

            // 빈 셀일 경우 다음 칸으로 이동하기 위해 continue
            if (cell == EMPTY_CELL) {
                continue;
            }
            // 블록일 경우 방향을 업데이트 해줍니다.
            if (BLOCK_MIN <= cell && cell <= BLOCK_MAX) {
                direction = REFLECT[cell][direction];
                score++;
                continue;
            }
            // 웜홀일 경우 위치를 업데이트 해줍니다.
            if (cell >= WORMHOLE_MIN) {
                int[] wormhole1 = wormholes[cell][ENDPOINT_0];
                int[] wormhole2 = wormholes[cell][ENDPOINT_1];
                if (row == wormhole1[ROW] && col == wormhole1[COL]) {
                    row = wormhole2[ROW];
                    col = wormhole2[COL];
                    continue;
                }
                if (row == wormhole2[ROW] && col == wormhole2[COL]) {
                    row = wormhole1[ROW];
                    col = wormhole1[COL];
                }
            }
        }
    }
}