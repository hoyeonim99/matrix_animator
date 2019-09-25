package gui;

import actionlisteners.*;
import logic.LinAlg;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

class VectorPane extends JPanel {
    private LinAlg linalg;
    private JTextField x = new JTextField(2);
    private JTextField y = new JTextField(2);

    VectorPane(LinAlg linalg){
        this.linalg = linalg;

        Font f = new Font("Helvetica", Font.BOLD, 48);
        x.setFont(f);
        y.setFont(f);

        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Vector"), new EmptyBorder(20, 20,
                20, 20)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(x, gbc);
        gbc.gridy++;
        add(y, gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(vetorButtons(), gbc);
    }

    private JPanel vetorButtons(){
        JPanel vectorButtons = new JPanel();
        vectorButtons.setLayout(new GridLayout(1, 4));

        JButton setVector = new JButton("Add");
        setVector.addActionListener(new SetVectorListener(linalg, x, y));

        JButton clearVectors = new JButton("Clear All");
        clearVectors.addActionListener(new ClearVectorsListener(linalg));

        vectorButtons.add(setVector);
        vectorButtons.add(clearVectors);

        return vectorButtons;
    }
}