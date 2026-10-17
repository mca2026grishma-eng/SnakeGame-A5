
import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private String direction;

    public Snake() {
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));

        direction = "RIGHT";
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String newDirection) {
        if (newDirection.equals("UP") && !direction.equals("DOWN")) {
            direction = newDirection;
        } else if (newDirection.equals("DOWN") && !direction.equals("UP")) {
            direction = newDirection;
        } else if (newDirection.equals("LEFT") && !direction.equals("RIGHT")) {
            direction = newDirection;
        } else if (newDirection.equals("RIGHT") && !direction.equals("LEFT")) {
            direction = newDirection;
        }
    }

    public void move(boolean grow) {
        Point head = body.getFirst();
        Point newHead;

        switch (direction) {
            case "UP":
                newHead = new Point(head.x, head.y - 1);
                break;
            case "DOWN":
                newHead = new Point(head.x, head.y + 1);
                break;
            case "LEFT":
                newHead = new Point(head.x - 1, head.y);
                break;
            default:
                newHead = new Point(head.x + 1, head.y);
                break;
        }

        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        }
    }

    public boolean hitsItself() {
        Point head = body.getFirst();

        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }

        return false;
    }

    public void reset() {
        body.clear();
        body.add(new Point(5, 5));
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));
        direction = "RIGHT";
    }
}
