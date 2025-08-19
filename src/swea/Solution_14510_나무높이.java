package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * - 매일 나무는 +1 또는 +2 성장할 수 있다.
 * - 가장 큰 나무의 높이를 기준으로, 각 나무와의 키 차이를 계산한다.
 * - 각 차이에 대해 (diff / 2)는 2성장 횟수, (diff % 2)는 1성장 횟수로 누적한다.
 * - 이후 2성장이 너무 많은 경우, 일부를 1성장 두 번으로 변환하여
 *   날짜 수를 최소화한다. (3일 주기를 고려한 최적화)
 */

/**
 * 메모리: 25216 KB
 * 시간: 85 ms
 * 난이도: D2
 */
public class Solution_14510_나무높이 {
    static int T;
    static int N;

    static int[] trees;

    static int maxHeight;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            trees = new int[N];
            maxHeight = Integer.MIN_VALUE;

            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                trees[i] = Integer.parseInt(st.nextToken());
                maxHeight = Math.max(maxHeight, trees[i]);
            }

            // 2성장과 1성장 횟수 누적용 변수
            int twoCount = 0; // 2씩 성장해야 하는 횟수
            int oneCount = 0; // 1씩 성장해야 하는 횟수

            // 나무가 2성장해야하는지 1성장해야하는지 2로 나누거나 모듈러 연산해 초기화합니다.
            for (int i = 0; i < N; i++) {
                int diff = maxHeight - trees[i];
                twoCount += diff / 2;
                oneCount += diff % 2;
            }

            // 핵심: 2성장이 너무 많으면 일부를 1성장 두 번으로 변환하여 날짜 수를 최소화 하도록 최적화합니다.
            if (oneCount < twoCount) {
                int convertTwoToOne = (twoCount - oneCount + 1) / 3;
                oneCount += convertTwoToOne * 2;
                twoCount -= convertTwoToOne;
            }

            // 최소 날짜를 계산합니다.
            int totalDays = 0;
            if (oneCount == twoCount) {
                totalDays = oneCount * 2;
            }
            if (oneCount > twoCount) {
                totalDays = twoCount * 2 + (oneCount - twoCount) * 2 - 1;
            }
            // 최적화 이후에는 oneCount와 twoCount 차이가 최대 1만 남는다.
            if (oneCount < twoCount) {
                totalDays = oneCount * 2 + 2;
            }

            sb.append("#").append(t).append(" ").append(totalDays).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}