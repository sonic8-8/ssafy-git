package swea;

import java.io.*;
import java.util.*;

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

            int twoCount = 0;
            int oneCount = 0;

            for (int i = 0; i < N; i++) {
                int diff = maxHeight - trees[i];
                twoCount += diff / 2;
                oneCount += diff % 2;
            }

            if (oneCount < twoCount) {
                int convertTwoToOne = (twoCount - oneCount + 1) / 3;
                oneCount += convertTwoToOne * 2;
                twoCount -= convertTwoToOne;
            }

            int totalDays = 0;
            if (oneCount == twoCount) {
                totalDays = oneCount * 2;
            }
            if (oneCount > twoCount) {
                totalDays = twoCount * 2 + (oneCount - twoCount) * 2 - 1;
            }
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