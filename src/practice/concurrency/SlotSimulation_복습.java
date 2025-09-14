package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 프린터에는 동시에 최대 k대의 프린터기가 있다.
 * 각 인쇄 작업은 도착 시간과 소요 시간이 주어진다.
 * 모든 인쇄 작업이 끝나는 시간을 구하라.
 *
 * 입력:
 * N=3, k=2
 * 작업 (도착, 소요):
 * (0, 4), (0, 3), (1, 2)
-----
3 2
0 4
0 3
1 2
-----
 * 출력: 5
 */
public class SlotSimulation_복습 {
    static int N, K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int arrival = Integer.parseInt(st.nextToken());
            int processing = Integer.parseInt(st.nextToken());

            jobs.add(new Job(arrival, processing));
        }

        // 도착 시간 관리 (제한을 관리하기 위한 슬롯)
        int[] slots = new int[K];
        int lastEnd = 0;

        for (Job job : jobs) {
            int arrival = job.arrival;
            int duration = job.duration;

            int index = -1;
            for (int i = 0; i < K; i++) {
                if (slots[i] <= arrival) {
                    index = i;
                    break;
                }
            }

            int start = 0;
            if (index )

            slots[index] = start + duration;
            lastEnd = Math.max(lastEnd, slots[index]);
        }

        bw.write();

        br.close();
        bw.close();
    }

    private static class Job {
        int arrival;
        int duration;

        public Job(int arrival, int duration) {
            this.arrival = arrival;
            this.duration = duration;
        }
    }
}
