import java.io.*;
import java.util.*;

// グラフを扱う Graph クラスを作成する
class Graph {
    protected ArrayList<Node> NodeList = new ArrayList<>();

    Graph() {
        Node[] a = new Node[25];
        for (int i = 0; i < a.length; i++) {
            switch (i) {
                case 0:
                    a[i] = new Node(85, 775, i, false, false, false, false);
                    a[i].setList(2);
                    a[i].setList(5);
                    break;
                case 1:
                    a[i] = new Node(100, 450, i, false, false, false, false);
                    a[i].setList(2);
                    a[i].setList(4);
                    break;
                case 2:
                    a[i] = new Node(170, 630, i, false, false, false, false);
                    a[i].setList(0);
                    a[i].setList(1);
                    a[i].setList(5);
                    a[i].setList(8);
                    break;
                case 3:
                    a[i] = new Node(190, 120, i, true, false, false, false);
                    a[i].setList(4);
                    a[i].setList(7);
                    break;
                case 4:
                    a[i] = new Node(270, 280, i, false, false, false, false);
                    a[i].setList(1);
                    a[i].setList(3);
                    a[i].setList(6);
                    break;
                case 5:
                    a[i] = new Node(280, 860, i, false, false, false, false);
                    a[i].setList(0);
                    a[i].setList(2);
                    a[i].setList(9);
                    break;
                case 6:
                    a[i] = new Node(440, 430, i, false, false, false, false);
                    a[i].setList(4);
                    a[i].setList(8);
                    a[i].setList(10);
                    break;
                case 7:
                    a[i] = new Node(450, 180, i, false, false, false, false);
                    a[i].setList(3);
                    a[i].setList(10);
                    a[i].setList(11);
                    break;
                case 8:
                    a[i] = new Node(465, 640, i, false, false, false, false);
                    a[i].setList(2);
                    a[i].setList(6);
                    a[i].setList(9);
                    a[i].setList(13);
                    break;
                case 9:
                    a[i] = new Node(530, 830, i, false, false, false, false);
                    a[i].setList(5);
                    a[i].setList(8);
                    a[i].setList(12);
                    break;
                case 10:
                    a[i] = new Node(630, 275, i, false, false, false, false);
                    a[i].setList(6);
                    a[i].setList(7);
                    a[i].setList(11);
                    a[i].setList(14);
                    a[i].setList(15);
                    break;
                case 11:
                    a[i] = new Node(640, 70, i, false, false, false, false);
                    a[i].setList(7);
                    a[i].setList(10);
                    a[i].setList(14);
                    break;
                case 12:
                    a[i] = new Node(735, 900, i, false, false, false, false);
                    a[i].setList(9);
                    a[i].setList(16);
                    break;
                case 13:
                    a[i] = new Node(860, 610, i, false, false, false, false);
                    a[i].setList(8);
                    a[i].setList(16);
                    a[i].setList(17);
                    a[i].setList(20);
                    break;
                case 14:
                    a[i] = new Node(880, 105, i, false, false, false, false);
                    a[i].setList(10);
                    a[i].setList(11);
                    a[i].setList(18);
                    break;
                case 15:
                    a[i] = new Node(880, 320, i, false, false, false, false);
                    a[i].setList(10);
                    a[i].setList(18);
                    break;
                case 16:
                    a[i] = new Node(920, 800, i, false, false, false, false);
                    a[i].setList(12);
                    a[i].setList(13);
                    a[i].setList(22);
                    break;
                case 17:
                    a[i] = new Node(925, 480, i, false, false, false, false);
                    a[i].setList(13);
                    a[i].setList(19);
                    a[i].setList(21);
                    break;
                case 18:
                    a[i] = new Node(1000, 185, i, false, true, false, false);
                    a[i].setList(14);
                    a[i].setList(15);
                    break;
                case 19:
                    a[i] = new Node(1080, 375, i, false, false, false, true);
                    a[i].setList(17);
                    a[i].setList(21);
                    break;
                case 20:
                    a[i] = new Node(1270, 635, i, false, false, false, false);
                    a[i].setList(13);
                    a[i].setList(21);
                    a[i].setList(22);
                    a[i].setList(23);
                    break;
                case 21:
                    a[i] = new Node(1230, 490, i, false, false, false, false);
                    a[i].setList(17);
                    a[i].setList(19);
                    a[i].setList(20);
                    break;
                case 22:
                    a[i] = new Node(1320, 820, i, false, false, false, false);
                    a[i].setList(16);
                    a[i].setList(20);
                    a[i].setList(24);
                    break;
                case 23:
                    a[i] = new Node(1530, 400, i, false, false, true, false);
                    a[i].setList(20);
                    a[i].setList(24);
                    break;
                case 24:
                    a[i] = new Node(1560, 750, i, false, false, false, false);
                    a[i].setList(23);
                    a[i].setList(22);
                    break;
                //case 25:
                    //a[i] = new Node(600, 600, i+1);
                    //a[i].setList(0);
                    //break;
                default:
                    a[i] = new Node(500, 500, 0, false, false, false, false);
                    break;

            }
            NodeList.add(a[i]);
        }
    }

    public ArrayList<Node> getNodeList() {
        return NodeList;
    }

    public void loadGraph() {

    }

    public void printGraph() {
        for (Node s : NodeList) {// NodeList全てのNodeをToStringで表示
            System.out.println(s.toString());
        }
    }

    public int size() {
        return NodeList.size();
    }
}