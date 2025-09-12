package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 계단은 동시에 최대 k명만 내려갈 수 있다.
 * 각 사람이 계단에 도착하는 시간이 주어진다.
 * 모든 사람이 계단을 다 내려가는 시간(최대 종료시간)을 구하라.
 * 계단 길이는 L이다.
 *
 * 입력:
 * N=4, k=2, L=3
 * 도착시간 = [0, 0, 1, 2]
 *
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
public class PQSimulation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = 4;
        int K = 2;
        int L = 3;

        int[] arrivals = {0, 0, 1, 2};

        Arrays.sort(arrivals);

        // 도착 시간, 소요 시간

        // 종료 시간 (오름차순)
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int lastEnd = 0;

        // 계단에 도착하는 시간
        for (int arrival : arrivals) {
            // 현재 계단에 올라가 있는 사람이 있고, 계단 도착 시간보다 종료시간이 작으면
            while (!pq.isEmpty() && pq.peek() <= arrival) {
                pq.poll(); // 계단에 올라간 사람 제거
            }

            // 시작 시간 변수 선언
            int start = 0;
            // 현재 계단에 사람이 K보다 적으면 (계단에 여유가 있으면)
            if (pq.size() < K) {
                // 도착 시간이 곧 시작시간 (바로 시작 가능)
                start = arrival;
            } else {
                // 현재 계단에 있는 사람 중 가장 빨리 나오는 사람의 시간이 곧 시작시간
                // (계단이 꽉 차 있으면, 가장 빨리 끝나는 사람 이후에 시작)
                start = pq.poll();
            }

            // 현재 들어갈 사람의 계단 종료 시간 계산
            int end = start + L;
            pq.add(end);
            // 전체 사람 중 제일 늦게 나오는 사람의 시간 갱신
            lastEnd = Math.max(lastEnd, end);
        }

        bw.write(String.valueOf(lastEnd));

        br.close();
        bw.close();
    }
}