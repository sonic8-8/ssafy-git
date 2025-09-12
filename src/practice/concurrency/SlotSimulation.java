package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * 프린터에는 동시에 최대 k대의 프린터기가 있다.
 * 각 인쇄 작업은 도착 시간과 소요 시간이 주어진다.
 * 모든 인쇄 작업이 끝나는 시간을 구하라.
 * <p>
 * 입력:
 * N=3, k=2
 * 작업 (도착, 소요):
 * (0, 4), (0, 3), (1, 2)
 * <p>
 * 출력: 5
 */
public class SlotSimulation {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // K대의 프린터
        // 도착 시간과 소요 시간
        // 모든 작업 끝나는 시간 구하기
        int N = 3;
        int K = 2;

        List<Job> jobs = Arrays.asList(
                new Job(0, 4),
                new Job(0, 3),
                new Job(1, 2)
        );

        // 각 프린터 종료 시간
        int[] slots = new int[K];
        int lastEnd = 0;

        for (Job job : jobs) {
            int arrival = job.arrival;
            int duration = job.duration;

            int index = -1;
            for (int i = 0; i < K; i++) {
                // 종료 시간이 도착 시간 이하면
                if (slots[i] <= arrival) {
                    index = i;
                    break;
                }
            }

            // 시작 시간
            int start = 0;
            // 여유가 있을 경우
            if (index != -1) {
                // 시작 시간이 곧 도착 시간
                start = arrival;
            } else {
                // 여유가 없을 경우
                index = 0;
                // 모든 종료 시간을 살펴보면서
                for (int i = 1; i < K; i++) {
                    // 가장 빨리 끝나는 종료 시간을 찾기
                    if (slots[i] < slots[index]) {
                        index = i;
                    }
                }
                // 가장 빨리 내려오는 종료 시간이 곧 시작 시간
                start = slots[index];
            }

            // 종료시간
            slots[index] = start + duration;
            lastEnd = Math.max(lastEnd, slots[index]);
        }

        bw.write(String.valueOf(lastEnd));

        br.close();
        bw.close();
    }

    public static class Job {
        int arrival;
        int duration;

        public Job(int arrival, int duration) {
            this.arrival = arrival;
            this.duration = duration;
        }
    }
}