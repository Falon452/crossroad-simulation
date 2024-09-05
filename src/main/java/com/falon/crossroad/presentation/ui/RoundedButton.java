package com.falon.crossroad.presentation.ui;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {

    private static final int ARC_WIDTH = 20;
    private static final int ARC_HEIGHT = 20;

    public RoundedButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2d.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2d.setColor(getBackground().brighter());
        } else {
            g2d.setColor(getBackground());
        }

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), ARC_WIDTH, ARC_HEIGHT);

        g2d.setColor(getForeground());
        FontMetrics fm = g2d.getFontMetrics();
        String text = getText();
        int textX = (getWidth() - fm.stringWidth(text)) / 2;
        int textY = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, textX, textY);

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
    }
}
