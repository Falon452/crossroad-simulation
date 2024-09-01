package com.falon.crossroad.ui;

import com.falon.crossroad.viewstate.CellItem;
import com.falon.crossroad.viewmodel.CrossroadViewModel;
import com.falon.crossroad.viewstate.CrossroadViewState;
import io.reactivex.rxjava3.disposables.Disposable;

import javax.swing.*;
import java.awt.*;

public class CrossroadView extends JComponent {

    private final static int CELL_SIZE = 20;
    private final CrossroadViewModel crossroadViewModel;
    private Disposable viewStateDisposable;

    private CrossroadViewState viewState;


    public CrossroadView(CrossroadViewModel crossroadViewModel) {
        this.crossroadViewModel = crossroadViewModel;
        setBackground(new Color(220, 238, 251));
        setOpaque(true);
    }

    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g);
    }

    private void drawNetting(Graphics g) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += CELL_SIZE;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += CELL_SIZE;
        }
        if (viewState != null) {
            CellItem[][] cellItems = viewState.cellItems;
            for (x = 0; x < cellItems.length; ++x) {
                for (y = 0; y < cellItems[x].length; ++y) {
                    CellItem cellItem = cellItems[x][y];
                    g.setColor(cellItem.squareColor);
                    g.fillRect((x * CELL_SIZE) + 1, (y * CELL_SIZE) + 1, (CELL_SIZE - 1), (CELL_SIZE - 1));

                    g.setColor(cellItem.circleColor);
                    int circleDiameter = CELL_SIZE / 2;
                    int circleX = (x * CELL_SIZE) + (CELL_SIZE - circleDiameter) / 2;
                    int circleY = (y * CELL_SIZE) + (CELL_SIZE - circleDiameter) / 2;
                    g.fillOval(circleX, circleY, circleDiameter, circleDiameter);
                }
            }
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        if (viewStateDisposable == null || viewStateDisposable.isDisposed()) {
            viewStateDisposable = crossroadViewModel.viewStateObservable().subscribe(items -> {
                this.viewState = items;
                repaint();
            });
        }
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        if (viewStateDisposable != null && !viewStateDisposable.isDisposed()) {
            viewStateDisposable.dispose();
        }
    }
}
