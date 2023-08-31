import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickBreaker extends JPanel implements ActionListener {
    private int width = 500;
    private int height = 600;
    private int ballX = width / 2;
    private int ballY = height - 50;
    private int ballRadius = 10;
    private int ballXSpeed = 3;
    private int ballYSpeed = -3;
    private int paddleX = width / 2 - 50;
    private int paddleWidth = 100;
    private int paddleHeight = 10;
    private int brickWidth = 50;
    private int brickHeight = 20;
    private boolean[][] bricks = new boolean[10][5];

    public BrickBreaker() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        Timer timer = new Timer(10, this);
        timer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddleX -= 10;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    paddleX += 10;
                }
            }
        });
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = true;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval(ballX - ballRadius, ballY - ballRadius, ballRadius * 2, ballRadius * 2);
        g.fillRect(paddleX, height - paddleHeight, paddleWidth, paddleHeight);
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j]) {
                    g.fillRect(i * brickWidth + i * 10 + 25, j * brickHeight + j * 10 + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballXSpeed;
        ballY += ballYSpeed;
        if (ballX - ballRadius < 0 || ballX + ballRadius > width) {
            ballXSpeed *= -1;
        }
        if (ballY - ballRadius < 0) {
            ballYSpeed *= -1;
        }
        if (ballY + ballRadius > height - paddleHeight && ballX > paddleX && ballX < paddleX + paddleWidth) {
            ballYSpeed *= -1;
        }
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] && ballY - ballRadius < j * brickHeight + j * 10 + brickHeight + 50 && ballY + ballRadius > j * brickHeight + j * 10 + 50 && ballX > i * brickWidth + i * 10 + 25 && ballX < i * brickWidth + i * 10 + brickWidth + 25) {
                    bricks[i][j] = false;
                    ballYSpeed *= -1;
                }
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new BrickBreaker());
        frame.pack();
        frame.setVisible(true);
    }
}
