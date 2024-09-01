package com.falon.crossroad.ui;

import com.falon.crossroad.CrossroadProgram;
import com.falon.crossroad.model.ActionCommand;
import com.falon.crossroad.viewmodel.CrossroadViewModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CrossroadPanel extends JPanel implements ActionListener, ChangeListener {

    private final Timer timer;
    private final CrossroadViewModel crossroadViewModel = new CrossroadViewModel();
    private JButton start;
    private JSlider slider;
    private final JFrame frame;
    private int iteration = 0;
    private final int maxDelay = 500;
    private static final int INIT_DELAY = 100;
    private boolean running = false;

    public CrossroadPanel(JFrame jf) {
        frame = jf;
        timer = new Timer(INIT_DELAY, this);
        timer.stop();
    }

    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(CrossroadProgram.WIDTH, CrossroadProgram.HEIGHT));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        start = createStyledButton("Start");
        start.setActionCommand(ActionCommand.TOGGLE_START.toString());
        start.addActionListener(this);

        slider = createStyledSlider();
        slider.setMinimum(0);
        slider.setMaximum(maxDelay);
        slider.addChangeListener(this);
        slider.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(start);
        buttonPanel.add(slider);

        CrossroadView crossroadView = new CrossroadView(crossroadViewModel);
        container.add(crossroadView, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(timer)) {
            iterate();
        } else {
            performActionCommand(e);
        }
    }

    private void iterate() {
        iteration++;
        frame.setTitle(CrossroadProgram.TITLE + " (" + iteration + " iteration)");
        crossroadViewModel.onIterate();
    }

    private void performActionCommand(ActionEvent e) {
        String command = e.getActionCommand();
        ActionCommand actionCommand = ActionCommand.from(command);
        if (actionCommand == ActionCommand.TOGGLE_START) {
            if (!running) {
                timer.start();
                start.setText("Pause");
            } else {
                timer.stop();
                start.setText("Start");
            }
            running = !running;
        }
    }

    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - slider.getValue());
    }

    private JButton createStyledButton(String text) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private JSlider createStyledSlider() {
        JSlider slider = new JSlider();
        slider.setUI(new javax.swing.plaf.metal.MetalSliderUI() {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(70, 130, 180));
                g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }
            @Override
            public void paintTrack(Graphics g) {
                g.setColor(new Color(200, 200, 200));
                g.fillRect(trackRect.x, trackRect.y + (trackRect.height / 2) - 2, trackRect.width, 4);
            }
        });
        return slider;
    }
}
