package Rain_drop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

    private static Game game;
    private static Image background;
    private static Image game_over;
    private static Image drop;
    private static float drop_left = 200;
    private static float drop_top = -100;
    private static float drop_v = 200;
    private static long frame_time;
    private static int score = 0;

    public static void main(String[] args) throws IOException {
        game = new Game();
        background = ImageIO.read(Game.class.getResourceAsStream("background.png"));
        game_over = ImageIO.read(Game.class.getResourceAsStream("game_over.png"));
        drop = ImageIO.read(Game.class.getResourceAsStream("drop.png"));
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setLocation(350,350);
        game.setSize(906,478);
        game.setResizable(false);
        frame_time = System.nanoTime();
        GameField field = new GameField();
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean is_drop = x >= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                if (is_drop) {
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game.getWidth() - drop.getHeight(null)));
                    drop_v = drop_v + 15;
                    score++;
                    game.setTitle("Score: " + score);
                }
            }
        });
        game.add(field);
        game.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - frame_time) * 0.000000001f;
        frame_time = current_time;
        drop_top = drop_top + drop_v * delta_time;
        g.drawImage(background,0,0,null);
        g.drawImage(drop,(int) drop_left, (int) drop_top,null);
        if (drop_top > game.getHeight()) g.drawImage(game_over,280,120,null);
    }

    public static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}
