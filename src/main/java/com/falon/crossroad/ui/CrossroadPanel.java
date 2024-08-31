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

public class CrossroadPanel extends JPanel implements ActionListener, ChangeListener {

    private final Timer timer;
    private final CrossroadViewModel crossroadViewModel = new CrossroadViewModel();
    private CrossroadView crossroadView;
    private JButton start;
    private JButton clear;
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

        start = new JButton(ActionCommand.TOGGLE_START.toString());
        start.setActionCommand(ActionCommand.TOGGLE_START.toString());
        start.addActionListener(this);

        clear = new JButton(ActionCommand.CLEAR.toString());
        clear.setActionCommand(ActionCommand.CLEAR.toString());
        clear.addActionListener(this);

        slider = new JSlider();
        slider.setMinimum(0);
        slider.setMaximum(maxDelay);
        slider.addChangeListener(this);
        slider.setValue(maxDelay - timer.getDelay());

        buttonPanel.add(start);
        buttonPanel.add(clear);
        buttonPanel.add(slider);

        crossroadView = new CrossroadView(crossroadViewModel);
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
        switch (actionCommand) {
            case TOGGLE_START -> {
                if (!running) {
                    timer.start();
                    start.setText("Pause");
                } else {
                    timer.stop();
                    start.setText("Start");
                }
                running = !running;
                clear.setEnabled(true);
            }
            case CLEAR -> {
                iteration = 0;
                timer.stop();
                start.setEnabled(true);
                crossroadViewModel.onClear();
            }
        }
    }

    public void stateChanged(ChangeEvent e) {
        timer.setDelay(maxDelay - slider.getValue());
    }
}
