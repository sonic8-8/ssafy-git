package etc;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 노랑을 0 파랑을 1이라고 했을 때,
 * 0은 연속 사용 가능 o
 * 1은 연속 사용 가능 x
 * <p>
 * 0 1
 * 00 01 10
 * dp[2] = 3
 * <p>
 * 3
 * 000 001 010
 * 100 101
 * dp[3] = 5;
 * <p>
 * 4
 * 0000
 * 0001 0010 0100 1000
 * 1001 1010 0101
 * dp[4] = 8
 * <p>
 * 5
 * 00000
 * 00001 00010 00100 01000 10000
 * 10001 10010 10100 01001 01010 00101
 * 10101
 * dp[5] = 13
 */
public class Example_1 {
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        dp = new int[9];

        dp[1] = 2;
        dp[2] = 3;

        for (int i = 3; i <= 8; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        bw.write(String.valueOf(dp[8]));

        br.close();
        bw.close();
    }
}