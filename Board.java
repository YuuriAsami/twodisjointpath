
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
    private int counter = 0;

    public Board(tester parent) {
        initBoard(parent);
    }

    private void initBoard(tester parent) {

        // constracter
        setFocusable(true);
        int DELAY = 350;
        timer = new Timer(DELAY, this);
        timer.start();
        statusbar = parent.getStatusBar();
        addKeyListener(new TAdapter());

        tGraph = new Algorithm();
        tGraph.BFS();
        tGraph.defineMainRoute();
        tGraph.definePotentialPp();
        tGraph.defineParPp();
        tGraph.defineFarthestNode();
        tGraph.defineTerminusLinkPath();
        tGraph.constructionTwoDisjointPath();
        tGraph.defineP1SuccID();
        tGraph.tBFS();
        tGraph.tdefineMainRoute();
        tGraph.tdefinePotentialPp();
        tGraph.tdefineParPp();
        tGraph.tdefineFarthestNode();
        tGraph.tdefineTerminusLinkPath();
        tGraph.tconstructionTwoDisjointPath();
        tGraph.defineP2SuccID();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // write lupe instruction
        if (counter == 1) {
            tGraph.propagationtoP1CandID();
        } else if (counter == 2) {
            tGraph.propagationtoP2CandID();
        } else if (counter == 3) {
            tGraph.definetoP1();
        } else if (counter == 4) {
            tGraph.definetoP2();
        } else if (counter == 5) {
            tGraph.propagationNG();
        } else if (counter == 6) {
            tGraph.readySTDAG();
            ;
        } else if (counter == 7) {
            tGraph.constructionST_DAG();
        }
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
        int r = 20;

        ArrayList<Node> Nodelist = tGraph.getNodeList();
        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            ArrayList<Integer> line = tmp.getList();
            
            if (tmp.getDAG() == 0) {
                for (int j = 0; j < line.size(); j++) {
                    if (line.get(j) < i) {
                        g.setColor(Color.BLACK);
                        BasicStroke wideStroke = new BasicStroke(2.0f);
                        ((Graphics2D) g).setStroke(wideStroke);
                        g.drawLine(tmp.getX(), tmp.getY(), Nodelist.get(line.get(j)).getX(),
                                Nodelist.get(line.get(j)).getY());
                    }
                }
            }
        }
        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            ArrayList<Integer> line = tmp.getList();
            ArrayList<Integer> P1line = tmp.getP1SuccID();
            ArrayList<Integer> P2line = tmp.getP2SuccID();
            if (tmp.getDAG() == 0) {
                for (int j = 0; j < line.size(); j++) {

                    for (int k = 0; k < P1line.size(); k++) {
                        if (P1line.get(k) == line.get(j)) {
                            int[] xy = new int[2];
                            xy = getArrowxy(r, Nodelist.get(P1line.get(k)).getX() + 5,
                                    Nodelist.get(P1line.get(k)).getY() + 5, tmp.getX() + 5, tmp.getY() + 5);
                            var arrow = new Arrow(new Point(tmp.getX(), tmp.getY()),
                                    new Point(xy[0], xy[1]));
                            BasicStroke wideStroke = new BasicStroke(4.0f);
                            ((Graphics2D) g).setStroke(wideStroke);
                            g.setColor(Color.GREEN);
                            arrow.draw((Graphics2D) g);
                            // g.drawLine(tmp.getX() + 10, tmp.getY() + 10,
                            // Nodelist.get(line.get(j)).getX() + 10,
                            // Nodelist.get(line.get(j)).getY() + 10);
                        }
                    }
                    for (int k = 0; k < P2line.size(); k++) {
                        if (P2line.get(k) == line.get(j)) {
                            int[] xy = new int[2];
                            xy = getArrowxy(r, Nodelist.get(P2line.get(k)).getX()-5,
                                    Nodelist.get(P2line.get(k)).getY()-5, tmp.getX()-5, tmp.getY()-5);
                            var arrow = new Arrow(new Point(tmp.getX(), tmp.getY()),
                                    new Point(xy[0], xy[1]));
                            g.setColor(Color.RED);
                            arrow.draw((Graphics2D) g);
                            // g.drawLine(tmp.getX() - 10, tmp.getY() - 10,
                            // Nodelist.get(line.get(j)).getX() - 10,
                            // Nodelist.get(line.get(j)).getY() - 10);
                        }
                    }
                }
            } else if (tmp.getDAG() == 1 || tmp.getDAG() == 2) {
                for (int j = 0; j < line.size(); j++) {
                    if (line.get(j) < i) {
                        g.setColor(Color.GRAY);
                        g.drawLine(tmp.getX(), tmp.getY(), Nodelist.get(line.get(j)).getX(),
                                Nodelist.get(line.get(j)).getY());
                    }
                }
            }
        }

        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            if (tmp.getDAG() == 2) {
                ArrayList<Integer> line = tmp.getList();
                ArrayList<Integer> DAGline = tmp.getDAGSuccID();
                for (int j = 0; j < line.size(); j++) {
                    for (int k = 0; k < DAGline.size(); k++) {
                        if (DAGline.get(k) == line.get(j)) {
                            int[] xy = new int[2];
                            xy = getArrowxy(r, Nodelist.get(DAGline.get(k)).getX(),
                                    Nodelist.get(DAGline.get(k)).getY(), tmp.getX(), tmp.getY());
                            var arrow = new Arrow(new Point(tmp.getX(), tmp.getY()),
                                    new Point(xy[0], xy[1]));
                            g.setColor(Color.BLUE);
                            BasicStroke wideStroke = new BasicStroke(4.0f);
                            ((Graphics2D) g).setStroke(wideStroke);
                            arrow.draw((Graphics2D) g);
                            // g.drawLine(tmp.getX(), tmp.getY(), Nodelist.get(DAGline.get(k)).getX(),
                            // Nodelist.get(DAGline.get(k)).getY());
                            break;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < tGraph.size(); i++) {
            Node tmp = Nodelist.get(i);
            if (tmp.gets1p()) {
                g.setColor(Color.GREEN);
                g.drawOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                g.drawString("s1", tmp.getX() - 40, tmp.getY());
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.getCp()+",Rp:
                // "+tmp.getRp()+",dp: "+tmp.getdp()+",Parp: "+tmp.getParp()+",Fp:
                // "+tmp.getFp(),
                // tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.getPotentialPp()+",Childp:
                // "+tmp.getChildp()+",Mp: "+tmp.getMp()+"NMp: "+tmp.getNMp(), tmp.getX()-50,
                // tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.getleafp()+",Marked:
                // "+tmp.getMarkedp()+",Existp: "+tmp.getExistp(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("sp1: "+tmp.getsp1(), tmp.getX()-50, tmp.getY()-20);
                // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY() -
                // 20);
                // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
                // tmp.getY()-20);
                g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
                // g.drawString("" + tmp.getOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            } else if (tmp.gets2p()) {
                g.setColor(Color.RED);
                g.drawOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                g.drawString("s2", tmp.getX() - 40, tmp.getY());
                // g.drawString("Lp: "+tmp.getLp()+",Pp: "+tmp.getPp()+"Cp: "+tmp.gettCp()+",Rp:
                // "+tmp.gettRp()+",dp: "+tmp.gettdp()+",Parp: "+tmp.gettParp()+",Fp:
                // "+tmp.gettFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.gettPotentialPp()+",Childp:
                // "+tmp.gettChildp()+",Mp: "+tmp.gettMp()+"NMp: "+tmp.gettNMp(), tmp.getX()-50,
                // tmp.getY()-35);
                // g.drawString("rootp: "+tmp.gettrootp()+",leafp: "+tmp.gettleafp()+",Marked:
                // "+tmp.gettMarkedp()+",Existp: "+tmp.gettExistp(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
                // tmp.getY()-20);
                g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
                // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY() -
                // 20);
                // g.drawString("" + tmp.gettOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            } else if (tmp.gett1p()) {
                g.setColor(Color.GREEN);
                g.drawOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                g.drawString("t1", tmp.getX() - 40, tmp.getY());
                // g.drawString("Lp: "+tmp.gettLp()+",Pp: "+tmp.gettPp()+"Cp:
                // "+tmp.gettCp()+",Rp:
                // "+tmp.gettRp()+",dp: "+tmp.gettdp()+",Parp: "+tmp.gettParp()+",Fp:
                // "+tmp.gettFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.gettPotentialPp()+",Childp:
                // "+tmp.gettChildp()+",Mp: "+tmp.gettMp()+"NMp: "+tmp.gettNMp(), tmp.getX()-50,
                // tmp.getY()-35);
                // g.drawString("rootp: "+tmp.gettrootp()+",leafp: "+tmp.gettleafp()+",Marked:
                // "+tmp.gettMarkedp()+",Existp: "+tmp.gettExistp(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY() -
                // 20);
                g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
                // g.drawString("" + tmp.gettOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            } else if (tmp.gett2p()) {
                g.setColor(Color.RED);
                g.drawOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.WHITE);
                g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                g.drawString("t2", tmp.getX() - 40, tmp.getY());
                // g.drawString("Lp: "+tmp.gettLp()+",Pp: "+tmp.gettPp()+"Cp:
                // "+tmp.gettCp()+",Rp:
                // "+tmp.gettRp()+",dp: "+tmp.gettdp()+",Parp: "+tmp.gettParp()+",Fp:
                // "+tmp.gettFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.gettPotentialPp()+",Childp:
                // "+tmp.gettChildp()+",Mp: "+tmp.gettMp()+"NMp: "+tmp.gettNMp(), tmp.getX()-50,
                // tmp.getY()-35);
                // g.drawString("rootp: "+tmp.gettrootp()+",leafp: "+tmp.gettleafp()+",Marked:
                // "+tmp.gettMarkedp()+",Existp: "+tmp.gettExistp(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY() -
                // 20);
                g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
                // g.drawString("" + tmp.gettOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            } else {
                g.setColor(Color.BLACK);
                g.drawOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                if (tmp.getToP1ID() == i) {
                    g.setColor(Color.RED);
                    g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                } else if (tmp.getToP2ID() == i) {
                    g.setColor(Color.BLUE);
                    g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                } else if (tmp.getNG() == 1) {
                    g.setColor(Color.GRAY);
                    g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillOval(tmp.getX() - r, tmp.getY() - r, 2 * r, 2 * r);
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font("Helvetica", Font.PLAIN, 13));
                // g.drawString("Lp: "+tmp.gettLp()+",Pp: "+tmp.gettPp()+"Cp:
                // "+tmp.gettCp()+",Rp:
                // "+tmp.gettRp()+",dp: "+tmp.gettdp()+",Parp: "+tmp.gettParp()+",Fp:
                // "+tmp.gettFp(), tmp.getX()-50, tmp.getY()-50);
                // g.drawString("PotentialPp: "+tmp.gettPotentialPp()+",Childp:
                // "+tmp.gettChildp()+",Mp: "+tmp.gettMp()+"NMp: "+tmp.gettNMp(), tmp.getX()-50,
                // tmp.getY()-35);
                // g.drawString("rootp: "+tmp.getrootp()+",leafp: "+tmp.gettleafp()+",Marked:
                // "+tmp.getMarkedp()+",Existp: "+tmp.gettExistp(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
                // tmp.getY()-20);
                // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY() -
                // 20);
                g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
            }
            // g.drawString("" + tmp.gettOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            // } else {
            // g.setColor(Color.BLACK);
            // g.drawOval(tmp.getX() - 20, tmp.getY() - 20, 40, 40);
            // g.setColor(Color.WHITE);
            // g.fillOval(tmp.getX() - 20, tmp.getY() - 20, 40, 40);
            // g.setColor(Color.BLACK);
            // g.setFont(new Font("Helvetica", Font.PLAIN, 13));
            // // g.drawString("Lp: "+tmp.gettLp()+",Pp: "+tmp.gettPp()+"Cp:
            // "+tmp.gettCp()+",Rp:
            // // "+tmp.gettRp()+",dp: "+tmp.gettdp()+",Parp: "+tmp.gettParp()+",Fp:
            // // "+tmp.gettFp(), tmp.getX()-50, tmp.getY()-50);
            // // g.drawString("PotentialPp: "+tmp.gettPotentialPp()+",Childp:
            // "+tmp.gettChildp(),
            // // tmp.getX()-50, tmp.getY()-35);
            // // g.drawString("rootp: "+tmp.gettrootp()+",leafp: "+tmp.gettleafp(),
            // tmp.getX()-50,
            // // tmp.getY()-20);
            // // g.drawString("sp1: "+tmp.getsp1()+",sp2: "+tmp.getsp2(), tmp.getX()-50,
            // // tmp.getY()-20);
            // // g.drawString("P1SuccID: " + tmp.getP1SuccID(), tmp.getX() - 50, tmp.getY()
            // - 20);
            // g.drawString("" + tmp.getId(), tmp.getX(), tmp.getY());
            // // g.drawString("" + tmp.gettOnPp(), tmp.getX() + 20, tmp.getY() + 20);
            // }
            // g.drawString("sp1: " + tmp.getsp1() + ",sp2: " + tmp.getsp2(), tmp.getX() -
            // 50,
            // tmp.getY() - 70);
            // g.drawString("tp1: " + tmp.gettsp1() + ",tp2: " + tmp.gettsp2(), tmp.getX() -
            // 50,
            // tmp.getY() - 55);
            g.setFont(new Font("Helvetica", Font.PLAIN, 15));
            // switch (counter) {
            //     case 0:
                
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         //g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         break;
            //     case 1:
            //         g.setColor(Color.BLUE);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.setColor(Color.BLACK);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         //g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         break;
            //     case 2:
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.setColor(Color.BLUE);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         g.setColor(Color.BLACK);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         //g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         break;
            //     case 3:
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.setColor(Color.BLUE);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         //g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         break;
            //     case 4:
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         //g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         g.setColor(Color.BLUE);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         break;
            //     case 5:
            //         g.setColor(Color.BLACK);
            //         g.drawString("toP1CandID: " + tmp.getP1CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 40);
            //         g.drawString(",toP1ID: " + tmp.getToP1ID(), tmp.getX() + 50, tmp.getY() - 40);
            //         g.drawString("toP2CandID: " + tmp.getP2CandID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         g.drawString(",toP2ID: " + tmp.getToP2ID(), tmp.getX() + 50, tmp.getY() - 25);
            //         // g.setColor(Color.BLUE);
            //         // g.drawString("NG: " + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 55);
            //         break;
            //     case 6:
            //         g.setColor(Color.BLACK);
            //         g.drawString("DAGSuccID: " + tmp.getDAGSuccID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         break;
            //     case 7:
            //         g.setColor(Color.RED);
            //         g.drawString("DAGSuccID: " + tmp.getDAGSuccID(), tmp.getX() - 50,
            //                 tmp.getY() - 25);
            //         break;
            //     default:
            //         break;
            // }
            // g.drawString("DAGSuccID: " + tmp.getDAGSuccID(), tmp.getX() - 50, tmp.getY()
            // - 55);
            // g.drawString("toP1CandID: " + tmp.getP1CandID() + "toP1ID: " +
            // tmp.getToP1ID(),
            // tmp.getX() - 50, tmp.getY() - 40);
            // g.drawString("toP2CandID: " + tmp.getP2CandID() + ",toP2ID: " +
            // tmp.getToP2ID(),
            // tmp.getX() - 50, tmp.getY() - 25);
            // g.drawString("toP1ID: " + tmp.getToP1ID() + ",toP2ID: " + tmp.getToP2ID() +
            // ",NG: "
            // + tmp.getNG(), tmp.getX() - 50, tmp.getY() - 25);
            // g.drawString(, tmp.getX() - 50, tmp.getY() - 20);

        }
    }

    public int[] getArrowxy(int r, int x, int y, int x1, int y1) {
        r += 8;
        int[] ans = new int[2];
        double Z = Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));
        double L = Z - r;
        ans[0] = (int) ((L * x + r * x1) / Z);
        ans[1] = (int) ((L * y + r * y1) / Z);
        return ans;
    }

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
                    counter++;
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
