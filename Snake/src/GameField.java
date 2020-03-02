import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 32;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private int appleX;
    private int appleY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField() {
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new KeyListener());
        setFocusable(true);
    }

    public void initGame() {
        this.dots = 3;
        for (int i = 0; i < dots; i++) {
            this.x[i] = 96 - i * DOT_SIZE;
            this.y[i] = 96;
        }
        new Timer(250,this).start();
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;

    }

    public void loadImages() {
        ImageIcon apple = new ImageIcon("apple.png");
        this.apple = apple.getImage();
        ImageIcon dot = new ImageIcon("dot.png");
        this.dot = dot.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i] , this);
            }
        } else {
            String str = "GAME OVER";
            Font font = new Font("Arial", Font.BOLD, 32);
            g.setColor(Color.RED);
            g.setFont(font);
            g.drawString(str,240,SIZE/2);
        }
    }

    public void move() {
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left){
            x[0] -= DOT_SIZE;
        }
        if (right) {
            x[0] += DOT_SIZE;
        }
        if (up) {
            y[0] -= DOT_SIZE;
        }
        if (down) {
            y[0] += DOT_SIZE;
        }
    }

    public void checkApple() {
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
        if (x[0] > SIZE) {
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > SIZE) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkCollisions();
            checkApple();
            move();
        }
        repaint();
    }

    class KeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && ! right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && ! left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && ! down) {
                left = false;
                right = false;
                up = true;
            }
            if (key == KeyEvent.VK_DOWN && ! up) {
                left = false;
                right = false;
                down = true;
            }
        }
    }
}