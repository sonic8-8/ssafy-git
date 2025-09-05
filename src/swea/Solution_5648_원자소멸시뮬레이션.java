package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 원자 리스트를 순회하며
 * 충돌 가능성이 있는 2개의 원자를 충돌쌍 리스트에 넣습니다.
 * 충돌쌍 리스트를 시간 기준 오름차순 정렬합니다.
 * 시간 순서대로 원자를 충돌시키고 에너지를 계산합니다.
 */

/**
 * 메모리: 39,320 KB
 * 시간: 244 ms
 * 난이도: 모의 SW 역량테스트 19.28%
 */
public class Solution_5648_원자소멸시뮬레이션 {
    static int T;
    static int N;

    static List<Atom> atoms;

    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());

            atoms = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                int energy = Integer.parseInt(st.nextToken());

                atoms.add(new Atom(x * 2, y * 2, dir, energy));
            }

            List<CollisionPair> collisionPairs = findCollisionPairs();
            int totalEnergy = getTotalEnergy(collisionPairs);

            sb.append("#").append(t).append(" ").append(totalEnergy).append("\n");
        }


        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    public static class Atom {
        int x;
        int y;
        int dir;
        int energy;

        public Atom(int x, int y, int dir, int energy) {
            this.x = x;
            this.y = y;
            this.dir = dir;
            this.energy = energy;
        }
    }

    public static class CollisionPair {
        int atomAIndex;
        int atomBIndex;
        int time;

        public CollisionPair(int atomAIndex, int atomBIndex, int time) {
            this.atomAIndex = atomAIndex;
            this.atomBIndex = atomBIndex;
            this.time = time;
        }
    }

    private static List<CollisionPair> findCollisionPairs() {
        ArrayList<CollisionPair> pairs = new ArrayList<>();

        atoms.sort(Comparator.comparingInt((Atom a) -> a.x).thenComparingInt(a -> a.y));

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                Atom atomA = atoms.get(i);
                Atom atomB = atoms.get(j);

                // 수직선
                if (atomA.x == atomB.x) {
                    if (atomA.dir == UP && atomB.dir == DOWN) {
                        pairs.add(new CollisionPair(i, j, Math.abs(atomA.y - atomB.y) / 2));
                    }
                }

                // 수평선
                if (atomA.y == atomB.y) {
                    if (atomA.dir == RIGHT && atomB.dir == LEFT) {
                        pairs.add(new CollisionPair(i, j, Math.abs(atomA.x - atomB.x) / 2));
                    }
                }

                // / 대각선
                // y = x + c
                // -c = x - y
                if (atomA.x - atomA.y == atomB.x - atomB.y) {
                    if (atomA.dir == RIGHT && atomB.dir == DOWN || atomA.dir == UP && atomB.dir == LEFT) {
                        pairs.add(new CollisionPair(i, j, Math.abs(atomA.x - atomB.x)));
                    }
                }

                // \ 대각선
                // y = -x + c
                // x + y = c
                if (atomA.x + atomA.y == atomB.x + atomB.y) {
                    if (atomA.dir == DOWN && atomB.dir == LEFT || atomA.dir == RIGHT && atomB.dir == UP) {
                        pairs.add(new CollisionPair(i, j, Math.abs(atomA.x - atomB.x)));
                    }
                }
            }
        }

        return pairs;
    }

    private static int getTotalEnergy(List<CollisionPair> collisionPairs) {
        // 시간 기준 오름차순
        collisionPairs.sort(Comparator.comparingInt(a -> a.time));

        int[] times = new int[N];
        Arrays.fill(times, Integer.MAX_VALUE);
        int totalEnergy = 0;

        for (CollisionPair pair : collisionPairs) {
            if (times[pair.atomAIndex] < pair.time
                    || times[pair.atomBIndex] < pair.time) {
                continue;
            }

            if (times[pair.atomAIndex] == Integer.MAX_VALUE) {
                times[pair.atomAIndex] = pair.time;
                totalEnergy += atoms.get(pair.atomAIndex).energy;
            }
            if (times[pair.atomBIndex] == Integer.MAX_VALUE) {
                times[pair.atomBIndex] = pair.time;
                totalEnergy += atoms.get(pair.atomBIndex).energy;
            }
        }
        return totalEnergy;
    }
}