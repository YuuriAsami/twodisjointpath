
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 22;

    private Timer timer;
    private boolean isStarted = false;
    private boolean isPaused = false;
    private JLabel statusbar;

    private int point;
    private Algorithm tGraph;

    public Board(tester parent) {
        initBoard(parent);
    }

    private void initBoard(tester parent) {

        // constracter
        setFocusable(true);
        int DELAY = 400;
        timer = new Timer(DELAY, this);
        timer.start();
        statusbar = parent.getStatusBar();
        addKeyListener(new TAdapter());

        tGraph = new Algorithm();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // write lupe instruction
         tGraph.BFS();
         tGraph.defineMainRoute();
         tGraph.definePotentialPp();
         tGraph.defineParPp();
         tGraph.defineFarthestNode();
         tGraph.defineTerminusLinkPath();
         tGraph.constructionTwoDisjointPath();
        repaint();
    }

    void start() {

        if (isPaused) {
            return;
        }

        isStarted = true;
        point = 0;
        timer.start();
    }

    private void pause() {

        if (!isStarted) {
            return;
        }

        isPaused = !isPaused;

        if (isPaused) {

            timer.stop();
            statusbar.setText("paused (point: " + String.valueOf(point) + ")");
        } else {

            timer.start();
            statusbar.setText(String.valueOf(point));
        }

        repaint();
    }

    private void doDrawing(Graphics g) {

        g.setColor(Color.BLACK);
        g.setFont(new Font("Helvetica", Font.PLAIN, 13));

        ArrayList<Node> Nodelist = tGraph.getNodeList();
        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            ArrayList<Integer> line = tmp.getList();
            for (int j = 0; j < line.size(); j++) {
                if(tmp.getOnPp()){
                    g.setColor(Color.RED);
                    g.drawLine(tmp.getX()+10, tmp.getY()+10,
                        Nodelist.get(line.get(j)).getX()+10, Nodelist.get(line.get(j)).getY()+10);
                }else{
                    g.setColor(Color.BLACK);
                    g.drawLine(tmp.getX(), tmp.getY(),
                            Nodelist.get(line.get(j)).getX(), Nodelist.get(line.get(j)).getY());
                }
               
            }
        }
        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            if(tmp.gets1p()) {
                g.setColor(Color.GREEN);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                //g.drawString("s1", tmp.getX()-20, tmp.getY()-20);
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked: "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            } else if(tmp.gets2p()) {
                g.setColor(Color.RED);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                //g.drawString("s2", tmp.getX()-20, tmp.getY()-20);
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked: "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString("sp1: "+tmp.getsp1(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            } else if(tmp.gett1p()) {
                g.setColor(Color.GREEN);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                //g.drawString("t1", tmp.getX()-20, tmp.getY()-20);
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked: "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString("sp1: "+tmp.getsp1()+"sp2: "+tmp.getsp2(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            } else if(tmp.gett2p()) {
                g.setColor(Color.RED);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                //g.drawString("t2", tmp.getX()-20, tmp.getY()-20);
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked: "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString("sp1: "+tmp.getsp1(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            } else if(tmp.getOnPp()){
                g.setColor(Color.BLACK);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked: "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString("sp1: "+tmp.getsp1(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            } else {
                g.setColor(Color.BLACK);
                g.drawOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX()-20, tmp.getY()-20, 40, 40);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp: "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp: "+tmp.getFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp: "+tmp.getChildp(), tmp.getX()-50, tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp(), tmp.getX()-50, tmp.getY()-20);
                g.drawString("sp1: "+tmp.getsp1(), tmp.getX()-50, tmp.getY()-20);
                g.drawString(""+tmp.getId(), tmp.getX(), tmp.getY());
                g.drawString(""+tmp.getOnPp(), tmp.getX()+20, tmp.getY()+20);
            }
        }
    }

    // write GUI
    // g.setColor(Color.WHITE);
    // g.setFont(new Font("Helvetica", Font.PLAIN, 13));
    // g.drawString(symbol,
    // j * squareW + ((squareW - g.getFontMetrics().stringWidth(symbol)) >> 1),
    // boardTop + i * squareH + (squareH >> 1) + (g.getFontMetrics().getHeight() >>
    // 2));
    // g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);
    // g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1,
    // x + squareWidth() - 1, y + 1);
    // }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();

            if (keycode == 'P') {
                pause();
                return;
            }

            if (isPaused) {
                return;
            }

            switch (keycode) {

                case KeyEvent.VK_A:

                    break;

                case KeyEvent.VK_K:

                    break;

                case KeyEvent.VK_J:

                    break;

                case KeyEvent.VK_W:

                    break;

                case KeyEvent.VK_S:

                    break;

                case KeyEvent.VK_R:

                    break;

            }
        }
    }
}