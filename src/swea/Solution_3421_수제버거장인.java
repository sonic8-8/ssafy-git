package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 모든 경우의 수를 살핀다.
 * -> 백트래킹으로 부분집합을 구현한다.
 *
 * 궁합이 맞지 않는 재료는 함께 포함하지 않는다.
 * 입력은 (1, 2) (1, 3) 처럼 들어올 수 있다.
 * -> List<Integer>[]로 graph 구현하기
 *
 * 현재 부분집합의 재료들과 이번 재귀 호출로 가져온 재료가 궁합이 맞는지 체크한다.
 * -> selected[]와 canInclude()로 궁합 여부를 확인하기
 */

/**
 * 메모리: 30800 KB
 * 시간: 114 ms
 * 난이도: D5
 */
public class Solution_3421_수제버거장인 {
    static int T;
    static int N, M;
    static List<Integer>[] notTogether;
    static boolean[] selected;

    static int count;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            notTogether = new ArrayList[N + 1];
            selected = new boolean[N + 1];

            for (int i = 1; i <= N; i++) {
                notTogether[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                notTogether[a].add(b);
                notTogether[b].add(a);
            }

            count = 0;
            subset(1);

            sb.append("#").append(t).append(" ").append(count).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void subset(int index) {
        if (index == N + 1) {
            count++;
            return;
        }

        if (canInclude(index)) {
            selected[index] = true;
            subset(index + 1);
            selected[index] = false;
        }

        subset(index + 1);
    }

    private static boolean canInclude(int index) {
        for (int banned : notTogether[index]) {
            if (selected[banned]) {
                return false;
            }
        }
        return true;
    }
}
