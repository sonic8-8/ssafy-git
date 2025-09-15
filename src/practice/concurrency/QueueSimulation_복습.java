package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 은행에 창구가 k개 있다.
 * 손님은 도착하면 waitingQueue에 들어가고, 창구가 비면 차례대로 처리한다.
 * 각 손님의 처리 시간은 동일하게 1이다.
 * 모든 손님이 업무를 마치는 시간을 구하라.
 * <p>
 * 입력:
 * N=5, k=2
 * 도착시간 = [0, 0, 1, 2, 3]
 * <p>
 * -----
입력
5 2
0 0 1 2 3
 * -----
 * 출력: 5
 */
public class QueueSimulation_복습 {
    static int N, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        Queue<Integer> waiting = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            waiting.add(Integer.parseInt(st.nextToken()));
        }

        Queue<Integer> processing = new ArrayDeque<>();
        int time = 0;
        while (!waiting.isEmpty() || !processing.isEmpty()) {
            while (!processing.isEmpty() && processing.peek() == time) {
                processing.poll();
            }

            while (!waiting.isEmpty() && waiting.peek() <= time && processing.size() < K) {
                waiting.poll();
                processing.add(time + 1);
            }

            time++;
        }

        bw.write(String.valueOf(time));

        br.close();
        bw.close();
    }
}

// -----
//static int N, K;
//
//public static void main(String[] args) throws IOException {
//    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
//
//    StringTokenizer st = new StringTokenizer(br.readLine());
//    N = Integer.parseInt(st.nextToken());
//    K = Integer.parseInt(st.nextToken());
//
//    Queue<Integer> waiting = new ArrayDeque<>();
//
//    st = new StringTokenizer(br.readLine());
//    for (int i = 0; i < N; i++) {
//        waiting.add(Integer.parseInt(st.nextToken()));
//    }
//
//    Queue<Integer> processing = new ArrayDeque<>();
//    int time = 0;
//
//    while (!waiting.isEmpty() || !processing.isEmpty()) {
//        // 창구 먼저 확인
//        while (!processing.isEmpty() && processing.peek() == time) {
//            processing.poll();
//        }
//
//        // 창구에 추가
//        while (!waiting.isEmpty() && waiting.peek() <= time && processing.size() < K) {
//            waiting.poll(); //
//            processing.add(time + 1); // 1초 후 종료
//        }
//
//        time++;
//    }
//
//    bw.write(String.valueOf(time));
//
//    br.close();
//    bw.close();
//}