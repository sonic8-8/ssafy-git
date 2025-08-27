package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * K개의 미생물 군집
 *
 * N x N 배열
 * 미생물 군집은 1시간에 1칸씩 이동
 * 약품 셀에 도달하면 절반 죽고 방향 반대로 바뀜
 * 미생물 수가 홀수면 2로 나누고 소수점 이하를 버림
 * 0이 될 경우 사라짐
 *
 * 군집이 같은 셀로 모이면 합쳐진다
 * 이동 방향은 미생물 수가 많은 군집의 방향
 *
 * M 시간동안 격리함
 */

/**
 * 메모리: 119364 KB
 * 시간: 3252 ms
 * 난이도: 모의 SW 역량테스트 54.62%
 */
public class Solution_2382_미생물격리 {
    static int T;
    static int N, M, K;

    static List<int[]> positions;
    static int[][][] infos;

    static int[] DIR_ROW = {0, -1, 1, 0, 0};
    static int[] DIR_COL = {0, 0, 0, -1, 1};

    static final int UP = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;

    static int[] OPPOSITE_DIR = {0, DOWN, UP, RIGHT, LEFT};

    static final int ROW = 0;
    static final int COL = 1;

    static final int COUNT = 0;
    static final int DIRECTION = 1;
    static final int MAX_COUNT = 2;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            positions = new ArrayList<>();
            infos = new int[N][N][2];

            // 초기화
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                int count = Integer.parseInt(st.nextToken());
                int direction = Integer.parseInt(st.nextToken());

                positions.add(new int[]{row, col});
                infos[row][col][COUNT] = count;
                infos[row][col][DIRECTION] = direction;
            }

            // 시간 경과에 따른 움직임
            for (int i = 0; i < M; i++) {
                simulate();
            }

            int total = 0;

            for (int[] position : positions) {
                total += infos[position[ROW]][position[COL]][COUNT];
            }

            sb.append('#').append(t).append(' ').append(total).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void simulate() {
        // 원본을 유지하기 위해 배열을 복사합니다
        int[][][] nextInfos = new int[N][N][3];
        List<int[]> nextPositions = new ArrayList<>();

        // 좌표를 담은 리스트로 이중 for문을 제거합니다
        for (int[] position : positions) {
            int row = position[ROW];
            int col = position[COL];
            int currentCount = infos[row][col][COUNT];

            // 비어있는 칸일 경우 가지치기
            if (currentCount == 0) {
                continue;
            }

            int currentDir = infos[row][col][DIRECTION];
            int nextRow = row + DIR_ROW[currentDir];
            int nextCol = col + DIR_COL[currentDir];

            // 약품 셀에 들어갔을 경우
            if (nextRow == 0 || nextRow == N - 1 || nextCol == 0 || nextCol == N - 1) {
                currentCount /= 2;
                currentDir = OPPOSITE_DIR[currentDir];
            }

            // 군집이 죽은 경우 가지치기
            if (currentCount == 0) {
                continue;
            }

            nextInfos[nextRow][nextCol][COUNT] += currentCount;

            // 이동 경로에 이미 미생물 군집이 존재할 경우
            // 미생물 군집이 3개 이상 합쳐질 수도 있기 때문에 max count와 비교합니다.
            if (nextInfos[nextRow][nextCol][MAX_COUNT] < currentCount) {
                nextInfos[nextRow][nextCol][DIRECTION] = currentDir;
                nextInfos[nextRow][nextCol][MAX_COUNT] = currentCount;
            }
        }

        // 원본 배열에 반영
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (nextInfos[i][j][COUNT] > 0) {
                    infos[i][j][COUNT] = nextInfos[i][j][COUNT];
                    infos[i][j][DIRECTION] = nextInfos[i][j][DIRECTION];
                    nextPositions.add(new int[]{i, j});
                } else {
                    infos[i][j][COUNT] = 0;
                    infos[i][j][DIRECTION] = 0;
                }
            }
        }

        // positions 업데이트
        positions = nextPositions;
    }
}