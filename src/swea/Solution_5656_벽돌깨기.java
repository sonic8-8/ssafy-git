package swea;

import java.io.*;
import java.util.*;

public class Solution_5656_벽돌깨기 {
    static int T;
    static int N, W, H;

    static int[][] bricks;
    static boolean[][] visited;

    static int totalBrickCount;
    static int deletedBrickCount;

    static int[] orders;

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

            bricks = new int[H][W];
            totalBrickCount = 0;

            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    bricks[i][j] = Integer.parseInt(st.nextToken());

                    if (bricks[i][j] != 0) {
                        totalBrickCount++;
                    }
                }
            }

            // N: 구슬 쏠 수 있는 횟수
            // W X H 배열

            // 구슬을 공중에서 떨어뜨린다. 좌우로만 움직여 위치를 조절하고 떨어뜨리면
            // 맨 위에 있는 벽돌만 깨트릴 수 있다
            // 벽돌 숫자 의미: 벽돌을 중심으로 상하좌우 N - 1만큼 제거

            // 최대한 벽돌을 많이 제거
            // 남은 벽돌의 개수를 구하기

            // 제거 과정

            int max = Integer.MIN_VALUE;

            // 모든 경우의 수를 만들어야한다
            // 시작점을 어떻게 설정할 것인가?
            // 재귀함수로 구현해야하나?
            // 개수가 정해져있으니 조합?
            // 이걸 잘 모르겠는데..
            // 예를 들어, 0 0 0, 0 0 1, 0 0 2, ..., 0 0 W -1 까지 있고
            // 0 1 0, 0 1 1, 0 1 2, ..., 0 1 w - 1..
            // 브루트 포스로 해보는게 나을듯?
            // 그런데 N이 4까지 될 수 있어서 각 경우마다 브루트포스로 구현하려면 4중 for문까지 해야되는데 ..?
            // 재귀함수로 구현하는게 나을듯

            orders = new int[3];
            dfs(0); // todo: 경우의 수 구하기 하다 말았음

            visited = new boolean[H][W];
            deletedBrickCount = 0;
            for (int col = 0; col < W; col++) {
                int row = 0;

                // 1. bfs로 탐색하기
                // 2. 탐색한 범위 내의 숫자를 모두 0으로 만들기
                bfs(row, col);

                // 3. 0이 아닌 숫자에 중력 적용시키기 (아래로 이동)
                adaptGravity();

                max = Math.max(max, deletedBrickCount);

            }
            // 4. 제거한 벽돌 수를 count라고 했을때 Math.max(max, count)로 최대 제거 벽돌 수를 갱신한다
            // 5. 전체 벽돌 수 - count로 남은 벽돌 수를 구한다

            // 0을 제외한 첫 번째 숫자를 찾기


            sb.append('#').append(t).append(' ').append(max).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void bfs(int r, int c) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{r, c, bricks[r][c]});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int value = current[2];

            for (int i = 0; i < 4; i++) {
                for (int range = 0; range < value; range++) {
                    int nextRow = row + DIR_ROW[i] * range;
                    int nextCol = col + DIR_COL[i] * range;

                    if (!visited[nextRow][nextCol]) {
                        visited[nextRow][nextCol] = true;
                        queue.add(new int[]{nextRow, nextCol, bricks[nextRow][nextCol]});
                        bricks[nextRow][nextCol] = 0;
                        deletedBrickCount++;
                    }
                }
            }
        }
    }

    private static void adaptGravity() {
        // 3-1. col별로 아래에서 위로 탐색하며 0이 아닌 숫자 queue에 넣기
        for (int col = 0; col < W; col++) {
            Queue<Integer> queue = new ArrayDeque<>();
            for (int row = H - 1; row >= 0; row--) {
                if (bricks[row][col] != 0) {
                    queue.add(bricks[row][col]);
                }
            }

            // 3-2. 다시 아래에서 위로 올라가며 queue에서 숫자를 빼서 넣기 (중력 적용)
            int row = H - 1;
            while (!queue.isEmpty()) {
                bricks[row--][col] = queue.poll();
            }
        }
    }

    private static void dfs(int depth) {
        if (orders.length == 3) {
            return;
        }

        for (int i = 0; i < W; i++) {
            orders[depth] = i;
            dfs(depth + 1);
        }
    }
}
