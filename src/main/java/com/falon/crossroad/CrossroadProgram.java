package com.falon.crossroad;

import com.falon.crossroad.ui.CrossroadPanel;

import javax.swing.*;

public class CrossroadProgram extends JFrame {

    public static final int WIDTH = 1220;
    public static final int HEIGHT = 1280;
    public static final String TITLE = "Crossroad Simulation";

    public CrossroadProgram() {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CrossroadPanel crossroadPanel = new CrossroadPanel(this);
        crossroadPanel.initialize(this.getContentPane());
        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);
        this.setResizable(false);
    }

    public static void main(String[] args) {
        new CrossroadProgram();
    }
}
