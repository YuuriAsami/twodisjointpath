import org.w3c.dom.NodeList;
import java.util.*;

public class kAlgorithm extends Algorithm {

    public kAlgorithm() {
        super();
    }

    // step2 toP1,toP2特定
    // toP1CandID伝搬アルゴリズム
    public void propagationtoP1CandID() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 25; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
        //ArrayList<Integer> mlist = new ArrayList<Integer>();
        for (int i = 0; i < num; i++) {
            //NodeList.get(list.get(i)).setMvar(NodeList.get(NodeList.get(list.get(i)).getp1SuccID()).getP1CandID());
        }
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gett2p()) {
                if (!now.getP1SuccID().isEmpty()) {
                    q.add(list.get(i));
                } else {
                    q.add(-1);
                }
            } else if (!now.gets2p()) {
                if (!now.getP2SuccID().isEmpty()) {
                    if (!now.getP1SuccID().isEmpty()) {
                        q.add(list.get(i));
                    } else {
                        //q.add(NodeList.get(now.getp1SuccID()).getMvar());
                    }
                }
            } else {
                q.add(0);
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(i);
            int qq = q.poll();
            if (qq != 0) {
                now.setToP1CandID(qq);
            } else {
            }
        }
    }

    // toP2CandID伝搬アルゴリズム
    public void propagationtoP2CandID() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // toP1決定、伝搬アルゴリズム
    public void definetoP1() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // toP1決定、伝搬アルゴリズム
    public void definetoP2() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // step3 ST-DAG構築
    // NG伝搬
    public void propagationNG() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // ST-DAG構築
    public void constructionST_DAG() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // step4 2頂点対点素パスの構築
    // t1NG,t2NG伝搬アルゴリズム
    public void propagationt1NGt2NG() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }

    // 2頂点対点素パス構築アルゴリズム
    public void constructiontwodisjointpath() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
    }
}
