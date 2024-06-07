import org.w3c.dom.NodeList;
import java.util.*;

public class Algorithm2 extends Graph {

    public Algorithm2() {
        super();
    }

    // step1.1
    // s2を根とするBFSを行う
    public void tBFS() {
        // キューを初期化
        ArrayDeque<Integer> q = new ArrayDeque<>();
        // 全ノードのL値を-1に設定
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).settLp(-1);
        }
        // 全ノードをID順に見ていきs2pがtrueならそのノードをキューに入れる
        for (int i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gets2p()) {
                q.add(i);
                NodeList.get(i).settLp(0);
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
                if (t.gettLp() == -1) {
                    t.settPp(f);
                    t.settLp(NodeList.get(f).gettLp() + 1);
                    q.add(tmp.get(i));
                }
            }
        }
    }

    // ノードt1にtrueを割り当て親に伝搬させていく
    public void tdefineMainRoute() {
        // t2pがtrueであるノードのOnPpをtrueに設定
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gett2p())
                break;
        }
        while (!NodeList.get(i).gets2p()) {
            NodeList.get(i).settOnPp(true);
            int id = NodeList.get(i).getId();
            i = NodeList.get(i).gettPp();
            NodeList.get(i).settCp(id);
        }

        NodeList.get(i).settOnPp(true);

    }

    // step1.2
    // 各ノードの親候補を決定する
    public void tdefinePotentialPp() {
        // R become maximam
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).settRp(1024);
        }

        // P上のノードに隣接するノードのみ先にR値、リストPotentialPpを更新する
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (!now.gettOnPp()) {
                ArrayList<Integer> nowList = now.getList();
                for (int j = 0; j < nowList.size(); j++) {
                    if (NodeList.get(nowList.get(j)).gettOnPp()) {
                        if (now.gettRp() < NodeList.get(nowList.get(j)).gettLp()) {
                            break;
                        } else if (now.gettRp() > NodeList.get(nowList.get(j)).gettLp()) {
                            now.settRp(NodeList.get(nowList.get(j)).gettLp());
                            now.removetPotentialPp();
                            now.settPotentialPp(nowList.get(j));
                        } else if (now.gettRp() == NodeList.get(nowList.get(j)).gettLp()) {
                            now.settPotentialPp(nowList.get(j));
                        }
                    }
                }
            }
        }

        // s1以外のノードすべてを見ていく
        boolean update = true;
        while (update) {
            update = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                // もしs1なら実行しない
                if (now.gets2p()) {
                    // break;
                } else if (now.gettOnPp()) {
                    // Ｐ上のノード
                    ArrayList<Integer> nowList = now.getList();
                    for (int j = 0; j < nowList.size(); j++) {
                        // nowList.get(j)がP上にないかつnowList.get(j)の親候補にnowが含まれていない
                        if (!NodeList.get(nowList.get(j)).gettOnPp()
                                && istPotentialPp(nowList.get(j), i)) {
                            // R_nowとR_nowList.get(j)を比較
                            if (now.gettRp() < NodeList.get(nowList.get(j)).gettRp()) {
                                // break;
                            } else if (now.gettRp() > NodeList.get(nowList.get(j)).gettRp()) {
                                now.settRp(NodeList.get(nowList.get(j)).gettRp());
                                now.removetPotentialPp();
                                now.settPotentialPp(nowList.get(j));
                                update = true;
                            } else if (now.gettRp() == NodeList.get(nowList.get(j)).gettRp()) {
                                if (istPotentialPp(i, nowList.get(j))) {
                                    now.settPotentialPp(nowList.get(j));
                                }
                            }
                        }
                    }
                } else if (!now.gettOnPp()) {
                    // それ以外
                    ArrayList<Integer> nowList = now.getList();
                    for (int j = 0; j < nowList.size(); j++) {
                        // nowList.get(j)の親候補にnowが含まれていない
                        if (istPotentialPp(nowList.get(j), i)) {
                            // nowList.get(j)がP上のノードなら
                            if (NodeList.get(nowList.get(j)).gettOnPp()) {
                                // R_nowとL_nowList.get(j)を比較
                                if (now.gettRp() < NodeList.get(nowList.get(j)).gettLp()) {
                                    // break;
                                } else if (now.gettRp() > NodeList.get(nowList.get(j)).gettLp()) {
                                    now.settRp(NodeList.get(nowList.get(j)).gettLp());
                                    now.removetPotentialPp();
                                    now.settPotentialPp(nowList.get(j));
                                    update = true;
                                } else if (now.gettRp() == NodeList.get(nowList.get(j)).gettLp()) {
                                    if (istPotentialPp(i, nowList.get(j))) {
                                        now.settPotentialPp(nowList.get(j));
                                    }
                                }
                                // nowList.get(j)がP上のノードではないなら
                            } else if (!NodeList.get(nowList.get(j)).gettOnPp()) {
                                // R_nowとR_nowList.get(j)を比較
                                if (now.gettRp() < NodeList.get(nowList.get(j)).gettRp()) {
                                    // break;
                                } else if (now.gettRp() > NodeList.get(nowList.get(j)).gettRp()) {
                                    now.settRp(NodeList.get(nowList.get(j)).gettRp());
                                    now.removetPotentialPp();
                                    now.settPotentialPp(nowList.get(j));
                                    update = true;
                                } else if (now.gettRp() == NodeList.get(nowList.get(j)).gettRp()) {
                                    if (istPotentialPp(i, nowList.get(j))) {
                                        now.settPotentialPp(nowList.get(j));
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
    public boolean istPotentialPp(int p, int now) {
        Node P = NodeList.get(p);
        ArrayList<Integer> PP = P.gettPotentialPp();
        for (int j = 0; j < PP.size(); j++) {
            if (PP.get(j) == now)
                return false; // 含まれている
        }
        return true;
    }

    // 親候補の中から親を一つ決定する
    public void tdefineParPp() {
        // d値を設定＆更新
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).settdp(-1);
            NodeList.get(i).settdefineDp(false);
        }

        for (int i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gettPotentialPp().isEmpty()) {
                NodeList.get(i).settdp(1);
                NodeList.get(i).settdefineDp(true);
            }
        }

        boolean update = true;
        while (update) {
            update = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                // ArrayList<Integer> nowList = now.getList();
                ArrayList<Integer> DPotential = now.gettPotentialPp();
                if (!now.gettdefineDp()) {
                    if (NodeList.get(DPotential.get(0)).gettdp() != -1) {
                        now.settdp(NodeList.get(DPotential.get(0)).gettdp() + 1);
                        now.settdefineDp(true);
                    }
                }
            }
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                if (!now.gettdefineDp()) {
                    update = true;
                }
            }
        }
        // 親ノードの決定
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).settParp(-1);
            NodeList.get(i).settdefineParp(false);
        }

        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowPotentialList = now.gettPotentialPp();
            for (int j = 0; j < nowPotentialList.size(); j++) {
                if (!nowPotentialList.isEmpty() && !now.gettOnPp()) {
                    if (NodeList.get(nowPotentialList.get(j)).gettOnPp()) {
                        if (now.gettRp() == NodeList.get(nowPotentialList.get(j)).gettLp()) {
                            now.settParp(nowPotentialList.get(j));
                            now.settdefineParp(true);
                            // NodeList.get(nowPotentialList.get(j)).setChildp(i);
                        }
                        break;
                    } else {
                        now.settdList(NodeList.get(nowPotentialList.get(j)).gettdp());
                    }
                } else if (!nowPotentialList.isEmpty() && now.gettOnPp()) {
                    now.settdList(NodeList.get(nowPotentialList.get(j)).gettdp());
                }
            }
            ArrayList<Integer> nowdList = now.gettdList();
            if (!now.gettdefineParp() && !nowdList.isEmpty()) {
                if (nowdList.size() == 1) {
                    now.settParp(nowPotentialList.get(0));
                } else if (nowdList.size() > 1) {
                    // Collections.sort(nowdList);
                    int min = Collections.min(nowdList);
                    for (int j = 0; j < nowPotentialList.size(); j++) {
                        if (NodeList.get(nowPotentialList.get(j)).gettdp() == min) {
                            if (!now.gettdefineParp()) {
                                now.settParp(nowPotentialList.get(j));
                                now.settdefineParp(true);

                            }
                        }
                    }
                }
            }
        }

        // 子ノードリストの作成
        // 隣接リストのParpを見て自身のノードIDと一致するならリストに追加する
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).removetChildp();
        }
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowList = now.getList();
            for (int j = 0; j < nowList.size(); j++) {
                if (NodeList.get(nowList.get(j)).gettParp() == i) {
                    now.settChildp(nowList.get(j));
                }
            }
        }
    }

    // BFS木の葉ノードにFpを割り当て、P上の根は最大のFを収集
    public void tdefineFarthestNode() {
        // ノードnowが葉ノード、根ノードであるかの判定
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (now.gettOnPp()) {
                if (now.gettPotentialPp().isEmpty()) {
                    now.settleafp(false);
                } else {
                    now.settleafp(true);
                }
                if (now.gettChildp().isEmpty()) {
                    now.settrootp(false);
                } else {
                    now.settrootp(true);
                }
            } else {
                now.settrootp(false);
                if (now.gettChildp().isEmpty()) {
                    now.settleafp(true);
                } else {
                    now.settleafp(false);
                }
            }
        }
        // 全てのノードのF値に-2を割り当てる
        for (int i = 0; i < NodeList.size(); i++) {
            NodeList.get(i).settFp(-2);
        }
        // 根ではなく葉であるノードはP上ならL値をそうでないなら-1をF値に割り当てる
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            if (!now.gettrootp() && now.gettleafp()) {
                if (now.gettOnPp()) {
                    now.settFp(now.gettLp());
                } else {
                    now.settFp(-1);
                }
            }
        }
        // 葉ノードのF値を根ノードまで伝搬する
        boolean fupdate = true;
        while (fupdate) {
            fupdate = false;
            for (int i = 0; i < NodeList.size(); i++) {
                Node now = NodeList.get(i);
                if (now.gettParp() != -1 && now.gettFp() != -2) {
                    if (now.gettFp() > NodeList.get(now.gettParp()).gettFp()) {
                        NodeList.get(now.gettParp()).settFp(now.gettFp());
                        fupdate = true;
                    }
                }
            }
        }
    }

    // step1.3
    // リンクパスの終点を特定
    public void tdefineTerminusLinkPath() {
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gets2p()) {
                NodeList.get(i).settMp(NodeList.get(i).gettFp());
                NodeList.get(i).settNMp(NodeList.get(i).gettFp());
                break;
            }
        }
        Node now = NodeList.get(i);
        Node Pnow = NodeList.get(i);
        now = NodeList.get(now.gettCp());
        Pnow = NodeList.get(now.gettPp());
        while (!now.gett2p()) {
            if (now.gettFp() == Pnow.gettMp()) {
                now.settMarkedp(true);
            } else {
                now.settMarkedp(false);
            }
            if (now.gettMarkedp()) {
                now.settMp(Pnow.gettNMp());
                now.settNMp(now.gettMp());
            } else {
                now.settMp(Pnow.gettMp());
                if (Pnow.gettNMp() < now.gettFp()) {
                    now.settNMp(now.gettFp());
                } else {
                    now.settNMp(Pnow.gettNMp());
                }
            }
            now = NodeList.get(now.gettCp());
            Pnow = NodeList.get(now.gettPp());
        }
        if (now.gettFp() == Pnow.gettMp()) {
            now.settMarkedp(true);
        } else {
            now.settMarkedp(false);
        }
        if (now.gettMarkedp()) {
            now.settMp(Pnow.gettNMp());
            now.settNMp(now.gettMp());
        } else {
            now.settMp(Pnow.gettMp());
            if (Pnow.gettNMp() < now.gettFp()) {
                now.settNMp(now.gettFp());
            } else {
                now.settNMp(Pnow.gettNMp());
            }
        }

        // st間に2本の点内素パスが存在するかの判定
        int k;
        for (k = 0; k < NodeList.size(); k++) {
            if (NodeList.get(k).gets2p())
                break;
        }
        if (NodeList.get(k).gettFp() != NodeList.get(k).gettLp()) {
            if (!NodeList.get(NodeList.get(k).gettCp()).gettMarkedp()) {
                NodeList.get(k).settExistp(true);
            }
        }
        now = NodeList.get(NodeList.get(k).gettCp());
        Pnow = NodeList.get(now.gettPp());
        while (!now.gett2p()) {
            if (!Pnow.gettMarkedp()) {
                now.settExistp(Pnow.gettExistp());
            } else {
                if (!Pnow.gettExistp()) {
                    now.settExistp(Pnow.gettExistp());
                } else {
                    boolean ExistNW = false;
                    Node NW = NodeList.get(now.gettCp());
                    for (int l = 0; l < NodeList.size(); l++) {
                        if (NW.gettMarkedp()) {
                            ExistNW = true;
                            break;
                        } else {
                            NW = NodeList.get(NW.gettCp());
                        }
                    }
                    if (ExistNW) {
                        if (NW.gettMp() > now.gettLp()) {
                            now.settExistp(Pnow.gettExistp());
                        } else {
                            now.settExistp(false);
                        }
                    } else {
                        now.settExistp(false);
                    }
                }
            }
            now = NodeList.get(now.gettCp());
            Pnow = NodeList.get(now.gettPp());
        }
        now.settExistp(Pnow.gettExistp());
    }

    // step1.4
    // 2本の点内素パスの構築
    public void tconstructionTwoDisjointPath() {
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gett2p())
                break;
        }
        if (NodeList.get(i).gettExistp()) {
            NodeList.get(i).settsp1(NodeList.get(i).gettParp());
            NodeList.get(i).settsp2(NodeList.get(i).gettPp());
            Node s1 = NodeList.get(NodeList.get(i).gettParp());
            Node s2 = NodeList.get(NodeList.get(i).gettPp());
            while (!s1.gets2p()) {
                if (s1.gettOnPp()) {
                    if (s1.gettMarkedp()) {
                        s1.settsp1(s1.gettParp());
                        s1 = NodeList.get(s1.gettParp());
                    } else {
                        s1.settsp1(s1.gettPp());
                        s1 = NodeList.get(s1.gettPp());
                    }
                } else {
                    s1.settsp1(s1.gettParp());
                    s1 = NodeList.get(s1.gettParp());
                }
            }
            while (!s2.gets2p()) {
                if (s2.gettOnPp()) {
                    if (s2.gettMarkedp()) {
                        s2.settsp1(s2.gettParp());
                        s2 = NodeList.get(s2.gettParp());
                    } else {
                        s2.settsp1(s2.gettPp());
                        s2 = NodeList.get(s2.gettPp());
                    }
                } else {
                    s2.settsp1(s2.gettParp());
                    s2 = NodeList.get(s2.gettParp());
                }
            }
        }
    }

}