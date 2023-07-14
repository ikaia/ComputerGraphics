/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beachball;

/**
 *
 * @author Ikaia Melton
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

//Creat
public class BeachBallGame extends JFrame implements KeyListener {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 650;
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 500;
    private static final int BALL_SIZE = 30;
    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 10;
    private static final int BALL_MOVEMENT_DELAY = 30;
    private static final int PADDLE_MOVEMENT_SPEED = 25;
    private static final int BALL_MIN_Y = 50;
    private static final int BALL_MAX_Y = GAME_HEIGHT;
    private static final int BALL_MIN_X = 50;
    private static final int BALL_MAX_X = GAME_WIDTH;
    private static final Color GAME_BACKGROUND_COLOR = Color.BLACK;
    private static final Color BALL_COLOR = Color.RED;
    private static final Color PADDLE_COLOR = Color.BLUE;
    private static final Font SCORE_FONT = new Font("Arial", Font.BOLD, 20);

    private Ball ball;
    private Paddle paddle;
    private Timer ballMovementTimer;
    private int score;
    private JLabel scoreLabel;

    public BeachBallGame() {
        super("BeachBallGame");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        ball = new Ball();
        paddle = new Paddle();

        JPanel gamePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(GAME_BACKGROUND_COLOR);
                g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
                ball.paint(g);
                paddle.paint(g);
                g.setColor(Color.WHITE);
                g.setFont(SCORE_FONT);
                g.drawString("Score: " + score, GAME_WIDTH / 2 - 40, 30);
                }
                };
                gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
                add(gamePanel, BorderLayout.CENTER);

                    ballMovementTimer = new Timer(BALL_MOVEMENT_DELAY, new BallMovementListener());
                    ballMovementTimer.start();

                    scoreLabel = new JLabel("Score: " + score);
                    scoreLabel.setFont(SCORE_FONT);
                    add(scoreLabel, BorderLayout.NORTH);

                    addKeyListener(this);
                    setFocusable(true);
                    setVisible(true);
                }

                private class Ball {
                    private int x;
                    private int y;
                    private int dx;
                    private int dy;

                    public Ball() {
                        x = GAME_WIDTH / 2 - BALL_SIZE / 2;
                        y = BALL_MIN_Y;
                        dx = 4;
                        dy = 2;
                    }

                    public void paint(Graphics g) {
                        g.setColor(BALL_COLOR);
                        g.fillOval(x, y, BALL_SIZE, BALL_SIZE);
                    }

                    public void move() {
                        if (x + dx < BALL_MIN_X || x + dx > BALL_MAX_X - BALL_SIZE) {
                            dx = -dx;
                        }
                        if (y + dy < BALL_MIN_Y) {
                            dy = -dy;
                        } else if (y + dy > BALL_MAX_Y - BALL_SIZE) {
                            ballMovementTimer.stop();
                            JOptionPane.showMessageDialog(BeachBallGame.this, "Game Over! Final score: " + score, "Game Over", JOptionPane.PLAIN_MESSAGE);
                            dispose();
                        } else if (y + dy > paddle.getY() - BALL_SIZE && x + dx > paddle.getX() && x + dx < paddle.getX() + PADDLE_WIDTH) {
                            dy = -dy;
                            score++;
                            scoreLabel.setText("Score: " + score);
                        }
                        x += dx;
                        y += dy;
                    }

                    public int getX() {
                        return x;
                    }

                    public int getY() {
                        return y;
                    }
                }

                private class Paddle {
                    private int x;
                    private int y;

                    public Paddle() {
                        x = GAME_WIDTH / 2 - PADDLE_WIDTH / 2;
                        y = BALL_MAX_Y - PADDLE_HEIGHT - 10;
                    }

                    public void paint(Graphics g) {
                        g.setColor(PADDLE_COLOR);
                        g.fillRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
                    }

                    public void moveLeft() {
                        if (x - PADDLE_MOVEMENT_SPEED >= 0) {
                            x -= PADDLE_MOVEMENT_SPEED;
                        }
                    }

                    public void moveRight() {
                        if (x + PADDLE_MOVEMENT_SPEED <= GAME_WIDTH - PADDLE_WIDTH) {
                            x += PADDLE_MOVEMENT_SPEED;
                        }
                    }

                    public int getX() {
                        return x;
                    }

                    public int getY() {
                        return y;
                    }
                }

                private class BallMovementListener implements ActionListener {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ball.move();
                        repaint();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_LEFT) {
                        paddle.moveLeft();
                    } else if (keyCode == KeyEvent.VK_RIGHT) {
                        paddle.moveRight();
                    }
                    repaint();
                }

                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }

                public static void main(String[] args) {
                    new BeachBallGame();
                }
                }