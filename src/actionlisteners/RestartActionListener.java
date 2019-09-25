package actionlisteners;

import logic.LinAlg;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RestartActionListener implements ActionListener {
    private LinAlg linalg;

    public RestartActionListener(LinAlg linalg){
        this.linalg = linalg;
    }

    public void actionPerformed(ActionEvent ae){
        linalg.changeRestart(true);
    }
}