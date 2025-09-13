package practice.concurrency;

import java.io.*;
import java.util.*;

/**
 * 문제:
 * N명의 손님이 햄버거 가게에 줄을 선다.
 * 각 손님은 도착 시간과 햄버거를 먹는 시간이 주어진다.
 * 가게에는 좌석이 K개 뿐이라, 자리가 없으면 기다려야 한다.
 * 모든 손님이 햄버거를 다 먹는 데 걸리는 총 시간을 구하라.
 * <p>
 * 입력:
 * N=3, K=1
 * 손님 (도착, 먹는시간):
 * (0, 3), (1, 2), (2, 2)
-----
콘솔 입력
3
1
0 3
1 2
2 2
-----
출력
7
-----
 */
public class TickSimulation_복습 {
    static int N;
    static int K;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        N = Integer.parseInt(br.readLine());
        K = Integer.parseInt(br.readLine());

        // 좌석 K개 제약
        // 햄버거 다 먹는데 걸리는 총 시간 구하기

        List<Person> people = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int arrival = Integer.parseInt(st.nextToken());
            int eatingTime = Integer.parseInt(st.nextToken());
            people.add(new Person(arrival, eatingTime));
        }

        // 대기열
        Queue<Person> waiting = new ArrayDeque<>();
        // 먹고 있는 사람
        List<Person> eating = new ArrayList<>();

        int time = 0; // 시간
        int index = 0; // 다음에 도착할 사람 인덱스
        int done = 0; // 다 먹은 사람 수

        while (done < N) {
            // 대기열 형성
            while (index < people.size() && people.get(index).arrival == time) {
                Person current = people.get(index);
                waiting.add(current);
                index++;
            }

            // 먹고 있는 사람들 시간 흐름 반영
            List<Person> finished = new ArrayList<>();
            for (Person p : eating) {
                p.eatingTime--;

                if (p.eatingTime == 0) {
                    finished.add(p);
                }
            }
            // 한 번에 제거
            eating.removeAll(finished);
            done += finished.size();


            // 대기열에 자리가 있을 경우 (K보다 작을때)
            // 대기열에 있던 사람들 eating으로 옮기기
            while (!waiting.isEmpty() && eating.size() < K) {
                eating.add(waiting.poll());
            }

            // 시간 증가
            if (done >= N) {
                break;
            }
            time++;
        }

        bw.write(String.valueOf(time));

        br.close();
        bw.close();
    }

    private static class Person {
        int arrival;
        int eatingTime;

        public Person(int arrival, int eatingTime) {
            this.arrival = arrival;
            this.eatingTime = eatingTime;
        }
    }
}
