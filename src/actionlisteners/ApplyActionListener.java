package actionlisteners;

import logic.LinAlg;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplyActionListener implements ActionListener {
    private LinAlg linalg;
    private JTextField a;
    private JTextField b;
    private JTextField c;
    private JTextField d;

    public ApplyActionListener(LinAlg linalg, JTextField a, JTextField b, JTextField c,
                        JTextField d){
        this.linalg = linalg;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public void actionPerformed(ActionEvent ae){
        double aValue = 1;
        double bValue = 0;
        double cValue = 0;
        double dValue = 1;
        try {
            aValue = Double.parseDouble(a.getText());
            bValue = Double.parseDouble(b.getText());
            cValue = Double.parseDouble(c.getText());
            dValue = Double.parseDouble(d.getText());
        }
        catch (Exception e){
            //do nothing
        }

        linalg.setNextMatrix(aValue, bValue, cValue, dValue);
        linalg.setGoing(true);
    }
}