package swea;
import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 다익스트라 알고리즘을 사용합니다.
 *
 * 일반 BFS는 visited 배열을 사용해 "한 번 방문한 경로"는 다시 방문하지 않습니다.
 * 하지만 이 문제에선 경로마다 걸리는 시간이 달라서 첫 방문이 항상 최소 시간을 보장하지 않습니다.
 * 따라서 더 짧은 경로가 나올 수 있기 때문에 여러 경로를 모두 고려해야합니다.
 * 그리고 이 여러 경로를 효율적으로 탐색하는 알고리즘이 다익스트라입니다.
 *
 * 다익스트라는 우선순위큐를 사용해 현재까지 비용이 가장 작은 경로부터 탐색합니다.
 * 이를 구현하기 위해 Compartor로 비용 기준 오름차순 정렬하도록 설정합니다.
 * 이렇게 되면, 비용이 작은 경로부터 순서대로 꺼내서 탐색하게 됩니다.
 * 만약 경로를 탐색하는데 이미 더 짧은 경로로 업데이트 되어있는 경우, 가지치기로 건너 뛰도록 합니다.
 */

/**
 * 메모리: 35000 KB
 * 시간: 230 ms
 * 난이도: D4
 */
public class Solution_1249_보급로 {
    static int T;
    static int N;
    static int[][] map;
    static int[][] times;

    static final int[] DIR_ROW = {-1, 0, 1, 0};
    static final int[] DIR_COL = {0, 1, 0, -1};

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            map = new int[N][N];
            times = new int[N][N];

            for (int i = 0; i < N; i++) {
                String line = br.readLine();
                for (int j = 0; j < N; j++) {
                    map[i][j] = line.charAt(j) - '0';
                    times[i][j] = Integer.MAX_VALUE;
                }
            }

            dijkstra();

            sb.append("#").append(t).append(" ").append(times[N - 1][N - 1]).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void dijkstra() {
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        pq.add(new int[]{0, 0, 0});
        times[0][0] = 0;

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int row = current[0];
            int col = current[1];
            int cost = current[2];

            if (times[row][col] < cost) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int nextRow = row + DIR_ROW[i];
                int nextCol = col + DIR_COL[i];

                if (nextRow < 0 || N <= nextRow || nextCol < 0 || N <= nextCol) {
                    continue;
                }

                int nextCost = cost + map[nextRow][nextCol];

                if (nextCost < times[nextRow][nextCol]) {
                    times[nextRow][nextCol] = nextCost;
                    pq.add(new int[]{nextRow, nextCol, nextCost});
                }
            }
        }
    }
}
