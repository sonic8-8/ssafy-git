package practice;

public class Array_10 {
    private static int R = 4, C = 5;
    private static char[][] map;
    private static StringBuilder sb = new StringBuilder();

    private static void setup() {
        map = new char[R][C];
        // TODO: 위 배열을 알파벳 대문자 A ~ T로 초기화 하시오.
        char alphabet = 'A';
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                map[row][col] = alphabet++;
            }
        }
        // END
    }

    public static void main(String[] args) {
        setup();
//        rowFirst();
//        colFirst();
//        zigzag();
        snail();
    }

    private static void rowFirst() {
        // TODO: 행우선 탐색을 작성하시오.
        for (int row = 0; row < R; row++) {
            for (int col = 0; col < C; col++) {
                sb.append(map[row][col]);
            }
        }
        // END
        System.out.println(sb.toString().equals("ABCDEFGHIJKLMNOPQRST"));
    }

    private static void colFirst() {
        // TODO: 열우선 탐색을 작성하시오.
        for (int col = 0; col < C; col++) {
            for (int row = 0; row < R; row++) {
                sb.append(map[row][col]);
            }
        }
        // END
        System.out.println(sb.toString().equals("AFKPBGLQCHMRDINSEJOT"));
    }

    private static void zigzag() {
        // TODO: zigzag 탐색을 작성하시오.
        for (int row = 0; row < R; row++) {
            if (row % 2 == 0) {
                for (int col = 0; col < C; col++) {
                    sb.append(map[row][col]);
                }
            } else {
                for (int col = C - 1; col >= 0; col--) {
                    sb.append(map[row][col]);
                }
            }
        }
        // END
        System.out.println(sb.toString().equals("ABCDEJIHGFKLMNOTSRQP"));
    }

    private static void snail() {
        // TODO: 달팽이 탐색을 작성하시오.
        boolean[][] visited = new boolean[R][C];
        int[] dirRow = {0, 1, 0, -1};
        int[] dirCol = {1, 0, -1, 0};

        int row = 0;
        int col = 0;
        int dir = 0;

        for (int i = 0; i < R * C; i++) {
            sb.append(map[row][col]);
            visited[row][col] = true;

            int nextRow = row + dirRow[dir];
            int nextCol = col + dirCol[dir];

            if (nextRow < 0 || R <= nextRow || nextCol < 0 || C <= nextCol
                    || (isIn(nextRow, nextCol) && visited[nextRow][nextCol])) {
                dir = (dir + 1) % 4;
                nextRow = row + dirRow[dir];
                nextCol = col + dirCol[dir];
            }

            row = nextRow;
            col = nextCol;
        }

        // END
        System.out.println(sb.toString().equals("ABCDEJOTSRQPKFGHINML"));
    }

    private static boolean isIn(int row, int col) {
        return 0 <= row && row < R && 0 <= col && col < C;
    }
}

