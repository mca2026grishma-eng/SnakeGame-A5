
import java.awt.Point;

public class GameController {

    private Snake snake;
    private Food food;
    private int score;
    private boolean gameOver;
    private boolean paused;
    private final int boardWidth;
    private final int boardHeight;

    public GameController(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        resetGame();
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        if (!gameOver) {
            this.paused = paused;
        }
    }

    public void changeDirection(String direction) {
        if (!gameOver && !paused) {
            snake.setDirection(direction);
        }
    }

    public void update() {
        if (gameOver || paused) {
            return;
        }

        Point nextHead = new Point(snake.getBody().getFirst());

        switch (snake.getDirection()) {
            case "UP":
                nextHead.y--;
                break;
            case "DOWN":
                nextHead.y++;
                break;
            case "LEFT":
                nextHead.x--;
                break;
            default:
                nextHead.x++;
                break;
        }

        if (nextHead.x < 0 || nextHead.x >= boardWidth
                || nextHead.y < 0 || nextHead.y >= boardHeight) {
            gameOver = true;
            return;
        }

        boolean eating = nextHead.equals(food.getPosition());

        if (snake.getBody().contains(nextHead)) {
            Point tail = snake.getBody().getLast();

            if (eating || !nextHead.equals(tail)) {
                gameOver = true;
                return;
            }
        }

        snake.move(eating);

        if (eating) {
            score += 10;
            food.generate(snake.getBody());
        }
    }

    public void resetGame() {
        snake = new Snake();
        food = new Food(boardWidth, boardHeight);
        food.generate(snake.getBody());

        score = 0;
        gameOver = false;
        paused = false;
    }
}
