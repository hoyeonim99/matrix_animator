package actionlisteners;

import logic.LinAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClearVectorsListener implements ActionListener {
    private LinAlg linalg;

    public ClearVectorsListener(LinAlg linalg){
        this.linalg = linalg;
    }

    public void actionPerformed(ActionEvent ae){
        linalg.clearVectors();
        linalg.resetColorCounter();
    }
}