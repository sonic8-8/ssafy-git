package swea;

import java.io.*;
import java.util.*;

public class Solution_1953_탈주범검거 {
    static int T;
    static int N, M, R, C, L;
    static int[][] map;
    static boolean[][] visited;

    static final int UP_DOWN_LEFT_RIGHT = 1;
    static final int UP_DOWN = 2;
    static final int LEFT_RIGHT = 3;
    static final int UP_RIGHT = 4;
    static final int DOWN_RIGHT = 5;
    static final int DOWN_LEFT = 6;
    static final int UP_LEFT = 7;

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

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

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
        int count = 1;

        while(!queue.isEmpty()) {
            if (level == L) {
                return count;
            }
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];

                for (int dir : DIR_PIPE[map[row][col]]) {
                    int nextRow = row + DIR_ROW[dir];
                    int nextCol = col + DIR_COL[dir];

                    if (nextRow < 0 || N <= nextRow || nextCol < 0 || M <= nextCol) {
                        continue;
                    }

                    if (!canConnect()) {
                        continue;
                    }

                    if (!visited[nextRow][nextCol] && map[nextRow][nextCol] != 0) {
                        visited[nextRow][nextCol] = true;
                        queue.add(new int[]{nextRow, nextCol});
                        count++;
                    }
                }
            }
            level++;
        }
        return count;
    }
}
