
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JFrame {

    private static final int COLUMNS = 24;
    private static final int ROWS = 20;

    private GameController controller;
    private GamePanel gamePanel;
    private JLabel scoreLabel;
    private JLabel statusLabel;
    private JButton pauseButton;
    private Timer gameTimer;

    public SnakeGame() {
        setTitle("SNAKE GAME | Arcade Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        controller = new GameController(COLUMNS, ROWS);
        gamePanel = new GamePanel(controller);

        createInterface();
        setupKeyboardControls();
        setupGameTimer();

        pack();
        setLocationRelativeTo(null);
    }

    private void createInterface() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 12));
        mainPanel.setBackground(new Color(11, 17, 29));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
            18, 18, 18, 18
        ));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);

        JLabel title = new JLabel("SNAKE GAME");
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        title.setForeground(new Color(52, 211, 153));

        JLabel subtitle = new JLabel("ARCADE EDITION");
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 10));
        subtitle.setForeground(new Color(148, 163, 184));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(
            titlePanel, BoxLayout.Y_AXIS
        ));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(subtitle);

        scoreLabel = new JLabel("SCORE  0");
        scoreLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(scoreLabel, BorderLayout.EAST);

        JPanel footer = new JPanel(new BorderLayout(10, 10));
        footer.setOpaque(false);

        statusLabel = new JLabel("  READY TO PLAY");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLabel.setForeground(new Color(52, 211, 153));

        JButton restartButton = createButton("NEW GAME");
        pauseButton = createButton("PAUSE");
        JButton startButton = createButton("START");

        JPanel buttons = new JPanel(new FlowLayout(
            FlowLayout.RIGHT, 8, 0
        ));
        buttons.setOpaque(false);
        buttons.add(startButton);
        buttons.add(pauseButton);
        buttons.add(restartButton);

        footer.add(statusLabel, BorderLayout.WEST);
        footer.add(buttons, BorderLayout.EAST);

        startButton.addActionListener(e -> startNewGame());

        restartButton.addActionListener(e -> startNewGame());

        pauseButton.addActionListener(e -> togglePause());

        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 11));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(31, 41, 55));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(71, 85, 105)
            ),
            BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void setupKeyboardControls() {
        JRootPane root = getRootPane();

        bindKey(root, "UP", KeyEvent.VK_UP, "UP");
        bindKey(root, "DOWN", KeyEvent.VK_DOWN, "DOWN");
        bindKey(root, "LEFT", KeyEvent.VK_LEFT, "LEFT");
        bindKey(root, "RIGHT", KeyEvent.VK_RIGHT, "RIGHT");

        bindKey(root, "W", KeyEvent.VK_W, "UP");
        bindKey(root, "S", KeyEvent.VK_S, "DOWN");
        bindKey(root, "A", KeyEvent.VK_A, "LEFT");
        bindKey(root, "D", KeyEvent.VK_D, "RIGHT");

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0),
                "PAUSE");

        root.getActionMap().put("PAUSE", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePause();
            }
        });
    }

    private void bindKey(
            JRootPane root, String name, int keyCode,
            String direction) {

        root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(keyCode, 0), name);

        root.getActionMap().put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.changeDirection(direction);
            }
        });
    }

    private void setupGameTimer() {
        gameTimer = new Timer(125, e -> {
            controller.update();
            updateInterface();
            gamePanel.repaint();

            if (controller.isGameOver()) {
                gameTimer.stop();
                pauseButton.setText("PAUSE");
            }
        });
    }

    private void startNewGame() {
        controller.resetGame();
        scoreLabel.setText("SCORE  0");
        statusLabel.setText("  GAME IN PROGRESS");
        pauseButton.setText("PAUSE");

        gamePanel.repaint();
        gameTimer.start();
    }

    private void togglePause() {
        if (controller.isGameOver()) {
            return;
        }

        boolean pause = !controller.isPaused();
        controller.setPaused(pause);

        if (pause) {
            gameTimer.stop();
            pauseButton.setText("RESUME");
            statusLabel.setText("  GAME PAUSED");
        } else {
            gameTimer.start();
            pauseButton.setText("PAUSE");
            statusLabel.setText("  GAME IN PROGRESS");
        }

        gamePanel.repaint();
    }

    private void updateInterface() {
        scoreLabel.setText("SCORE  " + controller.getScore());

        if (controller.isGameOver()) {
            statusLabel.setText("  GAME OVER - START A NEW GAME");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SnakeGame game = new SnakeGame();
            game.setVisible(true);
        });
    }
}
