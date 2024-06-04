// package tetris;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class tester extends JFrame {

    // write window panel here
    private JLabel statusbar;

    public tester() {

        initUI();
    }

    private void initUI() {

        statusbar = new JLabel(" sample");
        add(statusbar, BorderLayout.SOUTH);

        var board = new Board(this);
        add(board);

        board.start();

        setTitle("test");
        setSize(1800, 1000);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    JLabel getStatusBar() {
        return statusbar;
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var game = new tester();
            game.setVisible(true);
        });
    }
}