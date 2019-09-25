package gui;

import logic.ColoredVector;
import logic.Grid;
import logic.LinAlg;
import logic.VectorColor;
import org.apache.commons.math3.linear.RealVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

class DrawingBoard extends JPanel implements ActionListener {
    private Timer timer;
    private LinAlg linalg;

    DrawingBoard(LinAlg linalg){
        this.timer = new Timer(15, this);
        this.linalg = linalg;
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        //these next two blocks are for drawing the stationary background grid. The x & y
        // axes will be white.
        for (int i = 0; i <= 1000; i += 100){
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(Color.GRAY);
            if (i == 500){
                g2d.setColor(Color.WHITE);
            }
            g2d.drawLine(i, 0, i, 1000);
        }

        for (int i = 0; i <= 1000; i += 100){
            if (i == 500){
                g2d.setColor(Color.WHITE);
            }
            g2d.drawLine(0, i, 1000, i);
        }

        //this block is to draw the grid as it is transforming
        for (Grid grid : linalg.getIntermGrid()){
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));
            g2d.setColor(Color.CYAN);
            if (grid.getHighlight()){
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND));
                g2d.setColor(Color.WHITE);
            }
            RealVector vec1 = grid.getVector1();
            RealVector vec2 = grid.getVector2();
            g2d.drawLine((int) (500 + vec1.getEntry(0)),
                    (int) (500 - vec1.getEntry(1)),
                    (int) (500 + vec2.getEntry(0)),
                    (int) (500 - vec2.getEntry(1)));
        }

        //this block is for drawing the ColorVectors according to their member variables
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND));
        for (ColoredVector vec : linalg.getVectors().keySet()){
            if (linalg.getVectors().get(vec).getColor() == VectorColor.YELLOW){
                g2d.setColor(Color.YELLOW);
            }
            else if (linalg.getVectors().get(vec).getColor() == VectorColor.MAGENTA){
                g2d.setColor(Color.MAGENTA);
            }
            else {
                g2d.setColor(Color.GREEN);
            }
            drawArrow(g2d,
                    (int) (500 + linalg.getVectors().get(vec).getVector().getEntry(0)),
                    (int)
                            (500 - linalg.getVectors().get(vec).getVector().getEntry(1)));
        }

        timer.start();
    }

    public void actionPerformed(ActionEvent ae) {
        //each timer event, this will check whether the animation is turned on. If so,
        // it will play the animation by transformation the vectors/grid with intermMatrix
        if (linalg.getGoing()) {
            linalg.transform2();
        }

        //by accessing the restart boolean, this will either do nothing or call the
        // reset method to reset the state of the vectors/grid
        if (linalg.getRestart()){
            linalg.reset2();
            linalg.changeRestart(false);
        }

        repaint();
    }

    private static void drawArrow(Graphics g1, int x2, int y2) {
        Graphics2D g = (Graphics2D) g1.create();

        int arrowSize = 10;
        double dx = x2 - 500, dy = y2 - 500;
        double theta = Math.atan2(dy, dx); //Cartesian to polar
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        if (Math.abs(len) <= 5){
            arrowSize = 0;
        }
        AffineTransform at = AffineTransform.getTranslateInstance(500, 500);
        at.concatenate(AffineTransform.getRotateInstance(theta));
        g.transform(at);

        g.drawLine(0, 0, len - 5, 0);
        g.fillPolygon(new int[] {len, len - arrowSize, len- arrowSize},
                new int[] {0, -arrowSize, arrowSize}, 3);
    }
}