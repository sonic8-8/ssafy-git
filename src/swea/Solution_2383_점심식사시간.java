package swea;

import java.io.*;
import java.util.*;

public class Solution_2383_점심식사시간 {
    static int T;
    static int N;
    static int[][] board;

    static List<int[]> stairs;
    static List<int[]> people;
    static int[] select;

    static int lastEnd = Integer.MAX_VALUE;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            board = new int[N][N];

            stairs = new ArrayList<>();
            people = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                    if (board[i][j] > 1) {
                        stairs.add(new int[]{i, j, board[i][j]});
                        continue;
                    }
                    if (board[i][j] == 1) {
                        people.add(new int[]{i, j});
                    }
                }
            }

            select = new int[people.size()];

            // 계단 입구까지 이동
            // 계단 내려가는 시간
            // 최대 3명까지 올라가기 가능
            lastEnd = Integer.MAX_VALUE;
            subset(0);

            sb.append("#").append(t).append(" ").append(lastEnd).append("\n");
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    // 부분 집합으로 모든 경우의 수를 생성합니다
    private static void subset(int index) {
        if (index == people.size()) {
            // 시뮬레이션
            simulate();
            return;
        }

        // 1번 계단 선택
        select[index] = 0;
        subset(index + 1);


        // 2번 계단 선택
        select[index] = 1;
        subset(index + 1);
    }

    private static void simulate() {
        List<int[]> arrivals1 = new ArrayList<>();
        List<int[]> arrivals2 = new ArrayList<>();

        // 사람별 도착시간 계산
        for (int i = 0; i < people.size(); i++) {
            int[] person = people.get(i);
            int stairIndex = select[i];
            int[] stair = stairs.get(stairIndex);

            int dist = Math.abs(person[0] - stair[0]) + Math.abs(person[1] - stair[1]);
            int arrivalTime = dist; // 도착 후 1분은 PQ에서 처리
            if (stairIndex == 0) arrivals1.add(new int[]{arrivalTime});
            else arrivals2.add(new int[]{arrivalTime});
        }

        // 도착 시간 오름차순 정렬
        arrivals1.sort(Comparator.comparingInt(a -> a[0]));
        arrivals2.sort(Comparator.comparingInt(a -> a[0]));

        // PQ로 계단 위 사람 종료 시간 관리
        PriorityQueue<Integer> pq1 = new PriorityQueue<>();
        PriorityQueue<Integer> pq2 = new PriorityQueue<>();
        int endTime = 0;

        // 계단 1 시뮬레이션
        for (int[] arrival : arrivals1) {
            int arrive = arrival[0];

            // 계단 끝난 사람 제거
            while (!pq1.isEmpty() && pq1.peek() <= arrive) pq1.poll();

            int start;
            if (pq1.size() < 3) {
                start = arrive + 1; // 도착 후 1분 기다린 뒤 바로 시작
            } else {
                start = Math.max(arrive + 1, pq1.poll()); // 도착+1과 가장 빨리 끝나는 시간 중 늦은 것
            }

            int end = start + stairs.get(0)[2]; // 계단 길이 더하기
            pq1.add(end);
            endTime = Math.max(endTime, end);
        }

        // 계단 2 시뮬레이션
        for (int[] arrival : arrivals2) {
            int arrive = arrival[0];

            while (!pq2.isEmpty() && pq2.peek() <= arrive) pq2.poll();

            int start;
            if (pq2.size() < 3) {
                start = arrive + 1;
            } else {
                start = Math.max(arrive + 1, pq2.poll());
            }

            int end = start + stairs.get(1)[2];
            pq2.add(end);
            endTime = Math.max(endTime, end);
        }

        lastEnd = Math.min(lastEnd, endTime); // 모든 부분집합 중 최소 시간 갱신
    }
}