package UI.Custom.Button;

import javax.swing.*;
import java.awt.event.ActionListener;

public class FinishGameButton extends JButton {

    public FinishGameButton(final ActionListener actionListener) {
        this.setText("Finalizar Jogo");
        this.addActionListener(actionListener);
    }
}
