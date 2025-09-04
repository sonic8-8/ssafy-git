package swea;

import java.io.*;
import java.util.*;

public class Solution_5648_원자소멸시뮬레이션_참고용 {
    static final int[] dx = {0, 0, -1, 1}; //상 하 좌 우
    static final int[] dy = {1, -1, 0, 0};
    static final int MIN = 0, MAX = 4000; // 스케일 2배 적용
    static final int W = 5001; // 해시 키용 (x*W + y) 에서 충돌 방지용 폭

    static class Atom {
        int x, y, d, k;

        Atom(int x, int y, int d, int k) {
            this.x = x;
            this.y = y;
            this.d = d;
            this.k = k;
        }
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            int n = Integer.parseInt(br.readLine().trim());
            List<Atom> atoms = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());

                //음수 좌표 보정 + 0.5 보행 대신 격자 2배 확장
                x = (x + 1000) * 2;
                y = (y + 1000) * 2;
                atoms.add(new Atom(x, y, d, k));
            }

            long total = 0;
            while (atoms.size() >= 2) {
                HashSet<Integer> seen = new HashSet<>();
                HashSet<Integer> hits = new HashSet<>();
                List<Atom> next = new ArrayList<>();

                // 1) 전 원자 1칸 이동 & 위치 충돌 후보 수집
                for (Atom a : atoms) {
                    int nx = a.x + dx[a.d];
                    int ny = a.y + dy[a.d];
                    if (nx < MIN || nx > MAX || ny < MIN || ny > MAX) continue;
                    int key = nx * W + ny;
                    if (!seen.add(key)) hits.add(key); // 이미 있던 위치면 충돌 위치로 표시
                    next.add(new Atom(nx, ny, a.d, a.k));
                }

                // 2) 충돌 위치에 있는 원자들은 에너지 합산 후 제거
                atoms = new ArrayList<>();
                for (Atom a : next) {
                    int key = a.x * W + a.y;
                    if (hits.contains(key)) total += a.k;
                    else atoms.add(a);
                }
            }
            out.append('#').append(tc).append(' ').append(total).append('\n');

        }
        System.out.println(out);
    }
}