package gui;

import logic.LinAlg;

import javax.swing.*;
import java.awt.*;

public class UserInterface implements Runnable {
    private LinAlg linalg;

    public UserInterface(LinAlg linalg) {
        this.linalg = linalg;
    }

    public void run() {
        JFrame inputFrame; //inputting matrix, reset, and invert buttons
        JFrame displayFrame; //animation window

        displayFrame = new JFrame("Linear Transformations");

        displayFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        displayFrame.setPreferredSize(new Dimension(1000, 1000));

        this.createDisplayComponents(displayFrame.getContentPane());

        displayFrame.pack();
        displayFrame.setVisible(true);

        inputFrame = new JFrame("Control Panel");

        inputFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        inputFrame.setPreferredSize(new Dimension(670, 280));

        this.createInputComponents(inputFrame.getContentPane());

        inputFrame.pack();
        inputFrame.setVisible(true);
    }

    //for display frame
    private void createDisplayComponents(Container container) {
        DrawingBoard db = new DrawingBoard(linalg);
        container.add(db);
    }

    //for the input frame's buttons and textfields
    private void createInputComponents(Container container) {
        container.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10);
        container.add(new VectorPane(linalg), gbc);
        gbc.gridx++;
        container.add(new MatrixPane(linalg), gbc);
    }
}