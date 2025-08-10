package swea;

import java.io.*;
import java.util.*;

/**
 * 카드 18장을 절반으로 나누고
 * 나눈 카드로 만들 수 있는 순열로
 * 입력받은 상대의 카드 순서와 숫자 크기를 비교해
 * 게임을 이기는 경우의 수와 지는 경우의 수를 모두 구하기
 *
 * -> 순열 만들기
 */

/**
 * 메모리: 27520 KB
 * 시간: 1757 ms
 * 난이도: D3
 */
public class Solution_6808_규영이와인영이의카드게임 {
    static int T;
    static int[] myCards;
    static int[] enemyCards;
    static boolean[] used;

    static boolean[] visited;
    static int win, lose;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());

        for (int t = 1; t <= T; t++) {
            myCards = new int[9];
            enemyCards = new int[9];
            visited = new boolean[9];

            StringTokenizer st = new StringTokenizer(br.readLine());
            used = new boolean[19];

            for (int i = 0; i < 9; i++) {
                enemyCards[i] = Integer.parseInt(st.nextToken());
                used[enemyCards[i]] = true;
            }

            int index = 0;
            for (int num = 1; num <= 18; num++) {
                if (!used[num]) {
                    myCards[index++] = num;
                }
            }

            win = 0;
            lose = 0;
            perm(0, 0, 0);

            sb.append("#").append(t).append(" ").append(lose).append(" ").append(win).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void perm(int depth, int myScore, int enemyScore) {
        if (depth == 9) {
            if (myScore > enemyScore) {
                win++;
                return;
            }
            if (myScore < enemyScore) {
                lose++;
                return;
            }
            return;
        }

        for (int i = 0; i < 9; i++) {
            if (!visited[i]) {
                visited[i] = true;

                if (myCards[i] < enemyCards[depth]) {
                    perm(depth + 1, myScore, enemyScore + myCards[i] + enemyCards[depth]);
                }
                if (myCards[i] > enemyCards[depth]) {
                    perm(depth + 1, myScore + myCards[i] + enemyCards[depth], enemyScore);
                }

                visited[i] = false;
            }
        }
    }
}
