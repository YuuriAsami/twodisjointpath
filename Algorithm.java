import org.w3c.dom.NodeList;
import java.util.*;

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
    // 2本の点内素パスの構築
    public void constructionTwoDisjointPath() {
        int i;
        for (i = 0; i < NodeList.size(); i++) {
            if (NodeList.get(i).gett1p())
                break;
        }
        if (NodeList.get(i).getExistp()) {
            NodeList.get(i).setSp1(NodeList.get(i).getParp());
            NodeList.get(i).setSp2(NodeList.get(i).getPp());
            Node s1 = NodeList.get(NodeList.get(i).getParp());
            Node s2 = NodeList.get(NodeList.get(i).getPp());
            while (!s1.gets1p()) {
                if (s1.getOnPp()) {
                    if (s1.getMarkedp()) {
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
            while (!s2.gets1p()) {
                if (s2.getOnPp()) {
                    if (s2.getMarkedp()) {
                        s2.setSp2(s2.getParp());
                        s2 = NodeList.get(s2.getParp());
                    } else {
                        s2.setSp2(s2.getPp());
                        s2 = NodeList.get(s2.getPp());
                    }
                } else {
                    s2.setSp2(s2.getParp());
                    s2 = NodeList.get(s2.getParp());
                }
            }
        }
        for (int j = 0; j < NodeList.size(); j++) {
            if (j != 0) {
                if (NodeList.get(j).getsp1() == 0) {
                    NodeList.get(j).setSp1(-1);
                }
                if (NodeList.get(j).getsp2() == 0) {
                    NodeList.get(j).setSp2(-1);
                }
            }
        }
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
                        s2.settsp2(s2.gettParp());
                        s2 = NodeList.get(s2.gettParp());
                    } else {
                        s2.settsp2(s2.gettPp());
                        s2 = NodeList.get(s2.gettPp());
                    }
                } else {
                    s2.settsp2(s2.gettParp());
                    s2 = NodeList.get(s2.gettParp());
                }
            }
        }
        for (int j = 0; j < NodeList.size(); j++) {
            if (NodeList.get(j).gettsp1() == 0) {
                NodeList.get(j).settsp1(-1);
            }
            if (NodeList.get(j).gettsp2() == 0) {
                NodeList.get(j).settsp2(-1);
            }
        }
    }

    public void defineP1SuccID() {
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowlist = now.getList();
            for (int j = 0; j < nowlist.size(); j++) {
                if (NodeList.get(nowlist.get(j)).getsp2() == i) {
                    now.setP1SuccID(0, nowlist.get(j));
                }
                if (NodeList.get(nowlist.get(j)).getsp1() == i) {
                    now.setP1SuccID(0, nowlist.get(j));
                }
            }
            now.setToP1ID(-1);
            now.setNG(-1);
        }
    }

    public void defineP2SuccID() {
        for (int i = 0; i < NodeList.size(); i++) {
            Node now = NodeList.get(i);
            ArrayList<Integer> nowlist = now.getList();
            for (int j = 0; j < nowlist.size(); j++) {
                if (NodeList.get(nowlist.get(j)).gettsp2() == i) {
                    now.setP2SuccID(0, nowlist.get(j));
                }
                if (NodeList.get(nowlist.get(j)).gettsp1() == i) {
                    now.setP2SuccID(0, nowlist.get(j));
                }
            }
            now.setToP2ID(-1);
            now.setNG(-1);
        }
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
        for (int i = 0; i < num; i++) {
            if (!NodeList.get(list.get(i)).getP2SuccID().isEmpty()) {
                NodeList.get(list.get(i)).setMvar(
                        NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP1CandID());
            } else {
                NodeList.get(list.get(i)).setMvar(-1);
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gett2p()) {
                if (!now.getP1SuccID().isEmpty()) {
                    now.setRvar(list.get(i));
                } else {
                    now.setRvar(-1);
                }
            } else if (!now.gets2p()) {
                if (!now.getP2SuccID().isEmpty()) {
                    if (!now.getP1SuccID().isEmpty()) {
                        now.setRvar(list.get(i));
                    } else {
                        now.setRvar(now.getMvar());
                    }
                }
            } else {
                now.setRvar(-1);
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            now.setToP1CandID(now.getRvar());
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
        for (int i = 0; i < num; i++) {
            if (!NodeList.get(list.get(i)).getP2SuccID().isEmpty()) {
                NodeList.get(list.get(i)).setMvar(
                        NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP2CandID());
            } else {
                NodeList.get(list.get(i)).setMvar(-1);
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gett2p()) {
                if (!now.getP1SuccID().isEmpty()) {
                    now.setRvar(list.get(i));
                } else {
                    now.setRvar(-1);
                }
            } else if (!now.gets2p()) {
                if (!now.getP2SuccID().isEmpty()) {
                    if (!now.getP1SuccID().isEmpty() && (now.getMvar() == -1)) {
                        now.setRvar(list.get(i));
                    } else {
                        now.setRvar(now.getMvar());
                    }
                }
            } else {
                now.setRvar(-1);
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            now.setToP2CandID(now.getRvar());
        }
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
        for (int i = 0; i < list.size(); i++) {
            if (NodeList.get(list.get(i)).gets2p()) {
                if (!NodeList.get(list.get(i)).getP2SuccID().isEmpty()) {
                    NodeList.get(list.get(i)).setMvar(Math.min(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP1CandID(),
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(1)).getP1CandID()));
                    NodeList.get(list.get(i)).setMvar1(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP1CandID());
                    NodeList.get(list.get(i)).setMvar2(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(1)).getP1CandID());

                } else {
                    NodeList.get(list.get(i)).setMvar(-1);
                }
            } else {
                if (NodeList.get(list.get(i)).gettsp1() != -1) {
                    NodeList.get(list.get(i))
                            .setMvar(NodeList.get(NodeList.get(list.get(i)).gettsp1()).getToP1ID());
                } else if (NodeList.get(list.get(i)).gettsp2() != -1) {
                    NodeList.get(list.get(i))
                            .setMvar(NodeList.get(NodeList.get(list.get(i)).gettsp2()).getToP1ID());
                } else {
                    NodeList.get(list.get(i)).setMvar(-1);
                }
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gets2p()) {
                ArrayList<Integer> succlist = now.getP2SuccID();
                if (now.getP1SuccID().isEmpty()) {
                    if (NodeList.get(succlist.get(0)).getP1CandID() != -1
                            && NodeList.get(succlist.get(0)).getP1CandID() != list.get(i)) {
                        if (NodeList.get(succlist.get(1)).getP1CandID() != -1
                                && NodeList.get(succlist.get(1)).getP1CandID() != list.get(i)) {
                            now.setRvar(now.getMvar());
                        } else {
                            now.setRvar(now.getMvar1());
                        }
                    } else if (NodeList.get(succlist.get(1)).getP1CandID() != -1
                            && NodeList.get(succlist.get(1)).getP1CandID() != list.get(i)) {
                        now.setRvar(now.getMvar2());
                    } else {
                        now.setRvar(-1);
                    }
                } else {
                    now.setRvar(list.get(i));
                }
            } else {
                now.setRvar(now.getMvar());
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            now.setToP1ID(now.getRvar());
        }
    }

    // toP2決定、伝搬アルゴリズム
    public void definetoP2() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
        for (int i = 0; i < num; i++) {
            if (NodeList.get(list.get(i)).gets2p()) {
                if (!NodeList.get(list.get(i)).getP2SuccID().isEmpty()) {
                    NodeList.get(list.get(i)).setMvar(Math.min(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP2CandID(),
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(1)).getP2CandID()));
                    NodeList.get(list.get(i)).setMvar1(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getP2CandID());
                    NodeList.get(list.get(i)).setMvar2(
                            NodeList.get(NodeList.get(list.get(i)).getp2SuccID(1)).getP2CandID());
                } else {
                    NodeList.get(list.get(i)).setMvar(-1);
                }
            } else {
                if (NodeList.get(list.get(i)).gettsp1() != -1) {
                    NodeList.get(list.get(i))
                            .setMvar(NodeList.get(NodeList.get(list.get(i)).gettsp1()).getToP2ID());
                } else if (NodeList.get(list.get(i)).gettsp2() != -1) {
                    NodeList.get(list.get(i))
                            .setMvar(NodeList.get(NodeList.get(list.get(i)).gettsp2()).getToP2ID());
                } else {
                    NodeList.get(list.get(i)).setMvar(-1);
                }
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gets2p()) {
                if (now.getMvar1() != -1 && !NodeList.get(now.getMvar1()).gett1p()) {
                    if (now.getMvar2() != -1 && !NodeList.get(now.getMvar2()).gett1p()) {
                        now.setRvar(now.getMvar());
                    } else {
                        now.setRvar(now.getMvar1());
                    }
                } else if (now.getMvar2() != -1 && !NodeList.get(now.getMvar2()).gett1p()) {
                    now.setRvar(now.getMvar2());
                } else if (!now.getP1SuccID().isEmpty()) {
                    now.setRvar(list.get(i));
                } else {
                    now.setRvar(-1);
                }
            } else {
                now.setRvar(now.getMvar());
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            now.setToP2ID(now.getRvar());
        }
    }

    // step3 ST-DAG構築
    // NG伝搬
    public void propagationNG() {
        // for (int i = 0; i < NodeList.size(); i++) {
        // NodeList.get(i).setRvar(0);
        // }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= 24; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        Random rand = new Random();
        int num = rand.nextInt(24) + 1;
        for (int i = 0; i < num; i++) {
            if (!NodeList.get(list.get(i)).getP1SuccID().isEmpty()) {
                NodeList.get(list.get(i))
                        .setMvar(NodeList.get(NodeList.get(list.get(i)).getp1SuccID(0)).getNG());
            }
            if (!NodeList.get(list.get(i)).getP2SuccID().isEmpty()) {
                NodeList.get(list.get(i))
                        .setMvar1(NodeList.get(NodeList.get(list.get(i)).getp2SuccID(0)).getNG());
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            if (now.gets1p() || now.gets2p() || now.gett1p() || now.gett2p()
                    || istoP1i(list.get(i)) == 1 || istoP2i(i) == 1) {
                now.setRvar(0);
            } else if (!now.getP1SuccID().isEmpty() && istoP1i(now.getp1SuccID(0)) == 1) {
                now.setRvar(1);
            } else if (!now.getP1SuccID().isEmpty() && !now.getP2SuccID().isEmpty()) {
                if (now.getMvar() == 1) {
                    now.setRvar(1);
                } else {
                    now.setRvar(0);
                }
            } else if (!now.getP2SuccID().isEmpty() && istoP1i(now.getp2SuccID(0)) != 1
                    && !NodeList.get(now.getp2SuccID(0)).getP1SuccID().isEmpty()) {
                now.setRvar(1);
            } else if (!now.getP1SuccID().isEmpty() || !now.getP2SuccID().isEmpty()) {
                if (now.getP2SuccID().isEmpty()) {
                    if (now.getMvar() == 1) {
                        now.setRvar(1);
                    }
                } else if (now.getP2SuccID().isEmpty()) {
                    if (now.getMvar1() == 1) {
                        now.setRvar(1);
                    }
                } else {
                    now.setRvar(0);
                }
            }
        }
        for (int i = 0; i < num; i++) {
            Node now = NodeList.get(list.get(i));
            now.setNG(now.getRvar());
        }
    }

    public int istoP1i(int i) {
        Node istop1 = NodeList.get(i);
        if (istop1.getToP1ID() == i && (!istop1.getP2SuccID().isEmpty() || isiinP2SuccIDj(i))) {
            return 1;
        } else {
            return 0;
        }
    }

    public int istoP2i(int i) {
        Node istop1 = NodeList.get(i);
        if (istop1.getToP2ID() == i && (!istop1.getP2SuccID().isEmpty() || isiinP2SuccIDj(i))) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean isiinP2SuccIDj(int i) {
        if (NodeList.get(i).gett2p()) {
            if (NodeList.get(NodeList.get(i).gettsp1()).getp2SuccID(0) == i
                    || NodeList.get(NodeList.get(i).gettsp2()).getp2SuccID(0) == i) {
                return true;
            } else
                return false;
        } else if (!NodeList.get(i).gets2p()) {
            if (NodeList.get(i).gettsp1() != -1) {
                if (NodeList.get(NodeList.get(i).gettsp1()).getp2SuccID(0) == i) {
                    return true;
                } else
                    return false;
            } else if (NodeList.get(i).gettsp2() != -1) {
                if (NodeList.get(NodeList.get(i).gettsp2()).gets2p()) {
                    if (NodeList.get(NodeList.get(i).gettsp2()).getp2SuccID(1) == i) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (NodeList.get(NodeList.get(i).gettsp2()).getp2SuccID(0) == i) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
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
}
