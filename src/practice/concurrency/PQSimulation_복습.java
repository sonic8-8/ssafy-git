package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 계단은 동시에 최대 k명만 내려갈 수 있다.
 * 각 사람이 "계단에 도착하는 시간"이 주어진다.
 * 모든 사람이 "계단을 다 내려가는 시간(최대 종료시간)"을 구하라.
 * 계단 길이는 L이다.
 *
 * 입력:
 * N=4, k=2, L=3
 * 도착시간 = [0, 0, 1, 2]
-----
4 2 3
0 0 1 2
-----
 * 출력: 6
 */

/**
 * 아이디어:
 * 4가지 요소로 관리한다
 * 우선 순위 큐: 계단 위 사람들의 종료 시간 관리
 * 시작 시간: 계단 진입 시간
 * 종료 시간: 시작 시간 + 계단 오르는 데 걸리는 시간
 * 도착 시간: 계단 도착 시간
 */
public class PQSimulation_복습 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 사람 수
        int K = Integer.parseInt(st.nextToken()); // 동시에 내려갈 수 있는 숫자
        int L = Integer.parseInt(st.nextToken()); // 계단 다 내려가는데 걸리는 시간

        // 도착 시간 입력 받기
        List<Integer> arrivals = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arrivals.add(Integer.parseInt(st.nextToken()));
        }
        // 도착 시간 정렬
        Collections.sort(arrivals);

        // 계단 내려가는 중
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int lastEnd = 0;

        // 가장 늦게 계단 내려오는 시점 찾기가 목표!
        // lastEnd 찾기

        for (int arrival : arrivals) {
            // 계단에 사람이 있고,
            // 현재 도착한 사람보다 계단에 있는 사람 종료 시간이 작으면
            // 이미 내려왔어야하니까 반영해서 제거
            while (!pq.isEmpty() && pq.peek() <= arrival) {
                // 계단에서 사람 내려오기
                pq.poll();
            }

            // 계단에 여유가 있는지 확인
            int start = 0;
            if (pq.size() < K) {
                start = arrival;
            } else {
                // 기다려야하는지 확인
                int earliestEnd = pq.poll();
                start = Math.max(arrival, earliestEnd);
            }

            int end = start + L;
            pq.add(end);

            lastEnd = Math.max(lastEnd, end);
        }

        bw.write(String.valueOf(lastEnd));

        br.close();
        bw.close();
    }
}