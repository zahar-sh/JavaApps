package Programm;

import Game.*;
import MyPackage.Images;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.*;

public class GameField extends JPanel {

    private Player[] players = new Player[1];
    private Enemy[] enemies = new Enemy[20];
    private World world = new World();

    public boolean up, down, left, right, inGame = true;
    private int mouseX, mouseY;

    public Timer timer = new Timer(Constants.DELAY, e -> {
        inGame =  !players[0].isCrash();
        updateAll();
        repaint();
    });

    public GameField() {
        setFocusable(true);
        requestFocus();
        Player player = new Player(Constants.WIDTH / 2, Constants.HEIGHT - Constants.SHIP_SIZE_Y,
                Constants.SHIP_SIZE_X, Constants.SHIP_SIZE_Y,
                Images.convertToBufferedImage(Images.getImage("img\\player.png")));
        players[0] = player;

        for (int i = 0; i < enemies.length; i++) {
            enemies[i] = new Enemy(Constants.SHIP_SIZE_X, Constants.SHIP_SIZE_Y,
                    Images.convertToBufferedImage(Images.getImage("img\\enem.png")));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                if (e.getButton() == MouseEvent.BUTTON1) {
                    player.shot(x, y);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    for (Enemy enemy : enemies) {
                        if (enemy.contains(x, y)) {
                            player.homing(enemy);
                            break;
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
    }

    public void ultra() {
        players[0].ultra();
    }

    public void newGame() {
        for (Player player: players)
            player.newGame();
        for (Enemy enemy : enemies)
            enemy.heal = 0;
    }

    private void updateAll() {
        int speedY = 2;
        int speedX = 2;
        for (Player player : players) {
            if (up) {
                if (!(player.y - speedY <= 0))
                    player.y -= speedY;
                world.move(0, -speedY);
            }
            if (down) {
                if (!(player.y + player.height + speedY > Constants.HEIGHT))
                    player.y += speedY;
                world.move(0, speedY);
            }
            if (left) {
                if (!(player.x - speedX <= 0))
                    player.x -= speedX;
                world.move(-speedX, 0);
            }
            if (right) {
                if (!(player.x + player.width + speedX > Constants.WIDTH))
                    player.x += speedX;
                world.move(speedX, 0);
            }

            double a = player.x - mouseX;
            double b = player.y - mouseY;
            double distance = (Math.sqrt(a * a + b * b));
            double radians = a > 0 ? -Math.acos(b / distance) : Math.acos(b / distance);
            player.setRadians(radians);
            player.update(enemies);
        }
        for (Ship enemy : enemies) {
            enemy.update(players);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        world.draw(g);
        for (Player player : players) {
            player.draw(g);
        }
        for (Ship enemy : enemies) {
            enemy.draw(g);
        }
    }
}