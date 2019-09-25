package gui;

import actionlisteners.ApplyActionListener;
import actionlisteners.InverseListener;
import actionlisteners.RestartActionListener;
import logic.LinAlg;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

class MatrixPane extends JPanel {
    private LinAlg linalg;
    private JTextField a = new JTextField(4);
    private  JTextField b = new JTextField(4);
    private  JTextField c = new JTextField(4);
    private  JTextField d = new JTextField(4);

    MatrixPane(LinAlg linalg){
        this.linalg = linalg;

        Font f = new Font("Helvetica", Font.BOLD, 48);
        a.setFont(f);
        b.setFont(f);
        c.setFont(f);
        d.setFont(f);

        setLayout(new GridBagLayout());
        setBorder(new CompoundBorder(new TitledBorder("Matrix"), new EmptyBorder(20, 20,
         20, 20)));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        add(row1Panel(), gbc);
        gbc.gridy++;
        add(row2Panel(), gbc);
        gbc.gridy++;
        add(matrixButtons(), gbc);
    }

    private JPanel row1Panel(){
        JPanel row1 = new JPanel();
        row1.setLayout(new GridLayout(1, 2));
        row1.add(a);
        row1.add(b);

        return row1;
    }

    private JPanel row2Panel(){
        JPanel row2 = new JPanel();
        row2.setLayout(new GridLayout(1, 2));
        row2.add(c);
        row2.add(d);

        return row2;
    }

    private JPanel matrixButtons(){
        JPanel matrixButtons = new JPanel();
        matrixButtons.setLayout(new GridLayout(1, 3));

        JButton apply = new JButton("Apply");
        apply.addActionListener(new ApplyActionListener(linalg, a, b, c, d));

        JButton restart = new JButton("Restart");
        restart.addActionListener(new RestartActionListener(linalg));

        JButton invert = new JButton("Invert");
        invert.addActionListener(new InverseListener(linalg, a, b, c, d));

        matrixButtons.add(apply);
        matrixButtons.add(invert);
        matrixButtons.add(restart);

        return matrixButtons;
    }
}