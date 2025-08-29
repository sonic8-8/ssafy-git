package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 시뮬레이션 + DFS + BFS
 * 공중에서 구슬을 떨어뜨린다. (떨어뜨릴 위치는 좌우로 조절 가능)
 * 공중에서 떨어뜨리기 때문에, 맨 위에 있는 벽돌만 깨트릴 수 있다
 * 벽돌에 써있는 숫자만큼 주변 벽돌을 연쇄적으로 깨트릴 수 있다
 * 최대한 벽돌을 많이 제거 후 남은 벽돌의 개수 구하기
 *
 * 제거 과정
 * 1. 떨어뜨릴 위치로 모든 경우의 수 만들기 (중복 순열)
 *      ex) N이 3이라면, 0 1 1 / 1 2 1 / 3 1 2 같은 경우를 만들 수 있음
 * 2. bfs로 벽돌 깨트리기 (깨진 벽돌은 0으로 만들기)
 * 3. 남은 벽돌에 중력 적용시키기
 * 4. depth가 N에 도달하거나 벽돌이 모두 깨진 경우 min 업데이트
 */

/**
 * 메모리: 96,976 KB
 * 시간: 219 ms
 * 난이도: 모의 SW 역량 테스트 66.90%
 */
public class Solution_5656_벽돌깨기 {
    static int T;
    static int N, W, H;

    static int min;

    static int[] DIR_ROW = {-1, 0, 1, 0};
    static int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            int[][] board = new int[H][W];

            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            min = Integer.MAX_VALUE;
            dfs(0, board);

            sb.append('#').append(t).append(' ').append(min).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int depth, int[][] simulationBoard) {
        // 구슬 떨어뜨리기를 N회 시뮬레이션 했다면 남아있는 구슬 수를 센다.
        if (depth == N) {
            int remainBricks = countBricksFrom(simulationBoard);
            min = Math.min(min, remainBricks);
            return;
        }

        if (countBricksFrom(simulationBoard) == 0) {
            min = 0;
            return;
        }

        // 좌우로 움직이며 구슬을 떨어뜨릴 수 있는 모든 경우를 만든다
        for (int col = 0; col < W; col++) {
            // 배열 복사
            int[][] copiedBoard = copyOf(simulationBoard);

            // 쌓여있는 벽돌 중 가장 위쪽 벽돌의 row 찾기
            int topBrickRow = getTopBrickRow(copiedBoard, col);
            if (topBrickRow == -1) {
                continue;
            }

            // 구슬 떨어뜨린 후 벽돌 파괴
            bfs(copiedBoard, topBrickRow, col);

            // 중력 적용
            adaptGravity(copiedBoard);

            // 다음 경우 시뮬레이션
            dfs(depth + 1, copiedBoard);
        }
    }

    private static int[][] copyOf(int[][] simulationBoard) {
        int[][] copiedBoard = new int[H][W];
        for (int j = 0; j < H; j++) {
            copiedBoard[j] = simulationBoard[j].clone();
        }
        return copiedBoard;
    }

    private static int countBricksFrom(int[][] simulationBoard) {
        int count = 0;
        for (int row = 0; row < H; row++) {
            for (int col = 0; col < W; col++) {
                if (simulationBoard[row][col] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    // 가장 위쪽 벽돌 찾기
    private static int getTopBrickRow(int[][] simulationBoard, int col) {
        for (int row = 0; row < H; row++) {
            if (simulationBoard[row][col] != 0) {
                return row;
            }
        }
        return -1;
    }

    // 구슬 떨어트리고 벽돌 깨트리기
    private static void bfs(int[][] simulationBoard, int r, int c) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c, simulationBoard[r][c]});
        simulationBoard[r][c] = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int value = current[2];

            for (int i = 0; i < 4; i++) {
                for (int range = 1; range < value; range++) {
                    int nextRow = row + DIR_ROW[i] * range;
                    int nextCol = col + DIR_COL[i] * range;

                    if (nextRow < 0 || H <= nextRow || nextCol < 0 || W <= nextCol) {
                        continue;
                    }

                    if (simulationBoard[nextRow][nextCol] == 0) {
                        continue;
                    }

                    if (simulationBoard[nextRow][nextCol] > 1) {
                        queue.add(new int[]{nextRow, nextCol, simulationBoard[nextRow][nextCol]});
                    }
                    simulationBoard[nextRow][nextCol] = 0;
                }
            }
        }
    }

    private static void adaptGravity(int[][] simulationBoard) {
        // 3-1. col별로 아래에서 위로 탐색하며 0이 아닌 숫자 queue에 넣기
        for (int col = 0; col < W; col++) {
            Queue<Integer> queue = new ArrayDeque<>();
            for (int row = H - 1; row >= 0; row--) {
                if (simulationBoard[row][col] != 0) {
                    queue.add(simulationBoard[row][col]);
                    simulationBoard[row][col] = 0;
                }
            }

            // 3-2. 다시 아래에서 위로 올라가며 queue에서 숫자를 빼서 넣기 (중력 적용)
            int row = H - 1;
            while (!queue.isEmpty()) {
                simulationBoard[row--][col] = queue.poll();
            }
        }
    }
}