
// import java.util.*;
import java.util.ArrayList;

public class Node {
    private int id; // ノード番号
    private ArrayList<Integer> list; // 隣接リスト
    private boolean s1p; // ノードpがs1であるか
    private boolean s2p; // ノードpがs2であるか
    private boolean t1p; // ノードpがt1であるか
    private boolean t2p; // ノードpがt2であるか
    private int Pp; // step1.1のBFS木におけるノードpの親ID
    private int Cp; // P上のノードの子ID
    private ArrayList<Integer> Childp; // step1.2以降のBFS木におけるノードpの子リスト
    private boolean ChildList; // 子ノードリストが確定したか
    private int Lp; // ノードpから始点sまでの距離(L距離)
    private boolean OnPp; // ノードpが最短経路P上に存在するか
    private int Rp; // ノードpを含んでいる木における根のL距離
    private int Parp; // step1.2以降のBFS木におけるノードpの親ID
    private boolean defineParp; // step1.2以降のBFS木におけるノードpの親が決まったか
    private ArrayList<Integer> dList;
    private int dp; // ノードpからpを含む木における根までの距離
    private boolean defineDp; // ノードpのdpが決定されたかどうか
    private boolean rootp; // ノードpが根ノードかどうか
    private boolean leafp; // ノードpが葉ノードかどうか
    private int Fp; // 自身を根とする木における葉ノードのLpの最大値
    private ArrayList<Integer> PotentialPp; // ノードpの親となる可能性のあるノードID
    private int Mp; // 探索中のリンクパスPiの終点のL距離
    private int NMp; // 次に探索するリンクパスPi+1の終点のL距離
    private boolean Existp; // ノードsp間に２本の点素パスが存在するか
    private boolean Markedp; // ノードpがリンクパスの終点であるか
    private int sp1;
    private int sp2;
    private ArrayList<Integer> Parentp; // 点内素パス上における親ノード
    private ArrayList<Integer> Childrenp; // 点内素パス上における子ノード
    private int tPp; // step1.1のBFS木におけるノードpの親ID
    private int tCp; // P上のノードの子ID
    private ArrayList<Integer> tChildp; // step1.2以降のBFS木におけるノードpの子リスト
    private boolean tChildList; // 子ノードリストが確定したか
    private int tLp; // ノードpから始点sまでの距離(L距離)
    private boolean tOnPp; // ノードpが最短経路P上に存在するか
    private int tRp; // ノードpを含んでいる木における根のL距離
    private int tParp; // step1.2以降のBFS木におけるノードpの親ID
    private boolean tdefineParp; // step1.2以降のBFS木におけるノードpの親が決まったか
    private ArrayList<Integer> tdList;
    private int tdp; // ノードpからpを含む木における根までの距離
    private boolean tdefineDp; // ノードpのdpが決定されたかどうか
    private boolean trootp; // ノードpが根ノードかどうか
    private boolean tleafp; // ノードpが葉ノードかどうか
    private int tFp; // 自身を根とする木における葉ノードのLpの最大値
    private ArrayList<Integer> tPotentialPp; // ノードpの親となる可能性のあるノードID
    private int tMp; // 探索中のリンクパスPiの終点のL距離
    private int tNMp; // 次に探索するリンクパスPi+1の終点のL距離
    private boolean tExistp; // ノードsp間に２本の点素パスが存在するか
    private boolean tMarkedp; // ノードpがリンクパスの終点であるか
    private int tsp1;
    private int tsp2;
    private ArrayList<Integer> tParentp; // 点内素パス上における親ノード
    private ArrayList<Integer> tChildrenp; // 点内素パス上における子ノード
    private ArrayList<Integer> P1SuccID;
    private ArrayList<Integer> P2SuccID;
    private int toP1CandID;
    private int toP2CandID;
    private int toP1ID;
    private int toP2ID;
    private int NG;
    private ArrayList<Integer> DAGSuccID;
    private int DAG;
    private int mvar;
    private int mvar1;
    private int mvar2;
    private int rvar;
    private int x; // ノードのx座標
    private int y; // ノードのy座標

    public Node(int x, int y, int id, boolean s1p, boolean s2p, boolean t1p, boolean t2p) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.s1p = s1p;
        this.s2p = s2p;
        this.t1p = t1p;
        this.t2p = t2p;
        list = new ArrayList<>();
        PotentialPp = new ArrayList<>();
        Childp = new ArrayList<>();
        dList = new ArrayList<>();
        Parentp = new ArrayList<>();
        Childrenp = new ArrayList<>();
        tChildp = new ArrayList<>();
        tdList = new ArrayList<>();
        tPotentialPp = new ArrayList<>();
        tParentp = new ArrayList<>();
        tChildrenp = new ArrayList<>();
        P1SuccID = new ArrayList<>();
        P2SuccID = new ArrayList<>();
        DAGSuccID = new ArrayList<>();
        // q = new ArrayList<>();
        // toP1CandID = new ArrayList<>();
        // toP2CandID = new ArrayList<>();
    }

    // get
    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(int a) {
        list.add(a);
    }

    public int getId() {
        return id;
    }

    public boolean gets1p() {
        return s1p;
    }

    public boolean gets2p() {
        return s2p;
    }

    public boolean gett1p() {
        return t1p;
    }

    public boolean gett2p() {
        return t2p;
    }

    public int getPp() {
        return Pp;
    }

    public int getCp() {
        return Cp;
    }

    public int getLp() {
        return Lp;
    }

    public boolean getOnPp() {
        return OnPp;
    }

    public int getRp() {
        return Rp;
    }

    public int getParp() {
        return Parp;
    }

    public boolean getdefineParp() {
        return defineParp;
    }

    public int getdp() {
        return dp;
    }

    public boolean getdefineDp() {
        return defineDp;
    }

    public boolean getrootp() {
        return rootp;
    }

    public boolean getleafp() {
        return leafp;
    }

    public ArrayList<Integer> getdList() {
        return dList;
    }

    public int getFp() {
        return Fp;
    }

    public ArrayList<Integer> getPotentialPp() {
        return PotentialPp;
    }

    public int getMp() {
        return Mp;
    }

    public int getNMp() {
        return NMp;
    }

    public boolean getExistp() {
        return Existp;
    }

    public boolean getMarkedp() {
        return Markedp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getsp1() {
        return sp1;
    }

    public int getsp2() {
        return sp2;
    }

    public ArrayList<Integer> getChildp() {
        return Childp;
    }

    public boolean getChildList() {
        return ChildList;
    }

    public ArrayList<Integer> getParentp() {
        return Parentp;
    }

    public ArrayList<Integer> getChildrenp() {
        return Childrenp;
    }

    public int gettPp() {
        return tPp;
    }

    public int gettCp() {
        return tCp;
    }

    public ArrayList<Integer> gettChildp() {
        return tChildp;
    }

    public boolean gettChildList() {
        return tChildList;
    }

    public int gettLp() {
        return tLp;
    }

    public boolean gettOnPp() {
        return tOnPp;
    }

    public int gettRp() {
        return tRp;
    }

    public int gettParp() {
        return tParp;
    }

    public boolean gettdefineParp() {
        return tdefineParp;
    }

    public ArrayList<Integer> gettdList() {
        return tdList;
    }

    public int gettdp() {
        return tdp;
    }

    public boolean gettdefineDp() {
        return tdefineDp;
    }

    public boolean gettrootp() {
        return trootp;
    }

    public boolean gettleafp() {
        return tleafp;
    }

    public int gettFp() {
        return tFp;
    }

    public ArrayList<Integer> gettPotentialPp() {
        return tPotentialPp;
    }

    public int gettMp() {
        return tMp;
    }

    public int gettNMp() {
        return tNMp;
    }

    public boolean gettExistp() {
        return tExistp;
    }

    public boolean gettMarkedp() {
        return tMarkedp;
    }

    public int gettsp1() {
        return tsp1;
    }

    public int gettsp2() {
        return tsp2;
    }

    public ArrayList<Integer> getP1SuccID() {
        return P1SuccID;
    }

    public ArrayList<Integer> getP2SuccID() {
        return P2SuccID;
    }

    public int getp1SuccID(int i) {
        return P1SuccID.get(i);
    }

    public int getp2SuccID(int i) {
        return P2SuccID.get(i);
    }

    public int getP1CandID() {
        return toP1CandID;
    }

    public int getP2CandID() {
        return toP2CandID;
    }

    public int getMvar() {
        return mvar;
    }

    public int getMvar1() {
        return mvar1;
    }

    public int getMvar2() {
        return mvar2;
    }

    public int getRvar() {
        return rvar;
    }

    public int getToP1ID() {
        return toP1ID;
    }

    public int getToP2ID() {
        return toP2ID;
    }

    public int getNG() {
        return NG;
    }

    public ArrayList<Integer> getDAGSuccID() {
        return DAGSuccID;
    }

    public int getdagsuccid(int i) {
        return DAGSuccID.get(i);
    }

    public int getDAG() {
        return DAG;
    }

    // set
    public void setLp(int lp) {
        this.Lp = lp;
    }

    public void setPp(int pp) {
        Pp = pp;
    }

    public void setCp(int cp) {
        Cp = cp;
    }

    public void setOnPp(boolean onpp) {
        OnPp = onpp;
    }

    public void setRp(int rp) {
        Rp = rp;
    }

    public void setPotentialPp(int b) {
        PotentialPp.add(b);
    }

    public void setDp(int dp) {
        this.dp = dp;
    }

    public void setChildp(int c) {
        Childp.add(c);
    }

    public void setParp(int parp) {
        Parp = parp;
    }

    public void setDefineDp(boolean defineDp) {
        this.defineDp = defineDp;
    }

    public void setDefineParp(boolean defineParp) {
        this.defineParp = defineParp;
    }

    public void setdList(int d) {
        dList.add(d);
    }

    public void setChildList(boolean childList) {
        ChildList = childList;
    }

    public void setFp(int fp) {
        Fp = fp;
    }

    public void setRootp(boolean rootp) {
        this.rootp = rootp;
    }

    public void setLeafp(boolean leafp) {
        this.leafp = leafp;
    }

    public void setMp(int mp) {
        Mp = mp;
    }

    public void setNMp(int nMp) {
        NMp = nMp;
    }

    public void setMarkedp(boolean markedp) {
        Markedp = markedp;
    }

    public void setExistp(boolean existp) {
        Existp = existp;
    }

    public void setParentp(int p) {
        Parentp.add(p);
    }

    public void setChildrenp(int q) {
        Childrenp.add(q);
    }

    public void setSp1(int sp1) {
        this.sp1 = sp1;
    }

    public void setSp2(int sp2) {
        this.sp2 = sp2;
    }

    public void settPp(int tPp) {
        this.tPp = tPp;
    }

    public void settCp(int tCp) {
        this.tCp = tCp;
    }

    public void settChildp(int tc) {
        tChildp.add(tc);
    }

    public void settChildList(boolean tChildList) {
        this.tChildList = tChildList;
    }

    public void settLp(int tLp) {
        this.tLp = tLp;
    }

    public void settOnPp(boolean tOnPp) {
        this.tOnPp = tOnPp;
    }

    public void settRp(int tRp) {
        this.tRp = tRp;
    }

    public void settParp(int tParp) {
        this.tParp = tParp;
    }

    public void settdefineParp(boolean tdefineParp) {
        this.tdefineParp = tdefineParp;
    }

    public void settdList(int tdl) {
        tdList.add(tdl);
    }

    public void settdp(int tdp) {
        this.tdp = tdp;
    }

    public void settdefineDp(boolean tdefineDp) {
        this.tdefineDp = tdefineDp;
    }

    public void settrootp(boolean trootp) {
        this.trootp = trootp;
    }

    public void settleafp(boolean tleafp) {
        this.tleafp = tleafp;
    }

    public void settFp(int tFp) {
        this.tFp = tFp;
    }

    public void settPotentialPp(int tpp) {
        tPotentialPp.add(tpp);
    }

    public void settMp(int tMp) {
        this.tMp = tMp;
    }

    public void settNMp(int tNMp) {
        this.tNMp = tNMp;
    }

    public void settExistp(boolean tExistp) {
        this.tExistp = tExistp;
    }

    public void settMarkedp(boolean tMarkedp) {
        this.tMarkedp = tMarkedp;
    }

    public void settsp1(int tsp1) {
        this.tsp1 = tsp1;
    }

    public void settsp2(int tsp2) {
        this.tsp2 = tsp2;
    }

    public void setP1SuccID(int i, int p1SuccID) {
        P1SuccID.add(i, p1SuccID);
    }

    public void setP2SuccID(int j, int p2SuccID) {
        P2SuccID.add(j, p2SuccID);
    }

    public void setToP1CandID(int toP1CandID) {
        this.toP1CandID = toP1CandID;
    }

    public void setToP2CandID(int toP2CandID) {
        this.toP2CandID = toP2CandID;
    }

    public void setMvar(int mvar) {
        this.mvar = mvar;
    }

    public void setMvar1(int mvar1) {
        this.mvar1 = mvar1;
    }

    public void setMvar2(int mvar2) {
        this.mvar2 = mvar2;
    }

    public void setRvar(int rvar) {
        this.rvar = rvar;
    }

    public void setToP1ID(int toP1ID) {
        this.toP1ID = toP1ID;
    }

    public void setToP2ID(int toP2ID) {
        this.toP2ID = toP2ID;
    }

    public void setNG(int nG) {
        NG = nG;
    }


    public void setdagsuccid(int dagsuccid) {
        DAGSuccID.add(dagsuccid);
    }

    public void setDAGSuccID(int i, int dagsuccid) {
        DAGSuccID.set(i, dagsuccid);
    }

    public void setDAG(int dAG) {
        DAG = dAG;
    }

    // remove
    public void removePotentialPp() {
        PotentialPp.clear();
    }

    public void removeChildp() {
        Childp.clear();
    }

    public void removetPotentialPp() {
        tPotentialPp.clear();
    }

    public void removetChildp() {
        tChildp.clear();
    }
}
