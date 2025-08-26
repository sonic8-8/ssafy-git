package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 최소 클릭으로 모든 셀을 연다.
 * 빈 셀은 BFS로 인접한 빈 셀과 숫자 셀까지 한 번에 open한다.
 * 숫자 셀을 클릭하면 해당 칸만 open한다.
 *
 * 1. 각 셀을 숫자 셀인지 빈 셀인지 구분한다.
 * 2. 빈 셀을 클릭해서 BFS로 연쇄적으로 연다.
 * 3. 아직 열리지 않은 숫자 셀들을 하나씩 클릭한다.
 */

/**
 * 메모리: 45,232 KB
 * 시간: 198 ms
 * 난이도: D4
 */
public class Solution_1868_파핑파핑지뢰찾기 {
    static int T;
    static int N;
    static char[][] signBoard;

    static int[][] nearByLandMineCounts;
    static boolean[][] visited;

    static final char LAND_MINE_CELL = '*';
    static final char EMPTY_CELL = '.';

    static final int[] DIR_ROW = {-1, -1, -1, 0, 1, 1, 1, 0};
    static final int[] DIR_COL = {-1, 0, 1, 1, 1, 0, -1, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            signBoard = new char[N][N];
            nearByLandMineCounts = new int[N][N];
            visited = new boolean[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    signBoard[i][j] = line.charAt(j);
                }
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (signBoard[i][j] == LAND_MINE_CELL) {
                        nearByLandMineCounts[i][j] = -1;
                    } else {
                        nearByLandMineCounts[i][j] = countNearByLandMine(i, j);
                    }
                }
            }

            int clickCount = 0;

            // EMPTY_CELL 중 하나 클릭해서 BFS
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (signBoard[i][j] == EMPTY_CELL
                            && !visited[i][j] && nearByLandMineCounts[i][j] == 0) {
                        clickCount++;
                        openCell(i, j);
                    }
                }
            }

            // 남은 NEAR_BY_LAND_MINE_CELL 숫자 세기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (signBoard[i][j] == EMPTY_CELL
                            && !visited[i][j] && nearByLandMineCounts[i][j] > 0) {
                        clickCount++;
                    }
                }
            }

            sb.append('#').append(t).append(' ').append(clickCount).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void openCell(int row, int col) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{row, col});
        visited[row][col] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int currentRow = current[0];
            int currentCol = current[1];

            for (int i = 0; i < 8; i++) {
                int nextRow = currentRow + DIR_ROW[i];
                int nextCol = currentCol + DIR_COL[i];

                if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                    continue;
                }

                if (visited[nextRow][nextCol] || signBoard[nextRow][nextCol] == LAND_MINE_CELL) {
                    continue;
                }

                visited[nextRow][nextCol] = true;

                if (nearByLandMineCounts[nextRow][nextCol] == 0) {
                    queue.add(new int[]{nextRow, nextCol});
                }
            }
        }
    }

    private static int countNearByLandMine(int row, int col) {
        int count = 0;

        for (int i = 0; i < 8; i++) {
            int nextRow = row + DIR_ROW[i];
            int nextCol = col + DIR_COL[i];

            if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                continue;
            }

            if (signBoard[nextRow][nextCol] == LAND_MINE_CELL) {
                count++;
            }
        }

        return count;
    }
}