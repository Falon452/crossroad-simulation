package com.falon.crossroad.presentation.ui;

import com.falon.crossroad.CrossroadProgram;
import com.falon.crossroad.domain.model.ActionCommandType;
import com.falon.crossroad.presentation.viewmodel.CrossroadViewModel;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private JSlider trafficEastSlider;
    private JSlider trafficSouthSlider;
    private JSlider trafficWestSlider;
    private JSlider trafficNorthSlider;

    public CrossroadPanel(JFrame jf) {
        frame = jf;
        timer = new Timer(INIT_DELAY, this);
        timer.stop();
    }

    public void initialize(Container container) {
        container.setLayout(new BorderLayout());
        container.setSize(new Dimension(CrossroadProgram.WIDTH, CrossroadProgram.HEIGHT));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 20, 10)); // Changed layout to GridLayout with 2 rows and 1 column

        // First row panel for the start button and main slider
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        start = createStyledButton();
        start.setActionCommand(ActionCommandType.TOGGLE_START.toString());
        start.addActionListener(this);

        slider = createStyledSlider();
        slider.setMinimum(0);
        slider.setMaximum(maxDelay);
        slider.setValue(maxDelay - timer.getDelay());

        controlPanel.add(start);
        controlPanel.add(slider);

        // Second row panel for traffic sliders
        JPanel trafficSliderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 4));

        trafficEastSlider = createStyledSlider();
        trafficSouthSlider = createStyledSlider();
        trafficWestSlider = createStyledSlider();
        trafficNorthSlider = createStyledSlider();

        trafficSliderPanel.add(new JLabel("Traffic East"));
        trafficSliderPanel.add(trafficEastSlider);
        trafficSliderPanel.add(new JLabel("Traffic South"));
        trafficSliderPanel.add(trafficSouthSlider);
        trafficSliderPanel.add(new JLabel("Traffic West"));
        trafficSliderPanel.add(trafficWestSlider);
        trafficSliderPanel.add(new JLabel("Traffic North"));
        trafficSliderPanel.add(trafficNorthSlider);

        // Add both panels to the main buttonPanel
        buttonPanel.add(controlPanel);
        buttonPanel.add(trafficSliderPanel);

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
        ActionCommandType actionCommandType = ActionCommandType.from(command);
        switch (actionCommandType) {
            case TOGGLE_START -> {
                if (!running) {
                    timer.start();
                    start.setText("Pause");
                } else {
                    timer.stop();
                    start.setText("Start");
                }
                running = !running;
            }
            case UNKNOWN -> {}
        }
    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == slider) {
            timer.setDelay(maxDelay - slider.getValue());
        } else if (e.getSource() == trafficEastSlider) {
            crossroadViewModel.onTrafficEastSliderChange(trafficEastSlider.getValue());
        } else if (e.getSource() == trafficSouthSlider) {
            crossroadViewModel.onTrafficSouthSliderChange(trafficSouthSlider.getValue());
        } else if (e.getSource() == trafficWestSlider) {
            crossroadViewModel.onTrafficWestSliderChange(trafficWestSlider.getValue());
        } else if (e.getSource() == trafficNorthSlider) {
            crossroadViewModel.onTrafficNorthSliderChange(trafficNorthSlider.getValue());
        }
    }

    private JButton createStyledButton() {
        RoundedButton button = new RoundedButton("Start");
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }

    private JSlider createStyledSlider() {
        JSlider slider = new JSlider();
        slider.addChangeListener(this);
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
