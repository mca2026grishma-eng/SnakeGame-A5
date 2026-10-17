
import java.awt.Point;
import java.util.Random;
import java.util.LinkedList;

public class Food {

    private Point position;
    private final Random random;
    private final int boardWidth;
    private final int boardHeight;

    public Food(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        random = new Random();
        position = new Point(10, 10);
    }

    public Point getPosition() {
        return position;
    }

    public void generate(LinkedList<Point> snakeBody) {
        Point newPosition;

        do {
            newPosition = new Point(
                random.nextInt(boardWidth),
                random.nextInt(boardHeight)
            );
        } while (snakeBody.contains(newPosition));

        position = newPosition;
    }
}
