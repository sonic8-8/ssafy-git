package etc;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class 일타싸피_벡터 {

    static final String NICKNAME = "GWANGJU05_이승환";
    static final String HOST = "127.0.0.1";
    static final int PORT = 1447;
    static final int CODE_SEND = 9901;
    static final int CODE_REQUEST = 9902;
    static final int SIGNAL_ORDER = 9908;
    static final int SIGNAL_CLOSE = 9909;

    static final int TABLE_WIDTH = 254;
    static final int TABLE_HEIGHT = 127;
    static final int NUMBER_OF_BALLS = 6;
    public static final int X = 0;
    public static final int Y = 1;
    static final int NOT_ASSIGNED = -1;
    static final int[][] HOLES = {{X, X}, {127, X}, {254, X}, {X, 127}, {127, 127}, {254, 127}};
    static final float BALL_RADIUS = 5.73f;

    public static void main(String[] args) {

        Socket socket = null;
        byte[] bytes = new byte[1024];
        float[][] balls = new float[NUMBER_OF_BALLS][2];
        int order = X;

        try {
            socket = new Socket();
            System.out.println("Trying Connect: " + HOST + ":" + PORT);
            socket.connect(new InetSocketAddress(HOST, PORT));
            System.out.println("Connected: " + HOST + ":" + PORT);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            String send_data = CODE_SEND + "/" + NICKNAME + "/";
            bytes = send_data.getBytes("UTF-8");
            os.write(bytes);
            os.flush();
            System.out.println("Ready to play!\n--------------------");

            while (socket != null) {
                bytes = new byte[1024];
                int count_byte = is.read(bytes);
                String recv_data = new String(bytes, X, count_byte, "UTF-8");
                System.out.println("Data Received: " + recv_data);

                String[] split_data = recv_data.split("/");
                int idx = X;
                try {
                    for (int i = X; i < NUMBER_OF_BALLS; i++) {
                        for (int j = X; j < 2; j++) {
                            balls[i][j] = Float.parseFloat(split_data[idx++]);
                        }
                    }
                } catch (Exception e) {
                    bytes = (CODE_REQUEST + "/" + CODE_REQUEST).getBytes("UTF-8");
                    os.write(bytes);
                    os.flush();
                    System.out.println("Received Data has been corrupted, Resend Requested.");
                    continue;
                }

                // Signal check
                if (balls[X][X] == SIGNAL_ORDER) {
                    order = (int) balls[X][Y];
                    System.out.println("\n* You will be the " + (order == Y ? "first" : "second") + " player. *\n");
                    continue;
                } else if (balls[X][X] == SIGNAL_CLOSE) {
                    break;
                }

                float whiteBall_x = balls[X][X];
                float whiteBall_y = balls[X][Y];

                // ------------------ 목표 공 및 포켓 선택 ------------------
                float targetBall_x = NOT_ASSIGNED;
                float targetBall_y = NOT_ASSIGNED;
                float pocketX = NOT_ASSIGNED;
                float pocketY = NOT_ASSIGNED;
                float minScore = Float.MAX_VALUE;

                int[] candidates = (order == 1) ? new int[]{1, 3, 5} : new int[]{2, 4, 5};

                for (int c : candidates) {
                    if (balls[c][X] == NOT_ASSIGNED) continue;
                    float tx = balls[c][X];
                    float ty = balls[c][Y];

                    for (int p = 0; p < HOLES.length; p++) {
                        float px = HOLES[p][X];
                        float py = HOLES[p][Y];

                        boolean blocked = false;
                        for (int b = 0; b < NUMBER_OF_BALLS; b++) {
                            if (b == 0 || b == c) continue;
                            if (balls[b][X] == NOT_ASSIGNED) continue;
                            if (isBlocked(balls[b][X], balls[b][Y], tx, ty, px, py, BALL_RADIUS) ||
                                    isBlocked(balls[b][X], balls[b][Y], whiteBall_x, whiteBall_y, tx, ty, BALL_RADIUS)) {
                                blocked = true;
                                break;
                            }
                        }

                        if (!blocked) {
                            double dist = Math.hypot(tx - whiteBall_x, ty - whiteBall_y) + Math.hypot(px - tx, py - ty);
                            if (dist < minScore) {
                                minScore = (float) dist;
                                targetBall_x = tx;
                                targetBall_y = ty;
                                pocketX = px;
                                pocketY = py;
                            }
                        }
                    }
                }

                // 선택된 공이 없으면 마지막 공
                if (targetBall_x == NOT_ASSIGNED) {
                    targetBall_x = balls[5][X];
                    targetBall_y = balls[5][Y];
                    pocketX = HOLES[0][X];
                    pocketY = HOLES[0][Y];
                }

                // ------------------ 각도 계산 ------------------
                float angle = calculateAngle(whiteBall_x, whiteBall_y, targetBall_x, targetBall_y, pocketX, pocketY, BALL_RADIUS);

                // 힘 최대
                float power = 100;

                // 전송
                String merged_data = angle + "/" + power + "/";
                bytes = merged_data.getBytes("UTF-8");
                os.write(bytes);
                os.flush();
                System.out.println("Data Sent: " + merged_data);
            }

            os.close();
            is.close();
            socket.close();
            System.out.println("Connection Closed.\n--------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------------ 유틸 함수 ------------------

    public static boolean isBlocked(float pointX, float pointY, float x1, float y1, float x2, float y2, float threshold) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float lengthSquared = dx * dx + dy * dy;
        if (lengthSquared == 0) return false;

        float t = ((pointX - x1) * dx + (pointY - y1) * dy) / lengthSquared;
        t = Math.max(0, Math.min(1, t));
        float projX = x1 + t * dx;
        float projY = y1 + t * dy;
        float dist = (float) Math.hypot(projX - pointX, projY - pointY);

        return dist < threshold;
    }

    public static float calculateAngle(
            float whiteX, float whiteY,
            float targetX, float targetY,
            float pocketX, float pocketY,
            float ballRadius) {

        float vTargetToPocketX = pocketX - targetX;
        float vTargetToPocketY = pocketY - targetY;

        double dist = Math.sqrt(vTargetToPocketX * vTargetToPocketX + vTargetToPocketY * vTargetToPocketY);

        float hitX = targetX - (float) (ballRadius * vTargetToPocketX / dist);
        float hitY = targetY - (float) (ballRadius * vTargetToPocketY / dist);

        float vWhiteToHitX = hitX - whiteX;
        float vWhiteToHitY = hitY - whiteY;

        float refX = 0;
        float refY = -1;

        double dot = vWhiteToHitX * refX + vWhiteToHitY * refY;
        double cross = vWhiteToHitX * refY - vWhiteToHitY * refX;

        double angleRad = Math.acos(dot / Math.sqrt(vWhiteToHitX * vWhiteToHitX + vWhiteToHitY * vWhiteToHitY));
        float angleDeg = (float) Math.toDegrees(angleRad);

        if (cross < 0) {
            angleDeg = 360 - angleDeg;
        }

        return angleDeg;
    }
}

