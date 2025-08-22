package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 레벨별 BFS를 이용해서 탈주범이 이동할 수 있는 경로를 확인한다.
 *
 * - 핵심은 복잡한 조건을 가지치기 하는 것
 * - 범위 체크
 * - 빈 공간인지 파이프인지 체크
 * - 파이프가 이어져있는지 체크
 */

/**
 * 메모리: 31616 KB
 * 시간: 108 ms
 * 난이도: 모의 SW 역량테스트 68.78%
 */
public class Solution_1953_탈주범검거 {
    static int T;
    static int N, M, R, C, L;
    static int[][] map;
    static boolean[][] visited;

    static final int UP = 0;
    static final int RIGHT = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;

    static final int[][] DIR_PIPE = {
            {},
            {UP, RIGHT, DOWN, LEFT},
            {UP, DOWN},
            {LEFT, RIGHT},
            {UP, RIGHT},
            {DOWN, RIGHT},
            {DOWN, LEFT},
            {UP, LEFT}
    };

    // UP, RIGHT, DOWN, LEFT
    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    // 반대 방향
    static final int[] OPPOSITE = {DOWN, LEFT, UP, RIGHT};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            L = Integer.parseInt(st.nextToken());

            map = new int[N][M];
            visited = new boolean[N][M];

            for (int row = 0; row < N; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < M; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            int count = bfs();

            sb.append('#').append(t).append(' ').append(count).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static int bfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{R, C});
        visited[R][C] = true;

        int level = 0;
        int count = 0;

        while(!queue.isEmpty() && level < L) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];

                count++;

                for (int dir : DIR_PIPE[map[row][col]]) {
                    int nextRow = row + DIR_ROW[dir];
                    int nextCol = col + DIR_COL[dir];

                    if (nextRow < 0 || N <= nextRow || nextCol < 0 || M <= nextCol) {
                        continue;
                    }

                    if (map[nextRow][nextCol] == 0) {
                        continue;
                    }

                    if (!canConnect(dir, nextRow, nextCol)) {
                        continue;
                    }

                    if (!visited[nextRow][nextCol]) {
                        visited[nextRow][nextCol] = true;
                        queue.add(new int[]{nextRow, nextCol});
                    }
                }
            }
            level++;
        }
        return count;
    }

    private static boolean canConnect(int currentDir, int nextRow, int nextCol) {
        // 파이브 방향이 일치하는지 확인
        for (int nextDir : DIR_PIPE[map[nextRow][nextCol]]) {
            if (nextDir == OPPOSITE[currentDir]) {
                return true;
            }
        }
        return false;
    }
}
