package swea;

import java.io.*;
import java.util.*;

public class Solution_4008_숫자만들기_다음순열 {
    static int T;
    static int N;
    static int[] operations;
    static int[] numbers;

    static int min;
    static int max;
    static int difference;

    static final int PLUS = 0;
    static final int MINUS = 1;
    static final int PRODUCT = 2;
    static final int DIVIDE = 3;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;

            numbers = new int[N];
            operations = new int[N - 1];

            StringTokenizer st = new StringTokenizer(br.readLine());
            int index = 0;
            for (int i = 0; i < 4; i++) {
                int count = Integer.parseInt(st.nextToken());
                while (count-- > 0) {
                    operations[index++] = i;
                }

            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            updateDifference();
            while (nextPermutation(operations)) {
                updateDifference();
            }

            difference = max - min;
            sb.append("#").append(t).append(" ").append(difference).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static boolean nextPermutation(int[] selected) {
        int breakPoint = selected.length - 2;
        while (0 <= breakPoint && selected[breakPoint] >= selected[breakPoint + 1]) {
            breakPoint--;
        }
        if (breakPoint < 0) {
            return false;
        }

        int swapIndex = selected.length - 1;
        while (selected[breakPoint] >= selected[swapIndex]) {
            swapIndex--;
        }

        swap(selected, breakPoint, swapIndex);
        reverse(selected, breakPoint + 1, selected.length - 1);
        return true;
    }

    private static void swap(int[] selected, int a, int b) {
        int temp = selected[a];
        selected[a] = selected[b];
        selected[b] = temp;
    }

    private static void reverse(int[] selected, int start, int end) {
        while (start < end) {
            swap(selected, start++, end--);
        }
    }

    private static void updateDifference() {
        int result = numbers[0];
        for (int i = 0; i < N - 1; i++) {
            if (operations[i] == PLUS) {
                result += numbers[i + 1];
                continue;
            }
            if (operations[i] == MINUS) {
                result -= numbers[i + 1];
                continue;
            }
            if (operations[i] == PRODUCT) {
                result *= numbers[i + 1];
                continue;
            }
            if (operations[i] == DIVIDE) {
                result /= numbers[i + 1];
            }
        }

        min = Math.min(min, result);
        max = Math.max(max, result);
    }
}