//import java.util.*;
import java.util.ArrayList;

public class Node {
    private int id; // ノード番号
    private ArrayList<Integer> list; // 隣接リスト
    private boolean s1p; // ノードpがs1であるか
    private boolean s2p; // ノードpがs2であるか
    private boolean t1p; // ノードpがt1であるか
    private boolean t2p; // ノードpがt2であるか
    private int Pp; // step1.1のBFS木におけるノードpの親ID
    private ArrayList<Integer> Childp; //step1.2以降のBFS木におけるノードpの子リスト
    private boolean ChildOnPp; //Childpが最短経路P上に存在するか
    private int Lp; // ノードpから始点sまでの距離(L距離)
    private boolean OnPp; // ノードpが最短経路P上に存在するか
    private int Rp; // ノードpを含んでいる木における根のL距離
    private int Parp; // step1.2以降のBFS木におけるノードpの親ID
    private int dp; // ノードpからpを含む木における根までの距離
    private int Fp; // 自身を根とする木における葉ノードのLpの最大値??
    private ArrayList<Integer> PotentialPp; // ノードpの親となる可能性のあるノードID
    private int Mp; // 探索中のリンクパスPiの終点のL距離
    private int NMp; // 次に探索するリンクパスPi+1の終点のL距離
    private boolean Existp; // ノードsp間に２本の点素パスが存在するか
    private boolean Markedp; // ノードpがリンクパスの終点であるか
    private int PredPp; //最短経路P上のノードpの親ID
    private int SucPp; //最短経路P上のノードpの子ID
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
    }
    
    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(int a){
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

    public int getdp() {
        return dp;
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

    public int NMp() {
        return NMp;
    }

    public boolean getExistp() {
        return Existp;
    }

    public boolean getMarkedp() {
        return Markedp;
    }

    public int getPredPp() {
        return PredPp;
    }

    public int getSucPp() {
        return SucPp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ArrayList<Integer> getChildp() {
        return Childp;
    }

    public void setLp(int lp) {
        this.Lp = lp;
    }

    public void setPp(int pp) {
        Pp = pp;
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

    public void removePotentialPp() {
        PotentialPp.clear();
    }
}
