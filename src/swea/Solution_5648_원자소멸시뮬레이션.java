package swea;

import java.io.*;
import java.util.*;

public class Solution_5648_원자소멸시뮬레이션 {
    static int T;
    static int N;

    static Queue<int[]> elements;
    static Map<Integer, List<int[]>> positionElements;

    static int[] DIR_ROW = {1, -1, 0, 0};
    static int[] DIR_COL = {0, 0, -1, 1};

    static final int ROW = 0;
    static final int COL = 1;
    static final int DIR = 2;
    static final int ENERGY = 3;

    static final int HASH = 5000;

    static int totalEnergy;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            elements = new ArrayDeque<>();

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int col = Integer.parseInt(st.nextToken());
                int row = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                int energy = Integer.parseInt(st.nextToken());

                elements.add(new int[]{row * 2, col * 2, dir, energy});
            }

            // x축, y축 확인
            // (0, 0)R (1, 0)L: col이 동일하고 row가 작은 쪽이 R 큰 쪽이 L인 경우
            // (0, 0)U (0, 3)D:
            // (0, 0)D (0, -3)U

            // 사분면 확인
            // (0, 0) R 기준 (x1, y1)
            // 1사분면: X
            // 2사분면: D (x2, y2), Math.abs(x2 - x1) == Math.abs(y2 - y1)
            // 3사분면: X
            // 4사분면: U (x2, y2), Math.abs(x2 - x1) == Math.abs(y2 - y1)

            // 충돌할 경우 에너지를 방출하고 사라진다

            // 이동방향은 일정하다
            totalEnergy = 0;
            simulation();

            sb.append("#").append(t).append(" ").append(totalEnergy).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void simulation() {
        // 원소를 모두 한 번 이동시켰을 때 1초 시간이 흐름
        // 이동 후 위치가 같은 게 몇 개인지에 따라 방출하는 에너지를 계산한다
        positionElements = new HashMap<>();

        for (int time = 0; time < 2000 && !elements.isEmpty(); time++) {
            int elementCount = elements.size();

            positionElements.clear();

            // 1. 원자 이동
            for (int i = 0; i < elementCount; i++) {
                int[] current = elements.poll();
                int row = current[ROW];
                int col = current[COL];
                int dir = current[DIR];
                int energy = current[ENERGY];

                row += DIR_ROW[dir];
                col += DIR_COL[dir];

                if (Math.abs(row) > 2000 || Math.abs(col) > 2000) {
                    continue;
                }

                // 큐에서 모든 원소를 꺼내 Map으로 이동시킵니다
                positionElements
                        .computeIfAbsent(row + HASH + col, k -> new ArrayList<>())
                        .add(new int[]{row, col, dir, energy});
            }

            // 2. 충돌 판정
            for (List<int[]> list : positionElements.values()) {
                if (list.size() == 1) {
                    elements.add(list.get(0));
                } else {
                    for (int[] element : list) {
                        totalEnergy += element[ENERGY];
                    }
                }
            }

            if (elements.size() <= 1) {
                break;
            }
        }
    }
}