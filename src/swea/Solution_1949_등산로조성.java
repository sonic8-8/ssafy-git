package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 가장 높은 곳에서 시작해 낮은 경로를 DFS로 탐색합니다.
 * BFS도 생각해봤으나 지형을 깎는 경우를 구현하기가 어려워 적절하지 않다고 판단했습니다.
 *
 * 핵심은 4개입니다.
 * 1. visited 배열 사용
 * 2. 현재 칸과 다음 칸의 크기 비교
 * 3. 지형을 깎을 기회가 남았는지 여부를 boolean 매개변수로 넘기기 (isCut)
 * 4. 지형을 깎을 기회가 남았다면, 다음 칸을 깎았을 때 현재 칸보다 작아졌는지 확인하고 작아졌다면 방문하기
 */

/**
 * 메모리: 27880 KB
 * 시간: 98 ms
 * 난이도: 모의 SW 역량테스트 정답률 49.81%
 */
public class Solution_1949_등산로조성 {
    static int T;
    static int N, K;

    static int[][] map;
    static boolean[][] visited;

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    static int maxLength;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            visited= new boolean[N][N];

            maxLength = 0;

            // 처음 입력받으며 가장 높은 높이를 찾기
            int maxHeight = 0;
            for (int row = 0; row < N; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < N; col++) {
                    map[row][col] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, map[row][col]);
                }
            }

            // 가장 높은 곳에서 시작해서 낮은 곳으로 이동한다
            // 구현을 위해 전체 맵을 순회하며
            // 가장 높은 높이일 경우에서 시작해 DFS로 상하좌우를 탐색하기
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (map[row][col] == maxHeight) {
                        // 탐색 중 한 번에 한하여 높이를 최대 K만큼 깎을 수 있다
                        // 깎았는지 여부를 체크하는 boolean 사용하기
                        dfs(row, col, 1, false);
                    }
                }
            }

            sb.append('#').append(t).append(' ').append(maxLength).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dfs(int row, int col, int length, boolean isCut) {
        // DFS로 탐색하며 length를 계산하고
        // 더 이상 이동할 수 없게 되면 최대 길이를 갱신하려고 했으나
        // 더 이상 이동할 수 없는 기준 잡기가 애매해서
        // 그냥 탐색할때마다 최대 길이를 갱신하도록 구현했습니다.
        maxLength = Math.max(maxLength, length);

        // 이미 지나온 경로를 공사해서 다시 방문하는 경우를 방지합니다.
        // 예를 들어, 9 -> 8로 이동한 다음 이미 방문한 9를 7로 줄여서 8 -> 7 (이전 9)
        // 이런식으로 이동하는 경우를 방지합니다.
        visited[row][col] = true;

        for (int i = 0; i < 4; i++) {
            int nextRow = row + DIR_ROW[i];
            int nextCol = col + DIR_COL[i];

            if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                continue;
            }

            if (visited[nextRow][nextCol]) {
                continue;
            }

            // 다음 칸이 현재 칸보다 낮으면 방문
            if (map[row][col] > map[nextRow][nextCol]) {
                dfs(nextRow, nextCol, length + 1, isCut);
                continue;
            }

            // 다음 칸이 현재 칸보다 높고, 아직 지형을 깎은 적이 없다면 지형을 깎고 방문
            if (map[row][col] <= map[nextRow][nextCol] && !isCut) {
                for (int k = 1; k <= K; k++) {
                    if (map[nextRow][nextCol] - k < map[row][col]) {
                        map[nextRow][nextCol] -= k;
                        dfs(nextRow, nextCol, length + 1, true);
                        map[nextRow][nextCol] += k;
                    }
                }
            }
        }
        visited[row][col] = false;
    }
}