package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 이진탐색을 이용해 최장 증가 부분 수열 길이를 구합니다.
 * 실제 LIS 원소는 다를 수 있음
 */

/**
 * 메모리: 28,288 KB
 * 시간: 98 ms
 * 난이도: D3
 */
public class Solution_3307_최장증가부분수열 {
    static int T;
    static int N;

    static int[] numbers;
    static List<Integer> lis;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            numbers = new int[N];


            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            lis = new ArrayList<>();
            lis.add(numbers[0]);

            for (int i = 1; i < N; i++) {
                if (numbers[i] > lis.get(lis.size() - 1)) {
                    lis.add(numbers[i]);
                    continue;
                }

                int index = Collections.binarySearch(lis, numbers[i]);
                if (index < 0) {
                    index = -index - 1;
                }
                lis.set(index, numbers[i]);
            }

            sb.append("#").append(t).append(" ").append(lis.size()).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}