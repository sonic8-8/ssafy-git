package etc;

import java.io.*;

/**
 * 아이디어:
 * 나열하고 규칙 찾기
 * =================== 1개 ===================
 * ㅍ
 * ㄴ
 * dp[1] = 2;
 * <p>
 * =================== 2개 ===================
 * ㅍㅍ
 * ㅍㄴ ㄴㅍ
 * ㄴㄴ
 * ㅃ
 * dp[2] = 5
 * <p>
 * =================== 3개 ===================
 * ㅍㅍㅍ
 * ㅍㅍㄴ ㅍㄴㅍ ㄴㅍㅍ
 * ㅍㄴㄴ ㄴㅍㄴ ㄴㄴㅍ
 * ㄴㄴㄴ
 * ㅃㅍ ㅍㅃ
 * ㅃㄴ ㄴㅃ
 * dp[3] = 12
 * <p>
 * =================== 4개 ===================
 * ㅍㅍㅍㅍ
 * ㅍㅍㅍㄴ ㅍㅍㄴㅍ ㅍㄴㅍㅍ ㄴㅍㅍㅍ
 * ㅍㅍㄴㄴ ㅍㄴㅍㄴ ㅍㄴㄴㅍ ㄴㅍㅍㄴ ㄴㅍㄴㅍ ㄴㄴㅍㅍ
 * ㅍㄴㄴㄴ ㄴㅍㄴㄴ ㄴㄴㅍㄴ ㄴㄴㄴㅍ
 * ㄴㄴㄴㄴ
 * ㅃㅍㅍ ㅍㅃㅍ ㅍㅍㅃ
 * ㅃㅍㄴ ㅃㄴㅍ ㅍㅃㄴ ㅍㄴㅃ ㄴㅃㅍ ㄴㅍㅃ
 * ㅃㄴㄴ ㄴㅃㄴ ㄴㄴㅃ
 * ㅃㅃ
 * dp[4] = dp[3] * 2 + dp[2] = 16 + 13 = 29
 */
public class DPPractice2 {
    static int[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        dp = new int[7];

        dp[1] = 2;
        dp[2] = 5;

        for (int i = 3; i <= 6; i++) {
            dp[i] = dp[i - 1] * 2 + dp[i - 2];
        }

        bw.write(String.valueOf(dp[6]));

        br.close();
        bw.close();
    }
}