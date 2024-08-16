package Programm;

import Game.CenterLayout;
import Game.Constants;
import MyPackage.Images;
import MyPackage.Structure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createGUI);
    }

    public static void createGUI() {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);

        Image image = Images.getImage("img\\background.jpg");
        Structure<String, JPanel> structure = new Structure<>();
        String[] strings = {"New Game", "Button", "Settings", "Exit"};

        GameField gamePanel = new GameField();

        {
            for (int i = 0; i < strings.length - 1; i++) {
                structure.setNode(strings[i]);
            }

            JPanel menuPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0,
                            frame.getWidth(), frame.getHeight(), null);
                }
            };
            menuPanel.setLayout(new CenterLayout(Constants.DOT_SIZE_Y, Constants.DOT_SIZE_Y * 8,
                    Constants.DOT_SIZE_X * 8, Constants.DOT_SIZE_Y,
                    Constants.DISPLAY_SIZE));
            ActionListener actionListener = e -> {
                String command = e.getActionCommand();
                int i = 0;
                for (; i < strings.length - 1; i++) {
                    if (command.equals(strings[i])) {
                        structure.get().setVisible(false);
                        structure.nextNode(strings[i]);
                        JPanel panel = structure.get();
                        panel.setVisible(true);
                        if (i == 0) {
                            panel.setFocusable(true);
                            panel.requestFocus();
                            gamePanel.timer.start();
                        }
                    }
                }
                if (command.equals(strings[i])) {
                    System.exit(0);
                }
            };

            for (String s : strings) {
                JButton button = getButton(s);
                button.addActionListener(actionListener);
                menuPanel.add(button);
            }
            menuPanel.setVisible(true);
            structure.put(menuPanel);
        }

        structure.nextNode(strings[0]);

        {
            gamePanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (!gamePanel.inGame) {
                        gamePanel.newGame();
                        return;
                    }
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_W) gamePanel.up = true;
                    if (key == KeyEvent.VK_S) gamePanel.down = true;
                    if (key == KeyEvent.VK_A) gamePanel.left = true;
                    if (key == KeyEvent.VK_D) gamePanel.right = true;
                    if (key == KeyEvent.VK_ESCAPE) {
                        JPanel panel = structure.get();
                        panel.setVisible(false);
                        panel.setFocusable(false);
                        structure.previousNode();
                        panel = structure.get();
                        panel.setVisible(true);
                        panel.setFocusable(true);
                        panel.requestFocus();
                        gamePanel.timer.stop();
                    }
                    if (key == KeyEvent.VK_SPACE) {
                        gamePanel.ultra();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_W) gamePanel.up = false;
                    if (key == KeyEvent.VK_S) gamePanel.down = false;
                    if (key == KeyEvent.VK_A) gamePanel.left = false;
                    if (key == KeyEvent.VK_D) gamePanel.right = false;
                }
            });
            gamePanel.setVisible(false);
            structure.put(gamePanel);
            structure.previousNode();
        }

        structure.nextNode(strings[1]);

        {
            String exit = "Exit";
            JPanel panel1 = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0,
                            frame.getWidth(), frame.getHeight(), null);
                    g.setColor(Color.BLACK);
                    g.drawString(strings[1], 100, 100);
                }
            };
            panel1.setLayout(new CenterLayout(Constants.DOT_SIZE_Y, Constants.DOT_SIZE_Y * 14,
                    Constants.DOT_SIZE_X * 8, Constants.DOT_SIZE_Y,
                    Constants.DISPLAY_SIZE));
            ActionListener actionListener1 = e -> {
                String command = e.getActionCommand();
                if (command.equals(exit)) {
                    structure.get().setVisible(false);
                    structure.previousNode();
                    structure.get().setVisible(true);
                }
            };
            JButton button = getButton(exit);
            button.addActionListener(actionListener1);
            panel1.add(button);
            structure.put(panel1);
            panel1.setVisible(false);
            structure.previousNode();
        }

        structure.nextNode(strings[2]);

        {
            String exit = "Exit";
            JPanel panel1 = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0,
                            getWidth(), getHeight(), null);
                }
            };
            panel1.setLayout(new CenterLayout(Constants.DOT_SIZE_Y, Constants.DOT_SIZE_Y * 14,
                    Constants.DOT_SIZE_X * 8, Constants.DOT_SIZE_Y,
                    Constants.DISPLAY_SIZE));
            ActionListener actionListener1 = e -> {
                String command = e.getActionCommand();
                if (command.equals(exit)) {
                    structure.get().setVisible(false);
                    structure.previousNode();
                    structure.get().setVisible(true);
                }
            };
            JButton button = getButton(exit);
            button.addActionListener(actionListener1);
            panel1.add(button);
            structure.put(panel1);
            panel1.setVisible(false);
            structure.previousNode();
        }

        Container container = frame.getContentPane();
        container.setLayout(new CardLayout());
        for (JPanel panel : structure.getAllValues()) {
            container.add(panel);
        }
        frame.setVisible(true);
    }

    private static JButton getButton(String string) {
        JButton button = new JButton(string);
        button.setFont(Constants.FONT);
        button.setActionCommand(string);
        button.setBackground(Color.CYAN);
        return button;
    }
}