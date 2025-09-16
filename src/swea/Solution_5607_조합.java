package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * DP + 모듈러 거듭제곰(분할 정복)
 */

/**
 * 메모리: 34,304 kb
 * 시간: 102 ms
 * 난이도: D3
 */
public class Solution_5607_조합 {
    static int T;
    static int N, R;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        // 팩토리얼 미리 계산
        // 미리 계산하지 않으면 테스트 케이스마다 N!을 계산해야합니다
        long[] factorial = new long[1000001];
        long p = 1234567891;
        factorial[0] = 1;
        for (int i = 1; i <= 1000000; i++) {
            factorial[i] = (factorial[i - 1] * i) % p;
        }

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            R = Integer.parseInt(st.nextToken());

            // 분자
            long numerator = factorial[N];
            // 분모
            long denominator = (factorial[R] * factorial[N - R]) % p;

            long result = (numerator * power(denominator, p - 2, p)) % p;

            sb.append("#").append(t).append(" ").append(result).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    // 모듈러 거듭제곱
    // 분할 정복을 이용해 거듭제곱을 최적화합니다
    // 밑, 지수, 제수(나누는 수)
    // base, exponential, modulo
    private static long power(long base, long exp, long mod) {
        long result = 1;
        // 밑 최적화
        base %= mod;

        while (exp > 0) {
            // 지수의 마지막 비트가 1인지 검사
            if (exp % 2 == 1) {
                // 마지막 비트가 1이면 이번 base를 result에 곱하기
                result = (result * base) % mod;
            }
            // 밑을 제곱
            base = (base * base) % mod;

            // 지수를 2로 나눔
            exp /= 2;
        }
        return result;
    }
}
