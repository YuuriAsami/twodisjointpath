import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.*;

// class bfs {
// int depth;
// ArrayList<Integer> list;

// bfs(int d, ArrayList<Integer> l) {
// d = getLp();
// l = getList();
// }
// }
public class Algorithm extends Graph {

    public Algorithm() {
        super();
    }

    // step1.1
    // s1を根とするBFSを行う
    public void BFS() {
        // キューを初期化
        ArrayDeque<Integer> q = new ArrayDeque<>();
        // 全ノードのL値を-1に設定
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).setLp(-1);
        }
        // 全ノードをID順に見ていきs1pがtrueならそのノードをキューに入れる
        for (int i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gets1p()) {
                q.add(i);
                NodeList.get(i).setLp(0);
                break;
            }
        }
        // 以下キューが空になるまで繰り返す
        for (;;) {
            if (q.size() < 1) {
                break;
            }
            // キューの先頭の要素を取り出す
            int f = q.poll();
            // 該当するノードの隣接リストを取得しリスト内のノードのL値が-1ならL値を更新してキューに加える
            ArrayList<Integer> tmp = NodeList.get(f).getList();
            for (int i = 0; i < tmp.size(); i++) {
                Node t = NodeList.get(tmp.get(i));
                if (t.getLp() == -1) {
                    t.setPp(f);
                    t.setLp(NodeList.get(f).getLp() + 1);
                    q.add(tmp.get(i));
                }
            }
        }

    }

    // ノードt1にtrueを割り当て親に伝搬させていく
    public void defineMainRoute() {
        // t1pがtrueであるノードのOnPpをtrueに設定
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gett1p())
                break;
        }
        while (!NodeList.get(i).gets1p()) {
            NodeList.get(i).setOnPp(true);
            i = NodeList.get(i).getPp();
        }

        NodeList.get(i).setOnPp(true);

    }

    // step1.2
    // 各ノードの親候補を決定する
    public void definePotentialPp() {
        // R become maximam
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).setRp(1024);
        }

        // P上のノードに隣接するノードのみ先にR値、リストPotentialPpを更新する
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (!now.getOnPp()) {
                ArrayList<Integer> nowList = now.getList();
                for (int j = 0; j < nowList.size(); j++) {
                    if (NodeList.get(nowList.get(j)).getOnPp()) {
                        if (now.getRp() < NodeList.get(nowList.get(j)).getLp()) {
                            break;
                        } else if (now.getRp() > NodeList.get(nowList.get(j)).getLp()) {
                            now.setRp(NodeList.get(nowList.get(j)).getLp());
                            now.removePotentialPp();
                            now.setPotentialPp(nowList.get(j));
                        } else if (now.getRp() == NodeList.get(nowList.get(j)).getLp()) {
                            now.setPotentialPp(nowList.get(j));
                        }
                    }
                }
            } else {
                break;
            }
        }

        // s1以外のノードすべてを見ていく
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            // もしs1なら実行しない
            if (now.gets1p()) {
                break;
            } else if (now.getOnPp()) {
                // Ｐ上のノード
                ArrayList<Integer> nowList = now.getList();
                for (int j = 0; j < nowList.size(); j++) {
                    // nowList.get(j)がP上にないかつnowList.get(j)の親候補にnowが含まれていない
                    if (!NodeList.get(nowList.get(j)).getOnPp() && !isPotentialPp(j, i)) {
                        // R_nowとR_nowList.get(j)を比較
                        if (now.getRp() < NodeList.get(nowList.get(j)).getRp()) {
                            break;
                        } else if (now.getRp() > NodeList.get(nowList.get(j)).getRp()) {
                            now.setRp(NodeList.get(nowList.get(j)).getRp());
                            now.removePotentialPp();
                            now.setPotentialPp(nowList.get(j));
                        } else if (now.getRp() == NodeList.get(nowList.get(j)).getRp()) {
                            now.setPotentialPp(nowList.get(j));
                        }
                    }
                }
            } else if (!now.getOnPp()) {
                // それ以外
                ArrayList<Integer> nowList = now.getList();
                for (int j = 0; j < nowList.size(); j++) {
                    // nowList.get(j)の親候補にnowが含まれていない
                    if (!isPotentialPp(j, i)) {
                        // nowList.get(j)がP上のノード
                        if (NodeList.get(nowList.get(j)).getOnPp()) {
                            // R_nowとL_nowList.get(j)を比較
                            if (now.getRp() < NodeList.get(nowList.get(j)).getLp()) {
                                break;
                            } else if (now.getRp() > NodeList.get(nowList.get(j)).getLp()) {
                                now.setRp(NodeList.get(nowList.get(j)).getLp());
                                now.removePotentialPp();
                                now.setPotentialPp(nowList.get(j));
                            } else if (now.getRp() == NodeList.get(nowList.get(j)).getLp()) {
                                now.setPotentialPp(nowList.get(j));
                            }
                        } else {
                            // R_nowとR_nowList.get(j)を比較
                            if (now.getRp() < NodeList.get(nowList.get(j)).getRp()) {
                                break;
                            } else if (now.getRp() > NodeList.get(nowList.get(j)).getRp()) {
                                now.setRp(NodeList.get(nowList.get(j)).getRp());
                                now.removePotentialPp();
                                now.setPotentialPp(nowList.get(j));
                            } else if (now.getRp() == NodeList.get(nowList.get(j)).getRp()) {
                                now.setPotentialPp(nowList.get(j));
                            }
                        }
                    }
                }

            }
        }

    }

    public boolean isPotentialPp(int p, int now) {
        Node P = NodeList.get(p);
        ArrayList<Integer> PP = P.getPotentialPp();
        for (int j = 0; j < PP.size(); j++) {
            if (PP.get(j) == now)
                return false;
        }
        return true;
    }

    // 親候補の中から親を一つ決定する
    public void defineParPp() {

    }

    // BFS木の葉ノードにFpを割り当て、P上の根は最大のFを収集
    public void defineFarthestNode() {

    }

    // step1.3
}
