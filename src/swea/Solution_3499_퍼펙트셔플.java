package swea;

import java.io.*;
import java.util.*;

public class Solution_3499_퍼펙트셔플 {
    static int T;
    static int N;

    static String[] cards;
    static String[] a;
    static String[] b;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            cards = new String[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0 ; i < N; i++) {
                cards[i] = st.nextToken();
            }

            int mid = (N + 1) / 2;
            a = Arrays.copyOfRange(cards, 0, mid);
            b = Arrays.copyOfRange(cards, mid, N);

            int i = 0;
            int j = mid;

            StringBuilder cardBuilder = new StringBuilder();
            while (i < mid || j < N) {
                if (i < mid) {
                    cardBuilder.append(cards[i++]).append(" ");
                }
                if (j < N) {
                    cardBuilder.append(cards[j++]).append(" ");
                }
            }

            sb.append("#").append(t).append(" ").append(cardBuilder).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }
}