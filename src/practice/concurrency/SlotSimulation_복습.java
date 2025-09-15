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
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int arrival = Integer.parseInt(st.nextToken());
            int duration = Integer.parseInt(st.nextToken());
            jobs.add(new Job(arrival, duration));
        }

        int[] slots = new int[K];
        int lastEnd = 0;

        for (Job job : jobs) {
            int index = -1;
            for (int i = 0; i < K; i++) {
                if (slots[i] <= job.arrival) {
                    index = i;
                    break;
                }
            }

            int start = 0;
            if (index != -1) {
                start = job.arrival;
            } else {
                index = 0;
                for (int i = 1; i < K; i++) {
                    if (slots[i] < slots[index]) {
                        index = i;
                    }
                }
                start = slots[index];
            }

            int end = start + job.duration;
            slots[index] = end;
            lastEnd = Math.max(lastEnd, slots[index]);
        }

        bw.write(String.valueOf(lastEnd));

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