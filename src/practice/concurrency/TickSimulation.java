package practice.concurrency;

import java.util.*;

/**
 * 문제:
 * N명의 손님이 햄버거 가게에 줄을 선다.
 * 각 손님은 도착 시간과 햄버거를 먹는 시간이 주어진다.
 * 가게에는 좌석이 k개뿐이라, 자리가 없으면 기다려야 한다.
 * 모든 손님이 햄버거를 다 먹는 데 걸리는 총 시간을 구하라.
 *
 * 입력:
 * N=3, k=1
 * 손님 (도착, 먹는시간):
 * (0, 3), (1, 2), (2, 2)
 *
 * 출력: 7
 */

/**
 * 아이디어:
 * 각 상태를 자료구조로 나누는 것이 핵심
 */
public class TickSimulation {
    public static void main(String[] args) {
        int N = 3, K = 1;
        List<Person> people = Arrays.asList(
                new Person(0, 3),
                new Person(1, 2),
                new Person(2, 2)
        );
        // 만약 입력이 정렬되어있지 않다면 정렬 필요:
        people.sort(Comparator.comparingInt(p -> p.arrival));

        Queue<Person> waiting = new ArrayDeque<>();
        List<Person> eating = new ArrayList<>();

        int time = 0, done = 0;
        int index = 0; // 다음 도착할 사람 인덱스
        while (done < N) {
            // 현재 time에 도착하는 사람들 추가 (index 이동)
            while (index < people.size() && people.get(index).arrival == time) {
                Person p = people.get(index++);
                waiting.add(new Person(p.arrival, p.eatingTime));
            }

            // 먹던 손님 시간 감소
            List<Person> finished = new ArrayList<>();
            for (Person p : eating) {
                p.eatingTime--;
                if (p.eatingTime == 0) finished.add(p);
            }
            eating.removeAll(finished);
            done += finished.size();

            // 빈자리 있으면 waiting -> eating
            while (!waiting.isEmpty() && eating.size() < K) {
                eating.add(waiting.poll());
            }

            if (done >= N) break; // 마지막 사람 끝났으면 time 증가 없이 종료
            time++;
        }
        System.out.println("총 시간: " + time); // 7
    }

    public static class Person {
        int arrival;
        int eatingTime;

        public Person(int arrival, int eatingTime) {
            this.arrival = arrival;
            this.eatingTime = eatingTime;
        }
    }
}
