package actionlisteners;

import logic.*;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetVectorListener implements ActionListener {
    private LinAlg linalg;
    private JTextField x;
    private JTextField y;

    public SetVectorListener(LinAlg linalg, JTextField x, JTextField y){
        this.linalg = linalg;
        this.x = x;
        this.y = y;
    }

    public void actionPerformed(ActionEvent ae){
        if (linalg.getGoing()){
            return;
        }
        if (linalg.getMatrix().getEntry(0, 0) != 1 ||
                linalg.getMatrix().getEntry(0, 1) != 0 ||
                linalg.getMatrix().getEntry(1, 0) != 0 ||
                linalg.getMatrix().getEntry(1, 1) != 1){
            return;
        }

        double vectorX = 1;
        double vectorY = 1;

        try{
            vectorX = 100 * Double.parseDouble(x.getText());
            vectorY = 100 * Double.parseDouble(y.getText());
        }
        catch (Exception e){
            // do nothing
        }

        VectorColor color;

        switch(linalg.getColorCounter() % 3){
            case 1:
                color = VectorColor.MAGENTA;
                break;
            case 2:
                color = VectorColor.GREEN;
                break;
            default:
                color = VectorColor.YELLOW;
                break;
        }

        double[] data = {vectorX, vectorY};
        RealVector vec1 = MatrixUtils.createRealVector(data);
        RealVector vec2 = MatrixUtils.createRealVector(data);

        ColoredVector vec1Colored = new ColoredVector(vec1, color);
        ColoredVector vec2Colored = new ColoredVector(vec2, color);

        linalg.setColorCounter();

        linalg.getVectors().put(vec1Colored, vec2Colored);
    }
}