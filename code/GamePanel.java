
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int CELL_SIZE = 25;
    private static final int COLUMNS = 24;
    private static final int ROWS = 20;

    private final GameController controller;

    private final Color backgroundColor = new Color(18, 25, 38);
    private final Color gridColor = new Color(35, 45, 60);
    private final Color snakeColor = new Color(52, 211, 153);
    private final Color headColor = new Color(16, 185, 129);
    private final Color foodColor = new Color(251, 113, 133);

    public GamePanel(GameController controller) {
        this.controller = controller;

        setPreferredSize(new Dimension(
            COLUMNS * CELL_SIZE,
            ROWS * CELL_SIZE
        ));

        setBackground(backgroundColor);
        setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawGrid(g2);
        drawFood(g2);
        drawSnake(g2);
        drawStatus(g2);

        g2.dispose();
    }

    private void drawGrid(Graphics2D g2) {
        g2.setColor(gridColor);

        for (int x = 0; x <= COLUMNS; x++) {
            g2.drawLine(
                x * CELL_SIZE, 0,
                x * CELL_SIZE, ROWS * CELL_SIZE
            );
        }

        for (int y = 0; y <= ROWS; y++) {
            g2.drawLine(
                0, y * CELL_SIZE,
                COLUMNS * CELL_SIZE, y * CELL_SIZE
            );
        }
    }

    private void drawFood(Graphics2D g2) {
        Point food = controller.getFood().getPosition();

        int x = food.x * CELL_SIZE;
        int y = food.y * CELL_SIZE;

        g2.setColor(new Color(251, 113, 133, 55));
        g2.fillOval(x - 3, y - 3, CELL_SIZE + 6, CELL_SIZE + 6);

        g2.setColor(foodColor);
        g2.fillOval(x + 4, y + 4, CELL_SIZE - 8, CELL_SIZE - 8);
    }

    private void drawSnake(Graphics2D g2) {
        java.util.LinkedList<Point> body =
            controller.getSnake().getBody();

        for (int i = body.size() - 1; i >= 0; i--) {
            Point segment = body.get(i);

            int x = segment.x * CELL_SIZE;
            int y = segment.y * CELL_SIZE;

            g2.setColor(i == 0 ? headColor : snakeColor);

            g2.fillRoundRect(
                x + 2, y + 2,
                CELL_SIZE - 4, CELL_SIZE - 4,
                8, 8
            );

            if (i == 0) {
                drawEyes(g2, x, y);
            }
        }
    }

    private void drawEyes(Graphics2D g2, int x, int y) {
        g2.setColor(Color.WHITE);

        String direction = controller.getSnake().getDirection();

        if (direction.equals("LEFT") || direction.equals("RIGHT")) {
            int eyeX = direction.equals("RIGHT")
                ? x + 16 : x + 5;

            g2.fillOval(eyeX, y + 5, 4, 4);
            g2.fillOval(eyeX, y + 15, 4, 4);
        } else {
            int eyeY = direction.equals("DOWN")
                ? y + 16 : y + 5;

            g2.fillOval(x + 5, eyeY, 4, 4);
            g2.fillOval(x + 15, eyeY, 4, 4);
        }
    }

    private void drawStatus(Graphics2D g2) {
        if (controller.isPaused() && !controller.isGameOver()) {
            drawOverlay(g2, "PAUSED", "Press Resume to continue");
        } else if (controller.isGameOver()) {
            drawOverlay(
                g2,
                "GAME OVER",
                "Final Score: " + controller.getScore()
            );
        }
    }

    private void drawOverlay(
            Graphics2D g2, String title, String subtitle) {

        int width = getWidth();
        int height = getHeight();

        g2.setColor(new Color(10, 15, 25, 205));
        g2.fillRect(0, 0, width, height);

        g2.setFont(new Font("SansSerif", Font.BOLD, 34));
        g2.setColor(Color.WHITE);

        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(
            title,
            (width - fm.stringWidth(title)) / 2,
            height / 2 - 10
        );

        g2.setFont(new Font("SansSerif", Font.PLAIN, 16));
        g2.setColor(new Color(203, 213, 225));

        fm = g2.getFontMetrics();
        g2.drawString(
            subtitle,
            (width - fm.stringWidth(subtitle)) / 2,
            height / 2 + 25
        );
    }
}
