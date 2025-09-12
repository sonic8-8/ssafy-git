package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 은행에 창구가 k개 있다.
 * 손님은 도착하면 waitingQueue에 들어가고, 창구가 비면 차례대로 처리한다.
 * 각 손님의 처리 시간은 동일하게 1이다.
 * 모든 손님이 업무를 마치는 시간을 구하라.
 *
 * 입력:
 * N=5, k=2
 * 도착시간 = [0, 0, 1, 2, 3]
 *
 * 출력: 5
 */
public class QueueSimulation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = 5;
        int K = 2;
        Queue<Integer> arrivals = new ArrayDeque<>(Arrays.asList(0, 0, 1, 2, 3));
        Queue<Integer> processing = new ArrayDeque<>();

        // 현재 시간
        int time = 0;
        // 손님이 있는 경우나 처리 창구 처리중이라면
        while (!arrivals.isEmpty() || !processing.isEmpty()) {
            // 창구에 사람이 있고 현재 시간이 처리 완료 시간이면
            while (!processing.isEmpty() && processing.peek() == time) {
                // 창구에서 제거
                processing.poll();
            }

            // 도착해있는 사람이 있고 도착 시간과 현재 시간이 일치한 사람 그리고 창구에 여유가 있다면
            while (!arrivals.isEmpty() && arrivals.peek() <= time && processing.size() < K) {
                // 도착해 있는 사람 한 명을 꺼내서
                arrivals.poll();
                // 창구에 넣기 (처리 시간이 1이므로 종료 시간이 time + 1 임)
                processing.add(time + 1);
            }

            time++;
        }

        bw.write(String.valueOf(time));

        br.close();
        bw.close();
    }
}