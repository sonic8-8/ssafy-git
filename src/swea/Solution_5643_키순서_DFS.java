package swea;

import java.io.*;
import java.util.*;

/**
 * 아이디어:
 * 자신의 키가 몇 번째 인지 알 수 있는 학생 숫자 계산하기
 * a b 라면 a < b인 관계다
 *
 * 학생 수가 2일때
 * 학생 하나의 정확한 순서를 알기 위해선 관계 정보가 1개 필요하다
 *
 * 학생 수가 3일때
 * 학생 하나의 정확한 순서를 알기 위해선 관계 정보가 2개 필요할까? 예시를 생각해보자
 * 예를 들어 a b c 가 있을 때, a는 b, c와 관계 정보를 알아야한다.
 * 구체적으로 a b, a c라면
 * a보다 큰 게 b와 c 2개니까 a는 마지막에 있다고 판단할 수 있다. 즉, 정확한 위치를 알 수 있다
 * b와 c도 a보다 크다는 정보는 주어졌기 때문에
 * b와 c의 정확한 위치를 알기 위해선 b와 c의 관계를 알기 위한 1개의 관계 정보가 필요하다
 * 예를 들어, b c 로 주어지면 a, b, c의 정확한 위치를 파악할 수 있다
 *
 * 그런데, 학생 수가 3일때 학생 하나의 정확한 순서를 알기 위한 또다른 예시가 있다
 * 예를 들어 a b c가 있을 때, c의 정보를 알고 싶다고 하면
 * c는 a와 b의 관계 정보를 모두 알아야할까?
 * c와 직접적으로 연결되지 않은 정보를 통해서도 관계를 파악할 수 있다.
 * 구체적으로 a b, b c로 주어져도 정확한 위치를 알 수 있다
 * b가 a보다 크다는 관계를 통해 b에게 a를 흡수시키고
 * b와 c의 크기 비교만 하면 되는 것이다
 * a를 b에 흡수시켜서 c 앞에 2개의 노드가 있다고 생각하는 식이다
 *
 * 이를 코드로 구현하기 위해 생각해보니
 * 결론은 N개의 노드가 주어졌을 때,
 * 하나의 노드 앞뒤에 N - 1개의 노드의 연결돼있다는 정보만 알면 정확한 순서를 알 수 있다고 판단했다
 * 즉, 노드 앞뒤로 N - 1개가 존재한다는 것만 알면 된다
 *
 * 여러 개의 화살표를 받을 수 있으니까 인접리스트로 구현해보자
 */

/**
 * 메모리: 100,140 KB
 * 시간: 1484 ms
 * 난이도: D4
 */
public class Solution_5643_키순서_DFS {
    static int T;
    static int N, M;

    static List<Integer>[] afterGraph;
    static List<Integer>[] beforeGraph;
    static boolean[] visited;

    static int sum;
    static int count;

    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        T = Integer.parseInt(br.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(br.readLine());
            M = Integer.parseInt(br.readLine());

            afterGraph = new ArrayList[N + 1];
            beforeGraph = new ArrayList[N + 1];
            for (int i = 1; i <= N; i++) {
                afterGraph[i] = new ArrayList<>();
                beforeGraph[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());

                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                afterGraph[from].add(to);
                beforeGraph[to].add(from);
            }

            count = 0;

            for (int i = 1; i <= N; i++) {
                sum = 0;
                visited = new boolean[N + 1];
                afterDfs(i);

                visited = new boolean[N + 1];
                beforeDfs(i);

                if (sum == N - 1) {
                    count++;
                }
            }

            sb.append('#').append(t).append(' ').append(count).append('\n');
        }

        bw.write(sb.toString());

        br.close();
        bw.close();
    }

    private static void afterDfs(int node) {
        visited[node] = true;

        for (int next : afterGraph[node]) {
            if (!visited[next]) {
                sum++;
                afterDfs(next);
            }
        }
    }

    private static void beforeDfs(int node) {
        visited[node] = true;

        for (int next : beforeGraph[node]) {
            if (!visited[next]) {
                sum++;
                beforeDfs(next);
            }
        }
    }
}