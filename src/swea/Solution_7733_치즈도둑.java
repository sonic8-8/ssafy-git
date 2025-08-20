package swea;

import java.io.*;
import java.util.*;

public class Solution_7733_치즈도둑 {
    static int T;
    static int N;
    static int[][] cheeseScores;
    static boolean[][] visited;

    static int maxCheeseChunkCount;

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            cheeseScores = new int[N][N];
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    cheeseScores[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            maxCheeseChunkCount = 1;

            // 치즈 갉아먹기
            for (int i = 1; i <= 100; i++) {
                visited = new boolean[N][N];

                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        if (cheeseScores[row][col] <= i) {
                            visited[row][col] = true;
                        }
                    }
                }

                int currentCheeseChunkCount = 0;
                for (int row = 0; row < N; row++) {
                    for (int col = 0; col < N; col++) {
                        if (!visited[row][col]) {
                            visited[row][col] = true;
                            dfs(row, col);
//                            bfs(row, col);
                            currentCheeseChunkCount++;
                        }
                    }
                }
                maxCheeseChunkCount = Math.max(maxCheeseChunkCount, currentCheeseChunkCount);
            }

            sb.append('#').append(t).append(' ').append(maxCheeseChunkCount).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int row, int col) {
        for (int i = 0; i < 4; i++) {
            int nextRow = row + DIR_ROW[i];
            int nextCol = col + DIR_COL[i];

            if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                continue;
            }

            if (!visited[nextRow][nextCol]) {
                visited[nextRow][nextCol] = true;
                dfs(nextRow, nextCol);
            }
        }
    }

    private static void bfs(int startRow, int startCol) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startRow, startCol});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            for (int i = 0; i < 4; i++) {
                int nextRow = row + DIR_ROW[i];
                int nextCol = col + DIR_COL[i];

                if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                    continue;
                }

                if (!visited[nextRow][nextCol]) {
                    visited[nextRow][nextCol] = true;
                    queue.add(new int[]{nextRow, nextCol});
                }
            }
        }
    }
}


