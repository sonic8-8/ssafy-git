package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 부분집합 + 백트래킹
 * 부분 집합을 이용해 약물 A, 약물 B, 주입 X 경우를 살핍니다.
 * 모든 경우의 수를 살피면서
 * 동시에 가지치기로 불필요한 탐색을 제거합니다.
 */

/**
 * 메모리: 95368 KB
 * 시간: 536 ms
 * 난이도: 모의 SW 역량테스트 33.83%
 */
public class Solution_2112_보호필름 {
    static int T;
    static int D, W, K; // 두께, 가로 크기, 합격 기준
    static int[][] films;

    static int minInjectedCount;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            films = new int[D][W];

            for (int row = 0; row < D; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < W; col++) {
                    films[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            minInjectedCount = Integer.MAX_VALUE;
            tryInjectionBySubset(0 , 0);

            sb.append("#").append(t).append(" ").append(minInjectedCount).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void tryInjectionBySubset(int row, int injectedCount) {
        // 한 번이라도 성능 검사에 통과해서 최소 약품 주입 횟수를 구한 경우
        // 약품 주입을 필요 이상으로 했을 때, 바로 걸러내기 위해 가지치기합니다
        if (minInjectedCount <= injectedCount) {
            return;
        }

        // 성능 검사를 합격했을 경우 최소 약품 주입 횟수를 갱신합니다
        if (checkPass()) {
            minInjectedCount = injectedCount;
            return;
        }

        // 보호 필름 개수를 넘어서면 가지치기
        if (D <= row) {
            return;
        }

        // 부분 집합을 구하기 전에 미리 원본을 복사합니다
        int[] original = films[row].clone();

        // 약품 A 주입
        Arrays.fill(films[row], 0);
        tryInjectionBySubset(row + 1, injectedCount + 1);

        // 약품 B 주입
        Arrays.fill(films[row], 1);
        tryInjectionBySubset(row + 1, injectedCount + 1);

        // 원본을 넣으며, 약품 주입 X의 경우를 살핍니다
        // 다음 번 성능 검사를 위해 위치를 원복시킨 것
        films[row] = original;
        tryInjectionBySubset(row + 1, injectedCount);
    }

    private static boolean checkPass() {
        for (int col = 0; col < W; col++) {
            int count = 1;
            int maxCount = 1;
            for (int row = 1; row < D; row++) {
                if (films[row][col] == films[row - 1][col]) {
                    count++;
                    maxCount = Math.max(maxCount, count);
                    continue;
                }
                if (films[row][col] != films[row - 1][col]) {
                    count = 1;
                }
            }
            if (maxCount < K) {
                return false;
            }
        }
        return true;
    }
}
