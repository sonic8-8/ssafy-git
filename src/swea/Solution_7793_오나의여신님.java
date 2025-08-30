package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * "1초 단위로" 악마의 손아귀와 수연 위치를 한 번에 업데이트 한다
 * - 1초 단위로 업데이트 하기 위해 queue.size()와 for문을 이용합니다
 * - 동시에 업데이트 하기위해 악마와 수연 총 2개의 queue를 사용합니다.
 * - 시간을 편하게 관리하기 위해 수연 큐에 time까지 넣어서 관리했습니다
 *
 * 악마의 손아귀: 매초마다 상하좌우 인접 영역에 확장됩니다. 편하게 그냥 악마가 증식한다고 생각했음
 * ROCK 위치는 수연과 악마의 손아귀가 이동 불가능 함
 * SOOYEON은 GODDESS(여신) 위치로 이동해야합니다.
 */

/**
 * 메모리: 26,624 KB
 * 시간: 92 ms
 * 난이도: D5
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

    static int min;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new char[N][M];
            visited = new boolean[N][M];

            int[] sooyeon = new int[3]; // row, col, time
            Queue<int[]> devilQueue = new ArrayDeque<>(); // 악마가 여러 마리 등장 할 수 있어 큐로 관리합니다.

            for (int row = 0; row < N; row++) {
                String line = br.readLine();
                for (int col = 0; col < M; col++) {
                    map[row][col] = line.charAt(col);

                    if (map[row][col] == SOOYEON) {
                        sooyeon[0] = row;
                        sooyeon[1] = col;
                        sooyeon[2] = 0; // time
                    }
                    if (map[row][col] == DEVIL) {
                        devilQueue.add(new int[]{row, col});
                    }
                }
            }

            min = Integer.MAX_VALUE;

            // 수연, 악마 위치를 함께 탐색합니다.
            bfs(sooyeon, devilQueue);

            // 수연이 여신에게 도달하지 못한 경우 (min = -1 일 경우) GAME OVER를 출력합니다.
            sb.append("#").append(t).append(" ").append(min == -1 ? "GAME OVER" : min).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void bfs(int[] sooyeon, Queue<int[]> devilQueue) {
        Queue<int[]> sooyeonQueue = new ArrayDeque<>();
        sooyeonQueue.add(sooyeon);
        visited[sooyeon[0]][sooyeon[1]] = true;

        while (!sooyeonQueue.isEmpty()) {
            // 1. 악마 확산
            int devilSize = devilQueue.size(); // 1초 시간 단위를 구현하기 위해 size를 이용합니다.
            for (int i = 0; i < devilSize; i++) {
                int[] devilCurrent = devilQueue.poll();
                int devilRow = devilCurrent[0];
                int devilCol = devilCurrent[1];

                for (int j = 0; j < 4; j++) {
                    int nextDevilRow = devilRow + DIR_ROW[j];
                    int nextDevilCol = devilCol + DIR_COL[j];

                    if (nextDevilRow < 0 || N <= nextDevilRow || nextDevilCol < 0 || M <= nextDevilCol) {
                        continue;
                    }

                    if (map[nextDevilRow][nextDevilCol] == ROCK) {
                        continue;
                    }

                    // 지도에 악마 위치를 업데이트합니다
                    if (map[nextDevilRow][nextDevilCol] == EMPTY) {
                        map[nextDevilRow][nextDevilCol] = DEVIL;
                        devilQueue.add(new int[]{nextDevilRow, nextDevilCol});
                    }
                }
            }

            // 2. 수연 이동
            int sooyeonSize = sooyeonQueue.size(); // 1초 시간 단위를 구현하기 위해 size를 이용합니다
            for (int i = 0; i < sooyeonSize; i++) {
                int[] current = sooyeonQueue.poll();
                int row = current[0];
                int col = current[1];
                int time = current[2]; // 시간을 배열에 담아서 관리합니다

                for (int j = 0; j < 4; j++) {
                    int nextRow = row + DIR_ROW[j];
                    int nextCol = col + DIR_COL[j];

                    if (nextRow < 0 || N <= nextRow || nextCol < 0 || M <= nextCol) {
                        continue;
                    }

                    if (map[nextRow][nextCol] == ROCK) {
                        continue;
                    }

                    if (map[nextRow][nextCol] == DEVIL) {
                        continue;
                    }

                    if (visited[nextRow][nextCol]) {
                        continue;
                    }

                    // 수연은 지도에 위치를 업데이트하지 않고 visited 배열을 이용해 관리합니다.
                    if (map[nextRow][nextCol] == EMPTY) {
                        sooyeonQueue.add(new int[] {nextRow, nextCol, time + 1});
                        visited[nextRow][nextCol] = true;
                    }

                    // 여신에게 도달 성공
                    if (map[nextRow][nextCol] == GODDESS) {
                        min = time + 1;
                        return;
                    }
                }
            }
        }
        // 수연의 큐가 빌때까지 도달하지 못한 경우 -1을 반환합니다
        min = -1;
    }
}