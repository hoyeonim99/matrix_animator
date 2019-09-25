import gui.UserInterface;
import logic.LinAlg;

import javax.swing.*;

public class Main {
    public static void main(String[] args){

        LinAlg linalg = new LinAlg();
        linalg.setMatrix(1, 0, 0, 1);

        UserInterface ui = new UserInterface(linalg);
        SwingUtilities.invokeLater(ui);
    }
}
