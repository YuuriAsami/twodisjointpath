import org.w3c.dom.NodeList;
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
        while (!q.isEmpty()) {
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
            int id = NodeList.get(i).getId();
            i = NodeList.get(i).getPp();
            NodeList.get(i).setCp(id);
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
            }
        }

        // s1以外のノードすべてを見ていく
        boolean update = true;
        /* for(int k = 0; k < 100; k++) */while (update) {
            update = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                // もしs1なら実行しない
                if (now.gets1p()) {
                    // break;
                } else if (now.getOnPp()) {
                    // Ｐ上のノード
                    ArrayList<Integer> nowList = now.getList();
                    for (int j = 0; j < nowList.size(); j++) {
                        // nowList.get(j)がP上にないかつnowList.get(j)の親候補にnowが含まれていない
                        if (!NodeList.get(nowList.get(j)).getOnPp()
                                && isPotentialPp(nowList.get(j), i)) {
                            // R_nowとR_nowList.get(j)を比較
                            if (now.getRp() < NodeList.get(nowList.get(j)).getRp()) {
                                // break;
                            } else if (now.getRp() > NodeList.get(nowList.get(j)).getRp()) {
                                now.setRp(NodeList.get(nowList.get(j)).getRp());
                                now.removePotentialPp();
                                now.setPotentialPp(nowList.get(j));
                                update = true;
                            } else if (now.getRp() == NodeList.get(nowList.get(j)).getRp()) {
                                if (isPotentialPp(i, nowList.get(j))) {
                                    now.setPotentialPp(nowList.get(j));
                                }
                            }
                        }
                    }
                } else if (!now.getOnPp()) {
                    // それ以外
                    ArrayList<Integer> nowList = now.getList();
                    for (int j = 0; j < nowList.size(); j++) {
                        // nowList.get(j)の親候補にnowが含まれていない
                        if (isPotentialPp(nowList.get(j), i)) {
                            // nowList.get(j)がP上のノードなら
                            if (NodeList.get(nowList.get(j)).getOnPp()) {
                                // R_nowとL_nowList.get(j)を比較
                                if (now.getRp() < NodeList.get(nowList.get(j)).getLp()) {
                                    // break;
                                } else if (now.getRp() > NodeList.get(nowList.get(j)).getLp()) {
                                    now.setRp(NodeList.get(nowList.get(j)).getLp());
                                    now.removePotentialPp();
                                    now.setPotentialPp(nowList.get(j));
                                    update = true;
                                } else if (now.getRp() == NodeList.get(nowList.get(j)).getLp()) {
                                    if (isPotentialPp(i, nowList.get(j))) {
                                        now.setPotentialPp(nowList.get(j));
                                    }
                                }
                                // nowList.get(j)がP上のノードではないなら
                            } else if (!NodeList.get(nowList.get(j)).getOnPp()) {
                                // R_nowとR_nowList.get(j)を比較
                                if (now.getRp() < NodeList.get(nowList.get(j)).getRp()) {
                                    // break;
                                } else if (now.getRp() > NodeList.get(nowList.get(j)).getRp()) {
                                    now.setRp(NodeList.get(nowList.get(j)).getRp());
                                    now.removePotentialPp();
                                    now.setPotentialPp(nowList.get(j));
                                    update = true;
                                } else if (now.getRp() == NodeList.get(nowList.get(j)).getRp()) {
                                    if (isPotentialPp(i, nowList.get(j))) {
                                        now.setPotentialPp(nowList.get(j));
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

    }

    // ノードpの親候補リスト内にノードnowが含まれているかの判定
    public boolean isPotentialPp(int p, int now) {
        Node P = NodeList.get(p);
        ArrayList<Integer> PP = P.getPotentialPp();
        for (int j = 0; j < PP.size(); j++) {
            if (PP.get(j) == now)
                return false; // 含まれている
        }
        return true;
    }

    // 親候補の中から親を一つ決定する
    public void defineParPp() {
        // d値を設定＆更新
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).setDp(-1);
            NodeList.get(i).setDefineDp(false);
        }

        for (int i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).getPotentialPp().isEmpty()) {
                NodeList.get(i).setDp(1);
                NodeList.get(i).setDefineDp(true);
            }
        }

        boolean update = true;
        while (update) {
            update = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                // ArrayList<Integer> nowList = now.getList();
                ArrayList<Integer> DPotential = now.getPotentialPp();
                if (!now.getdefineDp()) {
                    if (NodeList.get(DPotential.get(0)).getdp() != -1) {
                        now.setDp(NodeList.get(DPotential.get(0)).getdp() + 1);
                        now.setDefineDp(true);
                    }
                }
            }
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                if (!now.getdefineDp()) {
                    update = true;
                }
            }
        }
        // 親ノードの決定
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).setParp(-1);
            NodeList.get(i).setDefineParp(false);
        }

        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowPotentialList = now.getPotentialPp();
            for (int j = 0; j < nowPotentialList.size(); j++) {
                if (!nowPotentialList.isEmpty() && !now.getOnPp()) {
                    if (NodeList.get(nowPotentialList.get(j)).getOnPp()) {
                        if (now.getRp() == NodeList.get(nowPotentialList.get(j)).getLp()) {
                            now.setParp(nowPotentialList.get(j));
                            now.setDefineParp(true);
                            // NodeList.get(nowPotentialList.get(j)).setChildp(i);
                        }
                        break;
                    } else {
                        now.setdList(NodeList.get(nowPotentialList.get(j)).getdp());
                    }
                } else if (!nowPotentialList.isEmpty() && now.getOnPp()) {
                    now.setdList(NodeList.get(nowPotentialList.get(j)).getdp());
                }
            }
            ArrayList<Integer> nowdList = now.getdList();
            if (!now.getdefineParp() && !nowdList.isEmpty()) {
                if (nowdList.size() == 1) {
                    now.setParp(nowPotentialList.get(0));
                } else if (nowdList.size() > 1) {
                    // Collections.sort(nowdList);
                    int min = Collections.min(nowdList);
                    for (int j = 0; j < nowPotentialList.size(); j++) {
                        if (NodeList.get(nowPotentialList.get(j)).getdp() == min) {
                            if (!now.getdefineParp()) {
                                now.setParp(nowPotentialList.get(j));
                                now.setDefineParp(true);

                            }
                        }
                    }
                }
            }
        }

        // 子ノードリストの作成
        // 隣接リストのParpを見て自身のノードIDと一致するならリストに追加する
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).removeChildp();
        }
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowList = now.getList();
            for (int j = 0; j < nowList.size(); j++) {
                if (NodeList.get(nowList.get(j)).getParp() == i) {
                    now.setChildp(nowList.get(j));
                }
            }
        }
    }

    // BFS木の葉ノードにFpを割り当て、P上の根は最大のFを収集
    public void defineFarthestNode() {
        // ノードnowが葉ノード、根ノードであるかの判定
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (now.getOnPp()) {
                if (now.getPotentialPp().isEmpty()) {
                    now.setLeafp(false);
                } else {
                    now.setLeafp(true);
                }
                if (now.getChildp().isEmpty()) {
                    now.setRootp(false);
                } else {
                    now.setRootp(true);
                }
            } else {
                now.setRootp(false);
                if (now.getChildp().isEmpty()) {
                    now.setLeafp(true);
                } else {
                    now.setLeafp(false);
                }
            }
        }
        // 全てのノードのF値に-2を割り当てる
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).setFp(-2);
        }
        // 根ではなく葉であるノードはP上ならL値をそうでないなら-1をF値に割り当てる
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (!now.getrootp() && now.getleafp()) {
                if (now.getOnPp()) {
                    now.setFp(now.getLp());
                } else {
                    now.setFp(-1);
                }
            }
        }
        // 葉ノードのF値を根ノードまで伝搬する
        boolean fupdate = true;
        while (fupdate) {
            fupdate = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                if (now.getParp() != -1 && now.getFp() != -2) {
                    if (now.getFp() > NodeList.get(now.getParp()).getFp()) {
                        NodeList.get(now.getParp()).setFp(now.getFp());
                        fupdate = true;
                    }
                }
            }
        }
    }

    // step1.3
    // リンクパスの終点を特定
    public void defineTerminusLinkPath() {
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gets1p()) {
                NodeList.get(i).setMp(NodeList.get(i).getFp());
                NodeList.get(i).setNMp(NodeList.get(i).getFp());
                break;
            }
        }
        int j;
        Node now = NodeList.get(i);
        Node Pnow = NodeList.get(i);
        now = NodeList.get(now.getCp());
        Pnow = NodeList.get(now.getPp());
        while (!now.gett1p()) {
            if (now.getFp() == Pnow.getMp()) {
                now.setMarkedp(true);
            } else {
                now.setMarkedp(false);
            }
            if (now.getMarkedp()) {
                now.setMp(Pnow.getNMp());
                now.setNMp(now.getMp());
            } else {
                now.setMp(Pnow.getMp());
                if (Pnow.getNMp() < now.getFp()) {
                    now.setNMp(now.getFp());
                } else {
                    now.setNMp(Pnow.getNMp());
                }
            }
            now = NodeList.get(now.getCp());
            Pnow = NodeList.get(now.getPp());
        }
        if (now.getFp() == Pnow.getMp()) {
            now.setMarkedp(true);
        } else {
            now.setMarkedp(false);
        }
        if (now.getMarkedp()) {
            now.setMp(Pnow.getNMp());
            now.setNMp(now.getMp());
        } else {
            now.setMp(Pnow.getMp());
            if (Pnow.getNMp() < now.getFp()) {
                now.setNMp(now.getFp());
            } else {
                now.setNMp(Pnow.getNMp());
            }
        }

        // st間に2本の点内素パスが存在するかの判定
        int k;
        for (k = 0; k < NodeList.size(); k++) {
            if (NodeList.get(k).gets1p())
                break;
        }
        if (NodeList.get(k).getFp() != NodeList.get(k).getLp()) {
            if (!NodeList.get(NodeList.get(k).getCp()).getMarkedp()) {
                NodeList.get(k).setExistp(true);
            }
        }
        now = NodeList.get(NodeList.get(k).getCp());
        Pnow = NodeList.get(now.getPp());
        while (!now.gett1p()) {
            if (!Pnow.getMarkedp()) {
                now.setExistp(Pnow.getExistp());
            } else {
                if (!Pnow.getExistp()) {
                    now.setExistp(Pnow.getExistp());
                } else {
                    boolean ExistNW = false;
                    Node NW = NodeList.get(now.getCp());
                    for (int l = 0; l < NodeList.size(); l++) {
                        if (NW.getMarkedp()) {
                            ExistNW = true;
                            break;
                        } else {
                            NW = NodeList.get(NW.getCp());
                        }
                    }
                    if (ExistNW) {
                        if (NW.getMp() > now.getLp()) {
                            now.setExistp(Pnow.getExistp());
                        } else {
                            now.setExistp(false);
                        }
                    } else {
                        now.setExistp(false);
                    }
                }
            }
            now = NodeList.get(now.getCp());
            Pnow = NodeList.get(now.getPp());
        }
        now.setExistp(Pnow.getExistp());
    }
    // step1.4
    //2本の点内素パスの構築
    public void constructionTwoDisjointPath() {
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gett1p())
                break;
        }
        if(NodeList.get(i).getExistp()) {
            NodeList.get(i).setSp1(NodeList.get(i).getParp());
            NodeList.get(i).setSp2(NodeList.get(i).getPp());
            Node s1 = NodeList.get(NodeList.get(i).getParp());
            Node s2 = NodeList.get(NodeList.get(i).getPp());
            while(!s1.gets1p()) {
                if(s1.getOnPp()) {
                    if(s1.getMarkedp()) {
                        s1.setSp1(s1.getParp());
                        s1 = NodeList.get(s1.getParp());
                    } else {
                        s1.setSp1(s1.getPp());
                        s1 = NodeList.get(s1.getPp());
                    }
                } else {
                    s1.setSp1(s1.getParp());
                    s1 = NodeList.get(s1.getParp());
                }
            }
            while(!s2.gets1p()) {
                if(s2.getOnPp()) {
                    if(s1.getMarkedp()) {
                        s2.setSp1(s2.getParp());
                        s2 = NodeList.get(s2.getParp());
                    } else {
                        s2.setSp1(s2.getPp());
                        s2 = NodeList.get(s2.getPp());
                    }
                } else {
                    s2.setSp1(s2.getParp());
                    s2 = NodeList.get(s2.getParp());
                }
            }
        }
    }
    // ↓Dデーモン？
    // step2
    // step3
    // step4
}
