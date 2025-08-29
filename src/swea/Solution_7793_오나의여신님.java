package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 1초마다 한 번씩 악마의 손아귀와 수연 위치를 동시에 업데이트 해야함
 * dfs와 bfs를 따로 쓰는게 아닌가..?
 * 하나로 두 개를 동시에 업데이트 해야함
 */

public class Solution_7793_오나의여신님 {
    static int T;
    static int N, M;

    static char[][] map;
    static boolean[][] visited;

    static int[] DIR_ROW = {-1, 0, 1, 0};
    static int[] DIR_COL = {0, 1, 0, -1};

    static final char SOOYEON = 'S';
    static final char GODDESS = 'D';
    static final char ROCK = 'X';
    static final char DEVIL = '*';
    static final char EMPTY = '.';
    static final char BLOCKED = 'B';

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            map = new char[N][M];
            visited = new boolean[N][M];

            int[] sooyeon = new int[2];
            int[] devil = new int[2];

            for (int row = 0; row < N; row++) {
                String line = br.readLine();
                for (int col = 0; col < M; col++) {
                    map[row][col] = line.charAt(col);

                    if (map[row][col] == SOOYEON) {
                        sooyeon[0] = row;
                        sooyeon[1] = col;
                    }
                    if (map[row][col] == DEVIL) {
                        devil[0] = row;
                        devil[1] = col;
                    }
                }
            }

            // 악마의 손아귀: 매초마다 상하좌우 인접 영역에 확장
            // ROCK 위치는 수연과 악마의 손아귀가 이동 불가능
            // SOOYEON은 GODDESS 위치로 이동해야함

            // 시뮬레이션을 위해 맵을 복사합니다
            char[][] copiedMap = new char[N][N];
            for (int i = 0; i < N; i++) {
                copiedMap[i] = map[i].clone();
            }

            min = Integer.MAX_VALUE;

            // 수연, 악마 위치 업데이트
            bfs(sooyeon, devil, copiedMap);

            sb.append("#").append(t).append(" ").append(min).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void bfs(int[] sooyeon, int[] devil, char[][] simulationMap) {
        Queue<int[]> sooyeonQueue = new ArrayDeque<>();
        sooyeonQueue.add(sooyeon);
        visited[sooyeon[0]][sooyeon[1]] = true;

        Queue<int[]> devilQueue = new ArrayDeque<>();
        devilQueue.add(devil);

        while (!sooyeonQueue.isEmpty() && !devilQueue.isEmpty()) {
            int[] devilCurrent = devilQueue.poll();
            int devilRow = devilCurrent[0];
            int devilCol = devilCurrent[1];

            int[] current = sooyeonQueue.poll();
            int row = current[0];
            int col = current[1];

            for (int i = 0; i < 4; i++) {
                int nextDevilRow = devilRow + DIR_ROW[i];
                int nextDevilCol = devilCol + DIR_COL[i];

                if (simulationMap[nextDevilRow][nextDevilCol] == ROCK) {
                    continue;
                }

                if (simulationMap[nextDevilRow][nextDevilCol] == EMPTY) {
                    simulationMap[nextDevilRow][nextDevilCol] = BLOCKED;
                }
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = row + DIR_ROW[i];
                int nextCol = col + DIR_COL[i];

                if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                    continue;
                }

                if (simulationMap[nextRow][nextCol] == ROCK) {
                    continue;
                }

                if (simulationMap[nextRow][nextCol] == BLOCKED) {
                    continue;
                }

                if (simulationMap[nextRow][nextCol] == DEVIL) {
                    continue;
                }

                // 이동할 수 없는지 여부 어떻게 체크할 것임?

                if (simulationMap[nextRow][nextCol] == EMPTY) {
                    simulationMap[nextRow][nextCol] = SOOYEON;
                    simulationMap[row][col] = EMPTY;
                    sooyeonDfs(nextRow, nextCol, totalTime + 1, simulationMap);
                }

                if (simulationMap[nextRow][nextCol] == GODDESS) {
                    min = Math.min(min, totalTime + 1);
                    return;
                }

            }
        }

    }
}
