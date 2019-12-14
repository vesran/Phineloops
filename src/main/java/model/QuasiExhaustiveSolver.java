package model;

import java.util.Deque;
import java.util.LinkedList;

public class QuasiExhaustiveSolver {

    private Level m_level;

    public QuasiExhaustiveSolver(Level lvl) {
        this.read(lvl);
    }

    public void read(Level lvl) {
        this.m_level = lvl;
    }

    public void solve() {
        Deque<Piece> stack = new LinkedList<>();


    }

    public static void main(String [] args) {

    }
}
